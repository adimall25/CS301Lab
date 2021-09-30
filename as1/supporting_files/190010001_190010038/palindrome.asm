	.data
a:
	101
	.text
main:
	load %x0, $a, %x6
	load %x0, $a, %x8
	add %x0, %x0, %x4
	addi %x0, 1, %x7
loop:
	blt %x8, %x7, temp
	divi %x8, 10, %x8
	muli %x4, 10, %x4
	add %x4, %x31, %x4
	jmp loop
temp:
	beq %x4, %x6, palindrome
	subi %x0, 1, %x10
	end
palindrome:
	addi %x0, 1, %x10
	end