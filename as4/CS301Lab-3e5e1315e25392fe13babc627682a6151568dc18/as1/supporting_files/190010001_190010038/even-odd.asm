	.data
a:
	11
	.text
main:
	load %x0, $a, %x6
	addi %x0, 1, %x4
	divi %x6, 2, %x8
	beq %x31, %x0, even
	beq %x31, %x4, odd
even:
	subi %x0, 1, %x10
	end
odd:
	addi %x10, 1, %x10
	end