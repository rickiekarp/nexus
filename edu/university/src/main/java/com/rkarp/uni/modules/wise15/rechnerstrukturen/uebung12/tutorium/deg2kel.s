.data
fmt:
    .ascii "Das Ergebnis ist %d\n"

.text

.global main

main:
	push %ebp
	mov %esp, %ebp

	movl $0x1, 0(%esp)
	call deg2Kel

	push %eax
	push $fmt
	call printf

	leave
	ret

deg2Kel:

	push %ebp
	movl %esp, %ebp

	movl 8(%ebp), %eax
	addl $0x111, %eax

	movl %ebp, %esp
	pop %ebp
	ret
