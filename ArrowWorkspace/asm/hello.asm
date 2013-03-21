.386
.model flat, stdcall
option casemap :none

include c:/masm32/include/windows.inc
include c:/masm32/include/kernel32.inc
include c:/masm32/include/masm32.inc

includelib kernel32.lib
includelib masm32.lib

.data
	hello db "Hello World!", 0
	
.code
	start: 
		invoke StdOut, addr hello
		invoke ExitProcess, 0
	end start