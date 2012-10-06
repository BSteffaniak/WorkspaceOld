package net.foxycorndog.foxy.compiler.keywords;

public class Decimal extends Keyword
{

	public Decimal()
	{
		super("decimal", "float", " ", " = " , "f");
	}
	
	public Object[] getCode(String words[], int offset)
	{
		return new Object[] {getJavaKeyword() + getAfterKeyword() + words[offset] + getAfterIdentifier() + words[offset + 2] + getAfterValue(), 3 };
	}
}