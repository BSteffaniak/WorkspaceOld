package net.foxycorndog.foxycompiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import net.foxycorndog.foxycompiler.java.JavaConverter;

public class Compiler
{
	private boolean	outputJava;
	
	private String	javaSource;
	
	private File	sourceFile, outputFile;
	
	private static final String arguments[];
	
	static
	{
		arguments = new String[] { "-o", "-j" };
	}
	
	public static void main(String args[])
	{
		args = new String[] { "src/net/foxycorndog/foxycompiler/Compiler.java", "-j", "-o", "src/test.txt" };
		
		Compiler compiler = new Compiler();
		
		compiler.parseArguments(args);
		
		compiler.compile();
		
		compiler.finish();
		
		System.out.println("done.");
	}
	
	public Compiler()
	{
		
	}
	
	private void parseArguments(String args[])
	{
		sourceFile = new File(args[0]);
		
		int index = 1;
		
		while (index < args.length)
		{
			for (int i = 0; i < arguments.length; i ++)
			{
				String argument = arguments[i];
				
				if (args[index].equalsIgnoreCase(argument))
				{
					if (argument.equals("-o"))
					{
						index++;
						
						outputFile = new File(args[index]);
					}
					else if (argument.equals("-j"))
					{
						outputJava = true;
					}
					
					break;
				}
			}
			
			index++;
		}
	}
	
	public void compile()
	{
		String source = getContents(sourceFile);
		
		javaSource = JavaConverter.convert(source);
	}
	
	public void finish()
	{
		if (outputJava)
		{
			try
			{
				if (outputFile == null)
				{
					int index = sourceFile.getName().indexOf('.');
					index = index < 0 ? sourceFile.getName().length() : index;
					
					String fileName = sourceFile.getName().substring(0, index) + ".txt";
					
					outputFile = new File(sourceFile.getParentFile(), fileName);
				}
				
				if (outputFile.exists() || outputFile.createNewFile())
				{
					writeContents(outputFile, javaSource);
				}
				else
				{
					System.err.println("WARNING: Unable to output java source.");
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void writeContents(File file, String contents)
	{
		try
		{
			PrintWriter writer = new PrintWriter(file);
			
			writer.write(contents);
			
			writer.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	private String getContents(File file)
	{
		StringBuilder contents = new StringBuilder();
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				contents.append(line);
			}
			
			reader.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return contents.toString();
	}
}