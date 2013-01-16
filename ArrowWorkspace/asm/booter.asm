mov ax, 0x9c;
mov ss, ax;
mov SP, 4096D;
mov AX, 0x7C0;
mov DS, AX;

labl db "Hello, OS!", 0xd, 0xa, '$';
index db 0D;

looper:
	jmp printLabl;
	
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
	mov AH, 0x08;
	int 0x10;
	
	jmp $;

times 510-($-$$) db 0;
dw 0xAA55;