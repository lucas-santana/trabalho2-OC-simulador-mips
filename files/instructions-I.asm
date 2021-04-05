#beq $s2, $zero, 1001 instrucoes beq ainda não funcionam
lw $s1, 1024($s0)
ori $s0, $zero, 0x1001
sll $s0, $s0, 16
ori $s0, $s0, 0x0000 
lw $s1, 0($s0)

loop:ori $s2, $zero, 0x1001
sll $s2, $s2, 16
ori $s2, $s2, 0x00004
sw $s1, 4($s2)
