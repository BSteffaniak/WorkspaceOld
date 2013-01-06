#include <stdio.h>

int gcdmem(int a, int b);

int main()
{
	int result;
	
	result = gcdmem(150, 34);
	printf("The gcd is %d\n", result);
	return 0;
}