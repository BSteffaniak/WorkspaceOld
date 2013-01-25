package net.foxycorndog.foxycompiler.argument;

public abstract class Argument
{
	private String argument;
	
	public Argument(String argument)
	{
		this.argument = argument;
	}
	
	public abstract void run();
}