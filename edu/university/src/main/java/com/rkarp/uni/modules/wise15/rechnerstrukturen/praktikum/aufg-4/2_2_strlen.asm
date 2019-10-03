;====================================
; Ihr Hauptprogramm
;====================================
  
Start:
     movi r10, 8
     lsli r10, 12
     movi r0, 0
     movi r11, 0
  
     jsr strlen

     halt
;=================================================
LDR5:
     ldw    r5, (r15)
     ;addi   r15, 2
     jmp    r15
;=================================================
; Unterprogramm strlen (Aufg 2.2)
;================================================ 
strlen:

	ldw r1, 0(r10)
    cmpe r1, r0
	bt ldr5
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
