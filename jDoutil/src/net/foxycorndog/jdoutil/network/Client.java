package net.foxycorndog.jdoutil.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

public abstract class Client extends Network
{
	private int    port;
	
	private String ip;
	
	private Socket socket;
	
	/**
	* The Constructor method that attempts to connect to the
	* specified Server.
	* 
	* @param ip The ip to the Server.
	* @param port The port to the Server.
	* @param poker The PokerGame object that the Client is create from.
	*/
	public Client(String ip, int port)
	{
		this.ip   = ip;
		this.port = port;
	}
	
	public void connect()
	{
		try
		{
			System.out.println("Attempting connection on " + ip + ":" + port);
			
			socket = new Socket(ip, port);
			
			setOut(new ObjectOutputStream(socket.getOutputStream()));
			setIn(new ObjectInputStream(socket.getInputStream()));
			
			setConnected(socket.isConnected());
			
			if (socket.isConnected())
			{
				System.out.println("Connected!!!");
			}
			else
			{
				System.out.println("Connection error!");
			}
			
			setConnection(socket);
		}
		catch (ConnectException ex)
		{
			System.out.println("Connection Refused!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		startInputLoop();
	}
}