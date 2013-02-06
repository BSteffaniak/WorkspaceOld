package net.foxycorndog.arrowide.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import net.foxycorndog.arrowide.language.Keyword;

public class FileUtils
{
//	private static final HashMap<String, HashSet<String>>	ENDINGS;
	private static final HashMap<String, Integer>			TYPES;
	
	public  static final int JAVA = 1, GLSL = 2, ASSEMBLY = 3, FOXY = 4, CPP = 5, H = 6, C = 7,
							TXT = 8, RTF = 9, EXE = 10, CLASS = 11, PHP = 12, PYTHON = 13;
	
	static
	{
		TYPES = new HashMap<String, Integer>();
		
		TYPES.put("java",  JAVA);
		TYPES.put("vs",    GLSL);
		TYPES.put("vert",  GLSL);
		TYPES.put("fs",    GLSL);
		TYPES.put("frag",  GLSL);
		TYPES.put("shade", GLSL);
		TYPES.put("shad",  GLSL);
		TYPES.put("sha",   GLSL);
		TYPES.put("asm",   ASSEMBLY);
		TYPES.put("foxy",  FOXY);
		TYPES.put("txt",   TXT);
		TYPES.put("rtf",   RTF);
		TYPES.put("exe",   EXE);
		TYPES.put("class", CLASS);
		TYPES.put("c",     C);
		TYPES.put("cpp",   CPP);
		TYPES.put("h",     H);
		TYPES.put("php",   PHP);
		TYPES.put("php5",  PHP);
		TYPES.put("py",    PYTHON);
		
//		ENDINGS = new HashMap<String, HashSet<String>>();
//		
//		ENDINGS.put("glsl.vertex.endings", toHashSet(new String[] { "vs", "vert" }));
//		ENDINGS.put("glsl.fragment.endings", toHashSet(new String[] { "fs", "frag", "shade", "shad", "sha" }));
//		
//		ENDINGS.put("java", toHashSet(new String[] { "java" }));
//		
//		ENDINGS.put("assembly", toHashSet(new String[] { "asm" }));
//		
//		ENDINGS.put("foxy", toHashSet(new String[] { "foxy" }));
//
//		ENDINGS.put("cpp", toHashSet(new String[] { "cpp" }));
//		
//		ENDINGS.put("h", toHashSet(new String[] { "h" }));
//		
//		ENDINGS.put("php", toHashSet(new String[] { "php", "php5" }));
	}
	
	private static <E> HashSet<E> toHashSet(E array[])
	{
		HashSet<E> set = new HashSet<E>();
		
		for (E s : array)
		{
			set.add(s);
		}
		
		return set;
	}
	
	public static int getLanguage(String name)
	{
		String ending = name.substring(name.lastIndexOf('.') + 1).toLowerCase();
		
		int language  = 0;
		
//		if (ENDINGS.get("glsl.vertex.endings").contains(ending) || ENDINGS.get("glsl.fragment.endings").contains(ending))
//		{
//			language = GLSL;
//		}
//		else if (ENDINGS.get("java").contains(ending))
//		{
//			language = JAVA;
//		}
//		else if (ENDINGS.get("assembly").contains(ending))
//		{
//			language = ASSEMBLY;
//		}
//		else if (ENDINGS.get("foxy").contains(ending))
//		{
//			language = FOXY;
//		}
//		else if (ENDINGS.get("cpp").contains(ending) || ENDINGS.get("h").contains(ending))
//		{
//			language = CPP;
//		}
		
		if (TYPES.containsKey(ending))
		{
			language = TYPES.get(ending);
		}
		
		return language;
	}
	
	public static boolean contains(String str, String endings[])
	{
		for (String ending : endings)
		{
			if (str.equals(ending))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean delete(File file)
	{
		if (file.isDirectory())
		{
			File subFiles[] = file.listFiles();
			
			for (int i = 0; i < subFiles.length; i ++)
			{
				delete(subFiles[i]);
			}
		}
		
		return file.delete();
	}
	
	public static boolean isFileName(String location)
	{
		location = removeEndingSlashes(location);
		
		for (int i = location.length() - 1; i >= 0 && location.charAt(i) != '/'; i --)
		{
			if (location.charAt(i) == '.')
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static String getParentFolder(String location)
	{
		location = removeEndingSlashes(location);
		
		int index = location.length() - 1;
		
		while (index >= 0)
		{
			if (location.charAt(index) == '/')
			{
				index--;
				
				break;
			}
			else
			{
				index--;
			}
		}
		
		return removeEndingSlashes(location.substring(0, index + 1));
	}
	
	public static String removeEndingSlashes(String location)
	{
		int lastIndex = location.length() - 1;
		
		while (lastIndex >= 0 && location.charAt(lastIndex) == '/')
		{
			lastIndex--;
		}
		
		return location.substring(0, lastIndex + 1);
	}
	
	public static int getFileType(String name)
	{
		int type = 0;
		
		if ((type = getLanguage(name)) != 0)
		{
			return type;
		}
		else
		{
			String ending = name.substring(name.lastIndexOf('.') + 1).toLowerCase();
			
			if (TYPES.containsKey(ending))
			{
				return TYPES.get(ending);
			}
		}
		
		return type;
	}
	
	public static String getFileName(String location)
	{
		location       = location.replace('\\', '/');
		
		location       = removeEndingSlashes(location);
		
		int firstIndex = location.lastIndexOf('/') + 1;
		
		return location.substring(firstIndex, location.length());
	}
	
	public static String getFileNameWithoutExtension(String location)
	{
		location = getFileName(location);
		
		int lastIndex = location.lastIndexOf('.');
		
		if (lastIndex > 0)
		{
			location = location.substring(0, lastIndex);
		}
		
		return location;
	}
	
	public static String removeExtension(String location)
	{
		int lastIndex = location.lastIndexOf('.');
		
		if (lastIndex > 0)
		{
			location = location.substring(0, lastIndex);
		}
		
		return location;
	}
	
	public static String getPrecedingPath(String path, String relativeTo)
	{
		File relativeFile = new File(relativeTo);
		
//		if (relativeFile.exists())
//		{
			if (relativeFile.exists() && !relativeFile.isDirectory())
			{
				relativeTo = getParentFolder(relativeTo);
			}
			
			String folderName = "/" + getFileName(relativeTo) + "/";
			
			int index = path.lastIndexOf(folderName);
			
			return path.substring(0, index);
//		}
//		else
//		{
//			throw new IllegalArgumentException('"' + relativeTo + '"' + " must be an existing location.");
//		}
	}
	
	public static String getPathRelativeTo(String path, String relativeTo)
	{
		File relativeFile = new File(relativeTo);
		
//		if (relativeFile.exists())
//		{
			if (relativeFile.exists() && !relativeFile.isDirectory())
			{
				relativeTo = getParentFolder(relativeTo);
			}
			
			String folderName = "/" + getFileName(relativeTo) + "/";
			
			int index = path.lastIndexOf(folderName);
			
			return path.substring(index + folderName.length());
//		}
//		else
//		{
//			throw new IllegalArgumentException('"' + relativeTo + '"' + " must be an existing location.");
//		}
	}
	
	public static Font loadMonospacedFont(Display display, String name, String location, int size, int style)
	{
		File file = new File(location);
		
		if (!file.exists())
		{
			throw new IllegalStateException("\"" + file.toString() + "\" does not exist.");
		}
		
		if (!display.loadFont(file.toString()))
		{
			throw new IllegalStateException("\"" + file.toString() + "\" did not load correctly.");
		}
		
		final Font font = new Font(display, name, size, style);
		
		display.addListener(SWT.Dispose, new Listener()
		{
			public void handleEvent(Event event)
			{
				font.dispose();
			}
		});
		
		return font;
	}
	
	public static void writeFile(String location, String text)
	{
		File file = new File(location);
		
		try
		{
			PrintWriter writer = new PrintWriter(new FileWriter(file));
			
			writer.print(text);
			
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static String getAbsolutePath(String location) throws IOException
	{
		File fileLocation = new File(location);
		
		String loc        = fileLocation.getCanonicalPath();
		
		return loc.replace('\\', '/');
	}
}
