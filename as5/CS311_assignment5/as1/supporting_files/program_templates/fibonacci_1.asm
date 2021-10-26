	.data
n:
	10
	.text
main:
	load %x0, $n, %x3	;x3 stores the number of terms
	addi %x0, 65536, %x4	;x4 stores the last address + 1
	addi %x0, 1, %x5	;x5 is counter variable
	addi %x0, 0, %x6	;x6 stores first number and 0
	addi %x0, 1, %x7	;x7 stores second number and 1
	sub %x4, %x5, %x8	;x8 tells where to store current number
	store %x6, 0, %x8
	beq %x7, %x3, endl
	addi %x5, 1, %x5
	sub %x4, %x5, %x8
	store %x7, 0, %x8
	addi %x0, 2, %x20
	beq %x3, %x20, endl
	addi %x5, 1, %x5
loop:
	bgt %x5, %x3, endl
	sub %x4, %x5, %x8
	add %x6, %x7, %x9
	store %x9, 0, %x8
	add %x0, %x7, x6
	add %x0, %x9, %x7
	addi %x5, 1, %x5
	jmp loop
endl:
	end