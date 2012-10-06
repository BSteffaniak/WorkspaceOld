package net.foxycorndog.poker.multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import net.foxycorndog.poker.Card;
import net.foxycorndog.poker.PokerGame;
import net.foxycorndog.poker.packet.Hand;

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
	public Server(int port, PokerGame poker)
	{
		super(poker);
		
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
			
			int playerHand[] = new int[]
			{
					getPoker().getDeck().dealCard().getRank(),
					getPoker().getDeck().dealCard().getRank(),
					getPoker().getDeck().dealCard().getRank(),
					getPoker().getDeck().dealCard().getRank(),
					getPoker().getDeck().dealCard().getRank()
			};
			
			boolean success = sendHand(playerHand, 1);
			
			if (success)
			{
				System.out.println("success......");
				
				sendPlayer(getPoker().getPlayer1());
			}
			else
			{
				System.out.println("Failed to send hand!");
				
				return false;
			}
			
			
			
			new Thread()
			{
				public void run()
				{
					new InputLoop()
					{
						public void onReceivedHand(Hand hand)
						{
							int playerHand[] = hand.getRanks();
							
							if (hand.getPlayer() == 1)
							{
								for (int i = 0; i < playerHand.length; i ++)
								{
									getPoker().getPlayer1().setCardInHand(new Card(playerHand[i]), i);
								}
							}
							else if (hand.getPlayer() == 2)
							{
								for (int i = 0; i < playerHand.length; i ++)
								{
									getPoker().getPlayer2().setCardInHand(new Card(playerHand[i]), i);
								}
							}
						}
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
}