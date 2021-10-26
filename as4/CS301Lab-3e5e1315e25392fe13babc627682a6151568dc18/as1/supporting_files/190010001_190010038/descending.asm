.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
	load %x0, $n, %x3
	subi %x3, 1, %x4
	subi %x3, 2, %x7
	addi %x0, 0, %x5
	addi %x0, 0, %x6
loop:
	bgt %x5, %x4, endUpperLoop
	addi %x0, 0, %x6
InnerLoop:
	bgt %x6, %x7, increment
	addi %x6, 1, %x8
	load %x6, $a, %x9
	load %x8, $a, %x10
	blt %x9, %x10, swap
	addi %x6, 1, %x6
	jmp InnerLoop
increment:
	addi %x5, 1, %x5
	jmp loop
swap:
	addi %x9, 0, %x11
	addi %x10, 0, %x9
	addi %x11, 0, %x10
	store %x9, $a, %x6
	store %x10, $a, %x8
	addi %x6, 1, %x6
	jmp InnerLoop
endUpperLoop:
	end