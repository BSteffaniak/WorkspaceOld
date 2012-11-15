public class BradenNumba
{
	private int numba;
	
	public static void main(String args[])
	{
		BradenNumba numba = new BradenNumba();
		
		System.out.println(numba);
	}
	
	public BradenNumba()
	{
		numba = 0;
		
		System.out.println("Searching");
		
		for (int i = 0; i < 10000; i ++)
		{
			if (isBradenNumba(i))
			{
				System.out.println(i);
//				numba = i;
//				break;
			}
		}
	}
	
	private boolean isBradenNumba(int numba)
	{
		String s = numba + "";
		
		int numbers[] = new int[s.length()];
		
		char arr[] = s.toCharArray();
		
		int num = 0;
		
		for (int i = 0; i < arr.length; i ++)
		{
			numbers[i] = Integer.valueOf(arr[i] + "");
			
			num += numbers[i];
		}
		
		for (int i = 0; i < numbers.length; i ++)
		{
			if ((i % 2 == 0 && isPrime(numbers[i])) || (i % 2 != 0 && !isPrime(numbers[i])))
			{
				return false;
			}
		}
		
		if (true)
		{
			if (isPalindrome(num) && isPrime(num))
			{
				return true;
			}
		}
		
//		while (prod < (numba * numba))
//		{
//			number = (prod) % 10;
//			
//			prod = prod * number;
//			
//			if (prod == (numba * numba))
//			{
//				return true;
//			}
//			else if (prod == 0)
//			{
//				break;
//			}
//		}
		
		return false;
	}
	
	private boolean isPrime(int numba)
	{
		for (int i = numba - 1; i > 1; i --)
		{
			if (numba % i == 0)
			{
				return false;
			}
		}
		
		return numba >= 2;
	}
	
	public boolean isPalindrome(int numba)
	{
		if (numba < 11)
		{
			return false;
		}
		
		String s = numba + "";
		
		int numbas[] = new int[s.length()];
		
		char arr[] = s.toCharArray();
		
		for (int i = 0; i < arr.length; i ++)
		{
			numbas[i] = Integer.valueOf(arr[i] + "");
		}
		
		for (int i = 0; i < numbas.length; i ++)
		{
			if (numbas[i] != numbas[numbas.length - i - 1])
			{
				return false;
			}
		}
		
		return true;
	}
	
	public String toString()
	{
		return "" + numba;
	}
}