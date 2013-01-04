org 100;

mov bx,ax ; general register to general register
mov [char],al ; general register to memory
mov bl,[char] ; memory to general register
mov dl,32 ; immediate value to general register
mov [char],32 ; immediate value to memory
mov ax,ds ; segment register to general register
mov [bx],ds ; segment register to memory
mov ds,ax ; general register to segment register
mov ds,[bx] ; memory to segment register
mov eax,cr0 ; control register to general register
mov cr3,ebx