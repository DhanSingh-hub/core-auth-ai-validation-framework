const fs = require('node:fs');
const path = require('node:path');
const assert = require('node:assert/strict');
const {JSDOM, VirtualConsole} = require(process.argv[3] || 'jsdom');

const directory = path.resolve(process.argv[2]);
const errors = [];
const virtualConsole = new VirtualConsole();
virtualConsole.on('jsdomError', error => errors.push(error.message));
const dom = new JSDOM(fs.readFileSync(path.join(directory, 'FULL-SEMANTIC-CHAIN-REPORT.html'), 'utf8'), {
  runScripts: 'dangerously', virtualConsole
});
const document = dom.window.document;
const element = id => document.getElementById(id);
const data = JSON.parse(element('data').textContent);

assert.equal(data.rules.length, 601);
assert.equal(data.brs.length, 6887);
assert.equal(data.scenarios.length, 12679);
assert.equal(data.cases.length, 21123);
assert.equal(data.queries.length, 109876);
assert.equal(element('rule-body').rows.length, 50);

element('query-kind').value = 'INDEPENDENT_BR_MEANING';
element('query-kind').dispatchEvent(new dom.window.Event('change'));
assert.ok(element('query-count').textContent.startsWith('593 selected'), element('query-count').textContent);

element('case-status').value = 'STRUCTURAL_BR_TS_TC_TD_LINKED';
element('case-status').dispatchEvent(new dom.window.Event('change'));
assert.ok(element('case-count').textContent.startsWith('20,941 selected'), element('case-count').textContent);

assert.ok(data.queries.every(query => query.status === 'OPEN_REVIEW_DEFERRED'));
assert.equal(data.summary.semanticAlignmentConfirmed, 0);
assert.equal(data.summary.executionCertified, false);
assert.deepEqual(errors, []);
dom.window.close();
console.log('PASS: full BR/TS/TC/TD and SME-query registers, source-rule and structural-chain filters, deferred approvals, and no browser errors');