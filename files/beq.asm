#fica incrementando o registrador $t0
loop: add $t3, $t3, $s1         #$t3=0     	    $s1 = 1
slt $t4, $t3,$s3                #$t4 = 0     	$s3 = 3
beq $t4, $s1, loop #teste
