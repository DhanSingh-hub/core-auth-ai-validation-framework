const test = require('node:test');
const assert = require('node:assert/strict');
const path = require('node:path');
const fs = require('node:fs');
const os = require('node:os');
const zlib = require('node:zlib');
const { build, candidateSubsets, column, crc32, saveWorkbook, worksheet } = require('./export-atl105-validation-workbook.cjs');

const pack = path.join(__dirname, '..', 'specifications', 'ATL105');
const sheets = build(pack);
const sheet = name => {
  const result = sheets.find(s => s.name === name);
  assert.ok(result, `Missing sheet ${name}`);
  return result;
};

test('source inventories and every element/transaction pair reconcile', () => {
  assert.equal(sheet('Elements').rows.length, 232);
  assert.equal(new Set(sheet('Elements').rows.map(r => r[0])).size, 231);
  assert.equal(sheet('Segments').rows.length, 49);
  assert.equal(sheet('Message Templates').rows.length, 31);
  assert.equal(sheet('Transaction Types').rows.length, 23);
  assert.equal(sheet('Element Transaction Scope').rows.length, 231 * 23);
  assert.equal(sheet('Segment Pairs').rows.length, 49 * 48 / 2);
  assert.equal(sheet('Authorizer Codes').rows.length, 92);
  assert.equal(sheet('State Codes').rows.length, 72);
  assert.ok(sheet('Currency Codes').rows.length > 100);
  assert.ok(sheet('State Codes').rows.some(r => r[1] === 'CA' && r[2] === '06'));
  assert.ok(sheet('Currency Codes').rows.some(r => r[0] === '012' && r[1] === 'Both'));
  assert.equal(new Set(sheets.map(s => s.name)).size, sheets.length);
  assert.ok(sheets.every(s => s.name.length <= 31 && !/[\\/?*:[\]]/.test(s.name)));
});

test('subsets keep required segments and enumerate each conditional choice once', () => {
  const segments = [{ id: '100', presence: 'R' }, { id: '101', presence: 'C' }, { id: '102', presence: 'O' }];
  const sets = candidateSubsets(segments);
  assert.deepEqual(sets.map(s => s.map(v => v.id)), [['100'], ['100', '101'], ['100', '102'], ['100', '101', '102']]);
  const all = sheet('Segment Subsets').rows;
  for (const row of sheet('Subset Coverage').rows) {
    const candidates = all.filter(c => c[1] === row[0]);
    assert.equal(candidates.length, row[3]);
    assert.equal(new Set(candidates.map(c => c[3])).size, candidates.length);
    const required = sheet('Template Segments').rows.filter(r => r[0] === row[0] && ['R', 'PRESENT'].includes(r[5])).map(r => r[2]);
    for (const c of candidates) {
      assert.ok(required.every(id => c[3].split(' > ').includes(id)));
      assert.equal(c[8], 'CANDIDATE_NOT_APPROVED');
      assert.equal(c[9], 'REVIEW_REQUIRED');
    }
  }
  assert.equal(all.filter(r => r[1] === 'TransArmor Key and Key ID Load Request').length, 0);
  assert.ok(all.some(r => r[1] === 'Loyalty Card Transaction Response'));
});

test('field rows preserve inheritance, alternate layouts and unusual widths', () => {
  const fields = sheet('Segment Fields').rows;
  assert.equal(fields.length, 399);
  const dl2 = fields.filter(r => r[0] === 'DL2');
  assert.deepEqual(dl2.map(r => r[2]), Array.from({ length: 13 }, (_, n) => String(n + 1)));
  assert.deepEqual(dl2.filter(r => Number(r[2]) >= 8 && Number(r[2]) <= 11).map(r => r[3]), ['82', '1', '66', '75']);
  assert.ok(dl2.filter(r => Number(r[2]) >= 8 && Number(r[2]) <= 11).every(r => r[12] === 'SOURCE_EXPLICIT_INHERITANCE'));
  assert.ok(fields.some(r => r[0] === '118' && r[5] === '3,600' && r[6] === 'R'));
  assert.ok(fields.some(r => r[0] === '156' && r[5] === '..200' && r[6] === 'R'));
  assert.ok(sheet('Segments').rows.some(r => r[0] === '116' && r[7] === 'BLOCKED_EXTERNAL_SOURCE'));
  assert.ok(fields.every(r => r[6]), 'Every extracted row must retain a parsed entry marker');
});

test('codes retain leading zeros, contextual duplicates and special prompt lengths', () => {
  const cards = sheet('Card Codes').rows;
  assert.ok(cards.some(r => r[0] === 'FINANCIAL_PROMPT' && r[1] === '001'));
  assert.ok(cards.some(r => r[0] === 'TABLE_LOAD' && r[1] === '173'));
  assert.ok(!cards.some(r => r[0] === 'FINANCIAL_PROMPT' && r[1] === '173'));
  const responses = sheet('Response Codes').rows.filter(r => r[0] === '83');
  assert.equal(responses.filter(r => r[2] === 'L').length, 2);
  assert.equal(responses.filter(r => r[2] === 'M').length, 2);
  const prompts = sheet('Prompt Code Candidates').rows;
  assert.ok(prompts.some(r => r[0] === '0001'));
  assert.ok(prompts.some(r => r[0] === '900' && r[4] === 'SPECIAL_3_CHARACTER'));
  assert.ok(prompts.filter(r => r[4] === 'SPECIAL_3_CHARACTER').every(r => r[0].length === 3));
  assert.ok(prompts.filter(r => r[4] === 'FINANCIAL_4_CHARACTER').every(r => r[0].length === 4 && r[1] !== '9'));
});

test('all appendices and source manifests are retained', () => {
  const appendices = new Set(sheet('Appendix Evidence').rows.map(r => r[0]));
  assert.equal(appendices.size, 31);
  for (const name of ['A', 'B', 'I', 'K', 'R', 'T', 'V', 'AA', 'AE']) assert.ok(appendices.has(name));
  assert.ok(sheet('Source Manifest').rows.every(r => /^[a-f0-9]{64}$/.test(r[2])));
  assert.ok(sheet('Source Evidence').rows.some(r => r[3] === 17700 && r[1] === '13'));
});

test('Excel cell limits, XML escaping and literal formula safety', () => {
  assert.equal(column(0), 'A');
  assert.equal(column(26), 'AA');
  assert.equal(crc32(Buffer.from('123456789')), 0xcbf43926);
  const xml = worksheet(['Code', 'Text'], [['001', '=1+1 & <test>']]);
  assert.match(xml, /t="inlineStr"/);
  assert.match(xml, />001</);
  assert.match(xml, /=1\+1 &amp; &lt;test&gt;/);
  assert.ok(!xml.includes('<f>'));
  assert.throws(() => worksheet(['Text'], [['a'.repeat(32768)]]), /cell limit/);
  assert.throws(() => worksheet(['Text'], [['a', 'b']]), /cell count/);
  for (const s of sheets) {
    assert.ok(s.rows.every(r => r.length === s.headers.length), s.name);
    assert.ok(s.rows.every(r => r.every(v => String(v ?? '').length <= 32767)), s.name);
  }
});

test('ZIP output independently inflates, verifies CRCs and includes all workbook parts', () => {
  const directory = fs.mkdtempSync(path.join(os.tmpdir(), 'atl105-workbook-test-'));
  const filename = path.join(directory, 'reference.xlsx');
  try {
    saveWorkbook(filename, [{ name: 'Codes', headers: ['Code'], rows: [['001'], ['=SUM(A1:A2)']] }]);
    const zip = fs.readFileSync(filename), entries = new Map();
    let at = 0;
    while (zip.readUInt32LE(at) === 0x04034b50) {
      const compressed = zip.readUInt32LE(at + 18), length = zip.readUInt16LE(at + 26), extra = zip.readUInt16LE(at + 28);
      const name = zip.subarray(at + 30, at + 30 + length).toString();
      const start = at + 30 + length + extra;
      const data = zlib.inflateRawSync(zip.subarray(start, start + compressed));
      assert.equal(data.length, zip.readUInt32LE(at + 22));
      assert.equal(crc32(data), zip.readUInt32LE(at + 14));
      entries.set(name, data.toString());
      at = start + compressed;
    }
    assert.equal(entries.size, 6);
    assert.match(entries.get('xl/worksheets/sheet1.xml'), />001</);
    assert.match(entries.get('xl/workbook.xml'), /name="Codes"/);
  } finally {
    fs.unlinkSync(filename);
    fs.rmdirSync(directory);
  }
});
