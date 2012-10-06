package net.foxycorndog.poker.packet;

public class Hand extends Packet
{
	private int player;
	
	private int ranks[];
	
	/**
	* Creates a new Hand used for sending the information of a
	* hand of cards through network.
	* 
	* @param ranks The ranks of the cards in an integer array.
	* @param player The player to send them to through the network.
	*/
	public Hand(int ranks[], int player)
	{
		super(0);
		
		this.ranks = ranks;
		
		this.player = player;
	}
	
	/**
	* Method used for getting the integer array of ranks that the
	* cards that were passed through the network have.
	* 
	* @return The ranks in the form of an integer array.
	*/
	public int[] getRanks()
	{
		return ranks;
	}
	
	/**
	* Method used for retrieving the Player number that will have
	* their hand set to these cards.
	* 
	* @return The Player's number.
	*/
	public int getPlayer()
	{
		return player;
	}
}