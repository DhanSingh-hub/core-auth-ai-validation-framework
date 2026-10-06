const fs = require('node:fs');
const path = require('node:path');
const assert = require('node:assert/strict');
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
assert.deepEqual(errors,[]);
dom.window.close();
console.log('PASS: offline DOM filters, details, selected CSV export, empty/reset states, pagination, queues and full print registers');