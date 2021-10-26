	.data
a:
	8
	.text
main:
	load %x0, $a, %x3
	divi %x3, 2, %x4
	addi %x0, 1, %x5
	beq %x31, %x5, odd
	beq %x31, %x0, even
odd:
	addi %x0, 1, %x10
	end
even:
	subi %x0, 1, %x10
	end