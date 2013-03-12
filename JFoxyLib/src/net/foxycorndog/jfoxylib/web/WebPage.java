package net.foxycorndog.jfoxylib.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class WebPage
{
	/**
	 * Returns the first line of the output from the url webpage.
	 * 
	 * @param url The url to the webpage.
	 * @return The first line of the output.
	 */
	public static String getFirstOutput(String url)
	{
		try
		{
			URL myurl = new URL(url);
			URLConnection yc = myurl.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String answer = in.readLine();
			in.close();
			
			return answer;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Can not connect to url: " + url);
		}
	}
	
	public static String[] getOutput(String url)
	{
		ArrayList<String> arr = new ArrayList<String>();
		
		try
		{
			URL myurl = new URL(url);
			URLConnection yc = myurl.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			
			String line = null;
			
			while ((line = in.readLine()) != null)
			{
				arr.add(line);
			}
			
			in.close();
			
			String answer[] = arr.toArray(new String[0]);
			
			return answer;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Can not connect to url: " + url);
		}
	}
}