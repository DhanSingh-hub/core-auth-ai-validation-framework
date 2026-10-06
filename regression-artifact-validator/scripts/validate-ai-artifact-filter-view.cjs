const fs = require('node:fs');
const path = require('node:path');
const assert = require('node:assert/strict');
const {createHash} = require('node:crypto');
const {JSDOM, VirtualConsole} = require(process.argv[3] || 'jsdom');
const directory = path.resolve(process.argv[2]);
const errors = [];
let exportedContent = '';
let exportedFilename = '';
const virtualConsole = new VirtualConsole();
virtualConsole.on('jsdomError', error => errors.push(error.message));
const dom = new JSDOM(fs.readFileSync(path.join(directory,'AI-ARTIFACT-FILTER-VIEW.html'),'utf8'),{
  runScripts:'dangerously',url:'file:///'+path.join(directory,'AI-ARTIFACT-FILTER-VIEW.html').replaceAll('\\','/'),
  virtualConsole, beforeParse(window) {
    window.HTMLElement.prototype.scrollIntoView=()=>{};
    window.Blob = class {constructor(parts) {this.parts=parts;}};
    window.URL.createObjectURL=blob=>{exportedContent=blob.parts.join('');return 'blob:test';};
    window.URL.revokeObjectURL=()=>{};
    window.HTMLAnchorElement.prototype.click=function(){exportedFilename=this.download;};
  }
});
const document = dom.window.document;
const element = id => document.getElementById(id);
const choose = (id,value) => {element(id).value=value;element(id).dispatchEvent(new dom.window.Event(id==='search'?'input':'change',{bubbles:true}));};
const count = value => assert.ok(element('result-count').textContent.startsWith(value+' selected'),element('result-count').textContent);
count('3,732');
choose('category','NO_CONSISTENT_TRANSACTION'); count('2,026');
choose('category','RESPONSE_SIDE_DEFERRED'); count('1,706');
choose('verdict','FAIL'); count('246');
element('reset').click();choose('source','external');count('3');
assert.equal(element('scenario-body').rows.length,3);
element('scenario-body').querySelector('button').click();
assert.ok(element('evidence').textContent.includes('NOT_ASSESSED'));
element('csv').click();
assert.equal(exportedFilename,'selected-no-tc-scenarios.csv');
assert.equal(exportedContent.split('\r\n').length,4);
assert.ok(exportedContent.includes('SC-5340') && exportedContent.includes('NOT_ASSESSED'));
element('reset').click();choose('search','nonexistent-scenario-xyz');count('0');
element('reset').click();element('next').click();
assert.ok(element('page-label').textContent.startsWith('Page 2'));
element('previous').click();assert.equal(element('scenario-body').rows.length,50);
document.querySelector('[data-tab="writes"]').click();
assert.equal(element('writes').hidden,false);assert.equal(element('missing').hidden,true);
assert.equal(element('write-body').rows.length,27);
assert.equal(element('audit-body').rows.length,4);
assert.equal(element('print-scenarios').rows.length,3732);
assert.equal(element('print-writes').rows.length,27);
const data = JSON.parse(element('data').textContent);
document.querySelector('[data-tab="coverage"]').click();
assert.equal(element('coverage').hidden,false);
assert.equal(element('writes').hidden,true);
assert.equal(element('coverage-total').rows.length,5);
assert.ok(element('coverage-total').textContent.includes('53.13%'));
assert.ok(element('coverage-total').textContent.includes('70.57%'));
assert.ok(element('coverage').textContent.includes('NOT_CALCULABLE'));
choose('coverage-mode','segments');
assert.equal(document.querySelector('[data-coverage-panel="segments"]').hidden,false);
assert.equal(document.querySelector('[data-coverage-panel="total"]').hidden,true);
assert.equal(element('coverage-segments').rows.length,data.coverage.segments.length);
assert.ok(element('coverage-segments').textContent.includes('UNASSIGNED'));
assert.ok(element('coverage-segments').textContent.includes('N/A'));
choose('coverage-mode','transactions');
assert.equal(element('coverage-transactions').rows.length,data.coverage.transactionTargets.length);
assert.equal(data.coverage.transactionTargets.reduce((count,row)=>count+row.scenarios,0),12679);
assert.equal(data.coverage.transactionTargets.reduce((count,row)=>count+row.scenariosWithTc,0),8947);
assert.equal(data.coverage.transactionTargets.reduce((count,row)=>count+row.tcCandidates,0),21123);
choose('coverage-mode','responses');
assert.equal(element('coverage-responses').rows.length,data.coverage.responseCodes.length);
assert.equal(data.coverage.responseCodes.find(row=>row.code==='1').scenariosDeclaringCode,2268);
assert.equal(data.coverage.responseCodes.find(row=>row.code==='NOT_DECLARED').scenariosDeclaringCode,10411);
assert.ok(data.coverage.responseCodes.every(row=>row.validatedCodeFamilyCoverage==='NOT_ASSESSED'));
choose('coverage-mode','types');
assert.equal(element('coverage-types').rows.length,data.coverage.scenarioTypes.length);
assert.equal(data.coverage.scenarioTypes.reduce((count,row)=>count+row.scenarios,0),12679);
assert.equal(data.coverage.executionCertified,false);
if(data.lateMatrix){
  document.querySelector('[data-tab="matrix-history"]').click();
  assert.equal(element('matrix-history').hidden,false);
  assert.equal(element('coverage').hidden,true);
  assert.ok(element('matrix-history').textContent.includes('61,107'));
  assert.ok(element('matrix-history').textContent.includes('REVIEW_REQUIRED'));
  assert.equal(data.lateMatrix.independent.leafRows,2000);
  assert.equal(data.lateMatrix.independent.detailRequirementCount,200);
  assert.equal(data.lateMatrix.independent.directOrScenarioBrs,3659);
  assert.equal(data.lateMatrix.issues.length,75);
  assert.deepEqual(data.lateMatrix.independent.matrixOnlyTracedBrIds,['REQ-SRC-ATL105-PDF-001:3226','REQ-SRC-ATL105-PDF-001:521']);
  assert.equal(Object.keys(data.lateMatrix.history.files).length,24);
  for(const [name,digest] of Object.entries(data.lateMatrix.history.files)){
    const file=path.join(directory,data.lateMatrix.history.directory,name);
    assert.equal(createHash('sha256').update(fs.readFileSync(file)).digest('hex'),digest,'Historical snapshot changed: '+name);
  }
  for(const link of element('matrix-history').querySelectorAll('a')){
    assert.ok(fs.existsSync(path.resolve(directory,link.getAttribute('href'))),'Missing matrix/history target: '+link.getAttribute('href'));
  }
  assert.equal(createHash('sha256').update(fs.readFileSync(data.lateMatrix.intake.file)).digest('hex'),data.lateMatrix.intake.sha256);
}
assert.deepEqual(errors,[]);
dom.window.close();
console.log('PASS: filters, CSV, pagination, queues, full registers and all five Coverage dimensions');