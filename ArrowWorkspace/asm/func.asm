segment .data
	
segment .bss
	
segment .text
	global gcdmem
	gcdmem:
		;enter 0, 0
		push	ebp
		mov		ebp, esp
		mov		eax, [ebp+8]
		mov		ebx, [ebp+12]
		
		looptop:
			cmp		eax, 0
			je		goback
			cmp		eax, ebx
			jge		modulo
			xchg	eax, ebx
		modulo:
			cdg
			idiv	ebx
			mov		eax, edx
			jmp		looptop
		goback:
			mov		eax, ebx
			mov		esp, ebp
			pop		ebp
			ret