package net.foxycorndog.foxycompiler;

import java.io.File;

public class Compiler
{
	private boolean outputJava;
	
	private File sourceFile, outputFile;
	
	private static final String arguments[];
	
	static
	{
		arguments = new String[] { "-o", "-j" };
	}
	
	public static void main(String args[])
	{
		new Compiler(args);
		
		System.out.println("done.");
	}
	
	public Compiler(String args[])
	{
		parseArguments(args);
	}
	
	private void parseArguments(String args[])
	{
		int index = 0;
		
		
		for (int i = 0; i < arguments.length; i ++)
		{
			while (index < args.length)
			{
				String argument = arguments[i];
				
				if (args[i].equalsIgnoreCase(argument))
				{
					if (argument.equals("-o"))
					{
						index++;
					}
					else if (argument.equals("-j"))
					{
						outputJava = true;
					}
					else
					{
						index++;
					}
				}
			}
		}
	}
}