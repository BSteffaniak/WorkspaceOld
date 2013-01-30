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
		StackList stack = new StackList();
		
		stack.push("namestest");
		stack.push("asdf23");
		stack.push("asd");
		stack.push("test");
		stack.push("asdf2");
		stack.push("fdasd");
		stack.push("namestest");
		
		System.out.println(stack);
		
		quest1007(stack, "test");
		
		System.out.println(stack);
	}
	
	public void test()
	{
		
	}
	
	public static void quest1007(StackList s, String name)
	{
		StackList temp = new StackList();
		
		boolean done = false;
		while (!done)
		{
			String item = (String) s.pop();
			done = item.equals(name);
			
			if (!done)
				temp.push(item);
		}
		while (!temp.isEmpty())
		{
			s.push(temp.pop());
		}
	}
}