	.data
a:
	123
	.text
main:
	addi %x0, 2, %x3
	load %x0, $a, %x4
	subi %x4, 1, %x5
	addi %x0, 0, %x7
loop:
	bgt %x3, %x5, temp
	div %x4, %x3, %x6
	beq %31, %x7, notprime
	addi %x3, 1, %x3
	jmp loop
temp:
	addi %x0, 1, %x10
	end
notprime:
	subi %x0, 1, %x10
	end