package net.foxycorndog.jdoutil.network;

import java.io.Serializable;

public class Packet implements Serializable
{
	private Object object;
	
	private int id;
	
	/**
	* Creates a new Packet. Packets are used for sending information
	* across a network.
	* 
	* @param object The object that is being sent.
	* @param id The id of the request that is being sent.
	*/
	public Packet(Object object, int id)
	{
		this.object = object;
		this.id     = id;
	}
	
	/**
	* Method used for getting the id of the Packet.
	* 
	* @return The id of the Packet.
	*/
	public int getId()
	{
		return id;
	}
	
	public Object getObject()
	{
		return object;
	}
}