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
	private Heap heap;
	
	/**
	 * First method ran. Does all of the neccessary testing.
	 *
	 * @param args The command line arguments for the program.
	 */
	public static void main(String args[])
	{
		Driver d = new Driver();
		
		d.heap.add(1);
		d.heap.add(10);
		d.heap.add(2);
		d.heap.add(20);
		d.heap.add(11);
		d.heap.add(4);
		d.heap.add(3);
		d.heap.add(22);
		d.heap.add(21);
		d.heap.add(12);
		d.heap.add(13);
		d.heap.add(5);
		d.heap.add(6);
		d.heap.add(7);
		d.heap.add(8);
		
		System.out.println(d.heap.removeMin());
		
		System.out.println(d.heap);
	}

	/**
	 * The default driver constructor that creates the
	 * needed objects.
	 */
	public Driver()
	{
		heap = new Heap();
	}
}