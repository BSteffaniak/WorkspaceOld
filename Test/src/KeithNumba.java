public class KeithNumba
{
	public static void main(String args[])
	{
		new KeithNumba();
	}
	
	public KeithNumba()
	{
		for (int i = 0; i < 10000000; i ++)
		{
			if (isKeith(i))
			{
				System.out.println(i);
			}
		}
		
		System.out.println("Done");
	}
	
	private boolean isKeith(int num)
	{
		String s = num + "";
		
		int numbers[] = new int[s.length()];
		
		int sum = 0;
		
		for (int i = 0; i < s.length(); i ++)
		{
			numbers[i] = Integer.valueOf(s.substring(i, i + 1));
			
			sum += numbers[i];
		}
		
		while (sum < num)
		{
			for (int i = 0; i < numbers.length - 1; i ++)
			{
				numbers[i] = numbers[i + 1];
			}
			
			numbers[numbers.length - 1] = sum;
			
			sum = 0;
			
			for (int numb : numbers)
			{
				sum += numb;
			}
			
			if (num == sum)
			{
				return true;
			}
		}
		
		return false;
	}
}