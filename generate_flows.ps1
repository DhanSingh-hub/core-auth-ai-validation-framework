$ErrorActionPreference='Stop'
$src='ATL105 Visa.html'
$rx=[regex]'<span class="caret ([a-z-]+)">TC:\s*(.*?)</span>'
# Partial Approval is a response tag ([Partial Approval]), NOT a message step -> excluded from scanner
$scan=[regex]::new('dup\s*completion|partial\s*completion|completion|comp(?![a-z])|authoriz[a-z]*|auth|cancellation|cancel[a-z]*|(?<![a-z])can(?![a-z])|timeout|sale|refund|return|void',[System.Text.RegularExpressions.RegexOptions]::IgnoreCase)

function Map-Action($m){
  $s=$m.ToLower()
  if($s.StartsWith('dup')){'DupCompletion'}
  elseif($s -match 'partial' -and $s -match 'completion'){'PartialCompletion'}
  elseif($s.StartsWith('completion') -or $s -eq 'comp'){'Completion'}
  elseif($s.StartsWith('authoriz') -or $s -eq 'auth'){'Authorization'}
  elseif($s.StartsWith('cancel') -or $s -eq 'can'){'Cancellation'}
  elseif($s -eq 'timeout'){'Timeout'}
  elseif($s -eq 'sale'){'Sale'}
  elseif($s -eq 'refund' -or $s -eq 'return'){'Refund'}
  elseif($s -eq 'void'){'Void'}
}

$recs=New-Object System.Collections.Generic.List[object]
foreach($l in (Get-Content $src)){
  $mm=$rx.Match($l); if(-not $mm.Success){continue}
  $status=$mm.Groups[1].Value
  $body=($mm.Groups[2].Value -replace '&lt;','<' -replace '&gt;','>' -replace '&amp;','&')
  $id=($body -split '\s')[0]; if($id.Length -lt 2){continue}
  $base=$id.Substring(0,$id.Length-1); $step=$id.Substring($id.Length-1,1)
  if($body -match '\d\d\.\d{3},\s*(.+)$'){$desc=$Matches[1].Trim()}else{$desc=$body}
  # strip trailing qualifier phrases like "of return" / "Void of return_Track2" (underscore boundary)
  $clean=$desc -replace '(?i)\bof\s+(return|sale|auth\w*|completion|void)(?![a-z])',''
  $acts=@(); foreach($x in $scan.Matches($clean)){ $a=Map-Action $x.Value; if($a){$acts+=$a} }
  $pass=($status -eq 'success-text' -or $status -eq 'override-pass-text')
  $recs.Add([pscustomobject]@{Id=$id;Base=$base;Step=$step;Desc=$desc;Pass=$pass;Acts=@($acts)})
}

$baseInfos=New-Object System.Collections.Generic.List[object]
foreach($g in ($recs | Group-Object Base)){
  $rowsB=@($g.Group | Sort-Object Step)
  $maxlen=(@($rowsB | ForEach-Object { $_.Acts.Count }) | Measure-Object -Maximum).Maximum
  if($maxlen -ge 2){
    $chainActs=@((@($rowsB) | Sort-Object { $_.Acts.Count } -Descending | Select-Object -First 1).Acts)
  } elseif($maxlen -eq 1){
    $chainActs=@(); foreach($r in $rowsB){ if($r.Acts.Count -ge 1){$chainActs+=$r.Acts[0]} }
  } else {
    $d=$rowsB[0].Desc
    if($d -match 'COF|Merchant Initiated'){$chainActs=@('COF/MIT')}
    elseif($d -match 'POSCondition|Fuel'){$chainActs=@('Unclassified')}
    else{$chainActs=@('Other')}
  }
  $chainActs=@($chainActs)
  $anyFail=(@($rowsB | Where-Object { -not $_.Pass }).Count) -gt 0
  $baseInfos.Add([pscustomobject]@{Base=$g.Name;Chain=($chainActs -join ' -> ');Acts=$chainActs;Steps=$chainActs.Count;Msgs=@($rowsB).Count;TxnPass=(-not $anyFail);Ex=$rowsB[0].Id})
}

$nMsg=$recs.Count
$nTxn=$baseInfos.Count

$agg=foreach($cg in ($baseInfos | Group-Object Chain)){
  $b=@($cg.Group)
  [pscustomobject]@{
    Chain=$cg.Name; Acts=@($b[0].Acts); Steps=$b[0].Steps; Txns=$b.Count
    Msgs=(@($b)|Measure-Object Msgs -Sum).Sum
    TxnPass=@($b|Where-Object{$_.TxnPass}).Count
    TxnFail=@($b|Where-Object{-not $_.TxnPass}).Count
    Ex=@(@($b|Select-Object -First 4).Ex)
  }
}
$single=@($agg | Where-Object {$_.Steps -eq 1} | Sort-Object @{e={-$_.Txns}})
$multi =@($agg | Where-Object {$_.Steps -ge 2} | Sort-Object @{e={$_.Steps}},@{e={-$_.Txns}})
$ordered=@($single + $multi)
$nFlows=$agg.Count

$color=@{Authorization='#1565c0';Sale='#00838f';Completion='#2e7d32';PartialCompletion='#558b2f';DupCompletion='#7cb342';Void='#d84315';Timeout='#6a1b9a';Refund='#ef6c00';Cancellation='#b71c1c';'COF/MIT'='#455a64';Unclassified='#607d8b';Other='#546e7a'}
$dsc=@{Authorization='Reserve funds';Sale='Auth + capture';Completion='Capture prior auth';PartialCompletion='Capture partial amt';DupCompletion='Duplicate capture (neg test)';Void='Reverse pre-settlement';Timeout='Timeout reversal';Refund='Credit cardholder';Cancellation='Cancel transaction';'COF/MIT'='Merchant-initiated';Unclassified='POS-condition fuel';Other='Other'}

function NodeHtml($act,$idx){
  $c=$color[$act]; $d=$dsc[$act]
  $nm=$act; if($act -eq 'Unclassified'){$nm='(unclassified)'} elseif($act -eq 'COF/MIT'){$nm='COF / MIT'} elseif($act -eq 'PartialCompletion'){$nm='Partial Completion'} elseif($act -eq 'DupCompletion'){$nm='Dup Completion'}
  "<div class=""node"" style=""border-top-color:$c""><div class=""badge"" style=""background:$c"">$idx</div><div class=""nm"" style=""color:$c"">$nm</div><div class=""ds"">$d</div></div>"
}
function ChainHtml($acts){
  $parts=@(); $i=0
  foreach($a in @($acts)){ $i++; if($i -gt 1){$parts+='<div class="arrow"></div>'}; $parts+=(NodeHtml $a $i) }
  ($parts -join '')
}
function Pill($p,$f){
  $t=$p+$f
  if($f -eq 0){"<span class=""st ok"">All pass &middot; $p/$t</span>"}
  elseif($p -eq 0){"<span class=""st bad"">All fail &middot; 0/$t</span>"}
  else{"<span class=""st warn"">$p pass / $f fail</span>"}
}
function ChainDisplay($chain){ $chain -replace ' -> ',' &rarr; ' -replace 'PartialCompletion','Partial Completion' -replace 'DupCompletion','Dup Completion' }

$h=@()
$h+='<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">'
$h+='<title>ATL105 Visa - Corrected Flow List</title><style>'
$h+='* { box-sizing:border-box; } body { font-family:"Segoe UI",Tahoma,sans-serif; background:#1B5E20; margin:0; padding:24px; color:#1f2937; }'
$h+='.wrap { max-width:1180px; margin:0 auto; } .panel { background:#fff; border-radius:10px; box-shadow:0 2px 6px rgba(0,0,0,.18); padding:22px; margin-bottom:22px; }'
$h+='h1 { margin:0 0 4px; font-size:24px; } h2 { margin:0 0 16px; font-size:17px; color:#1B5E20; border-left:4px solid #388e3c; padding-left:10px; }'
$h+='.sub { color:#555; font-size:13.5px; line-height:1.6; } .kpis { display:grid; grid-template-columns:repeat(auto-fit,minmax(130px,1fr)); gap:12px; margin-top:14px; }'
$h+='.kpi { background:#f4f7f4; border-left:4px solid #388e3c; border-radius:6px; padding:12px 14px; } .kpi .v { font-size:26px; font-weight:700; color:#1B5E20; } .kpi .l { font-size:11px; color:#667; text-transform:uppercase; letter-spacing:.03em; }'
$h+='.legend { display:flex; flex-wrap:wrap; gap:10px; margin-top:12px; } .legend .lg { display:flex; align-items:center; gap:6px; font-size:12.5px; color:#333; background:#f6f8f6; border-radius:14px; padding:4px 10px; } .legend .dot { width:12px; height:12px; border-radius:3px; }'
$h+='table { border-collapse:collapse; width:100%; font-size:14px; } th,td { border-bottom:1px solid #e3e3e3; padding:8px 10px; text-align:left; } th { background:#eef3ee; color:#1B5E20; font-size:12px; text-transform:uppercase; letter-spacing:.03em; } td.num { text-align:right; font-variant-numeric:tabular-nums; } .fail { color:#d32f2f; font-weight:700; } tr.new td { background:#fffde7; }'
$h+='.grid { display:grid; grid-template-columns:repeat(auto-fill,minmax(300px,1fr)); gap:16px; } .list { display:grid; grid-template-columns:1fr; gap:16px; }'
$h+='.card { border:1px solid #eceff1; border-radius:10px; padding:14px 16px; background:linear-gradient(180deg,#ffffff,#fafcfa); } .card-head { display:flex; align-items:flex-start; gap:12px; margin-bottom:14px; }'
$h+='.rank { flex:none; width:30px; height:30px; border-radius:50%; background:#1B5E20; color:#fff; font-weight:700; font-size:14px; display:flex; align-items:center; justify-content:center; }'
$h+='.title { font-size:15.5px; font-weight:600; color:#20303a; } .meta { display:flex; flex-wrap:wrap; gap:6px; margin-top:6px; } .st { font-size:11.5px; font-weight:600; border-radius:12px; padding:2px 9px; }'
$h+='.st.tc { background:#e8eef7; color:#1565c0; } .st.steps { background:#ede7f6; color:#6a1b9a; } .st.ok { background:#e6f4ea; color:#1e7e34; } .st.warn { background:#fff4e5; color:#b25c00; } .st.bad { background:#fdecea; color:#c62828; } .st.tag { background:#e0f2f1; color:#00695c; }'
$h+='.chain { display:flex; flex-wrap:nowrap; align-items:center; padding:6px 0 4px; overflow-x:auto; } .node { flex:none; width:150px; background:#fff; border:1px solid #e3e7ea; border-top:5px solid #607d8b; border-radius:10px; box-shadow:0 1px 3px rgba(0,0,0,.12); padding:10px 12px 11px; text-align:center; position:relative; }'
$h+='.node .badge { position:absolute; top:-11px; left:50%; transform:translateX(-50%); width:22px; height:22px; border-radius:50%; color:#fff; font-size:12px; font-weight:700; display:flex; align-items:center; justify-content:center; box-shadow:0 1px 2px rgba(0,0,0,.25); } .node .nm { font-size:13.5px; font-weight:700; margin-top:4px; } .node .ds { font-size:11px; color:#78909c; margin-top:3px; line-height:1.25; }'
$h+='.arrow { flex:none; width:38px; height:0; border-top:3px solid #b0bec5; position:relative; margin:0 2px; } .arrow::after { content:""; position:absolute; right:-1px; top:-6px; border-left:11px solid #b0bec5; border-top:6px solid transparent; border-bottom:6px solid transparent; }'
$h+='.examples { margin-top:12px; font-size:11.5px; color:#90a4ae; font-family:"Courier New",monospace; } .note { font-size:12.5px; color:#455a64; line-height:1.7; } .note b { color:#1B5E20; }'
$h+='</style></head><body><div class="wrap">'

# Header panel
$nSingle=$single.Count; $nMulti=$multi.Count
$h+='<div class="panel"><h1>Corrected Transaction-Flow List &mdash; End-to-End Chains</h1>'
$h+='<div class="sub"><b>Spec:</b> ATL105 &nbsp;|&nbsp; <b>Card:</b> Credit Visa &nbsp;|&nbsp; <b>Platform:</b> BuyPass &nbsp;|&nbsp; <b>Suite:</b> Credit VISA Regression &nbsp;|&nbsp; <b>Source:</b> ATL105 Visa.html<br><b>Method:</b> the 529 message rows are grouped into their parent transactions by base TC id (trailing digit = step index); each flow is counted once per <b>transaction</b>, and a transaction is Passed only if every step passed.</div>'
$h+='<div class="kpis">'
$h+=('<div class="kpi"><div class="v">{0}</div><div class="l">Message Rows</div></div>' -f $nMsg)
$h+=('<div class="kpi"><div class="v">{0}</div><div class="l">Transactions</div></div>' -f $nTxn)
$h+=('<div class="kpi"><div class="v">{0}</div><div class="l">Distinct Flows</div></div>' -f $nFlows)
$h+=('<div class="kpi"><div class="v">{0}</div><div class="l">Single-step</div></div>' -f $nSingle)
$h+=('<div class="kpi"><div class="v">{0}</div><div class="l">Multi-step</div></div>' -f $nMulti)
$h+='</div><div class="legend">'
foreach($k in 'Authorization','Sale','Completion','PartialCompletion','DupCompletion','Void','Timeout','Refund','Cancellation'){
  $nm=$k; if($k -eq 'PartialCompletion'){$nm='Partial Completion'} elseif($k -eq 'DupCompletion'){$nm='Dup Completion'}
  $h+=('<span class="lg"><span class="dot" style="background:{0}"></span>{1}</span>' -f $color[$k],$nm)
}
$h+='</div></div>'

# Table panel
$h+=('<div class="panel"><h2>Distinct End-to-End Flows ({0})</h2>' -f $nFlows)
$h+='<table><thead><tr><th>#</th><th>Flow Scenario</th><th>Steps</th><th>Transactions</th><th>Messages</th><th>Txn Pass</th><th>Txn Fail</th><th>Pass %</th></tr></thead><tbody>'
$rank=0
foreach($a in $ordered){
  $rank++
  $pct=[math]::Round(($a.TxnPass/$a.Txns)*100)
  $failCell= if($a.TxnFail -gt 0){'<span class="fail">'+$a.TxnFail+'</span>'} else {'0'}
  $newCls= if($a.Chain -eq 'Authorization -> Completion -> Void' -or $a.Chain -eq 'Authorization -> Completion -> Timeout'){' class="new"'} else {''}
  $h+=('<tr{0}><td class="num">{1}</td><td>{2}</td><td class="num">{3}</td><td class="num">{4}</td><td class="num">{5}</td><td class="num">{6}</td><td class="num">{7}</td><td class="num">{8}%</td></tr>' -f $newCls,$rank,(ChainDisplay $a.Chain),$a.Steps,$a.Txns,$a.Msgs,$a.TxnPass,$failCell,$pct)
}
$h+='</tbody></table><div class="note" style="margin-top:8px">Highlighted rows are flows that were <b>absent from the original Flow Coverage Report</b>. Pass/Fail is transaction-level (all steps must pass).</div></div>'

# Pictorial single-step
$h+='<div class="panel"><h2>Single-step Flows ('+$nSingle+')</h2><div class="grid">'
$rank=0
foreach($a in $single){
  $rank++
  $ex='e.g. TC '+((@($a.Ex)) -join ', ')
  $h+='<div class="card"><div class="card-head"><div class="rank">'+$rank+'</div><div class="titlewrap"><div class="title">'+(ChainDisplay $a.Chain)+'</div><div class="meta"><span class="st tc">'+$a.Txns+' txn</span><span class="st steps">1-step</span>'+(Pill $a.TxnPass $a.TxnFail)+'</div></div></div><div class="chain">'+(ChainHtml $a.Acts)+'</div><div class="examples">'+$ex+'</div></div>'
}
$h+='</div></div>'

# Pictorial multi-step
$h+='<div class="panel"><h2>Multi-step Flows ('+$nMulti+')</h2><div class="list">'
foreach($a in $multi){
  $rank++
  $ex='e.g. TC '+((@($a.Ex)) -join ', ')
  $stepTxt=$a.Steps.ToString()+'-step'
  $h+='<div class="card"><div class="card-head"><div class="rank">'+$rank+'</div><div class="titlewrap"><div class="title">'+(ChainDisplay $a.Chain)+'</div><div class="meta"><span class="st tc">'+$a.Txns+' txn</span><span class="st steps">'+$stepTxt+'</span>'+(Pill $a.TxnPass $a.TxnFail)+'</div></div></div><div class="chain">'+(ChainHtml $a.Acts)+'</div><div class="examples">'+$ex+'</div></div>'
}
$h+='</div></div>'

# lookup helper for narrative numbers
function TxnOf($chain){ $x=$agg|Where-Object{$_.Chain -eq $chain}; if($x){$x.Txns}else{0} }

$h+='<div class="panel"><h2>What changed vs. the original Flow Coverage Report</h2><div class="note">'
$h+='&bull; <b>Counted per transaction, not per message.</b> The original report bucketed each of the 529 messages as its own &ldquo;flow&rdquo; (which is why <i>Void = 134</i> topped its list). Grouping messages into their parent transactions yields <b>'+$nTxn+' transactions</b> across <b>'+$nFlows+' distinct end-to-end flows</b>.<br>'
$h+='&bull; <b>Two flows were missing entirely</b> from the original report and are now surfaced (highlighted above): <b>Authorization &rarr; Completion &rarr; Void</b> ('+(TxnOf 'Authorization -> Completion -> Void')+' txn) and <b>Authorization &rarr; Completion &rarr; Timeout</b> ('+(TxnOf 'Authorization -> Completion -> Timeout')+' txn). The original only depicted Authorization &rarr; Completion &rarr; Dup Completion among 3-step flows.<br>'
$h+='&bull; <b>Multi-step flows were badly undercounted</b> because most chain members were mis-filed as standalone messages. Corrected transaction counts: Authorization&rarr;Completion <b>'+(TxnOf 'Authorization -> Completion')+'</b> (orig 15), Authorization&rarr;Void <b>'+(TxnOf 'Authorization -> Void')+'</b> (orig 9), Sale&rarr;Void <b>'+(TxnOf 'Sale -> Void')+'</b> (orig 6), Sale&rarr;Timeout <b>'+(TxnOf 'Sale -> Timeout')+'</b> (orig 5), Refund&rarr;Void <b>'+(TxnOf 'Refund -> Void')+'</b> (orig 9).<br>'
$h+='&bull; <b>Void, Completion, Timeout and Refund are not standalone flows.</b> Every one of those messages is the tail of a chain, so they no longer appear as 1-step flows.<br>'
$h+='&bull; <b>Partial Approval, AVS/CVV, AFP, eWallet (Apple/Google Pay) and Moneris are attributes, not flows.</b> They are entry-mode / response overlays on the chains above (e.g. eWallet auths are ordinary Authorization&rarr;Completion / Authorization&rarr;Cancellation transactions) and remain tracked as tags in the original report&rsquo;s sections 3&ndash;6.</div></div>'

$h+='</div></body></html>'

$html=$h -join "`n"
Set-Content 'ATL105 Visa - Corrected Flow List.html' -Value $html -Encoding UTF8

# summary for verification
$sum=@()
$sum+=('messages={0} transactions={1} flows={2} single={3} multi={4}' -f $nMsg,$nTxn,$nFlows,$nSingle,$nMulti)
$tt=0;$tm=0
foreach($a in $ordered){ $tt+=$a.Txns; $tm+=$a.Msgs; $sum+=('{0,4}txn {1,4}msg {2,3}P/{3,3}F  {4}-step  {5}' -f $a.Txns,$a.Msgs,$a.TxnPass,$a.TxnFail,$a.Steps,$a.Chain) }
$sum+=('SUM txn={0} msg={1}' -f $tt,$tm)
Set-Content 'gen_summary.txt' -Value $sum -Encoding UTF8
Write-Output 'DONE'
