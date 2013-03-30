import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

/**
 * File Name:	Driver.java
 * Programming:	APCS
 * Author:		Braden Steffaniak
 * Date:		18Mar2013
 * Description:	Class that tests out the functionality the three
 * 	challenge functions.
 */
public class Driver
{
	private Heap			heap;

	private BufferedReader	reader;
	private PrintWriter		writer;

	/**
	 * First method ran. Does all of the neccessary testing.
	 *
	 * @param args The command line arguments for the program.
	 */
	public static void main(String args[])
	{
		Driver d = new Driver();

		d.readDataFile();

		while (!d.heap.isEmpty())
		{
			Comparable removed = d.heap.removeMin();

			System.out.println("Removed min(" + removed + "): " + d.heap);
			d.writer.println("Removed min(" + removed + "): " + d.heap);


		}

		d.outputData();

		d.writer.close();
	}

	/**
	 * Reads the data file and inputs the data into the Heap.
	 */
	private void readDataFile()
	{
		try
		{
			System.out.println("Adding:");
			writer.println("Adding:");

			String line = reader.readLine();

			while (line != null)
			{
				int value = Integer.valueOf(line);

				heap.add(value);

				System.out.println("Add " + value + ": " + heap);
				writer.println("Add " + value + ": " + heap);

				line = reader.readLine();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Output the data to the console and a file named "heapOutput.txt"
	 */
	private void outputData()
	{
		System.out.println("Final Heap:");
		System.out.println(heap);
		writer.println("Final Heap:");
		writer.println(heap);
	}

	/**
	 * The default driver constructor that creates the
	 * needed objects.
	 */
	public Driver()
	{
		heap = new Heap();

		try
		{
			reader = new BufferedReader(new FileReader("data.txt"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			writer = new PrintWriter(new FileWriter("heapOutput.txt"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}