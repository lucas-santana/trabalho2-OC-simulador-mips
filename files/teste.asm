
add $s7, $s1, $s2
sub $s6, $s1,$s2
or $s5,$s1, $s2 
and $s4,$s1, $s2

#verifica se s1 é menor do que s2
slt $s3, $s1,$s2

ori $s0, $zero, 0x1001
sll $s0, $s0, 16
ori $s0, $s0, 0x0000 
lw $s1, 0($s0)


ori $s2, $zero, 0x1001
sll $s2, $s2, 16
ori $s2, $s2, 0x00004
sw $s1, 4($s2)
