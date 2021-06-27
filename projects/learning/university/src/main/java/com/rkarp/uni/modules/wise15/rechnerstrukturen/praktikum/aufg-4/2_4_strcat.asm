;====================================
; Ihr Hauptprogramm
;====================================
  
Start:

    movi r10, 8
    lsli    r10, 12

    movi r0, 0

    movi r11, 8
	lsli    r11, 12
	bseti r11, 8
 
	jsr strcat

     	halt
;=================================================
LDR5:
     ldw    r5, (r15)
     ;addi   r15, 2
     jmp    r15

;=================================================
; Unterprogramm strcpy (Aufg 2.4)
;================================================ 
strcat:

	ldw r1, 0(r10)
	cmpe r1, r0
	bt catString
	addi r10, 2
	br strcat

catString:  
	ldw r1, 0(r11)
	stw r1, 0(r10)
	cmpe r1, r0
	bt ldr5
	addi r10, 2
	addi r11, 2
	br catString

;=================================================
;  Ein String
;=================================================
     .org    0x8000	; Adresse
     .ascii  "Ein S"	; der String
     .defw   0		; terminierendes Null

     .org    0x8100 
     .ascii  "ring"
     .defw 0
