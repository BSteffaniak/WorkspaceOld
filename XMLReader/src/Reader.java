import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Stack;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.xml.sax.XMLReader;

public class Reader
{
	private String fileLocation;
	
	public static void main(String args[])
	{
		Reader r = new Reader(".project");
	}
	
	public Reader(String fileLocation)
	{
		this.fileLocation = fileLocation;
		
		try
		{
			File xmlFile = new File(fileLocation);
			InputStream is = new FileInputStream(xmlFile);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(is);
			
			StringBuilder tabs = new StringBuilder();
			
			Stack<String> stack = new Stack<String>();
			
			String text = null;
			String lastText = null;
			String name = null;
			String lastName = null;
			
			StringBuilder output = new StringBuilder();
			
			int index = 0;
			
			while (reader.hasNext())
			{
			    if (reader.hasText())
			    {
			    	index++;
			    	
			    	text = reader.getText();
			    	
			    	text = formatText(text);
			    	
			    	if (name == lastName && lastText.length() > 0)
			    	{
			    		output.append("\n" + tabs + "<" + lastName + ">" + lastText + "</" + lastName + ">");
			    	}
			    	else
			    	{
			    		if (name == lastName)
			    		{
				    		output.append("</" + name + ">");
			    		}
			    		else if (lastName != null)
			    		{
				    		if (stack.isEmpty() || !stack.peek().equals(lastName))
				    		{
				    			output.append("\n" + tabs + "<" + lastName + ">");
				    			
				    			stack.push(lastName);
				    			
				    			tabs.append('\t');
				    		}
				    		else
				    		{
				    			tabs.deleteCharAt(0);
				    			
				    			output.append("\n" + tabs + "</" + stack.pop() + ">");
				    		}
			    		}
			    	}
			    	
			    	lastText = text;
			    	lastName = name;
			    }
			    else if (reader.isStartElement())
			    {
			    	name = reader.getLocalName();
			    }
			    else if (reader.isEndElement() && tabs.length() > 0)
			    {
			    	if (name != lastName)
			    	{
			    		
			    	}
			    	
//			    	System.out.println("</" + stack.pop() + ">");
			    }
			    
			    reader.next();
			}
			
			if (output.length() > 0)
			{
				output.deleteCharAt(0);
			}
			
			System.out.println(output);
			System.out.println("done " + stack);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (XMLStreamException e)
		{
			e.printStackTrace();
		}
	}
	
	private char nextChar(String text)
	{
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			
			if (c != '\t' && c != ' ' && c != '\n' && c != '\r')
			{
				return c;
			}
		}
		
		return 0;
	}
	
	private String formatText(String text)
	{
		StringBuilder builder = new StringBuilder(text);
		
		int index = 0;
		
		while (index < builder.length())
		{
			char c = builder.charAt(index);
			
			if (c == '\t' || c == ' ' || c == '\n' || c == '\r')
			{
				builder.deleteCharAt(index);
			}
			else
			{
				return builder.toString();
			}
		}
		
		return builder.toString();
	}
}