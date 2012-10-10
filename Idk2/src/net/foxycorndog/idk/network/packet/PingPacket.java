package net.foxycorndog.idk.network.packet;

public class PingPacket extends Packet
{
	private boolean received;
	
	private long    timeSent;
	private long    timeReceived;
	
	private int     ping;
	
	/**
	* Creates a Ping object used for receiving the ping count of
	* the network.
	*/
	public PingPacket()
	{
		super(0);
		
		timeSent = System.currentTimeMillis();
	}
	
	/**
	* Tell the network that the ping has been received.
	*/
	public void receive()
	{
		received = true;;
	}
	
	/**
	* A check that the network checks to see if it has already
	* been sent and just now been received back.
	* 
	* @return Whether it has been received by the other network.
	*/
	public boolean wasReceived()
	{
		return received;
	}
	
	/**
	* Method used for calculating the ping of the network. Called
	* when the ping has been sent and received to the same network.
	*/
	public void calculatePing()
	{
		timeReceived = System.currentTimeMillis();
		
		ping         = (int)(timeReceived - timeSent);
	}
	
	/**
	* Method used for getting the integer value of the ping.
	* 
	* @return The integer value of the ping.
	*/
	public int getPing()
	{
		return ping;
	}
}