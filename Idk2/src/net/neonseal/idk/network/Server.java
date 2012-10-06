package net.neonseal.idk.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import net.neonseal.idk.Idk;
import net.neonseal.idk.animatedobject.actors.player.Player;
import net.neonseal.idk.network.packet.PlayerLocationPacket;

public class Server extends Network
{
	private ServerSocket       server;
	
	/**
	* Creates a Server that extends the Network class. This constructor
	* method is used for creating a new ServerSocket.
	* 
	* @param port The port of the created Server
	* @param poker The PokerGame object that the Server is created from.
	*/
	public Server(int port, Idk idk)
	{
		super(idk);
		
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
			System.out.println("Server started on " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	* Method that waits until a Client has been created in conjunction
	* with this Server's IP and Port.
	* 
	* Next, it sends the Player his hand, and then starts the
	* infinite loop that waits for requests and Packets.
	* 
	* @return Whether the opponents Hand Packet was sent successfully.
	*/
	public boolean waitPlayer()
	{
		Socket client = null;
		
		try
		{
			client = server.accept();
			
			setOut(new ObjectOutputStream(client.getOutputStream()));
			setIn (new ObjectInputStream (client.getInputStream()));
			
			System.out.println("Connected!");
			
			setConnection(client);

			Player player = getIdk().getPlayer();
			sendPlayerLocation(new float[] { player.getAbsoluteX(), player.getAbsoluteY(), player.getMap().getRelativeX(), player.getMap().getRelativeY() });
			
			receivePlayer();
			
			new Thread()
			{
				public void run()
				{
					new InputLoop()
					{
						
					}.start();
				}
			}.start();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			
			return false;
		}
		
		return true;
	}
	
	public void receivePlayer()
	{
		PlayerLocationPacket playerLoc = null;
		
		try
		{
			playerLoc = (PlayerLocationPacket)getIn().readObject();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		float loc[] = playerLoc.getLocation();
		
		getIdk().addPlayer(loc[0], loc[1], loc[2], loc[3]);
	}
}