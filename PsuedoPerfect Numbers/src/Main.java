import java.util.ArrayList;
import java.util.HashSet;

public class Main
{
	private static final double EPSILON = 0.00000000000001;
	
	private static final HashSet<Long> primes = new HashSet<Long>();
	
	public static void main(String args[])
	{
		for (long i = 0; i < 1000000/*Long.MAX_VALUE*/; i ++)
		{
			if (isPrime(i))
			{
				primes.add(i);
			}
		}
		System.out.println("Done");
		//new Main();
	}
	
	public Main()
	{
		
		
		for (long i = 2; i < Long.MAX_VALUE; i ++)
		{
			boolean isPsuedo = false;
			
			ArrayList<Long> primaryFactors = getPrimaryFactors(i);
			
			double sum = 0;
			
			for (int j = 0; j < primaryFactors.size(); j ++)
			{
				sum += (1d / primaryFactors.get(j));
			}
			
			sum += (1d / i);
			
			if (1d - sum < EPSILON && sum <= 1 + EPSILON)
			{
				isPsuedo = true;
			}
//			if (sum == 1d)
//			{
//				isPsuedo = true;
//			}
			
//			System.out.println(sum);
			if (isPsuedo)
			{
				System.out.println(i);
			}
		}
	}
	
	private ArrayList<Long> getPrimaryFactors(long num)
	{
		ArrayList<Long> primes = new ArrayList<Long>();
		
		if (num == 2)
		{
			primes.add(2l);
			
			return primes;
		}
		
		for (long i = 2; i < num / 2 + 1; i ++)
		{
			if (num % i == 0)
			{
				if (isPrime(i))
				{
					primes.add(i);
				}
			}
		}
		
		return primes;
	}
	
	private static boolean isPrime(long n)
	{
//		for (long i = 2; i < n / 2 + 1; i ++)
//		{
//			if (n % i == 0)
//			{
//				return false;
//			}
//		}
		
		
		if (n < 2) return false;
		if (n == 2 || n == 3) return true;
		if (n % 2 == 0 || n % 3 == 0) return false;
		long sqrtN = (long)Math.sqrt(n) + 1;
		for (long i = 6; i <= sqrtN; i += 6)
		{
			if (n % (i - 1) == 0 || n % (i + 1) == 0) return false;
		}
		
//		return !new String(new char[(int) n]).matches(".?|(..+?)\\1+");
		
		return true;
	}
}