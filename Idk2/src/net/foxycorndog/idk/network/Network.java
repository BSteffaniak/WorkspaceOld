package net.foxycorndog.idk.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import net.foxycorndog.idk.Idk;
import net.foxycorndog.idk.network.packet.Packet;
import net.foxycorndog.idk.network.packet.PingPacket;
import net.foxycorndog.idk.network.packet.PlayerLocationPacket;

public abstract class Network
{
	private boolean            connected;
	private boolean            receivedCards;
	
	private ObjectOutputStream out;
	private ObjectInputStream  in;
	
	private Socket             connection;
	
	private Idk                idk;
	
	/**
	* Constructor method that is used to set the PokerGame variable
	* to the PokerGame variable sent through the parameters.
	* 
	* @param poker The PokerGame that this network is working
	* 		in conjunction with.
	*/
	public Network(Idk idk)
	{
		this.idk = idk;
	}
	
	/**
	* Method used for retrieving the PokerGame variable that is
	* used throughout the Network.
	* 
	* @return The PokerGame that is used for the Network.
	*/
	public Idk getIdk()
	{
		return idk;
	}
	
	/**
	* Method used for sending a Player's location to the other Network.
	* 
	* @param location The Player's location to send to the other Network.
	* @return Whether the location was sent successfully or not.
	*/
	public boolean sendPlayerLocation(float location[])
	{
		sendPacket(new PlayerLocationPacket(location));
		
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
			
			out.writeObject(new PingPacket());
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
	public void ping(PingPacket ping)
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
			Runtime.getRuntime().addShutdownHook(new Thread()
			{
				public void run()
				{
					try
					{
						connection.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					
					System.out.println("Shutting down.");
				}
			});
		}
		
		/**
		* Method that is called when a Player is received from the
		* other Network.
		* 
		* @param player The Player that was received from the other
		* 		Network.
		*/
		public void onReceivedPlayerLocation(PlayerLocationPacket playerLocation)
		{
			float loc[] = playerLocation.getLocation();
			
			idk.setPlayerLocation(loc);
		}

		/**
		* Method that is called when a Ping is received from the
		* other Network.
		* 
		* @param player The Ping that was received from the other
		* 		Network.
		*/
		public void onReceivedPing(PingPacket ping)
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
		* Method that is called when a Player has disconnected from the
		* other Network.
		*/
		public void onPlayerDisconnect()
		{
			System.out.println(" disconnected.");
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
				
				if (packet instanceof PlayerLocationPacket)
				{
					onReceivedPlayerLocation((PlayerLocationPacket)packet);
				}
				else if (packet instanceof PingPacket)
				{
					onReceivedPing((PingPacket)packet);
				}
			}
		}
	}
}