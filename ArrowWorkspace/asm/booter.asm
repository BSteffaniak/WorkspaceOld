mov AX, 0x9C;
mov SS, AX;
mov SP, 4096D;
mov AX, 0x7C0;
mov DS, AX;

;-------------------------------------

labl db "Welome to Awfudl OS!$";

;mov AH, 0x0E;
;mov AL, 0x33;
;int 0x10;

jmp printLabl;

;mov es, [labl];
;mov bp, 0;
;mov ah, 0x13;
;mov al, 0x01;
;mov cx, 20d;
;mov dh, 5d;
;mov dl, 5d;
;int 0x10;

;jmp clearScreen;

exit:
	endLoop:
		mov ah, 0xc;
		mov al, 0xa;
		mov cx, 10;
		mov dx, 10;
		int 0x10;
		
		jmp endLoop;

;clearScreen:
;	mov AH, 0x02		; Set cursor pos
;	mov DH, 0			; Row
;	mov DL, 0			; Column
;	int 0x10			; 

;	mov AH, 0x06		; 
;	mov AL, 0x00		; 
;	mov BH, 0x30		; 
;	mov BL, 0x30		; 
;	mov DX, 0xF8FF		; 
;	int 0x10			; 
	
;	mov AH, 0x09		; 
;	mov AL, 0x0			; Clear the screen with the empty character.
;	mov CX, 0x1000		; 
;	mov BL, 0x0A		; 
;	int 0x10			; 

;	mov AH, 0x0A		; 
;	mov CX, 0x1000		; 
;	int 0x10			; 
	
;	jmp printLabl		; 

printLabl:
	xor si, si;

	printLoop:
		mov cl, [labl + si];
		cmp cl, 20;
			jl exit;
		mov ah, 0x0E;
		mov al, [labl + si];
		int 0x10;
		inc si;
		jmp printLoop;

;fname db "test.txt", 0x00;

;createFile:
;	mov ah, 0x0e
;	mov al, 0x33;
;	int 0x10;
;	
;	mov AH, 0x3C;
;	mov DX, fname;
;	mov cx, 0x0;
;	int 0x10;

;-------------------------------------

times 510-($-$$) db 0;
dw 0xAA55;