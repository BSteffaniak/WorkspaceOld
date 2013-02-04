import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordFreq
{
	private Map	m;

	public static void main(String args[])
	{
		WordFreq f = new WordFreq();
	}
	
	/**
	 * Constructor for the WordFreq. Constructs the HashMap. uses that
	 * HashMap to find the occurrences of each word.
	 */
	public WordFreq()
	{
		m = new HashMap();
		loadMap(m);
		System.out.println(m); // print the values in the map
	}

	/**
	 * Method that takes a Map and adds integers to it that symbolize the
	 * number of occurrences of the specific word in the file.
	 * 
	 * @param m
	 */
	public void loadMap(Map m)
	{
		// < code to open the report file called “inFile” >
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(new File("inFile2.txt")));
			
			String line = null;
			
			line = reader.readLine();
			
			while (line != null) //<there are still words in “inFile” >
			{
				String words[] = line.split("\\s*[[.][,][ ][/][*][=][(][)][\r][\n][\t][\\][{][}][;][?][-][+]['][\"][:][-][+][>][<][!]]\\s*");
				
				for (int i = 0; i < words.length; i++)
				{
					String word = words[i]; // readWord() reads one word
														// in...may have to tokenize it
					if (i == 1)
					{
						System.out.println(word);
					}
					Integer num = (Integer) m.get(word); // get the value associated with
														// the key word
					if (num == null) // new word
						m.put(word, new Integer(1)); // put new word in the map since
														// we’ve seen it once
					else
						m.put(word, new Integer(num.intValue() + 1)); // add 1 to the
																	// number of times
																	// we’ve seen it
				}
				
				line = reader.readLine();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}