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
 * Date:		18Mar2013
 * Description:	Class that tests out the functionality the three
 * 	challenge functions.
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

		TreeMap<Object, Object> wordFreq = d.wordMap("poem.txt");

		System.out.println("Functions:");
		System.out.println(d.func(1));
		System.out.println(d.func(15));
		System.out.println(d.func(27));
		System.out.println(d.func(53));
		System.out.println("\nNumber of bit 1's:");
		System.out.println(d.numOnes(1, 1022));
		System.out.println("\nWordFreq:");
		System.out.println(wordFreq);

		d.writer.println("Functions:");
		d.writer.println(d.func(1));
		d.writer.println(d.func(15));
		d.writer.println(d.func(27));
		d.writer.println(d.func(53));
		d.writer.println("\nNumber of bit 1's:");
		d.writer.println(d.numOnes(0, 34));
		d.writer.println("\nWordFreq:");
		d.writer.println(wordFreq);

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
	private int func(int x)
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

		for (int i = 2; i < sqr; i++)
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
		if (x > y)
		{
			throw new IllegalArgumentException("X must " +
					"be smaller than Y!!");
		}
		else if (x < 0)
		{
			throw new IllegalArgumentException("X must >= 0");
		}
		else if (y >= 1023)
		{
			throw new IllegalArgumentException("I am told " +
					"to assume that y is supposed to me " +
					"smaller than 1023");
		}

		int count = 0;

		while (x <= y)
		{
			if (Integer.bitCount(x) >= 8)
			{
				count++;
			}

			x++;
		}

		return count;
	}

	/**
	 * Tests the word frequency of the poem.
	 */
	private TreeMap<Object, Object> wordMap(String location)
	{
		TreeMap<Object, Object> map = new TreeMap<Object, Object>();

		WordFreq f = new WordFreq(map, location);

		return map;
	}
}