package net.foxycorndog.arrowide.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.widgets.Display;

import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.file.FileUtils;

public class Command
{
	public static Process execute(String command, final ConsoleStream stream) throws IOException
	{
//		String javaHome  = System.getProperty("java.home");
//		String javaBin   = javaHome + "/bin/java";
//		String classpath = FileUtils.getParentFolder(classLocation);//System.getProperty("java.class.path");
//		String className = clazz.getCanonicalName();
		
//		command.replace(" ", "%20");
//		String split[] = command.split(" ");

		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
		while (m.find())
		{
		    list.add(m.group(1));
		}
		
		final ProcessBuilder builder = new ProcessBuilder(list);
		
		final Process process = builder.start();
		
		new Thread()
		{
			public void run()
			{
				Display.getDefault().asyncExec(new Runnable()
				{
					public void run()
					{
						new Thread()
						{
							public void run()
							{
								try
								{
									LogStreamReader lsr = new LogStreamReader(process.getInputStream(), stream);
									Thread thread = new Thread(lsr, "LogStreamReader");
									thread.start();
									
//										PrintStream ot = new PrintStream(process.getOutputStream());
//											System.setOut(ot);
//										int result      = process.waitFor();
									
									boolean failed = false;
									
//										if (result == 1)
//										{
										BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
										
										String  line = null;
										
										while ((line = reader.readLine()) != null)
										{
											stream.println(line);
											
											failed = true;
										}
//										}
//										else
//										{
//											reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//											
//											line   = null;
//											
//											while ((line = reader.readLine()) != null)
//											{
//												System.out.println(line);
//											}
//										}
									
									System.out.println(failed);
										
									if (!failed)
									{
										int result = process.waitFor();
									}
									else
									{
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
						}.start();
					}
				});
			}
		}.start();
		
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