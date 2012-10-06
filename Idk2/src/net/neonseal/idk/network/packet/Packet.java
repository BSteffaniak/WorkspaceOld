package net.neonseal.idk.network.packet;

import java.io.Serializable;

public class Packet implements Serializable
{
	private int type;
	
	static enum Request
	{
		
	}
	
	/**
	* Creates a new Packet. Packets are used for sending information
	* across a network.
	* 
	* @param type The type of request that is being sent.
	*/
	public Packet(int type)
	{
		this.type = type;
	}
	
	/**
	* Method used for getting the type of request the Packet
	* is used for.
	* 
	* @return The type of request that the Packet is used for.
	*/
	public int getType()
	{
		return type;
	}
}