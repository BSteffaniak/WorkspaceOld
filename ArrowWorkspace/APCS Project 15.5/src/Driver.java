import java.util.HashSet;
import java.util.HashMap;
import java.util.TreeMap;

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
	private HashSet<String> set;
	private HashMap<String, Integer> map;
	private TreeMap<Integer, String> tree;
	
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
		
		set.add("Fork");
		set.add("Spoon");
		set.add("boul");
		set.add("Fried");
		set.add("Chicken");
		set.add("Chair");
		set.add("floor");
		set.add("cheese");
		set.add("school");
		set.add("zap");
	}
	
	/**
	 * Create the HashMap and fill it with the data of a bunch
	 * of names that reference to their grades.
	 */
	private void createHashMap()
	{
		map = new HashMap<String, Integer>();
		
		map.put("Braden Steffaniak", 95);
		map.put("John Doe", 58);
		map.put("Max Spicer", 100);
		map.put("Henry Rybolt", 72);
		map.put("Barack Obama", 59);
		map.put("Hillary Clinton", 68);
		map.put("Joe Biden", 49);
		map.put("Tom Cruise", 75);
		map.put("Arnold Schwarzenegger", 87);
		map.put("Ed Hightower", 66);
	}
	
	/**
	 * Create the TreeMap and fill it with the data of a bunch
	 * of peoples IQ's and their names. The Tree will be sorted
	 * by the IQ from lowest to highest.
	 */
	private void createTreeMap()
	{
		tree = new TreeMap<Integer, String>();
		
		tree.put(100, "George W");
		tree.put(97, "Jacob");
		tree.put(93, "Jeremy");
		tree.put(98, "Bob");
		tree.put(95, "Leonardo");
		tree.put(110, "Thomas E");
		tree.put(109, "Max");
		tree.put(120, "Mr M");
		tree.put(115, "Henry");
		tree.put(250, "Braden Steffaniak");
	}
}