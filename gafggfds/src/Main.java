import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Main
{
	public static void main(String args[])
	{
		new Main();
	}
	
	public Main()
	{
		Integer num1 = 4;
		Integer num2 = 5;
		
		swap(num1, num2);
		
		System.out.println(num1 + ", " + num2);
	}
	
	private void swap(Integer a, Integer b)
	{
		Integer temp = a;
		a = b;
		b = temp;
	}
}