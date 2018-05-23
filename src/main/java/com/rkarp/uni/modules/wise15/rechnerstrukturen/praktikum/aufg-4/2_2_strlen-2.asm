;====================================
; Ihr Hauptprogramm
;====================================
  
Start:
     jsr LDR5
     .defw 0x8000
     mov r10, r5

     movi r11, 0
  
     jsr strlen

     halt

jumpback:
     jmp r15
;=================================================
LDR5:
     ldw    r5, (r15)
     addi   r15, 2
     jmp    r15
;=================================================
; Unterprogramm strlen (Aufg 2.2)
;================================================ 
strlen:
	movi r0, 0
	ldw r1, 0(r10)
    cmpe r1, r0
	bt jumpback
	addi r11, 1
	addi r10, 2
	br strlen

;=================================================
;  Ein String
;=================================================
     .org    0x8000	; Adresse
     .ascii  "Ein S"	; der String
     .defw   0		; terminierendes Null

     .org    0x8100 
     .ascii  "ring"
     .defw 0
