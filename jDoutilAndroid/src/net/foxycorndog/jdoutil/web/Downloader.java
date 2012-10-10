package net.foxycorndog.jdoutil.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class Downloader
{
	/**
	 * Downloads a specific file from the web to a specific location.
	 * 
	 * @param file The web-link to the file you are downloading.
	 * @param destination The destination DIRECTORY in which the file
	 * 		will be saved to.
	 * @param fileName The name of what the file will be called. (Include
	 * 		the extension)
	 * @return Whether the download was successful or not.
	 */
	public static boolean downloadFile(String file, String destination, String fileName)
	{
		destination = destination.endsWith("/") || destination.endsWith("\\") ? destination : destination + "/";
		
		File dest = new File(destination);
		
		if (!dest.isDirectory())
		{
			dest.mkdirs();
		}
		
		try
		{
			URL              url  = new URL(file);
			URLConnection    conn = url.openConnection();
			
//			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("pro",9999)); URLConnection conn = url.openConnection(proxy);
			
			InputStream      in   = conn.getInputStream();
			FileOutputStream out  = new FileOutputStream(destination + fileName);
			
			byte             b[]  = new byte[1024];
			int              count;
			
			while ((count = in.read(b)) >= 0)
			{
				out.write(b, 0, count);
			}
			
			out.flush();
			out.close();
			in.close();
			
			return true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
}