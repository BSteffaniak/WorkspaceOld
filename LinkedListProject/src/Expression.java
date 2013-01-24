/**
 * File:          Expression.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 23Jan2013
 * Description:   Class that can manipulate expressions.
 */
public class Expression
{
	private StackList	stack;
	
	/**
	 * Default constructor for the Expression class. Instantiates
	 * the stack.
	 */
	public Expression()
	{
		stack = new StackList();
	}
	
	public void push(Number num)
	{
		stack.push(num);
	}
	
	/**
	 * Method that adds the last two numbers that were entered into
	 * the stack.
	 */
	public void add()
	{
		Object tmp1 = stack.pop();
		Object tmp2 = stack.pop();
		
		if (tmp1 instanceof Number && tmp2 instanceof Number)
		{
			Number num1 = (Number)tmp1;
			Number num2 = (Number)tmp2;
		}
		else
		{
			throw new RuntimeException("The last two entrees must be Numbers.");
		}
	}
	
	/**
	 * Method that subtracts the last two numbers that were entered into
	 * the stack.
	 */
	public void subtract()
	{
		Object tmp1 = stack.pop();
		Object tmp2 = stack.pop();
		
		if (tmp1 instanceof Number && tmp2 instanceof Number)
		{
			Number num1 = (Number)tmp1;
			Number num2 = (Number)tmp2;
		}
		else
		{
			throw new RuntimeException("The last two entrees must be Numbers.");
		}
	}
	
	/**
	 * Method that prints out what is currently on the stack.
	 */
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		
		
		return builder.toString();
	}
}