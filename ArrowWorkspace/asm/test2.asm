org 0x100;
mov ah, display
mov dx, string2
int 0x21
int 0x20

lab: db 'Hello, World4!', 0Dh, 0Ah, '$'

display equ 0x9
length db $-lab
string2 db 'Hello, l: ', length, '!', 0xD, 0xA, '$'