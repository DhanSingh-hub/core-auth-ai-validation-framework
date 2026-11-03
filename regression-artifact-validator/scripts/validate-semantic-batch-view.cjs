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
virtualConsole.on('jsdomError', error=>errors.push(error.message));
const dom = new JSDOM(fs.readFileSync(path.join(directory,'SEMANTIC-FIRST-BATCH-REPORT.html'),'utf8'),{
  runScripts:'dangerously', virtualConsole,
  beforeParse(window){
    window.HTMLElement.prototype.scrollIntoView=()=>{};
    window.Blob=class{constructor(parts){this.parts=parts;}};
    window.URL.createObjectURL=blob=>{exportedContent=blob.parts.join('');return 'blob:test';};
    window.URL.revokeObjectURL=()=>{};
    window.HTMLAnchorElement.prototype.click=function(){exportedFilename=this.download;};
  }
});
const document = dom.window.document;
const element = id=>document.getElementById(id);
const choose=(id,value)=>{element(id).value=value;element(id).dispatchEvent(new dom.window.Event(id.endsWith('search')?'input':'change',{bubbles:true}));};
const count=(id,value)=>assert.ok(element(id).textContent.startsWith(value+' selected'),element(id).textContent);
assert.equal(document.querySelectorAll('[data-view]').length,element('matrix-history')?6:5);
count('case-count','100');
choose('case-intent','negative');count('case-count','25');
choose('case-condition','blocked');count('case-count','1');
element('case-csv').click();
assert.equal(exportedFilename,'selected-semantic-cases.csv');
assert.equal(exportedContent.split('\r\n').length,2);
element('case-reset').click();choose('case-condition','no-br');count('case-count','25');
element('case-reset').click();choose('case-condition','failure');count('case-count','2');
assert.ok(element('case-body').textContent.includes('TC-002869'));
element('case-body').querySelector('button').click();
assert.equal(element('case-evidence').hidden,false);
assert.ok(element('case-evidence').textContent.includes('NOT_CERTIFIED'));
assert.ok(element('case-evidence').textContent.includes('intendedNegativeTarget'));
assert.ok(element('case-evidence').textContent.includes('objectiveEvidence'));
assert.ok(element('case-evidence').textContent.includes('sourceReferences'));
element('case-reset').click();choose('case-search','no-such-case');count('case-count','0');
element('case-reset').click();
document.querySelector('[data-view="negatives"]').click();
assert.equal(element('blocker-body').rows.length,7);
element('blocker-body').querySelector('button').click();
assert.equal(element('cases').hidden,false);
assert.equal(element('negatives').hidden,true);
count('queue-count','2,005');
choose('queue-family','Financial Transaction Request');count('queue-count','416');
element('queue-csv').click();
assert.equal(exportedFilename,'selected-code-1-review.csv');
assert.equal(exportedContent.split('\r\n').length,417);
assert.ok(exportedContent.includes('NOT_ASSESSED'));
element('queue-reset').click();element('queue-next').click();
assert.ok(element('queue-page').textContent.startsWith('Page 2'));
element('queue-previous').click();assert.equal(element('queue-body').rows.length,50);
choose('queue-search','no-such-case');count('queue-count','0');element('queue-reset').click();
choose('queue-source','context');count('queue-count','880');
choose('queue-source','alias');count('queue-count','1,098');
choose('queue-source','output');count('queue-count','27');
choose('queue-source','matrix');count('queue-count','165');
element('queue-reset').click();
assert.equal(element('source-predicates').rows.length,2);
assert.equal(element('input-hashes').rows.length,5);
assert.equal(element('report-hashes').rows.length,(element('matrix-history')?5:4)+(element('autonomous-qualification')?1:0));
assert.equal(element('negative-classes').rows.length,7);
assert.equal(element('confounders').rows.length,4);
const data=JSON.parse(element('data').textContent);
assert.equal(data.summary.executionCertified,false);
assert.equal(data.summary.representativeRandomSample,false);
assert.equal(data.summary.negativeMutationStates.DECLARED_ABSENCE_PRESERVED,4);
assert.equal(data.summary.negativeMutationStates.DECLARED_VALUE_PRESERVED,20);
assert.equal(data.summary.negativeBlockerAssessment.targetCaseCount,7);
assert.equal(data.summary.negativeBlockerAssessment.sourceBackedAbsentRequiredFieldsObserved,4);
assert.equal(data.summary.negativeBlockerAssessment.sourceMaximumViolationsObserved,1);
assert.equal(data.summary.negativeBlockerAssessment.applicationDependentEnumCasesStillReviewRequired,1);
assert.equal(data.summary.negativeBlockerAssessment.externalProcessorOracleCasesStillBlocked,1);
assert.equal(data.summary.negativeBlockerAssessment.effectivenessCertified,0);
assert.equal(data.summary.brObjectiveEvidence.requirementReferences,300);
assert.equal(data.summary.brObjectiveEvidence.sourcePageMarkersResolved,300);
assert.equal(data.summary.brObjectiveEvidence.sourceElementAnchorsLocated,299);
assert.equal(data.summary.brObjectiveEvidence.casesWithExactRequirementSetMatch,100);
assert.equal(data.summary.brObjectiveEvidence.casesWithDivergentRequirementSets,0);
assert.equal(data.summary.brObjectiveEvidence.casesWithBothRequirementSetsEmpty,25);
assert.equal(data.summary.brObjectiveEvidence.casesWithExplicitTestcaseObjectiveText,22);
assert.equal(data.summary.brObjectiveEvidence.casesWithoutExplicitTestcaseObjectiveText,78);
assert.equal(data.summary.brObjectiveEvidence.semanticEquivalenceConfirmed,0);
assert.equal(data.controls.executionCertified,false);
assert.equal(data.summary.code1ReviewMatrixConcerns,165);
assert.ok(data.cases.every(row=>row.overallDisposition==='REVIEW_REQUIRED'));
assert.ok(data.queue.every(row=>row.independentDisposition==='NOT_ASSESSED'));
if(data.autonomousQualification){
  assert.equal(data.autonomousQualification.selectedPhysicalControls,20);
  assert.equal(data.autonomousQualification.javaGateCounts.negativeIsolation.PASS,40);
  assert.equal(data.autonomousQualification.javaGateCounts.hostOutcome.PASS,0);
  assert.equal(data.autonomousQualification.executionCertified,false);
  for(const link of element('autonomous-qualification').querySelectorAll('a'))assert.ok(fs.existsSync(path.resolve(directory,link.getAttribute('href'))));
}
if(data.lateMatrix){
  document.querySelector('[data-view="matrix-history"]').click();
  assert.equal(element('matrix-history').hidden,false);
  assert.equal(element('cases').hidden,true);
  assert.ok(element('matrix-history').textContent.includes('Previous semantic report'));
  assert.equal(data.lateMatrix.independent.directTcBrs,3659);
  assert.equal(data.lateMatrix.independent.matrixBrsWithTc,6);
  assert.equal(data.lateMatrix.independent.scenarioIdsWithoutBrLinks,155);
  assert.equal(data.lateMatrix.independent.producerNumeratorPercent,53.16);
  for(const [name,digest] of Object.entries(data.lateMatrix.history.files)){
    assert.equal(createHash('sha256').update(fs.readFileSync(path.join(directory,data.lateMatrix.history.directory,name))).digest('hex'),digest);
  }
  for(const link of element('matrix-history').querySelectorAll('a'))assert.ok(fs.existsSync(path.resolve(directory,link.getAttribute('href'))));
  if(data.lateMatrix.reconstruction){
    assert.equal(data.lateMatrix.reconstruction.leafRows,61044);
    assert.equal(data.lateMatrix.reconstruction.detailStatusAgreementCount,200);
    assert.equal(data.lateMatrix.reconstruction.hashVerifiedPhysicalFiles,21210);
    assert.equal(data.lateMatrix.reconstruction.executionCertified,false);
    assert.ok(element('matrix-history').textContent.includes('Earlier matrix-era semantic report'));
    for(const [name,digest] of Object.entries(data.lateMatrix.supplementHistory.files))assert.equal(createHash('sha256').update(fs.readFileSync(path.join(directory,data.lateMatrix.supplementHistory.directory,name))).digest('hex'),digest);
  }
}
assert.deepEqual(errors,[]);
dom.window.close();
console.log('PASS: semantic case filters, blockers, evidence, CSV exports, 2,005-row queue, pagination and assurance boundaries');