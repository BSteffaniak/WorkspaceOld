package net.foxycorndog.arrowide.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.widgets.Display;

import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.file.FileUtils;

public class Command
{
	private String						commands[];

	private ConsoleStream				stream;

	private ArrayList<CommandListener>	listeners;
	
	public Command(String command, ConsoleStream stream)
	{
		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
		
		while (m.find())
		{
			String s = m.group(1);
			
			s = (s.startsWith("\"") || s.startsWith("'")) && (s.endsWith("\"") || s.endsWith("'")) ? s.substring(1, s.length() - 1) : s;
			
		    list.add(s);
		}
		
		init(list.toArray(new String[0]), stream);
	}
	
	public Command(String command[], ConsoleStream stream)
	{
//		this.command = command;
		init(command, stream);
	}
	
	private void init(String command[], ConsoleStream stream)
	{
		this.commands = command;
		
		this.stream   = stream;
		
		listeners     = new ArrayList<CommandListener>();
	}
	
	public void execute() throws IOException
	{
		System.out.println(Arrays.asList(commands));
		
		final ProcessBuilder builder = new ProcessBuilder(commands);
		
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
									Process process = builder.start();
									
									LogStreamReader lsr = new LogStreamReader(process.getInputStream(), stream);
									Thread thread = new Thread(lsr, "LogStreamReader");
									thread.start();
									
									boolean failed = false;
									
									BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
									
									String  line = null;
									
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
												
												lsr.stop();
											}
											
											failed = true;
											
											stream.println(line);
										}
									}
									catch (IOException e)
									{
										e.printStackTrace();
									}
									
									thread.join();
									process.destroy();
									
									reader.close();
									
									if (!failed)
									{
										for (int i = listeners.size() - 1; i >= 0; i --)
										{
											listeners.get(i).resultReceived(0);
										}
										
										int result = process.waitFor();
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
	}
	
	public void addCommandListener(CommandListener lisetener)
	{
		listeners.add(lisetener);
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