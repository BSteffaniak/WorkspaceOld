package net.foxycorndog.arrowide.language.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.file.FileUtils;

public class JavaLanguage
{
	public  static final Color
			COMMENT_COLOR = new Color(Display.getCurrent(), 40, 140, 0),
			KEYWORD_COLOR = new Color(Display.getCurrent(), 150, 0, 0);
	
	public static void init()
	{
		
	}
	
	public static void run(final String classLocation, final ConsoleStream stream)
	{
		final PrintStream out = System.out;
		final PrintStream err = System.err;
		
//		System.setOut(stream);
//		System.setErr(stream);
		
		try
		{
			URL url = new URL("file://" + FileUtils.getParentFolder(classLocation) + "/");
			
			final ClassLoader cLoader   = new URLClassLoader(new URL[] { url });
			
			final String      className = classLocation.substring(classLocation.lastIndexOf('/') + 1, classLocation.lastIndexOf('.'));
			
			
//			Class        clazz     = cLoader.loadClass(className);//classLocation.substring(0, classLocation.lastIndexOf('.')));
			
//			final Method m         = clazz.getMethod("main", String[].class);
			
//			classRunning = true;
			
			new Thread()
			{
				public void run()
				{
					ArrowIDE.display.asyncExec(new Runnable()
					{
						public void run()
						{
							new Thread()
							{
								public void run()
								{
									try
									{
										Class clazz   = Class.forName(className, true, cLoader);
										
										try
										{
											Process process = exec(clazz, classLocation, stream);
											
											LogStreamReader lsr = new LogStreamReader(process.getInputStream(), stream);
											Thread thread = new Thread(lsr, "LogStreamReader:" + classLocation);
											thread.start();
											
//											PrintStream ot = new PrintStream(process.getOutputStream());
//												System.setOut(ot);
//											int result      = process.waitFor();
											
											boolean failed = false;
											
//											if (result == 1)
//											{
												BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
												
												String  line = null;
												
												while ((line = reader.readLine()) != null)
												{
													stream.println(line);
													
													failed = true;
												}
//											}
//											else
//											{
//												reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//												
//												line   = null;
//												
//												while ((line = reader.readLine()) != null)
//												{
//													System.out.println(line);
//												}
//											}
											
											System.out.println(failed);
												
											if (!failed)
											{
												int result = process.waitFor();
											}
											else
											{
												stream.println("failed");
												lsr.stop();
												thread.join();
												process.destroy();
											}
										}
										catch (InterruptedException e)
										{
											e.printStackTrace();
										}
										catch (IOException e)
										{
											e.printStackTrace();
										}
									}
									catch (ClassNotFoundException e)
									{
										e.printStackTrace();
									}
								}
							}.start();
						}
					});
				}
			}.start();
//			
//			while (classRunning)
//			{
//				if (!ArrowIDE.display.readAndDispatch())
//				{
//					ArrowIDE.display.sleep();
//				}
//			}
		}
//		catch (NoSuchMethodException e)
//		{
//			e.printStackTrace();
//		}
//		catch (ClassNotFoundException e)
//		{
//			e.printStackTrace();
//		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
//		System.setOut(out);
//		System.setErr(err);
	}
	
	private static Process exec(Class clazz, String classLocation, ConsoleStream stream) throws IOException, InterruptedException
	{
		String javaHome  = System.getProperty("java.home");
		String javaBin   = javaHome + "/bin/java";
		String classpath = FileUtils.getParentFolder(classLocation);//System.getProperty("java.class.path");
		String className = clazz.getCanonicalName();
		
		final ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);
		
		Process process = builder.start();
		
		return process;
	}
}

class LogStreamReader implements Runnable
{
	private boolean running;
	
	private ConsoleStream  stream;
	
	private BufferedReader reader;
    
    public LogStreamReader(InputStream is, ConsoleStream stream)
    {
        this.reader = new BufferedReader(new InputStreamReader(is));
        
        this.stream = stream;
        
        running = true;
    }

    public void run()
    {
        try
        {
            String line = null;
            
            while ((line = reader.readLine()) != null)
            {
            	if (running)
            	{
            		stream.println(line);
            	}
            	else
            	{
            		return;
            	}
            }
            
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void stop()
    {
    	running = false;
    }
}