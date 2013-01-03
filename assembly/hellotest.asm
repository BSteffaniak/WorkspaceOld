jmp 145
db 'Hello, world!', 0D, 0A, 'press any key to continue.$'

mov ah, 09
mov dx, 102
int 21
mov ah, 08
int 21
int 20