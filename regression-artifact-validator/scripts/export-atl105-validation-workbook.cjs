const fs = require('node:fs');
const path = require('node:path');
const zlib = require('node:zlib');
const crypto = require('node:crypto');

const REVIEW = 'REVIEW_REQUIRED';
const asArray = value => value == null ? [] : Array.isArray(value) ? value : [value];
const compact = value => value == null ? '' : typeof value === 'object' ? JSON.stringify(value) : String(value);
const xml = value => compact(value).replace(/[\u0000-\u0008\u000b\u000c\u000e-\u001f]/g, '')
  .replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
const assert = (condition, message) => { if (!condition) throw new Error(message); };

function column(index) {
  let result = '';
  for (let n = index + 1; n; n = Math.floor((n - 1) / 26)) result = String.fromCharCode(65 + (n - 1) % 26) + result;
  return result;
}

function worksheet(headers, rows) {
  assert(rows.length < 1048576, 'Worksheet exceeds Excel row limit');
  const last = `${column(headers.length - 1)}${rows.length + 1}`;
  const rowXml = (values, index) => {
    assert(values.length === headers.length, `Wrong cell count at row ${index + 1}`);
    return `<row r="${index + 1}"${index === 0 ? ' ht="30" customHeight="1"' : ''}>${values.map((v, c) => {
      assert(compact(v).length <= 32767, `Excel cell limit exceeded at ${column(c)}${index + 1}`);
      return `<c r="${column(c)}${index + 1}" t="inlineStr" s="${index === 0 ? 1 : 0}"><is><t xml:space="preserve">${xml(v)}</t></is></c>`;
    }).join('')}</row>`;
  };
  return `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<worksheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main"><dimension ref="A1:${last}"/>
<sheetViews><sheetView workbookViewId="0"><pane ySplit="1" topLeftCell="A2" activePane="bottomLeft" state="frozen"/></sheetView></sheetViews>
<sheetFormatPr defaultRowHeight="30"/><cols>${headers.map((h, c) => `<col min="${c + 1}" max="${c + 1}" width="${/evidence|description|condition|rule|source text|instruction|purpose|values/i.test(h) ? 64 : 25}" customWidth="1"/>`).join('')}</cols>
<sheetData>${rowXml(headers, 0)}${rows.map((r, i) => rowXml(r, i + 1)).join('')}</sheetData><autoFilter ref="A1:${last}"/></worksheet>`;
}

// A small dependency-free ZIP writer keeps the export runnable without Excel or npm packages.
const crcTable = Array.from({ length: 256 }, (_, n) => {
  for (let k = 0; k < 8; k++) n = n & 1 ? 0xedb88320 ^ (n >>> 1) : n >>> 1;
  return n >>> 0;
});
function crc32(buffer) {
  let crc = 0xffffffff;
  for (const b of buffer) crc = crcTable[(crc ^ b) & 255] ^ (crc >>> 8);
  return (crc ^ 0xffffffff) >>> 0;
}
function writeZip(filename, entries) {
  const locals = [], central = [];
  let offset = 0;
  for (const [name, contents] of entries) {
    const raw = Buffer.from(contents, 'utf8'), data = zlib.deflateRawSync(raw), file = Buffer.from(name);
    const crc = crc32(raw);
    const local = Buffer.alloc(30);
    local.writeUInt32LE(0x04034b50); local.writeUInt16LE(20, 4); local.writeUInt16LE(8, 8);
    local.writeUInt16LE(33, 12); local.writeUInt32LE(crc, 14);
    local.writeUInt32LE(data.length, 18); local.writeUInt32LE(raw.length, 22); local.writeUInt16LE(file.length, 26);
    locals.push(local, file, data);
    const dir = Buffer.alloc(46);
    dir.writeUInt32LE(0x02014b50); dir.writeUInt16LE(20, 4); dir.writeUInt16LE(20, 6);
    dir.writeUInt16LE(8, 10); dir.writeUInt16LE(33, 14); dir.writeUInt32LE(crc, 16);
    dir.writeUInt32LE(data.length, 20); dir.writeUInt32LE(raw.length, 24); dir.writeUInt16LE(file.length, 28);
    dir.writeUInt32LE(offset, 42); central.push(dir, file);
    offset += local.length + file.length + data.length;
  }
  const directory = Buffer.concat(central), end = Buffer.alloc(22);
  end.writeUInt32LE(0x06054b50); end.writeUInt16LE(entries.length, 8); end.writeUInt16LE(entries.length, 10);
  end.writeUInt32LE(directory.length, 12); end.writeUInt32LE(offset, 16);
  fs.writeFileSync(filename, Buffer.concat([...locals, directory, end]));
}

function saveWorkbook(filename, sheets) {
  const ns = 'http://schemas.openxmlformats.org';
  const entries = [
    ['[Content_Types].xml', `<?xml version="1.0"?><Types xmlns="${ns}/package/2006/content-types"><Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/><Default Extension="xml" ContentType="application/xml"/><Override PartName="/xl/workbook.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml"/><Override PartName="/xl/styles.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml"/>${sheets.map((s, i) => `<Override PartName="/xl/worksheets/sheet${i + 1}.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml"/>`).join('')}</Types>`],
    ['_rels/.rels', `<?xml version="1.0"?><Relationships xmlns="${ns}/package/2006/relationships"><Relationship Id="rId1" Type="${ns}/officeDocument/2006/relationships/officeDocument" Target="xl/workbook.xml"/></Relationships>`],
    ['xl/workbook.xml', `<?xml version="1.0"?><workbook xmlns="${ns}/spreadsheetml/2006/main" xmlns:r="${ns}/officeDocument/2006/relationships"><bookViews><workbookView/></bookViews><sheets>${sheets.map((s, i) => `<sheet name="${xml(s.name)}" sheetId="${i + 1}" r:id="rId${i + 1}"/>`).join('')}</sheets></workbook>`],
    ['xl/_rels/workbook.xml.rels', `<?xml version="1.0"?><Relationships xmlns="${ns}/package/2006/relationships">${sheets.map((s, i) => `<Relationship Id="rId${i + 1}" Type="${ns}/officeDocument/2006/relationships/worksheet" Target="worksheets/sheet${i + 1}.xml"/>`).join('')}<Relationship Id="styles" Type="${ns}/officeDocument/2006/relationships/styles" Target="styles.xml"/></Relationships>`],
    ['xl/styles.xml', `<?xml version="1.0"?><styleSheet xmlns="${ns}/spreadsheetml/2006/main"><fonts count="2"><font><sz val="11"/><name val="Calibri"/></font><font><b/><color rgb="FFFFFFFF"/><sz val="11"/><name val="Calibri"/></font></fonts><fills count="3"><fill><patternFill patternType="none"/></fill><fill><patternFill patternType="gray125"/></fill><fill><patternFill patternType="solid"><fgColor rgb="FF17365D"/><bgColor indexed="64"/></patternFill></fill></fills><borders count="1"><border/></borders><cellStyleXfs count="1"><xf numFmtId="0" fontId="0" fillId="0" borderId="0"/></cellStyleXfs><cellXfs count="2"><xf numFmtId="49" fontId="0" fillId="0" borderId="0" xfId="0" applyNumberFormat="1" applyAlignment="1"><alignment vertical="top" wrapText="1"/></xf><xf numFmtId="49" fontId="1" fillId="2" borderId="0" xfId="0" applyFont="1" applyFill="1" applyAlignment="1"><alignment vertical="center" wrapText="1"/></xf></cellXfs><cellStyles count="1"><cellStyle name="Normal" xfId="0" builtinId="0"/></cellStyles></styleSheet>`]
  ];
  for (const [i, sheet] of sheets.entries()) entries.push([`xl/worksheets/sheet${i + 1}.xml`, worksheet(sheet.headers, sheet.rows)]);
  fs.mkdirSync(path.dirname(filename), { recursive: true });
  const temporary = `${filename}.${process.pid}.tmp`;
  try {
    writeZip(temporary, entries);
    fs.renameSync(temporary, filename);
  } finally {
    if (fs.existsSync(temporary)) fs.unlinkSync(temporary);
  }
}

function sourceIndex(text) {
  const lines = text.split(/\r?\n/), records = [];
  let page = '', section = '', appendix = '';
  for (let i = 0; i < lines.length; i++) {
    const line = lines[i], pageMatch = line.match(/^--- PAGE (\d+) ---/);
    if (pageMatch) page = pageMatch[1];
    if (Number(page) >= 34) {
      const chapter = line.match(/^((?:1[0-4]|[1-9]))\.\s+([A-Za-z].*)/);
      if (chapter) { section = chapter[1]; appendix = ''; }
      const heading = line.match(/^((?:1[0-4]|[1-9])(?:\.\d+)+)\s+([A-Za-z].*)/);
      if (heading) section = heading[1];
      const app = line.match(/^Appendix ([A-Z]{1,2})\.\s+(.+)/);
      if (app) { appendix = app[1]; section = `Appendix ${appendix}`; }
    }
    records.push({ line: i + 1, page, section, appendix, text: line });
  }
  return records;
}

function parseSegments(records, elements) {
  const starts = records.filter(r => /^12\.\d+\s+/.test(r.text) && Number(r.page) >= 215 && Number(r.page) <= 334);
  assert(starts.length === 49, `Expected 49 Chapter 12 segment sections, got ${starts.length}`);
  return starts.map((start, i) => {
    const end = i + 1 < starts.length ? starts[i + 1].line - 1 : records.find(r => r.line > start.line && /^13\.\s/.test(r.text))?.line - 1;
    assert(end, 'Cannot find end of Chapter 12');
    const block = records.slice(start.line - 1, end);
    const raw = block.map(r => r.text).join('\n');
    const id = raw.match(/describes Data Segment No\.\s*(\d{3}|DL[1-8])/i)?.[1]
      || raw.match(/Data Segment No\.\s*(\d{3}|DL[1-8])/i)?.[1]
      || (start.section === '12.15' ? '116' : '');
    assert(id, `Missing segment number at ${start.section}`);
    const boundaries = [];
    for (let n = 0; n < block.length; n++) {
      const m = block[n].text.match(/^[ \t]*(\d+(?:[–-]\d+)?)\s+(\d{1,3})\s+(.+)/);
      if (m && elements[m[2]]) boundaries.push({ at: n, field: m[1], element: m[2] });
    }
    const fields = boundaries.map((b, n) => {
      const evidence = block.slice(b.at, n + 1 < boundaries.length ? boundaries[n + 1].at : block.length);
      const text = evidence.map(r => r.text).join('\n');
      const shape = text.match(/(?:^|\s)((?:\.\.)?\d[\d,]*(?:[–-]\d+)?|Var\.?|Variable|Fixed)\s+([ROC])(?:\d)?\s+/);
      const fixed = text.match(/Fixed [Vv]alue:\s*([^\n]+)/)?.[1]?.trim() || '';
      return { field: b.field, element: b.element, length: shape?.[1] || '', entry: shape?.[2] || '',
        fixed, line: evidence[0].line, page: evidence[0].page, section: evidence[0].section, text, extraction: 'SOURCE_ROW_EXTRACTED' };
    });
    if (id === 'DL2') {
      const inheritance = block.find(r => /This information is identical to the Primary Phone Number/.test(r.text));
      assert(inheritance, 'DL2 secondary phone inheritance evidence missing');
      for (let primary = 3; primary <= 6; primary++) {
        const original = fields.find(f => f.field === String(primary));
        assert(original, `DL2 primary field ${primary} missing`);
        fields.push({ ...original, field: String(primary + 5), line: inheritance.line, page: inheritance.page,
          section: inheritance.section, extraction: 'SOURCE_EXPLICIT_INHERITANCE',
          text: `Secondary phone field ${primary + 5} inherits primary field ${primary}; use the secondary phone number.\n${inheritance.text}\nPrimary field evidence:\n${original.text}` });
      }
      fields.sort((a, b) => Number(a.field) - Number(b.field));
    }
    return { id, section: start.section, name: start.text.replace(/^12\.\d+\s+/, '').trim(), line: start.line,
      page: start.page, end, raw, fields, external: id === '116' };
  });
}

function candidateSubsets(segments) {
  const optional = segments.filter(s => !['R', 'PRESENT'].includes(s.presence));
  assert(optional.length <= 19, 'Template subsets exceed safe workbook enumeration size');
  return Array.from({ length: 2 ** optional.length }, (_, mask) => segments.filter(s =>
    ['R', 'PRESENT'].includes(s.presence) || Boolean(mask & 2 ** optional.indexOf(s))));
}

function build(pack) {
  const inputs = new Map();
  function read(relative, json = true) {
    const filename = path.join(pack, ...relative.split('/')), buffer = fs.readFileSync(filename);
    inputs.set(relative, { bytes: buffer.length, hash: crypto.createHash('sha256').update(buffer).digest('hex') });
    const text = buffer.toString('utf8').replace(/^\uFEFF/, '');
    return json ? JSON.parse(text) : text;
  }
  const source = sourceIndex(read('docs/specs/extracted_text.txt', false));
  const inventory = read('test-output/test-solution-independent-review/all-element-inventory.json');
  const reference = read('test-output/test-solution-independent-review/atl105-all-elements-reference.json');
  const templates = read('docs/atl105_complete_templates.json').message_templates;
  const layoutIndex = read('docs/specs/kb/section-11-message-layout-index.json');
  const elements = inventory.elements;
  const segments = parseSegments(source, elements), sheets = [];
  const add = (name, headers, rows) => { sheets.push({ name, headers, rows }); return rows; };
  const info = add('START HERE', ['Topic', 'Instruction or finding'], []);
  info.push(
    ['Purpose', 'Source-derived ATL105 2026-3 reference for reviewing AI business requirements, scenarios, test cases and test data. This workbook does not execute or certify AI artifacts.'],
    ['Authority', 'PDF/extracted source is authoritative. Existing knowledge-base catalogs are secondary candidates, not blanket SME approvals. All unresolved semantics remain REVIEW_REQUIRED.'],
    ['Source', 'BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf; release August 07, 2026. PDF page numbers are physical page markers from extracted_text.txt.'],
    ['Elements', `${reference.elementCount} unique elements; ${reference.definitionCount} source definitions. Duplicate definitions and context-dependent widths are retained.`],
    ['Segment combinations', 'All subsets of C/O/unresolved segments within each represented template, retaining its R/PRESENT segments. These are structural candidates, NOT a list of valid transactions. Resolve triggers, dependencies, direction, card/transaction context and source conflicts before approval.'],
    ['Permutations', 'No arbitrary segment-order permutations: use source order/data sections. Repeating blocks/fields are modeled in evidence; repetition counts and every field-value cross-product are not enumerated.'],
    ['Prompt codes', 'Financial transaction x card combinations are syntactic candidates only, not proof of issuer/card support. Special 3-character prompt codes are listed separately; do not append them to financial transaction codes.'],
    ['R/O/C', 'R=required; O=optional; C=conditional. Unknown marker is not permission to omit. Template omission is not proof that a segment is forbidden.'],
    ['How to review AI output', 'Start with layout and transaction/card context, resolve Segment Subsets against Rules and source, inspect Segment Fields and Field Values, then link BR -> scenario -> case -> data using AI Review Register. Do not pass unresolved rules.'],
    ['Field extraction', 'Segment Fields is a source-row extraction with original evidence, not an approved parser schema. Repeated/ranged rows remain distinct. Blank parsed length or marker requires source review; Chapter 13 widths do not override context-specific layouts.'],
    ['Nested tables', 'Appendix I/K/T/V and other composite layouts are retained in Appendix Evidence with page/line ranges. Their nested subfields are source evidence, not fully normalized executable constraints.'],
    ['Data safety', 'All cells are text: leading zeros, literal formulas and punctuation are preserved. Use synthetic or approved masked test data only.'],
    ['Combination validity', 'Every subset, pair and transaction/card cross-product remains REVIEW_REQUIRED. Co-listed segments are not automatically compatible; unlisted pairs are not automatically forbidden.'],
    ['External dependencies', 'Segment 116 / TransArmor key-load details require an external specification not supplied locally. Such coverage is BLOCKED_EXTERNAL_SOURCE, not completed.']
  );
  const definitionRows = [], valueRows = [];
  for (const [id, e] of Object.entries(elements)) {
    for (const [n, d] of e.definitions.entries()) {
      const c = d.constraints || {}, evidence = d.sourceText || '';
      const valid = evidence.split(/Valid Codes\/Values:\s*/i).slice(1).join('Valid Codes/Values: ');
      const purpose = evidence.match(/Purpose:\s*([\s\S]*?)(?=Processing Rules:|Valid Codes\/Values:|$)/)?.[1]?.trim() || '';
      const rules = evidence.match(/Processing Rules:\s*([\s\S]*?)(?=Valid Codes\/Values:|$)/)?.[1]?.trim() || '';
      const page = source[d.sourceLine - 1]?.page;
      definitionRows.push([id, n + 1, d.name, c.characterType, c.lengthLabel, c.lengthMode, c.maxLength,
        c.representation, purpose, rules, valid, c.extractionStatus, d.sourceSection, page, d.sourceLine, REVIEW]);
      valueRows.push([id, n + 1, d.name, valid || 'NO CLOSED VALUE ENUMERATION EXTRACTED; inspect purpose, processing rules and external/appendix references.',
        c.representation, rules, d.sourceSection, page, d.sourceLine, REVIEW]);
    }
  }
  add('Elements', ['Element', 'Definition', 'Name', 'Character type', 'Length label', 'Length mode', 'Max bytes', 'Representation',
    'Purpose', 'Processing rules', 'Valid values evidence', 'Extraction status', 'Section', 'PDF page', 'Source line', 'Decision'], definitionRows);
  add('Field Values', ['Element', 'Definition', 'Name', 'Valid values evidence', 'Representation', 'Processing rules', 'Section', 'PDF page', 'Source line', 'Decision'], valueRows);
  add('Segments', ['Segment', 'Name', 'Section', 'PDF page', 'Source start line', 'Source end line', 'Extracted field rows', 'Decision'],
    segments.map(s => [s.id, s.name, s.section, s.page, s.line, s.end, s.fields.length, s.external ? 'BLOCKED_EXTERNAL_SOURCE' : REVIEW]));
  add('Segment Fields', ['Segment', 'Field context section', 'Field or range', 'Element', 'Element name', 'Parsed length', 'Parsed R O C', 'Fixed value evidence',
    'Chapter 13 types and widths', 'Field source evidence', 'PDF page', 'Source line', 'Extraction basis', 'Decision'],
    segments.flatMap(s => s.fields.map(f => [s.id, f.section, f.field, f.element, elements[f.element].definitions.map(d => d.name).join(' / '),
      f.length, f.entry, f.fixed, elements[f.element].definitions.map(d => compact(d.constraints)).join('\n'), f.text, f.page, f.line, f.extraction, REVIEW])));
  add('Field Coverage', ['Segment', 'Section', 'Extracted rows', 'Distinct field labels', 'Field labels with no parsed R O C', 'Review instruction'],
    segments.map(s => [s.id, s.section, s.fields.length, new Set(s.fields.map(f => f.field)).size,
      s.fields.filter(f => !f.entry).map(f => f.field).join(', '),
      s.external ? 'BLOCKED_EXTERNAL_SOURCE' : 'Repeated labels can be alternative prompt-specific layouts; nested fields remain in source/appendix evidence.']));
  const templateRows = [], templateFields = [], templateSegments = [], combos = [], conflicts = [], resolvedTemplates = [];
  for (const layout of layoutIndex.layouts) {
    const original = templates[layout.template_key];
    assert(original, `Missing template ${layout.template_key}`);
    const t = original.alias_of ? templates[original.alias_of] : original;
    assert(t, `Missing alias target ${original.alias_of}`);
    const rawSegments = Array.isArray(t.segments) ? t.segments : t.segments ? [t.segments] : asArray(t.blocks);
    const normalized = rawSegments.filter(s => s && typeof s === 'object').map(s => ({
      id: s.segment_number || s.segment,
      presence: s.presence_in_message || s.entry || '',
      condition: s.presence_condition || s.condition || '',
      section: s.data_section || '', name: s.segment_name || s.name || '', raw: s
    }));
    const blocked = /BLOCKED/.test(layout.status) || /BLOCKED/.test(original.review_status || '');
    templateRows.push([layout.section, layout.template_key, layout.kind, layout.status, original.alias_of || '',
      t.layout, t.segment_count, normalized.map(s => s.id).join(', '), t.message_length_maximum, layout.note || '',
      compact(t.field_separator_rules), compact(t.repeating_group), compact(t.envelope), compact(t.notes), REVIEW]);
    for (const s of normalized) {
      assert(s.id, `Template segment without ID: ${layout.template_key}`);
      templateSegments.push([layout.template_key, layout.section, s.id, s.name, s.section, s.presence, s.condition,
        s.raw.max_length, s.raw.position || s.raw.block_no || '', compact(s.raw.source), REVIEW]);
    }
    const groups = [['HEADER', t.data_section_1_fields], ['POSITIONAL MESSAGE', t.fields], ['SPDH HEADER', t.spdh_header_fields]];
    for (const s of normalized) groups.push([s.id, s.raw.fields]);
    for (const [group, fields] of groups) {
      for (const f of asArray(fields)) templateFields.push([layout.template_key, layout.section, group, f.field_no, f.element_no, f.name,
        f.entry, f.position, f.length, f.fixed_value, f.separator_after, f.description, compact(f.source), REVIEW]);
    }
    if (layout.note) conflicts.push([layout.template_key, layout.status, layout.note, layout.section, REVIEW]);
    for (const key of ['source_conflict', 'source_conflicts', 'approved_extensions', 'placement_note'])
      if (t[key]) conflicts.push([layout.template_key, key, compact(t[key]), layout.section, REVIEW]);
    const subsets = blocked ? [] : candidateSubsets(normalized);
    resolvedTemplates.push({ name: layout.template_key, segments: normalized, subsets: subsets.length, blocked });
    for (const [n, subset] of subsets.entries()) {
      const selected = new Set(subset.map(s => s.id));
      combos.push([`${layout.section}-${String(n + 1).padStart(5, '0')}`, layout.template_key, layout.section,
        subset.map(s => s.id).join(' > ') || '(NO NUMBERED SEGMENT; POSITIONAL MESSAGE)',
        subset.length, normalized.filter(s => !selected.has(s.id)).map(s => s.id).join(', '),
        normalized.filter(s => !['R', 'PRESENT'].includes(s.presence)).map(s =>
          `${s.id}: ${selected.has(s.id) ? 'included: confirm trigger/data' : 'omitted: prove trigger false or omission permitted'}${s.condition ? `; ${s.condition}` : ''}`).join('\n'),
        layout.status, 'CANDIDATE_NOT_APPROVED', REVIEW]);
    }
  }
  add('Message Templates', ['Section', 'Message family', 'Direction', 'Source status', 'Alias target', 'Wire layout', 'Declared segment count',
    'Listed segments', 'Maximum length', 'Source note', 'Separator rules', 'Repeating group', 'Envelope', 'Notes', 'Decision'], templateRows);
  add('Template Segments', ['Message family', 'Section', 'Segment', 'Name', 'Data section', 'Presence', 'Condition', 'Max length', 'Position or block', 'Source', 'Decision'], templateSegments);
  add('Template Fields', ['Message family', 'Section', 'Group or segment', 'Field', 'Element', 'Name', 'Presence', 'Position', 'Length', 'Fixed value', 'Separator after', 'Description', 'Source', 'Decision'], templateFields);
  add('Segment Subsets', ['Combination ID', 'Message family', 'Section', 'Listed segment order', 'Numbered segment count', 'Omitted segments',
    'Conditions to resolve', 'Template status', 'Combination status', 'Decision'], combos);
  add('Subset Coverage', ['Message family', 'Segment IDs', 'Non-R markers', 'Expected subsets', 'Exported subsets', 'Decision'],
    resolvedTemplates.map(t => [t.name, t.segments.map(s => s.id).join(', '), t.segments.filter(s => !['R', 'PRESENT'].includes(s.presence)).length,
      t.blocked ? 0 : 2 ** t.segments.filter(s => !['R', 'PRESENT'].includes(s.presence)).length, t.subsets,
      t.blocked ? 'BLOCKED_EXTERNAL_SOURCE' : REVIEW]));
  const pairs = [];
  for (let a = 0; a < segments.length; a++) for (let b = a + 1; b < segments.length; b++) {
    const x = segments[a].id, y = segments[b].id;
    const families = resolvedTemplates.filter(t => t.segments.some(s => s.id === x) && t.segments.some(s => s.id === y)).map(t => t.name);
    pairs.push([x, y, families.join('\n'), families.length ? 'CO_LISTED_NOT_PROOF_OF_COMPATIBILITY' : 'NOT_CO_LISTED_NOT_PROOF_OF_PROHIBITION', REVIEW]);
  }
  add('Segment Pairs', ['Segment A', 'Segment B', 'Co-listed message families', 'Evidence state', 'Decision'], pairs);
  add('Transaction Types', ['Transaction code', 'Transaction type', 'Class', 'Source anchor', 'Training context', 'Decision'],
    reference.transactionTypes.map(t => [t.code, t.name, t.class, t.sourceAnchor, t.testSolutionTraining, REVIEW]));
  const cardRows = [], appendixChunks = [], appendixRows = [];
  let appendix = '', scope = '', chunk = [];
  function flush() {
    if (!chunk.length) return;
    appendixChunks.push([appendix, scope, chunk[0].page, chunk[0].line, chunk.at(-1).line, chunk.map(r => r.text).join('\n'), REVIEW]);
    chunk = [];
  }
  const appendixSource = source.filter(r => r.appendix && Number(r.page) >= 496);
  for (const r of appendixSource) {
    if (appendix !== r.appendix || (chunk.length && (r.page !== chunk[0].page || chunk.length >= 100))) flush();
    appendix = r.appendix;
    if (appendix === 'E') {
      if (/^Used in the Table Load Response/.test(r.text)) scope = 'TABLE_LOAD';
      if (/^Used in Financial Transaction Prompt Codes/.test(r.text)) scope = 'FINANCIAL_PROMPT';
      if (/^Used in Special Transaction Prompt Codes/.test(r.text)) scope = 'SPECIAL_PROMPT';
    } else scope = `APPENDIX_${appendix}`;
    chunk.push(r);
  }
  flush();
  // Keep continuation lines with their code row, including repeated codes and page-spanning definitions.
  for (const app of ['C', 'D', 'E', 'F', 'G', 'H', 'J', 'L', 'Q', 'AC']) {
    const records = source.filter(r => r.appendix === app && Number(r.page) >= 503);
    const starts = [];
    let cardScope = '';
    for (const [i, r] of records.entries()) {
      if (app === 'E') {
        if (/^Used in the Table Load Response/.test(r.text)) cardScope = 'TABLE_LOAD';
        if (/^Used in Financial Transaction Prompt Codes/.test(r.text)) cardScope = 'FINANCIAL_PROMPT';
        if (/^Used in Special Transaction Prompt Codes/.test(r.text)) cardScope = 'SPECIAL_PROMPT';
      }
      const match = r.text.match(app === 'G' ? /^([0-9A-Z])\s+(\S.*)/ : /^(?:Code\s+)?(\d{2,3}(?:[–-]\d{2,3})?)\s+(\S.*)/);
      if (match) starts.push({ index: i, code: match[1], scope: cardScope });
    }
    for (const [i, start] of starts.entries()) {
      const rows = records.slice(start.index, i + 1 < starts.length ? starts[i + 1].index : records.length);
      const evidence = rows.map(r => r.text).join('\n');
      appendixRows.push([app, start.scope, start.code, evidence, rows[0].page, rows[0].line, REVIEW]);
      if (app === 'E') cardRows.push([start.scope, start.code, rows[0].text.replace(/^\d{3}\s+/, '').trim(), evidence, rows[0].page, rows[0].line, REVIEW]);
    }
  }
  add('Card Codes', ['Usage scope', 'Card or special prompt code', 'Card type and description', 'Source evidence', 'PDF page', 'Source line', 'Decision'], cardRows);
  add('Appendix Code Rows', ['Appendix', 'Scope', 'Code or range', 'Source evidence with continuation', 'PDF page', 'Source line', 'Decision'], appendixRows);
  add('Authorizer Codes', ['Code', 'Authorizer and type evidence', 'PDF page', 'Source line', 'Decision'],
    appendixRows.filter(r => r[0] === 'C').map(r => [r[2], r[3], r[4], r[5], r[6]]));
  conflicts.push(['Authorizer codes', 'SECONDARY_DOCUMENT_COUNT_DISCREPANCY',
    'Appendix C contains 92 source rows (codes range 00-98 with gaps). The existing appendix-code-tables.md describes 98 entries. This workbook follows the source rows, not that secondary count.',
    'Appendix C', REVIEW]);
  add('Appendix Evidence', ['Appendix', 'Scope', 'PDF page', 'Start line', 'End line', 'Source text including nested layouts', 'Decision'], appendixChunks);
  const stateRows = source.filter(r => r.appendix === 'D').flatMap(r => {
    const m = r.text.trim().match(/^(.+?)\s+([A-Z]{2})\s+(\d{2})(1)?$/);
    if (m?.[4]) assert(m[2] === 'NA' && m[3] === '00', 'Unexpected state-code footnote');
    return m ? [[m[1], m[2], m[3], '4;12;124', '102 positions 3-4', r.page, r.line,
      m[4] ? 'Printed 001 means 00 plus footnote 1: Table 041 Country Subdivision must be included; see source footnote.' : '',
      REVIEW]] : [];
  });
  add('State Codes', ['State or territory', 'Alphabetical code', 'ANSI code', 'Alphabetical elements', 'ANSI element context', 'PDF page', 'Source line', 'Footnote condition', 'Decision'], stateRows);
  const currencyRows = [], currencySource = source.filter(r => r.appendix === 'L');
  let previousCurrencyEnd = 0;
  for (let i = 0; i < currencySource.length; i++) {
    const r = currencySource[i], m = r.text.trim().match(/(?:^|\s)(\d{3})\s+(Both|MC|Visa(?:®)?)$/);
    if (!m) continue;
    currencyRows.push([m[1], m[2], currencySource.slice(previousCurrencyEnd, i + 1).map(v => v.text).join('\n'), r.page, r.line, REVIEW]);
    previousCurrencyEnd = i + 1;
  }
  add('Currency Codes', ['Currency code', 'Network support stated', 'Currency and country source evidence', 'PDF page', 'Source line', 'Decision'], currencyRows);
  add('Value Lookup Map', ['Elements', 'Value reference worksheet', 'Context instruction'], [
    ['All', 'Field Values; Elements', 'Begin with the source definition, representation and processing rules; select the context-specific definition rather than treating max length as fixed.'],
    ['7', 'Authorizer Codes', 'Source-listed authorizer codes are not proof of production activation.'],
    ['4;12;102;124', 'State Codes', 'Alphabetical values apply to 4/12/124; ANSI numeric values apply to Terminal Identifier 102 positions 3-4. Do not interchange them.'],
    ['14;78', 'Card Codes; Prompt Code Candidates', 'Distinguish table-load configuration codes from financial card codes and special prompt codes.'],
    ['20', 'Currency Codes', 'Check the source network support context, not just the ISO-shaped code.'],
    ['77', 'Appendix Code Rows (F)', 'Product-code ranges and categories require transaction/industry context.'],
    ['8;26;83', 'Response Codes; Appendix Evidence (H)', 'Response and decline meanings depend on message family; repeated L/M meanings remain distinct.'],
    ['111;112;113', 'Appendix Evidence (I and J)', 'Variable Information table IDs, nested attributes, valid values and POS entry-mode rules remain source evidence.'],
    ['116;118', 'Appendix Evidence (K)', 'Additional Information sub-tables require their own layout and trigger checks.'],
    ['189;190;191;192', 'Appendix Evidence (R and T)', 'TLV/tag and EMV additional-information layouts; external EMV restrictions remain contextual.'],
    ['Moneris;Download fields', 'Appendix Evidence (V and W)', 'Use the nested Moneris and download layout source in addition to outer segment fields.']
  ]);
  const responseRows = [];
  for (const id of ['7', '8', '26', '83']) {
    for (const d of elements[id].definitions) {
      const lines = d.sourceText.split(/\r?\n/), start = lines.findIndex(l => /Valid Codes\/Values:/.test(l));
      if (start < 0) continue;
      const rows = [];
      for (let n = start; n < lines.length; n++) {
        const m = lines[n].replace(/^Valid Codes\/Values:\s*/, '').match(/^[ \t]*([0-9A-Z]{1,2})\s+(\S.*)/);
        if (m) rows.push({ at: n, code: m[1] });
      }
      if (!rows.length) responseRows.push([id, d.name, '', lines.slice(start).join('\n'), d.sourceSection, d.sourceLine, REVIEW]);
      for (let n = 0; n < rows.length; n++) responseRows.push([id, d.name, rows[n].code,
        lines.slice(rows[n].at, n + 1 < rows.length ? rows[n + 1].at : lines.length).join('\n'),
        d.sourceSection, d.sourceLine + rows[n].at, REVIEW]);
    }
  }
  add('Response Codes', ['Element', 'Code family', 'Code', 'Context-dependent meaning evidence', 'Section', 'Source line', 'Decision'], responseRows);
  const financialCards = [...new Set(cardRows.filter(r => r[0] === 'FINANCIAL_PROMPT' && /^\d{3}$/.test(r[1])).map(r => r[1]))];
  const prompts = reference.transactionTypes.filter(t => t.code !== '9').flatMap(t => financialCards.map(c =>
    [t.code + c, t.code, t.name, c, 'FINANCIAL_4_CHARACTER', 'SYNTACTIC_CANDIDATE_NOT_CARD_TRANSACTION_APPROVAL', 'Appendices E and G; Element 78', REVIEW]));
  for (const r of cardRows.filter(r => r[0] === 'SPECIAL_PROMPT'))
    prompts.push([r[1], '9', r[2], r[1], 'SPECIAL_3_CHARACTER', 'SOURCE_LISTED_CONTEXT_AND_DIRECTION_REQUIRED', `Appendix E; line ${r[5]}`, REVIEW]);
  add('Prompt Code Candidates', ['Prompt code', 'Transaction code', 'Transaction type or purpose', 'Card code', 'Format', 'Applicability status', 'Source', 'Decision'], prompts);
  const rules = [];
  function walk(directory) {
    for (const entry of fs.readdirSync(directory, { withFileTypes: true })) {
      const filename = path.join(directory, entry.name);
      if (entry.isDirectory()) walk(filename);
      else if (entry.name.endsWith('-rule-catalog.json')) {
        const relative = path.relative(pack, filename).split(path.sep).join('/');
        const catalog = read(relative);
        for (const r of asArray(catalog.rules)) rules.push([r.ruleId || r.id, catalog.catalogId, r.title, r.class || r.ruleClass,
          r.severity, r.sourceAnchor?.section, r.sourceAnchor?.segment, r.sourceAnchor?.element, compact(r.sourceAnchor),
          relative, 'SECONDARY_CATALOG_SOURCE_REVIEW_REQUIRED']);
      }
    }
  }
  walk(path.join(pack, 'docs', 'specs', 'kb'));
  add('Rules and Dependencies', ['Rule ID', 'Catalog', 'Rule description', 'Class', 'Severity', 'Section', 'Segments', 'Elements', 'Source anchor', 'Catalog source', 'Decision'], rules);
  const transactionScope = read('test-output/test-solution-independent-review/atl105-element-transaction-applicability.json');
  add('Element Transaction Scope', ['Element', 'Transaction code', 'Transaction type', 'Class', 'Segments with evidence', 'Message families with evidence',
    'Applicability status', 'Reason', 'Evidence', 'Source anchor', 'Decision'],
    transactionScope.rows.map(r => [r.element, r.transactionTypeCode, r.transactionTypeName, r.transactionTypeClass,
      compact(r.segmentsWithElementEvidence), [...new Set(compact(r.messageFamiliesWithElementEvidence).split(';'))].join('; '), r.applicabilityStatus,
      r.reason, compact(r.evidence), compact(r.sourceAnchor), r.validityDecision]));
  add('Source Conflicts', ['Message family', 'Status or issue type', 'Description', 'Section', 'Decision'], conflicts);
  const checkRows = [
    ['BR', 'Correct source scope', 'Each BR links to a versioned section, segment, field/element and rule; distinguish catalog candidates from approvals.'],
    ['BR', 'Atomic and contextual', 'Preserve conditional triggers, direction, card/transaction eligibility, lifecycle dependencies and explicit omission rules.'],
    ['Scenario', 'Positive and negative paths', 'Cover trigger true/false, required segment missing, unsupported context, unknown compatibility and source conflicts.'],
    ['Test case', 'Measurable expected result', 'Specify input, execution steps, expected field/segment behavior and source-backed acceptance or review outcome.'],
    ['Test data', 'Field constraints', 'Verify context-specific type, byte width, representation, padding, enumeration/range, and fixed values; avoid treating every max as fixed.'],
    ['Test data', 'Serialization', 'Check field order, field separators, trailing omission rules, positional layouts, repeat counts and segment/message length from the applicable source.'],
    ['Test data', 'Segment count', 'Element 63 counts actual serialized segments where that field/layout applies, not a universal count for positional response formats.'],
    ['Test data', 'Prompt and response context', 'Validate financial vs special prompt length, transaction/card eligibility, direction and response-code meaning within the selected flow.'],
    ['Traceability', 'BR -> scenario -> case -> data', 'Every artifact has stable IDs and source-backed rule links; report missing links and uncovered rule paths.'],
    ['Approval', 'No false pass', 'Unresolved triggers, aliases, external source gaps or contradictions remain REVIEW_REQUIRED/BLOCKED; workbook presence does not establish correctness.']
  ];
  add('Validation Checklist', ['Artifact level', 'Check', 'Acceptance instruction'], checkRows);
  add('AI Review Register', ['Reference ID', 'Element', 'Rule ID', 'BR IDs', 'Scenario IDs', 'Test case IDs', 'Test data IDs', 'Source evidence',
    'Positive covered', 'Negative covered', 'Boundary covered', 'Condition true and false covered', 'Review status', 'Reviewer notes'],
    reference.elements.map(e => [`E-${e.element}`, e.element, '', '', '', '', '', e.definitions.map(d => `${d.sourceSection} line ${d.sourceLine}`).join('; '),
      '', '', '', '', 'NOT_REVIEWED', '']));
  add('Source Evidence', ['PDF page', 'Section', 'Appendix', 'Source line', 'Source text'],
    source.filter(r => r.text.trim()).map(r => [r.page, r.section, r.appendix, r.line, r.text]));
  read('docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf', false);
  add('Source Manifest', ['Input path relative to ATL105', 'Bytes', 'SHA256', 'Role'],
    [...inputs].map(([name, v]) => [name, v.bytes, v.hash, name.includes('rule-catalog') ? 'SECONDARY_RULE_CATALOG' : 'SOURCE_OR_DERIVED_REFERENCE']));
  info.push(['Generated UTC', new Date().toISOString()]);
  for (const sheet of sheets) info.push([`Sheet: ${sheet.name}`, `${sheet.rows.length} data rows`]);
  assert(definitionRows.length === reference.definitionCount, 'Element definition count does not reconcile');
  assert(Object.keys(elements).length === reference.elementCount, 'Element count does not reconcile');
  assert(layoutIndex.layouts.length === templateRows.length, 'Message template count does not reconcile');
  assert(new Set(segments.map(s => s.id)).size === 49, 'Segment IDs do not reconcile');
  assert(pairs.length === 1176, 'All unordered segment pairs must be represented');
  assert(financialCards.length > 30 && cardRows.some(r => r[1] === '900'), 'Card code extraction incomplete');
  assert(responseRows.filter(r => r[0] === '83' && r[2] === 'L').length === 2, 'Response L contexts must remain distinct');
  assert(stateRows.length === 72, `Expected all 72 state/territory mappings, got ${stateRows.length}`);
  assert(currencyRows.length > 100, 'Currency lookup extraction incomplete');
  return sheets;
}

if (require.main === module) {
  const pack = path.resolve(process.argv[2] || path.join(__dirname, '..', 'specifications', 'ATL105'));
  const output = path.resolve(process.argv[3] || path.join(pack, 'reports', 'ATL105-2026-3-Validation-Reference.xlsx'));
  const sheets = build(pack);
  saveWorkbook(output, sheets);
  console.log(JSON.stringify({ output, sheets: sheets.map(s => ({ name: s.name, rows: s.rows.length })) }, null, 2));
}

module.exports = { build, candidateSubsets, column, crc32, parseSegments, saveWorkbook, sourceIndex, worksheet };
