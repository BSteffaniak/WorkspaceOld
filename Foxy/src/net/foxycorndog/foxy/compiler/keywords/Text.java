package net.foxycorndog.foxy.compiler.keywords;

public class Text extends Keyword
{

	public Text()
	{
		super("text", "String", " ", " = ", "");
	}
	
	public Object[] getCode(String words[], int offset)
	{
		String interCode = "";
		
		int continuesLeft = 0;
		
		for (int i = offset + 2; i < words.length && !words[i].equals(";"); i ++)
		{
			continuesLeft ++;
			
			interCode += words[i];
		}
		
		return new Object[] { getJavaKeyword() + getAfterKeyword() + words[offset] + getAfterIdentifier() + interCode + getAfterValue(), continuesLeft };
	}
}