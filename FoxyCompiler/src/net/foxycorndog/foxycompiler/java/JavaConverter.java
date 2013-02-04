package net.foxycorndog.foxycompiler.java;

public class JavaConverter
{
	private static final int PRIVATE = 1, PUBLIC = 2, PROTECTED = 3;
	
	public static String convert(String source)
	{
		int visibility = PUBLIC;
		
		String words[] = source.split("\\s*" + "[.,[ ]/*=()\r\n\t\\[\\]{};[-][+]['][\"]:[-][+]><!|?]" + "+\\s*");
		
		StringBuilder output = new StringBuilder();
		
		for (int i = 0; i < words.length; i++)
		{
			output.append(words[i] + "\r\n");
		}
		
		return output.toString();
	}
}