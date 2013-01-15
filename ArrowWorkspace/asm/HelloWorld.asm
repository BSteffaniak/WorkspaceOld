.model small
.stack 0x100
.data
message db "Hello, world!", 0xd, 0xa, '$'

.code
main proc
	mov ax, @data
	mov ds, ax
	
	mov ah, 9
	mov dx, offset message
	int 0x21
	
	mov ax, 0x4c00
	int 0x21
main endp
end main