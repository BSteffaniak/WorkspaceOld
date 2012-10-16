public class Main
{
	
	
	public static void main(String args[])
	{
		new Main();
	}
	
	public Main()
	{
		int num1 = 4;
		int num2 = 5;
		
		swap(num1, num2);
		
		System.out.println(num1 + ", " + num2);
	}
	
	private void swap(Integer a, Integer b)
	{
		Integer a2 = a;
		Integer b2 = b;
		a = b2;
		b = a2;
	}
	
	
}