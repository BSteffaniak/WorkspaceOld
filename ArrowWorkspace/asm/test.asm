org 100h
mov ah, 9h
mov dx, string
int 21h
int 20h

string db 'Hello, World5!64', 0Dh, 0Ah, '$'