package net.foxycorndog.foxy.compiler.keywords;

public class Int extends Keyword
{
	public Int()
	{
		super("int", "int", " ", " = " , "");
	}
	
	public Object[] getCode(String words[], int offset)
	{
		return new Object[] { getJavaKeyword() + getAfterKeyword() + words[offset] + getAfterIdentifier() + words[offset + 2] + getAfterValue(), 3 };
	}
}