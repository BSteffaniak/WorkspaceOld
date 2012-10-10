package net.foxycorndog.jdoutil;

public class StringUtil
{
	public static int countOccurrences(String haystack, char needle)
	{
	    int count = 0;
	    for (int i = 0; i < haystack.length(); i ++)
	    {
	        if (haystack.charAt(i) == needle)
	        {
	             count ++;
	        }
	    }
	    return count;
	}
	
	public static String[] splitBetweenOccurrence(String haystack, char needle)
	{
		String split[] = new String[countOccurrences(haystack, needle) + 1];
		
		int occurrence = 0;
		
		int beginIndex = 0;
		
		for (int i = 0; i < haystack.length(); i ++)
		{
			if (haystack.charAt(i) == needle)
			{
				split[occurrence ++] = haystack.substring(beginIndex + (beginIndex == 0 ? 0 : 1), i);
				
				beginIndex = i;
			}
		}
		
		if (split.length > 1)
		{
			split[occurrence] = haystack.substring(beginIndex + (beginIndex == 0 ? 0 : 1), haystack.length());
		}
		
		return split;
	}
	
	public static boolean isNumber(String string)
	{
		return string.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
	}
	
	public static String replaceAll(String source, String replace)
	{
		String returner = "";
		
		for (int i = 0; i < source.length(); i ++)
		{
			returner += replace;
		}
		
		return returner;
	}
	
	public static String upperCaseEachWord(String str)
	{
		str = str.toLowerCase();
		
		char chars[] = str.toCharArray();
		
		String str2 = "";
		
		str2 += CharacterUtil.toUpperCase(chars[0]);
		
		for (int i = 1; i < chars.length; i ++)
		{
			if (chars[i] == ' ' && i + 1 < chars.length)
			{
				str2 += " " + CharacterUtil.toUpperCase(chars[i + 1]);
				
				i ++;
			}
			else
			{
				str2 += chars[i];
			}
		}
		
		return str2;
	}
}