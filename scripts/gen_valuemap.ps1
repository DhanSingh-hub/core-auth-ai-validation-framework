$ErrorActionPreference='Stop'
$ReportDir = Join-Path $PSScriptRoot '..\reports\atl105-visa'
$src = Join-Path $ReportDir 'ATL105 Visa.html'
$rxCaret=[regex]'<span class="caret[^"]*">(.*?)</span>'
$rxLeafOpen=[regex]'<div class="leaf-item[^"]*">(.*)$'
function Dec($s){ $s -replace '&lt;','<' -replace '&gt;','>' -replace '&amp;','&' }
function Inner($s){ if($s -match '^\s*<(.+?)>'){ $Matches[1].Trim() } else { ($s -replace '\s*=\s*$','').Trim() } }

$stack=New-Object System.Collections.ArrayList
$pending=$null; $inResults=$false
$seen=@{}; $order=0
$records=New-Object System.Collections.Generic.List[object]
$segType=@{}; $segCount=@{}; $vals=@{}
$inLeaf=$false; $buf=''

function Finish-Leaf($text){
  $t=Dec $text; $t=$t -replace '</div>\s*$',''
  $fname=Inner $t
  $val=''; if($t -match '^\s*<[^>]+>\s*=\s*(.*)$'){ $val=$Matches[1] }
  $val=($val -replace '\s+$','')
  $segIdx=-1; for($i=$stack.Count-1;$i -ge 0;$i--){ if($stack[$i] -match 'Segment$'){ $segIdx=$i; break } }
  if($segIdx -ge 0){
    $seg=$stack[$segIdx]; $ctx='?'; for($i=$segIdx;$i -ge 0;$i--){ if($stack[$i] -match '^Merchant (Request|Response)$'){$ctx=$stack[$i];break} }
    $below=@(); for($i=$segIdx+1;$i -lt $stack.Count;$i++){ $below+=$stack[$i] }
    $path=(@($below+$fname) -join ' > '); $key="$ctx|$seg|$path"
    if(-not $seen.ContainsKey($key)){ $seen[$key]=$order++; $records.Add([pscustomobject]@{Ctx=$ctx;Seg=$seg;Path=$path;O=$seen[$key]}) }
    if($fname -eq 'SegmentType'){ $tk="$seg|$ctx"; if(-not $segType.ContainsKey($tk)){ $segType[$tk]=$val } }
    $vk="$seg|$ctx|$path"; if(-not $vals.ContainsKey($vk)){ $vals[$vk]=@{} }
    $vv=$val; if($vv -eq ''){ $vv='__EMPTY__' }
    if($vals[$vk].ContainsKey($vv)){ $vals[$vk][$vv]++ } else { $vals[$vk][$vv]=1 }
  }
}

foreach($raw in [System.IO.File]::ReadLines($src,[System.Text.Encoding]::UTF8)){
  if(-not $inResults){ if($raw -match 'id="resultsUL"'){ $inResults=$true }; continue }
  if($inLeaf){ $buf+="`n"+$raw; if($raw -match '</div>'){ Finish-Leaf $buf; $inLeaf=$false; $buf='' }; continue }
  $mc=$rxCaret.Match($raw)
  if($mc.Success){
    $name=Inner (Dec $mc.Groups[1].Value)
    $segIdx=-1; for($i=$stack.Count-1;$i -ge 0;$i--){ if($stack[$i] -match 'Segment$'){ $segIdx=$i; break } }
    $isSeg=($name -match 'Segment$'); $isCtx=($name -match '^Merchant (Request|Response)$')
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
  if($ml.Success){ if($raw -match '</div>'){ Finish-Leaf $ml.Groups[1].Value } else { $inLeaf=$true; $buf=$ml.Groups[1].Value }; continue }
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
$allCodes=@{}; foreach($k in $segType.Keys){ $allCodes[$segType[$k]]=1 }; $nCodes=$allCodes.Keys.Count

function He($s){ [string]$s -replace '&','&amp;' -replace '<','&lt;' -replace '>','&gt;' -replace '"','&quot;' }
function Trunc($s,$n){ if($s.Length -gt $n){ $s.Substring(0,$n)+([char]0x2026) } else { $s } }
function ValDisp($k){ if($k -eq '__EMPTY__'){ '(blank)' } else { $k } }
$ENUM=12
function RenderVals($vh){
  if(-not $vh -or $vh.Keys.Count -eq 0){ return '' }
  $n=$vh.Keys.Count; $sorted=@($vh.GetEnumerator() | Sort-Object @{e={-$_.Value}},@{e={[string]$_.Key}})
  $o=''
  if($n -le $ENUM){
    foreach($e in $sorted){ $o+=('<span class="v" title="{0}">{1}<span class="c">&times;{2}</span></span>' -f (He $e.Key),(He (Trunc (ValDisp $e.Key) 28)),$e.Value) }
  } else {
    $o+=('<span class="v hi">{0} distinct values</span>' -f $n)
    foreach($e in ($sorted | Select-Object -First 3)){ $o+=('<span class="v ex" title="{0}">{1}<span class="c">&times;{2}</span></span>' -f (He $e.Key),(He (Trunc (ValDisp $e.Key) 16)),$e.Value) }
  }
  $o
}

$h=@()
$h+='<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">'
$h+='<title>ATL105 Visa - Segment, Element &amp; Value Map</title><style>'
$h+='*{box-sizing:border-box;} body{font-family:"Segoe UI",Tahoma,sans-serif;background:#1B5E20;margin:0;padding:24px;color:#1f2937;}'
$h+='.wrap{max-width:1180px;margin:0 auto;} .panel{background:#fff;border-radius:10px;box-shadow:0 2px 6px rgba(0,0,0,.18);padding:22px;margin-bottom:22px;}'
$h+='h1{margin:0 0 4px;font-size:24px;} h2{margin:0;font-size:17px;} .sub{color:#555;font-size:13.5px;line-height:1.6;}'
$h+='.kpis{display:grid;grid-template-columns:repeat(auto-fit,minmax(130px,1fr));gap:12px;margin-top:14px;} .kpi{background:#f4f7f4;border-left:4px solid #388e3c;border-radius:6px;padding:12px 14px;} .kpi .v2{font-size:26px;font-weight:700;color:#1B5E20;} .kpi .l{font-size:11px;color:#667;text-transform:uppercase;letter-spacing:.03em;}'
$h+='.seg-head{display:flex;align-items:center;gap:12px;margin-bottom:6px;flex-wrap:wrap;} .seg-ic{flex:none;width:34px;height:34px;border-radius:8px;color:#fff;font-weight:700;font-size:13px;display:flex;align-items:center;justify-content:center;}'
$h+='.badge{display:inline-block;font-size:11.5px;font-weight:600;border-radius:12px;padding:2px 9px;} .b-code{background:#ede7f6;color:#4527a0;} .b-cnt{background:#e6f4ea;color:#1e7e34;}'
$h+='.seg-desc{color:#78909c;font-size:12.5px;margin:2px 0 12px 46px;}'
$h+='.cols{display:grid;grid-template-columns:1fr 1fr;gap:18px;} @media(max-width:820px){.cols{grid-template-columns:1fr;}}'
$h+='.ctx-title{font-size:12px;font-weight:700;text-transform:uppercase;letter-spacing:.04em;color:#1B5E20;border-bottom:2px solid #e3e7ea;padding-bottom:4px;margin-bottom:8px;}'
$h+='.pill{display:inline-block;background:#eef3ee;color:#1B5E20;border-radius:10px;padding:1px 7px;font-size:10.5px;margin-left:6px;}'
$h+='ul.els{list-style:none;padding:0;margin:0;} ul.els li{padding:5px 0;border-bottom:1px dashed #eee;} .fname{font-family:"Courier New",monospace;font-size:12.5px;color:#20303a;font-weight:600;} li.hdr .fname{color:#9aa7b0;font-weight:400;} li.grp .fname{color:#0d47a1;font-weight:700;}'
$h+='.vwrap{margin-top:3px;} .v{display:inline-block;background:#eef3ee;color:#2e5d34;border-radius:9px;padding:1px 7px;margin:2px 4px 0 0;font-size:10.5px;font-family:"Courier New",monospace;} .v .c{color:#9bb39b;margin-left:3px;} .v.hi{background:#fff4e5;color:#b25c00;font-weight:700;} .v.ex{background:#f3f4f6;color:#607d8b;} .empty{color:#b0bec5;font-style:italic;font-family:"Segoe UI";}'
$h+='</style></head><body><div class="wrap">'
$h+='<div class="panel"><h1>Segment, Element &amp; Value Map</h1>'
$h+='<div class="sub"><b>Spec:</b> ATL105 &nbsp;|&nbsp; <b>Card:</b> Credit Visa &nbsp;|&nbsp; <b>Platform:</b> BuyPass &nbsp;|&nbsp; <b>Suite:</b> Credit VISA Regression &nbsp;|&nbsp; <b>Source:</b> ATL105 Visa.html<br>For every segment field, the <b>distinct values exercised across the 529 test messages</b> are shown as <span class="v">value<span class="c">&times;count</span></span> pills. Fields with more than '+$ENUM+' distinct values are summarised (count + top 3). &middot; = fixed-width filler; <span class="empty">(blank)</span> = empty field.</div>'
$h+='<div class="kpis">'
$h+=('<div class="kpi"><div class="v2">{0}</div><div class="l">Segments</div></div>' -f $meta.Keys.Count)
$h+=('<div class="kpi"><div class="v2">{0}</div><div class="l">SegmentType Codes</div></div>' -f $nCodes)
$h+=('<div class="kpi"><div class="v2">{0}</div><div class="l">Distinct Elements</div></div>' -f $nElem)
$h+='<div class="kpi"><div class="v2">529</div><div class="l">Test Messages</div></div>'
$h+='</div></div>'

$idx=0
foreach($seg in $meta.Keys){
  $idx++; $col=$meta[$seg][0]; $desc=$meta[$seg][1]
  $codes=@(); foreach($c in 'Merchant Request','Merchant Response'){ $tk="$seg|$c"; if($segType.ContainsKey($tk)){ $codes+=$segType[$tk] } }
  $codes=@($codes | Select-Object -Unique)
  $cnt=0; if($segCount.ContainsKey($seg)){ $cnt=$segCount[$seg] }
  $abbr=-join ($seg.Split(' ') | ForEach-Object { $_.Substring(0,1) })
  $h+='<div class="panel">'
  $h+=('<div class="seg-head"><div class="seg-ic" style="background:{0}">{1}</div><h2>{2}. {3}</h2><span class="badge b-code">Type {4}</span><span class="badge b-cnt">{5} occurrences</span></div>' -f $col,$abbr,$idx,(He $seg),($codes -join ' / '),$cnt)
  $h+=('<div class="seg-desc">{0}</div><div class="cols">' -f (He $desc))
  foreach($ctx in 'Merchant Request','Merchant Response'){
    $els=@($records | Where-Object {$_.Seg -eq $seg -and $_.Ctx -eq $ctx} | Sort-Object O)
    if($els.Count -eq 0){ continue }
    $tk="$seg|$ctx"; $code=''; if($segType.ContainsKey($tk)){ $code=$segType[$tk] }
    $ctxShort= if($ctx -eq 'Merchant Request'){'Request'}else{'Response'}
    $h+=('<div><div class="ctx-title">{0} <span class="pill">type {1}</span> <span class="pill">{2} fields</span></div><ul class="els">' -f $ctxShort,$code,$els.Count)
    foreach($e in $els){
      $parts=$e.Path -split ' > '; $depth=$parts.Count-1; $leaf=$parts[$parts.Count-1]
      $isGrp=@($records | Where-Object {$_.Seg -eq $seg -and $_.Ctx -eq $ctx -and $_.Path -like ($e.Path+' > *')}).Count -gt 0
      $cls=''; if($leaf -eq 'SegmentType' -or $leaf -eq 'SegmentLength'){$cls=' class="hdr"'}; if($isGrp){$cls=' class="grp"'}
      $pad=$depth*18
      $vk="$seg|$ctx|$($e.Path)"; $vh=$null; if($vals.ContainsKey($vk)){ $vh=$vals[$vk] }
      $vhtml=''; if(-not $isGrp){ $vhtml=RenderVals $vh }
      $h+=('<li{0}><div class="fname" style="padding-left:{1}px">{2}</div>' -f $cls,$pad,(He $leaf))
      if($vhtml){ $h+=('<div class="vwrap">{0}</div>' -f $vhtml) }
      $h+='</li>'
    }
    $h+='</ul></div>'
  }
  $h+='</div></div>'
}

$h+='<div class="panel"><h2 style="color:#1B5E20;border-left:4px solid #388e3c;padding-left:10px;">How to read the value map</h2><div class="sub" style="margin-top:8px">'
$h+='&bull; <span class="v">value<span class="c">&times;N</span></span> means that value appeared in N of the segment occurrences. Values are sorted most-frequent first.<br>'
$h+='&bull; <span class="v hi">K distinct values</span> marks high-variability fields (PANs, sequence numbers, amounts, track/EMV/Moneris data); the top 3 values are shown as samples.<br>'
$h+='&bull; <b>&middot;</b> characters are the report&rsquo;s fixed-width padding/filler; <span class="empty">(blank)</span> is an empty field.<br>'
$h+='&bull; Header fields <i>SegmentType</i> / <i>SegmentLength</i> are greyed; grouped parents (<i>PromptCode</i>, <i>ProductTableEntry</i>) are bold with their sub-fields indented.</div></div>'
$h+='</div></body></html>'
$html=$h -join "`n"
Set-Content (Join-Path $ReportDir 'ATL105 Visa - Segment Value Map.html') -Value $html -Encoding UTF8
Write-Output ('records={0} elements={1} codes={2}' -f $records.Count,$nElem,$nCodes)
Write-Output 'DONE'
