package net.foxycorndog.foxy.compiler.keywords;

public class Debug extends Keyword
{

	public Debug()
	{
		super("debug", "System.out.print", "(", "", ")");
	}
	
	public Object[] getCode(String words[], int offset)
	{
		String interCode = "";
		
		int continuesLeft = 0;
		
		for (int i = offset; i < words.length && !words[i].equals(";"); i ++)
		{
			continuesLeft ++;
			
			interCode += words[i];
		}
		
		return new Object[] { getJavaKeyword() + getAfterKeyword() + interCode + getAfterValue(), continuesLeft };
	}
}