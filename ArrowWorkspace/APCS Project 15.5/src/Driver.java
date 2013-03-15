import java.util.HashSet;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.ArrayList;

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
	private BufferedReader				reader;
	
	private String						words[][];
	
	private HashSet<String>				set;
	private HashMap<String, Integer>	map;
	private TreeMap<Integer, String>	tree;
	
	/**
	 * First method ran. Does all of the neccessary testing.
	 * 
	 * @param args The command line arguments for the program.
	 */
	public static void main(String args[])
	{
		Driver d = new Driver();
		
		System.out.println("HashSet " + d.set);
		System.out.println("HashMap " + d.map);
		System.out.println("TreeMap " + d.tree);
	}
	
	/**
	 * The default driver constructor that creates the
	 * needed objects.
	 */
	public Driver()
	{
		try
		{
			reader = new BufferedReader(new FileReader(new File("data.txt")));
			
			ArrayList<String[]> temp = new ArrayList<String[]>();
			
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				temp.add(line.split(" "));
			}
			
			words = new String[temp.size()][];
			
			for (int i = 0; i < temp.size(); i++)
			{
				words[i] = temp.get(i);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		createHashSet();
		createHashMap();
		createTreeMap();
	}
	
	/**
	 * Create the HashSet and fill it with the data of a bunch
	 * of words that pertain to a game of scrabble.
	 */
	private void createHashSet()
	{
		set = new HashSet<String>();
		
		for (int i = 0; i < words[0].length; i++)
		{
			set.add(words[0][i]);
		}
	}
	
	/**
	 * Create the HashMap and fill it with the data of a bunch
	 * of names that reference to their grades.
	 */
	private void createHashMap()
	{
		map = new HashMap<String, Integer>();
		
		for (int i = 0; i < words[1].length; i += 3)
		{
			map.put(words[1][i] + " " + words[1][i + 1], Integer.valueOf(words[1][i + 2]));
		}
	}
	
	/**
	 * Create the TreeMap and fill it with the data of a bunch
	 * of peoples IQ's and their names. The Tree will be sorted
	 * by the IQ from lowest to highest.
	 */
	private void createTreeMap()
	{
		tree = new TreeMap<Integer, String>();
		
		for (int i = 0; i < words[2].length; i += 3)
		{
			tree.put(Integer.valueOf(words[2][i]), words[2][i + 1] + " " + words[2][i + 2]);
		}
	}
	
	
}