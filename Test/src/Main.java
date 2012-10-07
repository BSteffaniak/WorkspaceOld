import java.math.BigDecimal;

public class Main
{
	public static void main(String args[])
	{
		new Main();
	}
	
	public Main()
	{
		double x = -10;
		double y = -1;
		
		int quadrant = 0;
		
		if (x < 0)
		{
			if (y < 0)
			{
				quadrant = 2;
			}
			else
			{
				quadrant = 1;
			}
		}
		else
		{
			if (y < 0)
			{
				quadrant = 3;
			}
			else
			{
				quadrant = 0;
			}
		}
		
		double opposite = Math.abs(x);
		double adjacent = Math.abs(y);
		
		double degrees = Math.toDegrees(Math.atan(opposite / adjacent));
		
		degrees += 90 * quadrant;
		
		degrees %= 360;
		
		System.out.println(degrees);
		
//		BigDecimal answer = new BigDecimal(0);
//		
//		boolean add = true;
//		
//		Thread.currentThread().setPriority(10);
//		
//		for (long i = 1; i < Long.MAX_VALUE / 9999999999l; i ++)
//		{
//			if (add)
//			{
//				answer.add(new BigDecimal(1d/(i*i)));
//			}
////			else
////			{
////				answer -= 1d/i;
////			}
//			
////			add = !add;
//		}
//		
////		answer *= 4;
//		
////		double o = 1;
////		
////		for (long i = 0; i < Long.MAX_VALUE / 9999999999l; i += 1)
////		{
////			answer += (i * i) / o;
////			
////			o += 2;
////		}
//		
//		answer.multiply(new BigDecimal(6));
//		
//		double ans = answer.doubleValue();
//		System.out.println(Math.sqrt(ans));
	}
}