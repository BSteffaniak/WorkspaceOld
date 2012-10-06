package net.foxycorndog.jdoutilandroid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileUtil
{
	public static void delete(String location)
	{
		new File(location).delete();
	}
	
	public static String[] getOutput(String location)
	{
		String answer[] = null;
		
		try
		{
			BufferedReader buffer = new BufferedReader(new FileReader(location));
			
			String line = null;
			
			ArrayList<String> outp = new ArrayList<String>();
			
			while ((line = buffer.readLine()) != null)
			{
				outp.add(line);
			}
			
			answer = ArrayUtil.toStringArray(outp);
		}
		catch (FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		return answer;
	}
	
	public static String getFirstOutput(String location) throws IOException
	{
		String answer = null;
		
		BufferedReader buffer = new BufferedReader(new FileReader(location));
		
		answer = buffer.readLine();
		
		return answer;
	}
}