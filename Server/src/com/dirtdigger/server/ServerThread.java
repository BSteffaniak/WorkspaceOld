package com.dirtdigger.server;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BradenSteffaniak
 */
public class ServerThread implements Runnable
{
	//Server s;
	Thread t;
	public ServerThread()
	{
		//s = new Server();
		t = new Thread(this);
		t.start();
	}

	public void run()
	{
		for (;;)
		{
			try
			{
				Thread.sleep(50);
			} 
			catch (InterruptedException ex)
			{
				Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
			}
			Main.send.setLocation(Main.mainFrame.getWidth() - 90, Main.mainFrame.getHeight() - 45);
			Main.startStop.setLocation(Main.mainFrame.getWidth() - 90, 10);
			Main.sp.setSize(Main.mainFrame.getWidth() - 100, Main.mainFrame.getHeight() - 55);
			Main.textField.setSize(Main.mainFrame.getWidth() - 100, 20);
			Main.textField.setLocation(5, Main.mainFrame.getHeight() - 45);
		}
	}

}
