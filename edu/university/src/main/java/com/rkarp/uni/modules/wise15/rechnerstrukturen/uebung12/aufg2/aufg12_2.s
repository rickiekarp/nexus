.data
fmt:
    .ascii "Das Ergebnis ist %d\n"

.text

.global main

main:
	push %ebp
	mov %esp, %ebp

	movl $100, 0(%esp)
	call fah2cel

	push %eax
	push $fmt
	call printf

	leave
	ret

fah2cel:

	push %ebp
	movl %esp, %ebp

	movl 8(%ebp), %eax
	subl $32, %eax
	imull $142, %eax
	shr $8,%eax

	movl %ebp, %esp
	pop %ebp
	ret
