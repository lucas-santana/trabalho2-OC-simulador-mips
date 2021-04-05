#fica incrementando o registrador zero
loop: add $zero,$zero,$s1
slt $s3, $s1,$s2
beq $s2, $zero, loop #teste
