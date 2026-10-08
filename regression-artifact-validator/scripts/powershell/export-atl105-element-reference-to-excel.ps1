param(
    [string]$Pack = (Join-Path $PSScriptRoot '..\..\specifications\ATL105'),
    [string]$Output = (Join-Path $PSScriptRoot '..\..\specifications\ATL105\test-output\test-solution-independent-review\atl105-all-elements-reference.xlsx')
)

$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem
$Pack = (Resolve-Path $Pack).Path
$referencePath = Join-Path $Pack 'test-output\test-solution-independent-review\atl105-all-elements-reference.json'
$elementCsv = Join-Path $Pack 'test-output\test-solution-independent-review\atl105-all-elements.csv'
$contextCsv = Join-Path $Pack 'test-output\test-solution-independent-review\atl105-element-contexts.csv'
$transactionJson = Join-Path $Pack 'test-output\test-solution-independent-review\atl105-element-transaction-applicability.json'
$omissionJson = Join-Path $Pack 'test-output\test-solution-independent-review\atl105-element-omission-matrix.json'
$approvalJson = Join-Path $Pack 'test-output\test-solution-independent-review\atl105-br-approval-matrix.json'
$dependencyJson = Join-Path $Pack 'test-output\test-solution-independent-review\atl105-dependency-absence-review-matrix.json'
foreach ($path in @($referencePath, $elementCsv, $contextCsv, $transactionJson, $omissionJson, $approvalJson, $dependencyJson)) {
    if (-not (Test-Path $path)) { throw "Missing generated element reference input: $path" }
}
$transactionMatrix = Get-Content $transactionJson -Raw -Encoding UTF8 | ConvertFrom-Json
$omissionMatrix = Get-Content $omissionJson -Raw -Encoding UTF8 | ConvertFrom-Json
$approvalMatrix = Get-Content $approvalJson -Raw -Encoding UTF8 | ConvertFrom-Json
$dependencyMatrix = Get-Content $dependencyJson -Raw -Encoding UTF8 | ConvertFrom-Json
$matrix = Get-Content $referencePath -Raw -Encoding UTF8 | ConvertFrom-Json
$elements = @($matrix.elements)
$elementRows = [System.Collections.Generic.List[object[]]]::new()
foreach ($record in (Import-Csv $elementCsv -Encoding UTF8)) {
    $elementRows.Add([object[]]@($record.PSObject.Properties | ForEach-Object { $_.Value }))
}
$contextRows = [System.Collections.Generic.List[object[]]]::new()
foreach ($record in (Import-Csv $contextCsv -Encoding UTF8)) {
    $contextRows.Add([object[]]@($record.PSObject.Properties | ForEach-Object { $_.Value }))
}

function ConvertTo-ColumnName([int]$Index) {
    $name = ''
    while ($Index -gt 0) {
        $remainder = ($Index - 1) % 26
        $name = [char](65 + $remainder) + $name
        $Index = [math]::Floor(($Index - 1) / 26)
    }
    return $name
}

function ConvertTo-CellXml([string]$Reference, [string]$Value, [bool]$Header) {
    $escaped = [System.Security.SecurityElement]::Escape($Value)
    $style = if ($Header) { ' s="1"' } else { '' }
    return "<c r=`"$Reference`" t=`"inlineStr`"$style><is><t xml:space=`"preserve`">$escaped</t></is></c>"
}

function New-SheetXml([string[]]$Headers, [System.Collections.IEnumerable]$Rows) {
    $rowCount = 1
    foreach ($row in $Rows) { $rowCount++ }
    $lastColumn = ConvertTo-ColumnName $Headers.Count
    $xml = [System.Text.StringBuilder]::new()
    [void]$xml.Append('<?xml version="1.0" encoding="UTF-8" standalone="yes"?>')
    [void]$xml.Append('<worksheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main">')
    [void]$xml.Append("<dimension ref=`"A1:$lastColumn$rowCount`"/><sheetViews><sheetView workbookViewId=`"0`"><pane ySplit=`"1`" topLeftCell=`"A2`" activePane=`"bottomLeft`" state=`"frozen`"/></sheetView></sheetViews><sheetFormatPr defaultRowHeight=`"15`"/><sheetData>")
    [void]$xml.Append('<row r="1">')
    for ($column = 0; $column -lt $Headers.Count; $column++) {
        $cell = ConvertTo-ColumnName ($column + 1)
        [void]$xml.Append((ConvertTo-CellXml "$cell`1" $Headers[$column] $true))
    }
    [void]$xml.Append('</row>')
    $rowNumber = 2
    foreach ($row in $Rows) {
        [void]$xml.Append("<row r=`"$rowNumber`">")
        for ($column = 0; $column -lt $Headers.Count; $column++) {
            $cell = ConvertTo-ColumnName ($column + 1)
            $value = if ($null -ne $row[$column]) { [string]$row[$column] } else { '' }
            [void]$xml.Append((ConvertTo-CellXml "$cell$rowNumber" $value $false))
        }
        [void]$xml.Append('</row>')
        $rowNumber++
    }
    [void]$xml.Append('</sheetData>')
    [void]$xml.Append("<autoFilter ref=`"A1:$lastColumn$rowCount`"/><pageMargins left=`"0.25`" right=`"0.25`" top=`"0.5`" bottom=`"0.5`" header=`"0.2`" footer=`"0.2`"/></worksheet>")
    return $xml.ToString()
}

function Add-Worksheet($Zip, [string]$Path, [string]$Content) {
    $entry = $Zip.CreateEntry($Path, [System.IO.Compression.CompressionLevel]::Fastest)
    $writer = [System.IO.StreamWriter]::new($entry.Open(), [System.Text.UTF8Encoding]::new($false))
    try { $writer.Write($Content) } finally { $writer.Dispose() }
}

$definitionRows = [System.Collections.Generic.List[object[]]]::new()
$ruleRows = [System.Collections.Generic.List[object[]]]::new()
$relationshipRows = [System.Collections.Generic.List[object[]]]::new()
$transactionRows = [System.Collections.Generic.List[object[]]]::new()
$omissionRows = [System.Collections.Generic.List[object[]]]::new()
$approvalRows = [System.Collections.Generic.List[object[]]]::new()
$dependencyReviewRows = [System.Collections.Generic.List[object[]]]::new()
foreach ($element in $elements) {
    foreach ($definition in $element.definitions) {
        $constraint = $definition.constraints
        $definitionRows.Add([object[]]@(
            $element.element, $definition.name, $definition.sourceSection, $definition.sourceLine,
            $constraint.characterType, $constraint.lengthLabel, $constraint.lengthMode, $constraint.maxLength,
            $constraint.representation, $constraint.extractionStatus, $definition.validCodesEvidence, $definition.sourceEvidence
        ))
    }
    foreach ($rule in $element.businessRules) {
        $ruleRows.Add([object[]]@(
            $element.element, $rule.ruleId, $rule.title, $rule.ruleClass, $rule.severity, $rule.sourceCatalog,
            $rule.sourceAnchor.specification, $rule.sourceAnchor.version, $rule.sourceAnchor.section,
            $rule.sourceAnchor.segment, $rule.sourceAnchor.element, $rule.sourceAnchor.rule,
            $rule.matchStatus, (($rule.existingBusinessRequirements | ForEach-Object id) -join '; '),
            $rule.brStatus, $rule.negativeCoverageCount
        ))
    }
    foreach ($dependency in $element.dependencies) {
        $relationshipRows.Add([object[]]@(
            $element.element, 'DEPENDENCY_CANDIDATE', $dependency.ruleId, $dependency.ruleClass,
            $dependency.title, $dependency.relationship, $dependency.status, $dependency.sourceAnchor.section,
            $dependency.sourceAnchor.segment, $dependency.sourceAnchor.element
        ))
    }
    foreach ($absence in $element.explicitAbsenceOrConditionRules) {
        $relationshipRows.Add([object[]]@(
            $element.element, 'EXPLICIT_ABSENCE_OR_CONDITION_CANDIDATE', $absence.ruleId, '',
            $absence.title, 'DIRECT_ELEMENT_ANCHOR', $absence.status, $absence.sourceAnchor.section,
            $absence.sourceAnchor.segment, $absence.sourceAnchor.element
        ))
    }
}
foreach ($row in $transactionMatrix.rows) {
    $transactionRows.Add([object[]]@(
        $row.element, $row.transactionTypeCode, $row.transactionTypeName, $row.transactionTypeClass,
        $row.sourceAnchor, $row.segmentsWithElementEvidence, $row.messageFamiliesWithElementEvidence,
        $row.applicabilityStatus, $row.reason, $row.evidence, $row.validityDecision
    ))
}
foreach ($row in $omissionMatrix.rows) {
    $omissionRows.Add([object[]]@(
        $row.element, $row.sourceNames, $row.messageFamily, $row.transactionFamilyLabel, $row.sourceSection,
        $row.segment, $row.segmentPresence, $row.elementPresence, $row.contextKind, $row.calledStatus,
        $row.reason, $row.sourceEvidenceStatus, $row.decision
    ))
}
foreach ($row in $approvalMatrix.rows) {
    $approvalRows.Add([object[]]@(
        $row.ruleId, $row.title, $row.class, $row.severity, $row.sourceCatalog,
        $row.sourceAnchor.section, $row.sourceAnchor.segment, $row.sourceAnchor.element, $row.sourceAnchor.rule,
        $row.sourceEvidenceVerified, $row.sourceEvidenceResolution, $row.authoredBrLinkStatus,
        $row.authoredBrCount, $row.negativeBrCount, $row.semanticApprovalStatus, $row.businessRuleStatus,
        $row.decisionAuthority, $row.chainDerivationStatus, $row.approvalEvidence
    ))
}
foreach ($row in $dependencyMatrix.rows) {
    $dependencyReviewRows.Add([object[]]@(
        $row.element, $row.candidateType, $row.ruleId, $row.ruleClass, $row.title, $row.relationship,
        $row.candidateStatus, $row.sourceSection, $row.sourceSegment, $row.sourceElement, $row.sourceRule,
        $row.semanticReviewStatus, $row.approvalStatus, $row.enforcementStatus, $row.decisionNeeded
    ))
}

$overviewRows = [System.Collections.Generic.List[object[]]]::new()
$overviewRows.Add([object[]]@('Specification', "$($matrix.specification) $($matrix.specificationVersion)"))
$overviewRows.Add([object[]]@('Unique element IDs', "$($matrix.summary.elements)"))
$overviewRows.Add([object[]]@('Chapter 13 definitions', "$($matrix.summary.chapter13Definitions)"))
$overviewRows.Add([object[]]@('Definitions with constraints extracted', "$($matrix.summary.definitionsWithConstraintsExtracted)"))
$overviewRows.Add([object[]]@('Elements with linked BR candidates', "$($matrix.summary.elementsWithLinkedExistingBrCandidates)"))
$overviewRows.Add([object[]]@('Active Section 11 field usages', "$($matrix.summary.elementsWithActiveSection11FieldUsages) elements / $($matrix.summary.activeSection11FieldContextRows) usages"))
$overviewRows.Add([object[]]@('Omitted family/segment review rows', "$($matrix.summary.omittedFamilySegmentReviewRows)"))
$overviewRows.Add([object[]]@('Elements with transaction code list', 'Element 78 only'))
$overviewRows.Add([object[]]@('Transaction-type applicability', 'Not exhaustively mapped per element; REVIEW_REQUIRED where unsupported'))
$overviewRows.Add([object[]]@('Business-rule approval', '0; BR links are candidates, not approval'))
$overviewRows.Add([object[]]@('Absence interpretation', 'Not listed in Section 11 is not proof of prohibition'))
$overviewRows.Add([object[]]@('Authority / status', "$($matrix.authority) / $($matrix.status)"))

$elementHeaders = @('Element','Source Names','Definition Count','Source Status','Segments','Message Families','Transaction Applicability Status','Transaction Specificity','Transaction Codes','Rule Count','Rule IDs','BR Candidate IDs','Absence/Condition Rule IDs','Absence Conclusion')
$definitionHeaders = @('Element','Definition Name','Source Section','Source Line','Character Type','Length Label','Length Mode','Maximum Length','Representation','Extraction Status','Valid Codes/Values Evidence','Source Evidence')
$contextHeaders = @('Element','Message Family','Transaction/Family Label','Section 11 Section','Template Review Status','Segment','Segment Presence','Element Presence','Context Kind','Evidence Status','Evidence')
$ruleHeaders = @('Element','Rule ID','Rule Title','Class','Severity','Catalog','Specification','Version','Section','Segment','Anchored Element','Rule Anchor','Match Status','Existing BR IDs','BR Status','Negative Coverage Count')
$relationshipHeaders = @('Element','Relationship Type','Rule ID','Rule Class','Rule Title','Relationship','Status','Section','Segment','Anchored Elements')
$transactionHeaders = @('Element','Transaction Type Code','Transaction Type Name','Transaction Type Class','Source Anchor','Segments With Element Evidence','Families With Element Evidence','Applicability Status','Reason','Evidence','Validity Decision')
$omissionHeaders = @('Element','Source Names','Message Family','Transaction/Family Label','Source Section','Segment','Segment Presence','Element Presence','Context Kind','Called Status','Reason','Source Evidence Status','Decision')
$approvalHeaders = @('Rule ID','Title','Class','Severity','Source Catalog','Section','Segment','Element','Rule Anchor','Source Evidence Verified','Source Evidence Resolution','Authored BR Link Status','Authored BR Count','Negative BR Count','Semantic Approval Status','Business Rule Status','Decision Authority','Chain Derivation Status','Approval Evidence')
$dependencyReviewHeaders = @('Element','Candidate Type','Rule ID','Rule Class','Title','Relationship','Candidate Status','Source Section','Source Segment','Source Element','Source Rule','Semantic Review Status','Approval Status','Enforcement Status','Decision Needed')
$overviewHeaders = @('Field','Value')

$elementData = [System.Collections.Generic.List[object[]]]::new()
foreach ($element in $elements) {
    $names = @($element.sourceNames) -join '; '
    $codes = @($element.transactionTypeCodes | ForEach-Object { "$($_.code) $($_.name)" }) -join '; '
    $elementData.Add([object[]]@(
        $element.element, $names, $element.definitions.Count, $element.sourceStatus, (@($element.segments) -join '; '),
        (@($element.messageFamilyContexts | Select-Object -ExpandProperty messageFamily -Unique) -join '; '),
        $element.transactionTypeApplicabilityStatus, $element.transactionTypeSpecificity, $codes,
        $element.businessRules.Count, (@($element.businessRules | ForEach-Object ruleId) -join '; '),
        (@($element.businessRules | ForEach-Object { $_.existingBusinessRequirements } | ForEach-Object id | Sort-Object -Unique) -join '; '),
        (@($element.explicitAbsenceOrConditionRules | ForEach-Object ruleId) -join '; '), $element.absenceConclusion
    ))
}

$sheets = @(
    @{ Name='Overview'; Headers=$overviewHeaders; Rows=$overviewRows.ToArray() },
    @{ Name='Elements'; Headers=$elementHeaders; Rows=$elementData.ToArray() },
    @{ Name='Definitions'; Headers=$definitionHeaders; Rows=$definitionRows.ToArray() },
    @{ Name='Family Segment Contexts'; Headers=$contextHeaders; Rows=$contextRows.ToArray() },
    @{ Name='Business Rules'; Headers=$ruleHeaders; Rows=$ruleRows.ToArray() },
    @{ Name='Dependencies Absence'; Headers=$relationshipHeaders; Rows=$relationshipRows.ToArray() },
    @{ Name='Transaction Applicability'; Headers=$transactionHeaders; Rows=$transactionRows.ToArray() },
    @{ Name='Element Omissions'; Headers=$omissionHeaders; Rows=$omissionRows.ToArray() },
    @{ Name='BR Approval'; Headers=$approvalHeaders; Rows=$approvalRows.ToArray() },
    @{ Name='Dependency Absence Review'; Headers=$dependencyReviewHeaders; Rows=$dependencyReviewRows.ToArray() }
)

$outputDirectory = Split-Path $Output -Parent
New-Item -ItemType Directory -Path $outputDirectory -Force | Out-Null
$temporary = [IO.Path]::ChangeExtension($Output, '.tmp.xlsx')
if (Test-Path $temporary) { Remove-Item -LiteralPath $temporary -Force }
$file = [IO.File]::Open($temporary, [IO.FileMode]::CreateNew)
$zip = [System.IO.Compression.ZipArchive]::new($file, [System.IO.Compression.ZipArchiveMode]::Create, $false)
try {
    $contentTypes = [System.Text.StringBuilder]::new()
    [void]$contentTypes.Append('<?xml version="1.0" encoding="UTF-8" standalone="yes"?><Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types"><Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/><Default Extension="xml" ContentType="application/xml"/><Override PartName="/xl/workbook.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml"/><Override PartName="/xl/styles.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml"/>')
    for ($index = 1; $index -le $sheets.Count; $index++) { [void]$contentTypes.Append("<Override PartName=`"/xl/worksheets/sheet$index.xml`" ContentType=`"application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml`"/>") }
    [void]$contentTypes.Append('</Types>')
    Add-Worksheet $zip '[Content_Types].xml' $contentTypes.ToString()

    Add-Worksheet $zip '_rels/.rels' '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships"><Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="xl/workbook.xml"/></Relationships>'
    $workbook = [System.Text.StringBuilder]::new()
    $rels = [System.Text.StringBuilder]::new()
    [void]$workbook.Append('<?xml version="1.0" encoding="UTF-8" standalone="yes"?><workbook xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"><sheets>')
    [void]$rels.Append('<?xml version="1.0" encoding="UTF-8" standalone="yes"?><Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">')
    for ($index = 0; $index -lt $sheets.Count; $index++) {
        $number = $index + 1
        $name = [System.Security.SecurityElement]::Escape($sheets[$index].Name)
        [void]$workbook.Append("<sheet name=`"$name`" sheetId=`"$number`" r:id=`"rId$number`"/>")
        [void]$rels.Append("<Relationship Id=`"rId$number`" Type=`"http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet`" Target=`"worksheets/sheet$number.xml`"/>")
    }
    [void]$workbook.Append('</sheets><calcPr calcId="191029"/></workbook>')
    [void]$rels.Append("<Relationship Id=`"rId$($sheets.Count + 1)`" Type=`"http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles`" Target=`"styles.xml`"/></Relationships>")
    Add-Worksheet $zip 'xl/workbook.xml' $workbook.ToString()
    Add-Worksheet $zip 'xl/_rels/workbook.xml.rels' $rels.ToString()
    Add-Worksheet $zip 'xl/styles.xml' '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><styleSheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main"><fonts count="2"><font><sz val="11"/><name val="Calibri"/></font><font><b/><sz val="11"/><name val="Calibri"/></font></fonts><fills count="2"><fill><patternFill patternType="none"/></fill><fill><patternFill patternType="gray125"/></fill></fills><borders count="1"><border><left/><right/><top/><bottom/><diagonal/></border></borders><cellStyleXfs count="1"><xf numFmtId="0" fontId="0" fillId="0" borderId="0"/></cellStyleXfs><cellXfs count="2"><xf numFmtId="0" fontId="0" fillId="0" borderId="0" xfId="0"/><xf numFmtId="0" fontId="1" fillId="0" borderId="0" xfId="0" applyFont="1"/></cellXfs><cellStyles count="1"><cellStyle name="Normal" xfId="0" builtinId="0"/></cellStyles></styleSheet>'
    for ($index = 0; $index -lt $sheets.Count; $index++) {
        $sheet = $sheets[$index]
        Add-Worksheet $zip "xl/worksheets/sheet$($index + 1).xml" (New-SheetXml $sheet.Headers $sheet.Rows)
    }
}
finally {
    $zip.Dispose()
    $file.Dispose()
}
Move-Item -LiteralPath $temporary -Destination $Output -Force
Write-Output "Workbook created: $Output"
