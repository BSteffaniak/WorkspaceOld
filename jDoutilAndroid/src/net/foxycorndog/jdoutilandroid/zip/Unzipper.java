package net.foxycorndog.jdoutilandroid.zip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Unzipper
{
	/**
	 * This method
	 * --Reads an input stream
	 * --Writes the value to the output stream
	 * --Uses 1KB buffer.
	 */
	private static final void writeFile(InputStream in, OutputStream out) throws IOException
	{
		byte[] buffer = new byte[1024];
		int len;
 
		while ((len = in.read(buffer)) >= 0)
		{
			out.write(buffer, 0, len);
		}
 
		in.close();
		out.close();
	}
	
	public static boolean unzip(String zipFileLocation, String destination)
	{
		destination = destination.endsWith("/") || destination.endsWith("\\") ? destination : destination + "/";
		
		Enumeration<?> entriesEnum;
		ZipFile zipFile;
		
		try
		{
			zipFile = new ZipFile(zipFileLocation);
			entriesEnum = zipFile.entries();
 
			File directory = new File(destination);
 
			/**
			 * Check if the directory to extract to exists
			 */
			if(!directory.exists())
			{
				/**
				 * If not, create a new one.
				 */
				new File(destination).mkdirs();
			}
			
			while (entriesEnum.hasMoreElements())
			{
				try
				{
					ZipEntry entry = (ZipEntry) entriesEnum.nextElement();
 
					if (entry.isDirectory())
					{
						
					}
					else
					{
						File f = new File(destination + entry.getName());
						
						f = new File(f.getParent());
						
						f.mkdirs();
 
						writeFile(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(destination + entry.getName())));
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
 
			zipFile.close();
			
			return true;
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		return false;
	}
}