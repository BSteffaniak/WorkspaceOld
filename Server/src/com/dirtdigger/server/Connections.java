package com.dirtdigger.server;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author BradenSteffaniak
 */
public class Connections implements Runnable
{
	public static int id;
	private static Thread thread;
	private static Socket[] sockets = new Socket[16];
	private static int socketNumber = 0;
	private static Connection c[] = new Connection[16];
	private static int connectionNumber = 0;
	private static boolean comeOn = false;

	private final String CLASSNAME = getClass().getName();
	//Server s;
	Thread t;
	public Connections()
	{
		//s = new Server();
		comeOn = true;
		t = new Thread(this);
		t.start();
	}

	private void waiting()
	{
		while (true)
		{
			try
			{
				System.out.println("TEST");
				//Tell the Socket (skt) to wait until a connection is accepted
				sockets[socketNumber] = Main.ss.accept();
				String message = "A Client has connected from: " + sockets[socketNumber].getLocalSocketAddress().toString().replace("/", "");
				Main.addMessage(message, getClass().getSimpleName());
				Main.mainFrame.validate();
				c[connectionNumber++] = new Connection(sockets[socketNumber++]);
			}
			catch (IOException ex)
			{
				Methods.stateError(ex, 1, CLASSNAME, false);
			}
		}
	}

	public static void sendMessage(String message, String from, String ip, int mode)
	{
		for (int i = 0; i < connectionNumber; i++)
		{
			c[i].chatted(message, from, ip, mode);
		}
		Main.chatLog.setText(Main.chatLog.getText() + "<" + from + " (" + ip + ")> " + message + "\n");
	}
	
	public static void closeSocket(int index)
	{
		try
		{
			sockets[index].close();
		}
		catch (IOException ex)
		{
			
		}
	}
	
	public void run()
	{
		while (true)
		{
			if (comeOn)
			{
				waiting();
			}
			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException ex)
			{
				Methods.stateError(ex, 2, CLASSNAME, true);
			}
		}
	}
}
