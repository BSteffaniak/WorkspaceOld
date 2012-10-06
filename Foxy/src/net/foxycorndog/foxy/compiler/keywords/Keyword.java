package net.foxycorndog.foxy.compiler.keywords;

public class Keyword
{
	private String afterKeyword, afterIdentifier, afterValue;
	private String keyword, javaKeyword;
	
	public Keyword(String keyword, String javaKeyword, String afterKeyword, String afterIdentifier, String afterValue)
	{
		this.keyword            = keyword;
		this.javaKeyword        = javaKeyword;
		
		this.afterKeyword       = afterKeyword;
		this.afterIdentifier    = afterIdentifier;
		this.afterValue         = afterValue;
	}
	
	public String getKeyword()
	{
		return keyword;
	}
	
	public String getJavaKeyword()
	{
		return javaKeyword;
	}
	
	public String getAfterKeyword()
	{
		return afterKeyword;
	}
	
	public String getAfterIdentifier()
	{
		return afterIdentifier;
	}
	
	public String getAfterValue()
	{
		return afterValue;
	}
	
	public Object[] getCode(String words[], int offset)
	{
		return new Object[] { keyword + words[offset], 1 };
	}
}