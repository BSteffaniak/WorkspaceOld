package net.foxycorndog.poker.packet;

import java.io.Serializable;

public class Packet implements Serializable
{
	private int type;
	
	public static final int DEAL = 1, RESULTS = 2, WAITING = 3, READY_TO_DEAL = 4, READY_FOR_RESULTS = 5;
	
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