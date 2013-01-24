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
	
	/**
	 * Method to push a number on the stack.
	 * 
	 * @param num
	 */
	public void push(Number num)
	{
		pushy(num);
	}
	
	/**
	 * Method to push a number on the stack.
	 * 
	 * @param num
	 */
	public Expression pushy(Number num)
	{
		stack.push(num);
		
		return this;
	}
	
	/**
	 * Method that adds the last two numbers that were entered into
	 * the stack.
	 */
	public void add()
	{
		addy();
	}
	
	/**
	 * Method that adds the last two numbers that were entered into
	 * the stack.
	 */
	public Expression addy()
	{
		Object tmp1 = stack.pop();
		Object tmp2 = stack.pop();
		
		if (tmp1 instanceof Number && tmp2 instanceof Number)
		{
			Number num1 = (Number)tmp1;
			Number num2 = (Number)tmp2;
			
			double result = num2.doubleValue() + num1.doubleValue();
			
			stack.push(result);
		}
		else
		{
			throw new RuntimeException("The last two entrees must have been Numbers.");
		}
		
		return this;
	}
	
	/**
	 * Method that subtracts the last two numbers that were entered into
	 * the stack.
	 */
	public void subtract()
	{
		subtracty();
	}
	
	/**
	 * Method that subtracts the last two numbers that were entered into
	 * the stack.
	 */
	public Expression subtracty()
	{
		Object tmp1 = stack.pop();
		Object tmp2 = stack.pop();
		
		if (tmp1 instanceof Number && tmp2 instanceof Number)
		{
			Number num1 = (Number)tmp1;
			Number num2 = (Number)tmp2;
			
			double result = num2.doubleValue() - num1.doubleValue();
			
			stack.push(result);
		}
		else
		{
			throw new RuntimeException("The last two entrees must have been Numbers.");
		}
		
		return this;
	}
	
	/**
	 * Method that subtracts the last two numbers that were entered into
	 * the stack.
	 */
	public void multiply()
	{
		multiplie();
	}
	
	/**
	 * Method that subtracts the last two numbers that were entered into
	 * the stack.
	 */
	public Expression multiplie()
	{
		Object tmp1 = stack.pop();
		Object tmp2 = stack.pop();
		
		if (tmp1 instanceof Number && tmp2 instanceof Number)
		{
			Number num1   = (Number)tmp1;
			Number num2   = (Number)tmp2;
			
			double result = num2.doubleValue() - num1.doubleValue();
			
			stack.push(result);
		}
		else
		{
			throw new RuntimeException("The last two entrees must have been Numbers.");
		}
		
		return this;
	}
	
	/**
	 * Method that subtracts the last two numbers that were entered into
	 * the stack.
	 */
	public void divide()
	{
		dividey();
	}
	
	/**
	 * Method that subtracts the last two numbers that were entered into
	 * the stack.
	 */
	public Expression dividey()
	{
		Object tmp1 = stack.pop();
		Object tmp2 = stack.pop();
		
		if (tmp1 instanceof Number && tmp2 instanceof Number)
		{
			Number num1   = (Number)tmp1;
			Number num2   = (Number)tmp2;
			
			double result = num2.doubleValue() / num1.doubleValue();
			
			stack.push(result);
		}
		else
		{
			throw new RuntimeException("The last two entrees must have been Numbers.");
		}
		
		return this;
	}
	
	public Number getResult()
	{
		return (Number)stack.peek();
	}
	
	/**
	 * Method that prints out what is currently on the stack.
	 */
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(stack.toString());
		
		String stackName = stack.getClass().getSimpleName();
		
		int stackIndex   = builder.indexOf(stackName);
		
		builder.replace(stackIndex, stackIndex * stackName.length(), this.getClass().getSimpleName());
		
		return builder.toString();
	}
}