import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class Driver
{
	public Driver()
	{
		try
		{
			PrintWriter writer = new PrintWriter(new FileWriter(new File("nums.txt")));
			
			for (int i = 10000; i < 20000; i++)
			{
				writer.println(i);
			}
			
			writer.close();
			
			System.out.println("done");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		new Driver();
	}
}