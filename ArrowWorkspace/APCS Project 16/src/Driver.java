import java.util.HashSet;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.ArrayList;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

/**
 * File Name:	Driver.java
 * Programming:	APCS
 * Author:		Braden Steffaniak
 * Date:		13Mar2013
 * Description:	Class that tests out the functionality of a
 * 	HashSet, HashMap, and TreeMap.
 */
public class Driver
{
	private PrintWriter		writer;
	private BufferedReader	reader;

	/**
	 * First method ran. Does all of the neccessary testing.
	 *
	 * @param args The command line arguments for the program.
	 */
	public static void main(String args[])
	{
		Driver d = new Driver();

		System.out.println(d.func(1));
		System.out.println(d.func(15));
		System.out.println(d.func(27));
		System.out.println(d.func(53));
		System.out.println(d.numOnes(65, 867));

		d.writer.println(d.func(1));
		d.writer.println(d.func(15));
		d.writer.println(d.func(27));
		d.writer.println(d.func(53));
		d.writer.println(d.numOnes(65, 867));

		d.writer.close();
	}

	/**
	 * The default driver constructor that creates the
	 * needed objects.
	 */
	public Driver()
	{
		try
		{
			writer = new PrintWriter(new FileWriter(
					new File("binaryPoemMap.txt")));


		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Tests out the functions given by mister m.
	 */
	public int func(int x)
	{
		boolean isPrime = isPrime(x);

		if (x <= 1)
		{
			return 1;
		}
		else if (isPrime)
		{
			return (x * x) + x;
		}
		else
		{
			return 2 * func(x - 3) - 4;
		}
	}

	/**
	 * Returns whether the number is prime.
	 */
	private boolean isPrime(int x)
	{
		int sqr = (int)Math.sqrt(x) + 1;

		for (int i = 2;
		 i < sqr;
		 i++)
		{
			if (x % i == 0)
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the amount of numbers with 8 bits from x-y.
	 */
	private int numOnes(int x, int y)
	{
		int count = 0;

		while (x <= y)
		{
			if (x > y)
			{
				throw new IllegalArgumentException("X must " +
						"be smaller than Y!!");
			}
			else if (y >= 1024)
			{
				throw new IllegalArgumentException("I am told " +
						"to assume that y is supposed to me " +
						"smaller than 1024");
			}

			if (Integer.bitCount(x) == 8)
			{
				count ++;
			}

			x++;
		}

		return count;
	}

	/**
	 * Tests the word frequency of the poem.
	 */
	private void wordMap()
	{

	}
}