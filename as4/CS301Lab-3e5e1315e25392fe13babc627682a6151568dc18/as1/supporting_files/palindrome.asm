	.data
a:
	1221
	.text
main:
	load %x0, $a, %x3
	add %x0, %x3, %x6
	add %x0, %x0, %x4
	add %x0, %x0, %x5
loop:
	muli %x4, 10, %x4
	divi %x3, 10, %x3
	add %x4, %x31, %x4
	beq %x5, %x3, temp
	jmp loop
temp:
	beq %x6, %x4, palindrome
	jmp endl
palindrome:
	addi %x0, 1, %x10
	end
endl:
	subi %x0, 1, %x10
	end