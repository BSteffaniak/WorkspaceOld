package net.foxycorndog.poker.packet;

public class Player extends Packet
{
	net.foxycorndog.poker.Player player;
	
	/**
	* Creates a new Player packet used for sending Players across
	* the network.
	* 
	* @param player The player to send.
	*/
	public Player(net.foxycorndog.poker.Player player)
	{
		super(0);
		
		this.player = player;
	}
	
	/**
	* Method used for getting the Player from the Packet.
	* 
	* @return The net.foxycorndog.poker.Player associated with this
	* 		class.
	*/
	public net.foxycorndog.poker.Player getPlayer()
	{
		return player;
	}
}