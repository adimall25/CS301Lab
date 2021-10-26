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
	subi %x3, 1, %x9
	addi %x0, 0, %x4
	addi %x0, 1, %x5
outerloop:
	bgt x4, x9, endl
	load %x4, $a, %x6
	jmp innerloop
innerloop:
	bgt %x5, %x9, gotoouter
	load %x5, $a, %x7
	bgt %x7, %x6, swap
	jmp gotoinner
swap:
	add %x0, %x6, %x8
	store %x7, $a, %x4
	store %x8, $a, %x5
	add %x0, %x7, %x6
	add %x0, %x8, %x7
	jmp gotoinner
gotoinner:
	addi %x5, 1, %x5
	jmp innerloop
gotoouter:
	addi %x4, 1, %x4
	addi %x4, 1, %x5
	jmp outerloop
endl:
	end