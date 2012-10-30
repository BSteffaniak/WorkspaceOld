import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ArrayThing
{
	private int                arraySize;
	
	private ObjectInputStream  in;
	private ObjectOutputStream out;
	
	private int                randomArray[];
	
	public static void main(String args[])
	{
		new ArrayThing();
	}
	
	public ArrayThing()
	{
		arraySize = 1000000;
		
		long timeBefore = System.currentTimeMillis();
//		System.out.println(System.currentTimeMillis());
//		System.out.println(System.nanoTime());
		
		writeToFile();
//		readFromFile();
		
		long timeAfter = System.currentTimeMillis();
		
		System.out.println(timeAfter - timeBefore);
	}
	
	private void readFromFile()
	{
		randomArray = (int[])read("arrayFile.dat");

		System.out.println("Read: ");
		for (int i = 0; i < randomArray.length; i ++)
		{
			System.out.println(randomArray[i]);
		}
	}
	
	private void writeToFile()
	{
		randomArray = new int[arraySize];
		
		for (int i = 0; i < randomArray.length; i ++)
		{
			randomArray[i] = (int)(Math.random() * 10);
		}
		
		write("arrayFile.dat", randomArray);
		
//		System.out.println("Wrote: ");
//		for (int i = 0; i < randomArray.length; i ++)
//		{
//			System.out.println(randomArray[i]);
//		}
	}
	
	private Object read(String location)
	{
		Object obj = null;
		
		try
		{
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(location)));
		
			obj = in.readObject();
			
			in.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			
		}
		
		return obj;
	}
	
	private void write(String location, Object obj)
	{
		try
		{
			out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(location)));
			
			out.writeObject(obj);
			
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}