include 'win32ax.inc'
.code
start:
	invoke MessageBoxA, 0, "TEST", "TEST", MB_YESNO + MB_ICONQUESTION
	cmp eax, IDYES
	je clickedYes
	jne clickedNo
.end start

clickedYes:
	invoke MessageBoxA, 0, "YAY", "CLICKED YES", MB_OK + MB_ICONQUESTION
	invoke ExitProcess, 0

clickedNo:
	invoke MessageBoxA, 0, "why did you click no :(", "why", MB_OK + MB_ICONQUESTION
	invoke ExitProcess, 0