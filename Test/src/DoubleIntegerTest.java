public class DoubleIntegerTest
{
	public static void main(String args[])
	{
		new DoubleIntegerTest();
	}
	
	public DoubleIntegerTest()
	{
		double arr[] = new double[]
		{
			0, 6.0, 3.0000000000000001, 5.0
		};
		
		System.out.println(searchMe(arr, 3));
	}
	
	public boolean searchMe(double array[], int target)
	{
		for (int i = 0; i < array.length; i ++)
		{
			if (array[i] == target)
			{
				return true;
			}
		}
		
		return false;
	}
}