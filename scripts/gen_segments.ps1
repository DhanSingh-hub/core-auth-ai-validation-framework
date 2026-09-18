$ErrorActionPreference='Stop'
$ReportDir = Join-Path $PSScriptRoot '..\reports\atl105-visa'
$src = Join-Path $ReportDir 'ATL105 Visa.html'
$rxCaret=[regex]'<span class="caret[^"]*">(.*?)</span>'
$rxLeafOpen=[regex]'<div class="leaf-item[^"]*">(.*)$'   # value may wrap to next lines
function Dec($s){ $s -replace '&lt;','<' -replace '&gt;','>' -replace '&amp;','&' }
function Inner($s){ if($s -match '^\s*<(.+?)>'){ $Matches[1].Trim() } else { ($s -replace '\s*=\s*$','').Trim() } }
function ValOf($s){ if($s -match '=\s*(.*?)(</div>)?\s*$'){ $Matches[1].Trim() } else { '' } }

$stack=New-Object System.Collections.ArrayList
$pending=$null; $inResults=$false
$seen=@{}; $order=0
$records=New-Object System.Collections.Generic.List[object]
$segType=@{}; $segCount=@{}

foreach($raw in (Get-Content $src)){
  if(-not $inResults){ if($raw -match 'id="resultsUL"'){ $inResults=$true }; continue }
  $mc=$rxCaret.Match($raw)
  if($mc.Success){
    $name=Inner (Dec $mc.Groups[1].Value)
    $segIdx=-1; for($i=$stack.Count-1;$i -ge 0;$i--){ if($stack[$i] -match 'Segment$'){ $segIdx=$i; break } }
    $isSeg=($name -match 'Segment$'); $isCtx=($name -eq 'Merchant Request' -or $name -eq 'Merchant Response')
    if($isSeg){ if($segCount.ContainsKey($name)){$segCount[$name]++}else{$segCount[$name]=1} }
    if($segIdx -ge 0 -and -not $isSeg -and -not $isCtx){
      $seg=$stack[$segIdx]; $ctx='?'; for($i=$segIdx;$i -ge 0;$i--){ if($stack[$i] -match '^Merchant (Request|Response)$'){$ctx=$stack[$i];break} }
      $below=@(); for($i=$segIdx+1;$i -lt $stack.Count;$i++){ $below+=$stack[$i] }
      $path=(@($below+$name) -join ' > '); $key="$ctx|$seg|$path"
      if(-not $seen.ContainsKey($key)){ $seen[$key]=$order++; $records.Add([pscustomobject]@{Ctx=$ctx;Seg=$seg;Path=$path;O=$seen[$key]}) }
    }
    $pending=$name; continue
  }
  $ml=$rxLeafOpen.Match($raw)
  if($ml.Success){
    $body=Dec $ml.Groups[1].Value; $fname=Inner $body
    $segIdx=-1; for($i=$stack.Count-1;$i -ge 0;$i--){ if($stack[$i] -match 'Segment$'){ $segIdx=$i; break } }
    if($segIdx -ge 0){
      $seg=$stack[$segIdx]; $ctx='?'; for($i=$segIdx;$i -ge 0;$i--){ if($stack[$i] -match '^Merchant (Request|Response)$'){$ctx=$stack[$i];break} }
      $below=@(); for($i=$segIdx+1;$i -lt $stack.Count;$i++){ $below+=$stack[$i] }
      $path=(@($below+$fname) -join ' > '); $key="$ctx|$seg|$path"
      if(-not $seen.ContainsKey($key)){ $seen[$key]=$order++; $records.Add([pscustomobject]@{Ctx=$ctx;Seg=$seg;Path=$path;O=$seen[$key]}) }
      if($fname -eq 'SegmentType'){ $tk="$seg|$ctx"; if(-not $segType.ContainsKey($tk)){ $segType[$tk]=ValOf $body } }
    }
    continue
  }
  if($raw -match '<ul class="nested">'){ if($pending -ne $null){ [void]$stack.Add($pending); $pending=$null } else { [void]$stack.Add('~') }; continue }
  if($raw -match '</ul>'){ if($stack.Count -gt 0){ $stack.RemoveAt($stack.Count-1) }; continue }
}

$meta=[ordered]@{
 'Standard Segment'=@('#1565c0','Core transaction data - terminal, card, amounts')
 'Product Code Segment'=@('#00838f','Fuel / merchandise product lines')
 'Variable Info Segment'=@('#2e7d32','Variable table fields (request)')
 'Additional Info Segment'=@('#ef6c00','Response additional-info table')
 'EMV Data Segment'=@('#6a1b9a','EMV chip data')
 'Transaction Attributes Segment'=@('#455a64','Settlement / receipt attributes')
 'Moneris Data Segment'=@('#b71c1c','Moneris interchange data')
}
$distinct=@{}; foreach($r in $records){ $distinct["$($r.Seg)|$($r.Path)"]=1 }
$nElem=$distinct.Keys.Count
$allCodes=@{}; foreach($k in $segType.Keys){ $allCodes[$segType[$k]]=1 }
$nCodes=$allCodes.Keys.Count

function He($s){ $s -replace '&','&amp;' -replace '<','&lt;' -replace '>','&gt;' }

$h=@()
$h+='<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">'
$h+='<title>ATL105 Visa - Segment &amp; Element Coverage</title><style>'
$h+='*{box-sizing:border-box;} body{font-family:"Segoe UI",Tahoma,sans-serif;background:#1B5E20;margin:0;padding:24px;color:#1f2937;}'
$h+='.wrap{max-width:1180px;margin:0 auto;} .panel{background:#fff;border-radius:10px;box-shadow:0 2px 6px rgba(0,0,0,.18);padding:22px;margin-bottom:22px;}'
$h+='h1{margin:0 0 4px;font-size:24px;} h2{margin:0;font-size:17px;} .sub{color:#555;font-size:13.5px;line-height:1.6;}'
$h+='.kpis{display:grid;grid-template-columns:repeat(auto-fit,minmax(130px,1fr));gap:12px;margin-top:14px;} .kpi{background:#f4f7f4;border-left:4px solid #388e3c;border-radius:6px;padding:12px 14px;} .kpi .v{font-size:26px;font-weight:700;color:#1B5E20;} .kpi .l{font-size:11px;color:#667;text-transform:uppercase;letter-spacing:.03em;}'
$h+='.seg-head{display:flex;align-items:center;gap:12px;margin-bottom:6px;} .seg-ic{flex:none;width:34px;height:34px;border-radius:8px;color:#fff;font-weight:700;font-size:13px;display:flex;align-items:center;justify-content:center;}'
$h+='.badge{display:inline-block;font-size:11.5px;font-weight:600;border-radius:12px;padding:2px 9px;margin-left:6px;} .b-code{background:#ede7f6;color:#4527a0;} .b-req{background:#e8eef7;color:#1565c0;} .b-resp{background:#fff4e5;color:#b25c00;} .b-cnt{background:#e6f4ea;color:#1e7e34;}'
$h+='.seg-desc{color:#78909c;font-size:12.5px;margin:2px 0 12px 46px;}'
$h+='.cols{display:grid;grid-template-columns:1fr 1fr;gap:18px;} @media(max-width:760px){.cols{grid-template-columns:1fr;}}'
$h+='.ctx-title{font-size:12px;font-weight:700;text-transform:uppercase;letter-spacing:.04em;color:#1B5E20;border-bottom:2px solid #e3e7ea;padding-bottom:4px;margin-bottom:8px;}'
$h+='ul.els{list-style:none;padding:0;margin:0;} ul.els li{font-family:"Courier New",monospace;font-size:12.5px;padding:3px 0;border-bottom:1px dashed #eee;color:#37474f;} ul.els li.hdr{color:#90a4ae;} ul.els li.grp{font-weight:700;color:#20303a;}'
$h+='.pill{display:inline-block;background:#eef3ee;color:#1B5E20;border-radius:10px;padding:1px 7px;font-size:10.5px;margin-left:6px;}'
$h+='</style></head><body><div class="wrap">'
$h+='<div class="panel"><h1>Message Segment &amp; Element Coverage</h1>'
$h+='<div class="sub"><b>Spec:</b> ATL105 &nbsp;|&nbsp; <b>Card:</b> Credit Visa &nbsp;|&nbsp; <b>Platform:</b> BuyPass &nbsp;|&nbsp; <b>Suite:</b> Credit VISA Regression &nbsp;|&nbsp; <b>Source:</b> ATL105 Visa.html<br>Distinct message segments and the data elements populated under each across the 529 test messages. <b>SegmentType</b> and <b>SegmentLength</b> are the header fields present on every segment.</div>'
$h+='<div class="kpis">'
$h+=('<div class="kpi"><div class="v">{0}</div><div class="l">Segments</div></div>' -f $meta.Keys.Count)
$h+=('<div class="kpi"><div class="v">{0}</div><div class="l">SegmentType Codes</div></div>' -f $nCodes)
$h+=('<div class="kpi"><div class="v">{0}</div><div class="l">Distinct Elements</div></div>' -f $nElem)
$h+='<div class="kpi"><div class="v">529</div><div class="l">Test Messages</div></div>'
$h+='</div></div>'

$idx=0
foreach($seg in $meta.Keys){
  $idx++
  $col=$meta[$seg][0]; $desc=$meta[$seg][1]
  $codes=@(); foreach($c in 'Merchant Request','Merchant Response'){ $tk="$seg|$c"; if($segType.ContainsKey($tk)){ $codes+=$segType[$tk] } }
  $codes=@($codes | Select-Object -Unique)
  $cnt=0; if($segCount.ContainsKey($seg)){ $cnt=$segCount[$seg] }
  $abbr=-join ($seg.Split(' ') | ForEach-Object { $_.Substring(0,1) })
  $h+='<div class="panel">'
  $h+=('<div class="seg-head"><div class="seg-ic" style="background:{0}">{1}</div><h2>{2}. {3}</h2><span class="badge b-code">Type {4}</span><span class="badge b-cnt">{5} occurrences</span></div>' -f $col,$abbr,$idx,(He $seg),($codes -join ' / '),$cnt)
  $h+=('<div class="seg-desc">{0}</div>' -f (He $desc))
  $h+='<div class="cols">'
  foreach($ctx in 'Merchant Request','Merchant Response'){
    $els=@($records | Where-Object {$_.Seg -eq $seg -and $_.Ctx -eq $ctx} | Sort-Object O)
    if($els.Count -eq 0){ continue }
    $tk="$seg|$ctx"; $code=''; if($segType.ContainsKey($tk)){ $code=$segType[$tk] }
    $ctxShort= if($ctx -eq 'Merchant Request'){'Request'}else{'Response'}
    $h+=('<div><div class="ctx-title">{0} <span class="pill">type {1}</span> <span class="pill">{2} fields</span></div><ul class="els">' -f $ctxShort,$code,$els.Count)
    foreach($e in $els){
      $parts=$e.Path -split ' > '
      $depth=$parts.Count-1; $leaf=$parts[$parts.Count-1]
      $cls='' ; if($leaf -eq 'SegmentType' -or $leaf -eq 'SegmentLength'){$cls=' class="hdr"'}
      $isGrp = @($records | Where-Object {$_.Seg -eq $seg -and $_.Ctx -eq $ctx -and $_.Path -like ($e.Path+' > *')}).Count -gt 0
      if($isGrp){$cls=' class="grp"'}
      $pad=$depth*18
      $h+=('<li{0} style="padding-left:{1}px">{2}</li>' -f $cls,$pad,(He $leaf))
    }
    $h+='</ul></div>'
  }
  $h+='</div></div>'
}

$h+='<div class="panel"><h2 style="color:#1B5E20;border-left:4px solid #388e3c;padding-left:10px;">Notes</h2><div class="sub" style="margin-top:8px">'
$h+='&bull; <b>Type codes:</b> 100 Standard, 102 Product Code, 111 Variable Info, 112 Additional Info, 130/131 EMV Data (request/response), 134 Transaction Attributes, 135/136 Moneris Data (request/response).<br>'
$h+='&bull; <b>Grouped elements</b> (bold) contain the indented sub-fields beneath them &mdash; e.g. <i>PromptCode</i> &rarr; TransactionType, CardType and <i>ProductTableEntry</i> &rarr; ProductCode, UnitOfMeasure, Quantity, UnitPrice, ProductAmount.<br>'
$h+='&bull; <b>VarInfoTable_/AddlInfoTable_</b> numbers are table field IDs carried in the variable / additional-info segments.<br>'
$h+='&bull; The <b>Merchant Response</b> body also carries message-level fields (ResponseCode, ApprovedAmount, DeclineCode, AuthorizerCode, AuthorizerRespCode, SettlementDate&hellip;) that sit outside any segment and are therefore not listed above.</div></div>'

$h+='</div></body></html>'
$html=$h -join "`n"
Set-Content (Join-Path $ReportDir 'ATL105 Visa - Segment Coverage.html') -Value $html -Encoding UTF8
Write-Output ('segments={0} codes={1} distinctElements={2}' -f $meta.Keys.Count,$nCodes,$nElem)
Write-Output 'DONE'
