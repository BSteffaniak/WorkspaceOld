package net.foxycorndog.jdoutil.network;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class Server extends Network
{
	private int          port;
	
	private Socket       socket;
	
	private ServerSocket server;
	
	/**
	* Creates a Server that extends the Network class. This constructor
	* method is used for creating a new ServerSocket.
	* 
	* @param port The port of the created Server
	* @param poker The PokerGame object that the Server is created from.
	*/
	public Server(int port)
	{
		this.port = port;
	}
	
	public void create()
	{
		try
		{
			server = new ServerSocket(port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			try
			{
				System.out.println("Server started on " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
			}
			catch (UnknownHostException e)
			{
				e.printStackTrace();
			}
			
			socket = server.accept();
			
			setConnection(socket);
			
			setOut(new ObjectOutputStream(socket.getOutputStream()));
			setIn(new ObjectInputStream(socket.getInputStream()));
			
			setConnected(socket.isConnected());
			
			System.out.println("Connected!");
			
			sendPacket(new Packet(new Point(3, 1), 2));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		startInputLoop();
	}
}