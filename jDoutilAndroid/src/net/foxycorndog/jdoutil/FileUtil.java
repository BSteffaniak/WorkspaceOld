package net.foxycorndog.jdoutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;

public class FileUtil
{
	public static void delete(Context context, String location)
	{
		context.deleteFile(location);
	}
	
	public static final int MODE_WORLD_READABLE = Context.MODE_WORLD_READABLE;
	public static final int MODE_WORLD_WRITABLE = Context.MODE_WORLD_WRITEABLE;
	public static final int MODE_PRIVATE        = Context.MODE_PRIVATE;
	public static final int MODE_APPEND         = Context.MODE_APPEND;
	
	
	public static String[] getOutput(Resources res, int id)
	{
		String answer[] = null;
		
		try
		{
			BufferedReader buffer = getBufferedReader(res, id);
			
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
	
	public static Object[] getObjects(Resources res, int id)
	{
		Object objects[] = null;
		
		try
		{
			ObjectInputStream ois = new ObjectInputStream(getInputStream(res, id));
			
			Object obj;
			
			ArrayList<Object> outp = new ArrayList<Object>();
			
			while ((obj = ois.readObject()) != null)
			{
				outp.add(obj);
			}
			
			objects = ArrayUtil.toObjectArray(outp);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (StreamCorruptedException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return objects;
	}
	
	public void writeObjects(Context context, String location, Object objects[])
	{
		writeObjects(context, location, objects, MODE_WORLD_READABLE);
	}
	
	public void writeObjects(Context context, String location, Object objects[], int mode)
	{
		ObjectOutputStream oos = getObjectOutputStream(context, location, mode);
		
		for (Object obj : objects)
		{
			try
			{
				oos.writeObject(obj);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public static String getFirstOutput(String location) throws IOException
	{
		String answer = null;
		
		BufferedReader buffer = new BufferedReader(new FileReader(location));
		
		answer = buffer.readLine();
		
		return answer;
	}
	
	public static FileOutputStream getFileOutputStream(Context context, String location, int mode)
	{
		FileOutputStream fos = null;
		
		try
		{
			fos = context.openFileOutput(location, mode);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return fos;
	}
	
	public static OutputStreamWriter getOutputStreamWriter(Context context, String location, int mode)
	{
		OutputStreamWriter osw = new OutputStreamWriter(getFileOutputStream(context, location, mode));
		
		return osw;
	}
	
	public static ObjectOutputStream getObjectOutputStream(Context context, String location)
	{
		return getObjectOutputStream(context, location, MODE_WORLD_READABLE);
	}
	
	public static ObjectOutputStream getObjectOutputStream(Context context, String location, int mode)
	{
		ObjectOutputStream oos = null;
		
		try
		{
			oos = new ObjectOutputStream(getFileOutputStream(context, location, mode));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return oos;
	}
	
	public static InputStream getInputStream(Resources res, int id)
	{
		InputStream is = res.openRawResource(id);
		
		return is;
	}
	
	public static BufferedReader getBufferedReader(Resources res, int id)
	{
		InputStream is = getInputStream(res, id);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		return br;
	}
}