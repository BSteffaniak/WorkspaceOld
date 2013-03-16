org 0100h;

mov ah, 09h;
mov dx, st;
int 021h;

mov ah, 01h;
int 021h;

cmp al, 079h;
	je  isY;
	jne notY;

isY:
	mov ah, 09h;
	mov dx, str2;
	int 021h;
	jmp exit;
notY:
	mov ah, 09h;
	mov dx, str3;
	int 021h;
	jmp exit;
exit:
	mov ah, 01h;
	int 021h;
	int 020h;

st:   db 'Hello World: ', '!', 0Dh, 0Ah, '$';
str2: db 0Dh, 0Ah, 'pressed y ', $-st, '$';
str3: db 0Dh, 0Ah, 'not y', '$';