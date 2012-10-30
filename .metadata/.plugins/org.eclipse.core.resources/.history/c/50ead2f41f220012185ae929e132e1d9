package net.foxycorndog.poker.multiplayer;

import static net.foxycorndog.poker.packet.Packet.DEAL;
import static net.foxycorndog.poker.packet.Packet.READY_FOR_RESULTS;
import static net.foxycorndog.poker.packet.Packet.READY_TO_DEAL;
import static net.foxycorndog.poker.packet.Packet.RESULTS;
import static net.foxycorndog.poker.packet.Packet.WAITING;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import net.foxycorndog.poker.Card;
import net.foxycorndog.poker.Player;
import net.foxycorndog.poker.PokerGame;
import net.foxycorndog.poker.packet.Hand;
import net.foxycorndog.poker.packet.Packet;
import net.foxycorndog.poker.packet.Ping;

public abstract class Network
{
	private boolean            connected;
	private boolean            receivedCards;
	
	private ObjectOutputStream outj;
	private ObjectInputStream  in;
	
	private Socket             connection;
	
	private PokerGame          poker;
	
	/**
	* Constructor method that is used to set the PokerGame variable
	* to the PokerGame variable sent through the parameters.
	* 
	* @param poker The PokerGame that this network is working
	* 		in conjunction with.
	*/
	public Network(PokerGame poker)
	{
		this.poker = poker;
	}
	
	/**
	* Method used for retrieving the PokerGame variable that is
	* used throughout the Network.
	* 
	* @return The PokerGame that is used for the Network.
	*/
	public PokerGame getPoker()
	{
		return poker;
	}
	
	/**
	* Method used for sending a Player to the other Network.
	* 
	* @param p The Player to send to the other Network.
	* @return Whether the Player was sent successfully or not.
	*/
	public boolean sendPlayer(Player p)
	{
		sendPacket(new net.foxycorndog.poker.packet.Player(p));
		
		return true;
	}
	
	/**
	* Method used for sending a Hand to the other Network.
	* 
	* @param hand The Hand to send to the other Network.
	* @param player The Player to send the hand to.
	* @return Whether the Hand was sent successfully or not.
	*/
	public boolean sendHand(int hand[], int player)
	{
		sendPacket(new Hand(hand, player));
		
		return true;
	}
	
	/**
	* Method to send a request to the other Network. The request
	* depends on the final static integers in the Packet class.
	* 
	* @param type The type of request to send.
	*/
	public void sendRequest(int type)
	{
		sendPacket(new Packet(type));
	}
	
	/**
	* Method used to send a packet to the other Network.
	* 
	* @param object The Packet to send to the other Network.
	*/
	public void sendPacket(Packet object)
	{
		try
		{
			out.writeObject(object);
			
			out.writeObject(new Ping());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	* Method to send an already created Ping object to the other
	* Network.
	* 
	* @param ping The Ping that has been received from the other
	* 		Network.
	*/
	public void ping(Ping ping)
	{
		try
		{
			out.writeObject(ping);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	* Method to retrieve whether the Network has received the cards.
	* 
	* @return Whether the Network has received the cards.
	*/
	public boolean hasReceivedCards()
	{
		return receivedCards;
	}
	
	/**
	* Method that sets whether the Network has received the Cards
	* from the other Network or not.
	* 
	* @param received Whether the Network has received the Cards or not.
	*/
	public void setReceivedCards(boolean received)
	{
		this.receivedCards = received;
	}
	
	/**
	* Method that gets whether the Network is connected to another
	* Network or not.
	* 
	* @return Whether the Network is connected to another Network or not.
	*/
	public boolean isConnected()
	{
		return connected;
	}
	
	/**
	* Method that sets whether the Network is connected to another
	* Network or not.
	* 
	* @param connected Whether the Network is connected to another
	* 		Network or not.
	*/
	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}
	
	/**
	* Method to get the ObjectOutputStream of the Network.
	* 
	* @return The ObjectOutputStream of the Network.
	*/
	public ObjectOutputStream getOut()
	{
		return out;
	}
	
	/**
	* Method to set the ObjectOutputStream for the Network.
	* 
	* @param out The ObjectOutputStream for the Network to send objects
	* 		to the other Network.
	*/
	public void setOut(ObjectOutputStream out)
	{
		this.out = out;
	}
	
	/**
	* Method to get the ObjectInputStream of the Network.
	* 
	* @return The ObjectInputStream of the Network.
	*/
	public ObjectInputStream getIn()
	{
		return in;
	}
	
	/**
	* Method to set the ObjectInputStream for the Network.
	* 
	* @param in The ObjectInputStream for the Network to read objects
	* 		from the other Network.
	*/
	public void setIn(ObjectInputStream in)
	{
		this.in = in;
	}
	
	/**
	* Method to set the connection to a Socket.
	* 
	* @param connection The Socket to set as the connection.
	*/
	public void setConnection(Socket connection)
	{
		this.connection = connection;
	}
	
	/**
	* Class that is used for the loop that loops through all
	* of the input received from the other Network.
	* 
	* @author Braden Steffaniak
	*/
	abstract class InputLoop
	{
		/**
		* Default Constructor for the InputLoop class.
		*/
		public InputLoop()
		{
			
		}
		
		/**
		* Method that is called when a Player is received from the
		* other Network.
		* 
		* @param player The Player that was received from the other
		* 		Network.
		*/
		public void onReceivedPlayer(net.foxycorndog.poker.packet.Player player)
		{
			net.foxycorndog.poker.Player playr = player.getPlayer();
			
			poker.setOpponent(playr);
			
			for (int i = 0; i < 5; i ++)
			{
				playr.setCardInHand(new Card(playr.getCardFromHand(i).getRank()), i);
			}
			
			poker.setPlayerConnected(true);
		}

		/**
		* Method that is called when a Hand is received from the
		* other Network.
		* 
		* @param hand The Hand that was received from the other
		* 		Network.
		*/
		public void onReceivedHand(Hand hand)
		{
			setReceivedCards(false);
			
			int playerHand[] = hand.getRanks();
			
			if (hand.getPlayer() == 1)
			{
				for (int i = 0; i < playerHand.length; i ++)
				{
					poker.getPlayer1().setCardInHand(new Card(playerHand[i]), i);
				}
				
				setReceivedCards(true);
			}
			else if (hand.getPlayer() == 2)
			{
				for (int i = 0; i < playerHand.length; i ++)
				{
					poker.getPlayer2().setCardInHand(new Card(playerHand[i]), i);
				}
			}
		}

		/**
		* Method that is called when a Ping is received from the
		* other Network.
		* 
		* @param player The Ping that was received from the other
		* 		Network.
		*/
		public void onReceivedPing(Ping ping)
		{
			if (ping.wasReceived())
			{
				ping.calculatePing();
				
				System.out.println("Ping: " + ping.getPing());
			}
			else
			{
				ping.receive();
				
				ping(ping);
			}
		}

		/**
		* Method that is called when a deal request is received from the
		* other Network.
		*/
		public void onReceivedRequestDeal()
		{
			poker.getPlayer2().setReadyToDeal(true);
			
			poker.setWaitingOnPlayer(false);
				
			if (poker.getPlayer1().getReadyToDeal())
			{
				sendRequest(READY_TO_DEAL);
				
				poker.deal();
			}
			else
			{
				sendRequest(WAITING);
				
				poker.setPlayerWaitingOn(true);
			}
		}

		/**
		* Method that is called when a results request is received
		* from the other Network.
		*/
		public void onReceivedRequestResults()
		{
			poker.getPlayer2().setReadyForResults(true);
			
			poker.setWaitingOnPlayer(false);
				
			if (poker.getPlayer1().getReadyForResults())
			{
				sendRequest(READY_FOR_RESULTS);
				
				poker.results();
			}
			else
			{
				sendRequest(WAITING);
				
				poker.setPlayerWaitingOn(true);
			}
		}

		/**
		* Method that is called when a waiting request is received
		* from the other Network.
		*/
		public void onReceivedRequestWaiting()
		{
			poker.setWaitingOnPlayer(true);
		}

		/**
		* Method that is called when a ready for results request
		* is received from the other Network.
		*/
		public void onReceivedRequestReadyForResults()
		{
			if (!poker.hasShownResults())
			{
				poker.setPlayerWaitingOn(false);
				
				poker.results();
			}
		}

		/**
		* Method that is called when a ready to deal request is
		* received from the other Network.
		*/
		public void onReceivedRequestReadyToDeal()
		{
			if (!poker.hasDealt())
			{
				poker.setPlayerWaitingOn(false);
				
				poker.deal();
			}
		}

		/**
		* Method that is called when a Player has disconnected from the
		* other Network.
		*/
		public void onPlayerDisconnect()
		{
			System.out.println(poker.getPlayer2().getName() + " disconnected.");
		}
		
		/**
		* The loop method that runs forever while the Socket is
		* connected.
		*/
		public final void start()
		{
			while (connection.isConnected())
			{
				Packet packet = null;
				
				try
				{
					packet = (Packet)in.readObject();
					
//					System.out.println(packet.getClass().getSimpleName());
					
//					receivePings();
				}
				catch (SocketException ex)
				{
					onPlayerDisconnect();
					
					break;
				}
				catch (EOFException ex)
				{
					onPlayerDisconnect();
					
					break;
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
					
					break;
				}
				catch (IOException e)
				{
					e.printStackTrace();
					
					break;
				}
				
				if (packet instanceof net.foxycorndog.poker.packet.Player)
				{
					onReceivedPlayer((net.foxycorndog.poker.packet.Player)packet);
				}
				else if (packet instanceof Hand)
				{
					onReceivedHand((Hand)packet);
				}
				else if (packet instanceof Ping)
				{
					onReceivedPing((Ping)packet);
				}
				else if (packet.getType() == DEAL)
				{
					onReceivedRequestDeal();
				}
				else if (packet.getType() == RESULTS)
				{
					onReceivedRequestResults();
				}
				else if (packet.getType() == Packet.WAITING)
				{
					onReceivedRequestWaiting();
				}
				else if (packet.getType() == READY_FOR_RESULTS)
				{
					onReceivedRequestReadyForResults();
				}
				else if (packet.getType() == READY_TO_DEAL)
				{
					onReceivedRequestReadyToDeal();
				}
			}
		}
	}
}