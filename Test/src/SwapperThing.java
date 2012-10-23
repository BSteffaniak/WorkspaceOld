import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * File:          SwapperThing.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 6Sep2012
 * Description:   Class that tests a lot of swapping methods.
 */
public class SwapperThing
{
	private static int num1, num2;
	
	private static int num3, num4;
	
	/**
	 * Main method for the application that starts it up and
	 * tests all of the swapping methods.
	 */
	public static void main(String args[])
	{
		// #1
		num1 = 0;
		num2 = 34;
		
		System.out.println("Static before: " + num1 + ", " + num2);
		
		swapStatic();
		
		System.out.println("Static after: " + num1 + ", " + num2);
		
		// #2
		int number1 = 12;
		int number2 = 124;
		
		Reference pp  = new Reference(number1);
		Reference pp2 = new Reference(number2);
		
		System.out.println("Reference before: " + pp.getObj() + ", "
				+ pp2.getObj());
		
		swap(pp, pp2);
		
		System.out.println("Reference after: " + pp.getObj() + ", "
				+ pp2.getObj());
		
		// #3
		num3 = 8;
		num4 = 12;
		
		System.out.println("Local method before: " + num3 + ", " + num4);
		
		swapLocals();
		
		System.out.println("Local method after: " + num3 + ", " + num4);
		
		// #4
		int array[] = { 1, 10 };
		
		System.out.println("Primitive array before: " + array[0] + ", "
				+ array[1]);
		
		swapArray(array, 0, 1);

		System.out.println("Primitive array after: " + array[0] + ", "
				+ array[1]);
		
		// #5
		ArrayList list = new ArrayList();
		
		list.add(4);
		list.add(10);
		
		System.out.println("List before: " + list.get(0) + ", "
				+ list.get(1));
		
		swapList(list, 0, 1);
		
		System.out.println("List before: " + list.get(0) + ", "
				+ list.get(1));
	}
	
	/**
	 * Swaps the elements in the list at the specified indices.
	 * 
	 * @param a The list to swap elements of.
	 * @param index1 The first index.
	 * @param index2 The second index.
	 */
	public static void swapList(List a, int index1, int index2)
	{
		Object temp = a.get(index1);
		a.set(index1, a.get(index2));
		a.set(index2, temp);
	}
	
	/**
	 * Method to swap the elements in the array at the specified
	 * indices.
	 * 
	 * @param a The integer array.
	 * @param index1 The first index.
	 * @param index2 the second index.
	 */
	public static void swapArray(int a[], int index1, int index2)
	{
		int temp = a[index1];
		a[index1] = a[index2];
		a[index2] = temp;
	}
	
	/**
	 * Swaps two static variables.
	 */
	private static void swapStatic()
	{
		int temp = num1;
		num1 = num2;
		num2 = temp;
	}
	
	/**
	 * Swaps to local variables to the method using another private
	 * method.
	 */
	public static void swapLocals()
	{
		int num3 = SwapperThing.num3;
		int num4 = SwapperThing.num4;
		
		Reference ref1 = new Reference(num3);
		Reference ref2 = new Reference(num4);
		
		swap(ref1, ref2);
		
		SwapperThing.num3 = (Integer)ref1.getObj();
		SwapperThing.num4 = (Integer)ref2.getObj();
	}
	
	/**
	 * Swaps two objects that contain a generic object.
	 * 
	 * @param a The first value to swap.
	 * @param b The second value to swap.
	 */
	private static <T> void swap(Reference<T> a, Reference<T> b)
	{
	    T tmp = a.getObj();  
	    a.setObj(b.getObj());  
	    b.setObj(tmp);  
	}  
}