package net.foxycorndog.poker.multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

import net.foxycorndog.poker.Card;
import net.foxycorndog.poker.PokerGame;
import net.foxycorndog.poker.packet.Hand;

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
	public Client(String ip, int port, PokerGame poker)
	{
		super(poker);
		
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
		sendPlayer(getPoker().getPlayer1());
		
		receiveFirstHand();
		
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
	
	/**
	* Method used to receive the first Hand of the game, and set the
	* cards to the values received from the Hand.
	*/
	public void receiveFirstHand()
	{
		Hand hand = null;
		
		try
		{
			hand = (Hand)getIn().readObject();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		int playerHand[] = hand.getRanks();
		
		for (int i = 0; i < playerHand.length; i ++)
		{
			getPoker().getPlayer1().setCardInHand(new Card(playerHand[i]), i);
		}
		
		getPoker().calculateCards();
	}
}