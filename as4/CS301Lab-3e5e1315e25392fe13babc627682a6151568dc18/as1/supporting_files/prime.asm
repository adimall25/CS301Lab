	.data
a:
	23
	.text
main:
	load %x0, $a, %x3
	addi %x0, 1, %x8
	beq %x3, %x8, notprime
	addi %x0, 2, %x4	;iterator
	subi %x3, 1, %x7
loop:
	bgt %x4, %x7, prime
	div %x3, %x4, %x6
	beq %x31, %x0, notprime
	addi %x4, 1, $x4
	jmp loop
notprime:
	subi %x0, 1, %x10
	end
prime:
	addi %x0, 1, %x10
	end