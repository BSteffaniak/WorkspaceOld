.386
.model flat,stdcall
option casemap:none
include C:\masm32\include\windows.inc
include C:\masm32\include\user32.inc
include C:\masm32\include\kernel32.inc
includelib C:\masm32\lib\user32.lib
includelib C:\masm32\lib\kernel32.lib

Java_ShowMessage_HelloDll PROTO :DWORD, :DWORD, :DWORD

;This macro returns pointer to the function table in fnTblPtr

GetFnTblPtr MACRO envPtr, fnTblPtr
	mov ebx, envPtr
	mov eax, [ebx]
	mov fnTblPtr, eax
ENDM

;This macro returns pointer to desired function in fnPtr.

GetFnPtr MACRO fnTblPtr, index, fnPtr
	mov eax, index
	mov ebx, 4
	mul ebx
	mov ebx, fnTblPtr
	add ebx, eax
	mov eax, [ebx]
	mov fnPtr, eax
ENDM


.data
  Caption		db "JAV_ASM",0
  ErrorMsg		db "String conversion error",0
  SccsMsg		db "MessageBox displayed",0

.code
hwEntry proc hInstance:HINSTANCE, reason:DWORD, reserved1:DWORD

	mov eax, TRUE
	ret

hwEntry endp

Java_ShowMessage_HelloDll proc JNIEnv:DWORD, jobject:DWORD, Msgptr:DWORD

  LOCAL fntblptr		:DWORD
  LOCAL Message 	:DWORD
  LOCAL fnptr		:DWORD
  

	GetFnTblPtr JNIEnv, fntblptr	; get pointer to function table
      GetFnPtr fntblptr, 169, fnptr	; get pointer to GETstringUTFChars

	push NULL		; push
	push Msgptr	      ; parameters for
	push JNIEnv	      ; GetStringUTFChars

	call [fnptr]	; call GetStringUTFChars

	mov Message, eax

	.if eax == NULL
		invoke MessageBox, NULL, addr ErrorMsg, addr Caption, 16

		GetFnPtr fntblptr, 167, fnptr	; get pointer to NewStringUTF

		push offset ErrorMsg		; push parameters for
		push JNIEnv			      ; NewStringUTF

		call [fnptr]			; call NewStringUTF

	.else
		invoke MessageBox, NULL, Message, addr Caption, 64

		push Message
		push Msgptr
		push JNIEnv

		call [fnptr]		      ; release string


		GetFnPtr fntblptr, 167, fnptr	; get pointer to NewStringUTF

		push offset SccsMsg		; push parameters for
		push JNIEnv			      ; NewStringUTF

		call [fnptr]			; call NewStringUTF

	.endif

	ret

Java_ShowMessage_HelloDll endp

End hwEntry