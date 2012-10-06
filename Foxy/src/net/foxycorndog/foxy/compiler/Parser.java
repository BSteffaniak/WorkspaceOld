package net.foxycorndog.foxy.compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import net.foxycorndog.foxy.compiler.keywords.Debug;
import net.foxycorndog.foxy.compiler.keywords.Decimal;
import net.foxycorndog.foxy.compiler.keywords.Int;
import net.foxycorndog.foxy.compiler.keywords.Keyword;
import net.foxycorndog.foxy.compiler.keywords.Text;

public class Parser
{
	private static String tabs = "\t";
	
	private static final Keyword keywords[] = new Keyword[]
			{
				new Int(),
				new Decimal(),
				new Debug(),
				new Text()
			};
	
	private static final char letters[] = new char[]
			{
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
				'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2',
				'3', '4', '5', '6', '7', '8', '9', '_', '.'
			};
	
	private static final char symbols[] = new char[]
			{
				'~', '!', '#', '$', '%', '^', '&', '*', '(', ')', '-',
				'=', '+', '+', '/', '?', '>', '<', ':', ';', '\'', '\"',
				',', '\\', '{', '}'
			};
	
	public Parser()
	{
		
	}
	
	public static void parse(String fileName, String text)
	{
		boolean hasExtension = false;
		
		String fileNameWithoutExtension = null;
		
		if (fileName.length() >= 5)
		{
			hasExtension = fileName.substring(fileName.length() - 5, fileName.length()).equalsIgnoreCase(".java") ? true : false;
			
			if (!hasExtension)
			{
				hasExtension = fileName.substring(fileName.length() - 5, fileName.length()).equalsIgnoreCase(".foxy") ? true : false;
			}
		}
		
		if (hasExtension)
		{
			fileNameWithoutExtension = fileName.substring(0, fileName.length() - 5);
		}
		else
		{
			fileNameWithoutExtension = fileName;
		}
		
		ArrayList<String> wordsList = new ArrayList<String>();
		
		String javaCode = "";
		
		javaCode += "public class " + fileNameWithoutExtension + "\n" +
					"{\n" + tabs;
		
		String nextWord = "";
		
		char quote = ' ';
		
		for (int i = 0; i < text.length(); i ++)
		{
			if (isLetter(text.charAt(i)))
			{
				nextWord += text.charAt(i);
			}
			else if (isSymbol(text.charAt(i)))
			{
				if (text.charAt(i) == '\"' || text.charAt(i) == '\'')
				{
					if (quote == text.charAt(i))
					{
						quote = ' ';
					}
					else
					{
						quote = text.charAt(i);
					}
					
					if (quote != ' ')
					{
						wordsList.add(text.charAt(i) + "");
					}
				}
				else if (quote != ' ')
				{
					//wordsList.add(text.charAt(i) + "");
					nextWord += text.charAt(i);
				}
				
				if (quote == ' ')
				{
					if (!nextWord.equals(""))
					{
						wordsList.add(nextWord);
					}
					
					wordsList.add(text.charAt(i) + "");
					
					nextWord = "";
				}
			}
			else
			{
				if (quote == ' ')
				{
					if (!nextWord.equals(""))
					{
						wordsList.add(nextWord);
					}
					
					nextWord = "";
				}
				else
				{
					nextWord += text.charAt(i);
				}
			}
		}
		
//		Binding binding = new Binding();
//		GroovyShell shell = new GroovyShell(binding);
		
		if (!nextWord.equals(""))
		{
			wordsList.add(nextWord);
		}
		
		String words[] = new String[wordsList.size()];
		
		for (int i = 0; i < wordsList.size(); i ++)
		{
			words[i] = wordsList.get(i);
			
			System.out.println(words[i]);
		}
		
		System.out.println("--- End ---");
		
		Integer continuesLeft = 0;
		
		mainLoop:
		for (int i = 0; i < words.length; i ++)
		{
			if (continuesLeft > 0)
			{
				continuesLeft --;
				
				continue;
			}
			
			String word = words[i];
			
			for (int j = 0; j < keywords.length; j ++)
			{
				if (word.equals(keywords[j].getKeyword()))
				{
					Keyword keyword = keywords[j];
					
					Object code[] = keyword.getCode(words, i + 1);
					
					continuesLeft += (Integer)code[1];
					
					//keywrd + afterKeyword + identifier + afterIdentifier + value + afterValue;
					
					javaCode += code[0];
					
					continue mainLoop;
				}
			}
			
			if (word.equals(";"))
			{
				javaCode += ";\n" + tabs;
			}
			else if (word.equals("{"))
			{
				javaCode += "\n" + tabs + "{\n";
				tabs += "\t";
				javaCode += tabs;
			}
			else if (word.equals("}"))
			{
				tabs = tabs.substring(0, tabs.length() - 2);
				
				javaCode = javaCode.substring(0, javaCode.length() - 1);
				
				javaCode += "}\n" + tabs;
			}
			else
			{
				String before   = "";
				
				char charBefore = javaCode.charAt(javaCode.length() - 1);
				
				if (charBefore == '\t' || word.equals("(") || word.equals(")"))
				{
					before = "";
				}
				else
				{
					before = " ";
				}
				
				javaCode += before + word;
			}
		}
		
		javaCode = javaCode.substring(0, javaCode.length() - 1);
		
		javaCode += "}";
		
		System.out.println(javaCode);
		
		PrintWriter pw = null;
		
		try
		{
			pw = new PrintWriter(new File(fileNameWithoutExtension + ".foxy"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		pw.append(text);
		pw.close();
		
		pw = null;
		
		try
		{
			pw = new PrintWriter(new File(fileNameWithoutExtension + ".java"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		pw.append(javaCode);
		pw.close();
		
	//	Compiler.compile(fileName);
	}
	
	public static boolean isLetter(char c)
	{
		for (int i = 0; i < letters.length; i ++)
		{
			if (c == letters[i])
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isSymbol(char c)
	{
		for (int i = 0; i < symbols.length; i ++)
		{
			if (c == symbols[i])
			{
				return true;
			}
		}
		
		return false;
	}
}