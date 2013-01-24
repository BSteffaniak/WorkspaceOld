package net.foxycorndog.arrowide.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.widgets.Display;

import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.file.FileUtils;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;

public class Command
{
	private String                      directory, line;
	
	private ConsoleStream				stream;
	
	private Display						display;
	
	private String						commands[];

	private ArrayList<CommandListener>	listeners;
	
	public Command(Display display, String command, ConsoleStream stream, String directory)
	{
		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
		
		while (m.find())
		{
			String s = m.group(1);
			
			s = (s.startsWith("\"") || s.startsWith("'")) && (s.endsWith("\"") || s.endsWith("'")) ? s.substring(1, s.length() - 1) : s;
			
		    list.add(s);
		}
		
		init(display, list.toArray(new String[0]), stream, directory);
	}
	
	public Command(Display display, String command[], ConsoleStream stream, String directory)
	{
//		this.command = command;
		init(display, command, stream, directory);
	}
	
	private void init(Display display, String command[], ConsoleStream stream, String directory)
	{
		this.display   = display;
		
		this.directory = directory;
		
		this.commands  = command;
		
		this.stream    = stream;
		
		listeners      = new ArrayList<CommandListener>();
	}
	
	public void execute() throws IOException
	{
		System.out.println(Arrays.asList(commands) + ", " + directory);
		
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
									ProcessBuilder builder = new ProcessBuilder(commands);
									
									if (directory != null)
									{
										builder.directory(new File(directory));
									}
									
									Process process = builder.start();
									
									LogStreamReader lsr = new LogStreamReader(display, process.getInputStream(), stream, CONFIG_DATA.get("workspace.location") + "/");
									Thread thread = new Thread(lsr, "LogStreamReader");
									thread.start();
									
									boolean failed = false;
									
									BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
									
									try
									{
										while ((line = reader.readLine()) != null)
										{
											if (!failed)
											{
												for (int i = listeners.size() - 1; i >= 0; i --)
												{
													listeners.get(i).resultReceived(1);
												}
											}
											
											failed = true;
											
											display.syncExec(new Runnable()
											{
												public void run()
												{
													if (stream != null)
													{
														stream.println(line);
													}
												}
											});
											
											if (!reader.ready())
											{
												break;
											}
										}
									}
									catch (IOException e)
									{
										failed = true;
										
										e.printStackTrace();
									}
									
									line = null;
									
									if (!failed)
									{
										display.syncExec(new Runnable()
										{
											public void run()
											{
												for (int i = listeners.size() - 1; i >= 0; i --)
												{
													listeners.get(i).resultReceived(0);
												}
											}
										});
										
										int result = process.waitFor();
									
										thread.join();
										reader.close();
										lsr.stop();
									}
									
									process.destroy();
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
	}
	
	public void addCommandListener(CommandListener lisetener)
	{
		listeners.add(lisetener);
	}
}

class LogStreamReader implements Runnable
{
	private boolean			running;

	private String			location, line;

	private ConsoleStream	stream;

	private BufferedReader	reader;
    
	private Display			display;
	
    public LogStreamReader(Display display, InputStream is, ConsoleStream stream, String location)
    {
    	this.location = location;
    	
        this.reader = new BufferedReader(new InputStreamReader(is));
        
        this.stream = stream;
        
        this.display = display;
        
        running = true;
    }

    public void run()
    {
        try
        {
            while ((line = reader.readLine()) != null)
            {
            	line = line.replace(location, "");
            	
            	if (running)
            	{
            		display.syncExec(new Runnable()
    				{
    					public void run()
    					{
    						if (stream != null)
							{
    							stream.println(line);
							}
    					}
    				});
            	}
            	else
            	{
            		return;
            	}
            }
            
            line = null;
            
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