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
$segType=@{}; $vals=@{}
$inLeaf=$false; $buf=''
$EMPTY='__EMPTY__'

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
    $vv=$val; if($vv -eq ''){ $vv=$EMPTY }
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

# Expiration validation: dual-format (prefer YYMM, fall back to MMYY). Report reference month = 2026-09.
function ExpStatus($v){
  if($v -notmatch '^[0-9]{4}$'){ return ,@('Invalid','not a 4-digit date') }
  $a=[int]$v.Substring(0,2); $b=[int]$v.Substring(2,2)
  if($b -ge 1 -and $b -le 12){ $yy=$a; $mm=$b; $fmt='YYMM' }
  elseif($a -ge 1 -and $a -le 12){ $yy=$b; $mm=$a; $fmt='MMYY' }
  else { return ,@('Invalid','no valid month in either YYMM/MMYY') }
  $year=2000+$yy
  $expired = ($year -lt 2026) -or ($year -eq 2026 -and $mm -lt 9)
  $st= if($expired){'Expired'}else{'Valid'}
  return ,@($st, ('decoded {0:D4}-{1:D2} ({2})' -f $year,$mm,$fmt))
}

function Csv($s){ $t=[string]$s -replace "`r",'' -replace "`n",' ' -replace "`t",' '; '"'+($t -replace '"','""')+'"' }

$segOrder='Standard Segment','Product Code Segment','Variable Info Segment','Additional Info Segment','EMV Data Segment','Transaction Attributes Segment','Moneris Data Segment'
$rows=New-Object System.Collections.Generic.List[string]
$rows.Add('Segment,Context,SegmentType,Element,Value,Occurrences,Status,Note')
$nRows=0; $nExp=0; $nInv=0
foreach($seg in $segOrder){
  foreach($ctx in 'Merchant Request','Merchant Response'){
    $els=@($records | Where-Object {$_.Seg -eq $seg -and $_.Ctx -eq $ctx} | Sort-Object O)
    foreach($e in $els){
      $tk="$seg|$ctx"; $code=''; if($segType.ContainsKey($tk)){ $code=$segType[$tk] }
      $leaf=($e.Path -split ' > ')[-1]
      $vk="$seg|$ctx|$($e.Path)"; if(-not $vals.ContainsKey($vk)){ continue }
      $isExp = ($leaf -match 'Expir')
      foreach($pair in ($vals[$vk].GetEnumerator() | Sort-Object @{e={-$_.Value}},@{e={[string]$_.Key}})){
        $rawv=$pair.Key; $cnt=$pair.Value
        $status=''; $note=''
        $disp=$rawv
        if($rawv -eq $EMPTY){ $disp=''; $note='empty / not populated' }
        if($isExp -and $rawv -ne $EMPTY){ $r=ExpStatus $rawv; $status=$r[0]; $note=$r[1]; if($status -eq 'Expired'){$nExp++}; if($status -eq 'Invalid'){$nInv++} }
        $rows.Add( ((Csv $seg),(Csv $ctx),(Csv $code),(Csv $e.Path),(Csv $disp),(Csv $cnt),(Csv $status),(Csv $note)) -join ',' )
        $nRows++
      }
    }
  }
}
Set-Content (Join-Path $ReportDir 'ATL105 Visa - Field Values.csv') -Value $rows -Encoding UTF8
Write-Output ('rows={0} expired={1} invalid={2}' -f $nRows,$nExp,$nInv)
Write-Output 'DONE'
