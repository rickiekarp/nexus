;====================================
; Ihr Hauptprogramm
;====================================
  
Start:
	movi r0, 3
	movi r1, 1
	movi r2, 3

	movi r12, 1

	.stack r14
	movi r14, 0

	.push r2

	jsr towers

     	halt
;=================================================
LDR5:
     ldw    r5, (r15)
     ;addi   r15, 2
     jmp    r15

;=================================================
; Unterprogramm strcpy (Aufg 2.3)
;================================================ 
towers:

	cmpe r0, r12
	bt printf
	movi r4, 6
	subi r4, r1
	subi r4, r2
	subi r0, 1

;=================================================
;  Ein String
;=================================================
     .org    0x8000	; Adresse
     .ascii  "Ein S"	; der String
     .defw   0		; terminierendes Null

     .org    0x8100 
     .ascii  "ring"
     .defw 0
