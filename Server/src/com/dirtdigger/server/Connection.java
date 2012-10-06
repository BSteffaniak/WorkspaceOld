package com.dirtdigger.server;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author BradenSteffaniak
 */
public class Connection implements Runnable
{
	private int id;
	private Thread thread;
	private Socket skt;
	private int mode = 1;

	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean done = true;

	private final String CLASSNAME = getClass().getName();

	//Server s;
	Thread t;
	public Connection(Socket skt)
	{
		id = Connections.id ++;
		this.skt = skt;
		done = false;
		running();
	}

	private void running()
	{
		try
		{
			out = new ObjectOutputStream(skt.getOutputStream());
			out.flush();
			in = new ObjectInputStream(skt.getInputStream());
			
			out.writeInt(4);
			out.writeObject("");
			out.writeObject("Server");
			out.writeObject(skt.getInetAddress().getHostAddress());

			//s = new Server();
			t = new Thread(this);
			t.start();
		}
		catch (IOException ex)
		{
			Methods.stateError(ex, 54, CLASSNAME, true);
		}
	}

	protected void chatted(String message, String from, String ip, int mode)
	{
		//if there is a message and the socket is created

		if (message != null)
		{
			try
			{
				//try to write a String to whoever is waiting on the
				//other side. :)
				out.writeInt(mode);
				out.writeObject(message);
				if (from.equals("Client"))
				{	
					out.writeObject("Client");
				}
				else if (from.equals("Server"))
				{
					out.writeObject("Server");
				}
				out.writeObject(ip);
				
				Random random = new Random();
				ArrayList<Integer> j = new ArrayList<Integer>();
				
				j.add(random.nextInt(5));
				j.add(random.nextInt(5));
					
				out.writeObject(j);
			}
			catch (IOException ex)
			{
				Methods.stateError(ex, 53, CLASSNAME, true);
			}
		}

		//Set the textField text back to nothing so it is ready for typing
		//again.

		Main.textField.setText("");
	}

	@Override
	public void run()
	{
		for (;;)
		{
			try
			{
				Thread.sleep(50);
				mode = in.readInt();
				String input = (String) in.readObject();
				String ip = (String) in.readObject();
				ArrayList<Integer> j = (ArrayList<Integer>)in.readObject();
				if (mode == 1)
				{
					Connections.sendMessage(input, "Client", ip, 2);
				}
				else if (mode == 2)
				{
					Connections.sendMessage(input, "Client", ip, 2);
				}
				else if (mode == 3)
				{
					Main.chatLog.setText(Main.chatLog.getText() + "<Client (" + ip + ") has left the game>\n");
				}
				else
				{
					Main.chatLog.setText(Main.chatLog.getText() + "<Client (" + ip + ")>" + input +
							"\n");
				}
				Main.chatLog.setText(Main.chatLog.getText() + "<Server (" + ip + ")> " + j.get(0) + ", " + j.get(1) +
						"\n");
			}
			catch (ClassNotFoundException ex)
			{
				Methods.stateError(ex, 1, CLASSNAME, true);
			}
			catch (IOException ex)
			{
				Methods.stateError(ex, 52, CLASSNAME, true);
			}
			catch (InterruptedException ex)
			{
				Methods.stateError(ex, 51, CLASSNAME, true);
			}	
		}
	}
}
