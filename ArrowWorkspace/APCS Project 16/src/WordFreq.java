import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * File:			WordFreq.java
 * Author:			Braden Steffaniak
 * Programming:		APCS
 * Last Modified:	5Feb2013
 * Description:		Class that tests out the Map class finding
 * 		the number of occurrences in a text file.
 */

public class WordFreq
{
	/**
	 * Constructor for the WordFreq. Constructs the HashMap. uses that
	 * HashMap to find the occurrences of each word.
	 */
	public WordFreq(Map m, String fileLocation)
	{
		loadMap(m, fileLocation);
	}

	/**
	 * Method that takes a Map and adds integers to it that symbolize the
	 * number of occurrences of the specific word in the file.
	 *
	 * @param m The map to load to.
	 */
	private void loadMap(Map m, String fileLocation)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileLocation)));

			String line = null;

			line = reader.readLine();

			while (line != null) //<there are still words in “inFile” >
			{
				String words[] = line.split("\\s*[[0-9].,[ ]/*=()\r\n\t\\[\\]?{};[-][+]['][\"]:[-][+]><!]\\s*");

				for (int i = 0; i < words.length; i++)
				{
					String word = words[i];

					if (word.length() > 0)
					{
						Integer num = (Integer)m.get(word);

						if (num == null)
						{
							m.put(word, new Integer(1));
						}
						else
						{
							m.put(word, new Integer(num.intValue() + 1));
						}
					}
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