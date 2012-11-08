import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Division
{
	int variables[];
	
	public static void main(String args[])
	{
		Division d = new Division();
		
		
	}
	
	public Division()
	{
//		write(40, 6, 7, 73);
		try
		{
			read();
			
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private int gcf()
	{
		return 0;
	}
	
	private int lcm()
	{
		return 0;
	}
	
	private void read() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(new File("data.txt")));
		
		String line = br.readLine();
		
		String numbers[] = line.split(" ");
		
		variables = new int[numbers.length];
		
		for (int i = 0; i < numbers.length; i ++)
		{
			variables[i] = Integer.valueOf(numbers[i]);
		}
		
		br.close();
	}
	
	private void write(int ... variables)
	{
		try
		{
			PrintWriter pw = new PrintWriter(new FileWriter(new File("data.txt")));
			
			pw.write(variables[0] + " " + variables[1] + " " + variables[2] + " " + variables[3]);
			
			pw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}