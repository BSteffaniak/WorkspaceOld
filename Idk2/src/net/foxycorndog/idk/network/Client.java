package net.foxycorndog.idk.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

import net.foxycorndog.idk.Idk;
import net.foxycorndog.idk.animatedobject.actors.player.Player;
import net.foxycorndog.idk.network.packet.PlayerLocationPacket;

public class Client extends Network
{
	private Socket    socket;
	
	/**
	* The Constructor method that attempts to connect to the
	* specified Server.
	* 
	* @param ip The ip to the Server.
	* @param port The port to the Server.
	* @param poker The PokerGame object that the Client is create from.
	*/
	public Client(String ip, int port, Idk idk)
	{
		super(idk);
		
		try
		{
			System.out.println("Attempting connection on " + ip + ":" + port);
			
			socket = new Socket(ip, port);
			
			setOut(new ObjectOutputStream(socket.getOutputStream()));
			setIn(new ObjectInputStream (socket.getInputStream()));
			
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
	}
	
	/**
	* Method that waits for input from the Server through a loop
	* that runs until the Server or Client has disconnected.
	*/
	public void start()
	{
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