mov AX, 0x9C;
mov SS, AX;
mov SP, 4096D;
mov AX, 0x7C0;
mov DS, AX;

labl db "Hello, OS!", 0xD, 0xA, '$';
index db 0D;


;mov ax, 0x0600
;mov bh, 0x40
;mov cx, 0x0000
;mov dx, 0x184F
;int 10h

looper:
	jmp exit;printLabl;
	
printLabl:
	xor CX, CX;
	lablLoop:
		mov AH, 0x0E;
		mov AL, [labl];
		int 0x10;
		inc CX;
		
		cmp CX, 9
		je exit;
		
		jmp lablLoop;

exit:
		mov AH, 0x0E;
		mov AL, [labl];
		int 0x10;
		
		mov AH, 0x0E;
		mov AL, [labl+1];
		int 0x10;
		
		mov AH, 0x0E;
		mov AL, [labl+2];
		int 0x10;
		
		mov AH, 0x0E;
		mov AL, [labl+3];
		int 0x10;
		
		mov AH, 0x0E;
		mov AL, [labl+4];
		int 0x10;

	mov AH, 0x08;
	int 0x10;
	
	jmp $;

times 510-($-$$) db 0;
dw 0xAA55;