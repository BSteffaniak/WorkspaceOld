import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
* File:  			Division.java
* Author:			Braden Steffaniak
* Programming:  	APCS
* Last Modified: 	8Nov2012
* Description: Shows off a few AWESOME math methods.
*/

public class Division
{
	int variables[];
	
	/**
	 * The entry point of the program in which the program starts.
	 * Runs the functions from the Division class.
	 * 
	 * @param args The command line arguments.
	 */
	public static void main(String args[])
	{
		Division d = new Division();
		
		try
		{
			d.read();
			
			System.out.println("Read the variables: " + d.variables[0] +
					", " + d.variables[1] + ", " + d.variables[2] +
					", " + d.variables[3]);
			
			System.out.println("GCF of first two: " + d.gcf());
			System.out.println("LCM of last two: " + d.lcm());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Method used to find the greatest common factor of the
	 * first two integers read from the file.
	 * 
	 * @return The greatest common factor.
	 */
	private int gcf()
	{
		int max = Math.max(variables[0], variables[1]);
		int min = Math.min(variables[0], variables[1]);
		
		for (int i = min; i > 1; i --)
		{
			if (max % i == 0 && min % i == 0)
			{
				return i;
			}
		}
		
		return 1;
	}
	
	/**
	 * Method used to find the least common multiple of the
	 * last two integers read from the file.
	 * 
	 * @return The least common multiple.
	 */
	private int lcm()
	{
		int max = Math.max(variables[2], variables[3]);
		int min = Math.min(variables[2], variables[3]);
		
		for (int i = max;; i += max)
		{
			if (i % max == 0 && i % min == 0)
			{
				return i;
			}
		}
	}
	
	/**
	 * Method that reads integers from a file and stores them
	 * in the variables array.
	 * 
	 * @throws IOException
	 */
	private void read() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(
				new File("data.txt")));
		
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