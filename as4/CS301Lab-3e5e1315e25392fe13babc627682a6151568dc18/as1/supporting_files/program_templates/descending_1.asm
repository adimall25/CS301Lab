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
	load %x0, $n, %x3	;x3 stores the number of elements
	subi %x3, 1, %x9	;x9 stores the index of last element 
	addi %x0, 0, %x4	;x4 is the outer loop iterator
	addi %x0, 1, %x5	;x5 is the inner loop iterator
outerloop:
	bgt x4, x9, endl
	load %x4, $a, %x6	;x6 is the outer loop element
	jmp innerloop
innerloop:
	bgt %x5, %x9, gotoouter
	load %x5, $a, %x7	;x7 is the inner loop element
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