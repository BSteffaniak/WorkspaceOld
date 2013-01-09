org 0x100;

mov ah, 0x9;
mov dx, st;
int 0x21;

mov ah, 0x1;
int 0x21;

cmp al, 0x79;
	je  isY;
	jne notY;

isY:
	mov ah, 0x9;
	mov dx, str2;
	int 0x21;
	jmp exit;
notY:
	mov ah, 0x9;
	mov dx, str3;
	int 0x21;
	jmp exit;
exit:
	mov ah, 0x1;
	int 0x21;
	int 0x20;

st   db 'Hello World: ', '!', 0xD, 0xA, '$';
len equ $-st;
str2 db 0xD, 0xA, 'pressed y ', len, '$';
str3 db 0xD, 0xA, 'not y', '$';