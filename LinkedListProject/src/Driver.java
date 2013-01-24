/**
 * File:          Driver.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 22Jan2013
 * Description:   Class that demonstrates the stack and queue lists.
 */
public class Driver
{
	/**
	 * The main method that tests out the lists.
	 * 
	 * @param args The command line arguments.
	 */
	public static void main(String args[])
	{
		QueueList queue = new QueueList();
		
		System.out.println(queue);
		
		queue.enqueue("Bill");
		queue.enqueue("Fred");
		queue.enqueue("Joe");
		queue.enqueue("Henrietta");
		queue.enqueue("Mr. M");
		queue.enqueue("Norman");
		
		System.out.println(queue);
		
		System.out.println(queue.dequeue());
		System.out.println(queue.dequeue());
		
		System.out.println(queue);
		
		StackList stack = new StackList();
		
		System.out.println(stack);
		
		stack.push("Bill");
		stack.push("Fred");
		stack.push("Joe");
		stack.push("Henrietta");
		stack.push("Mr. M");
		stack.push("Norman");
		
		System.out.println(stack);
		
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		
		System.out.println(stack);
	}
}