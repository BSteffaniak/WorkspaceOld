package net.foxycorndog.poker;
/**
* File:  			Card.java HONORS
* Author:			Braden Steffaniak
* Programming:  	1st Hour
* Last Modified: 	28Feb2012
* Description: Declares several variables that set the
* specific card apart from the rest in the deck.
*/

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Card implements Serializable
{
	// 2, 3, 4, ..., 10, 11 = Jack, 12 = Queen, 13 = King, 14 = Ace
	private transient int       value;
	
	// 0 = Heart, 1 = Diamond, 2 = Club, 3 = Spade
	private transient int       suit;
	
	// Red or Black (true or false)
	private transient boolean   color;
	
	// The Icon that shows on the front of the Card
	private transient ImageIcon faceImage;
	
	// The Image that shows on the back of every Card
	private transient ImageIcon backImage;
	
	// 0 - 51. 0 = 2C, 1 = 3C, 2 = 4C, B = AC, 14 = 2?
	private transient int       rank;
	
	/**
	* Creates a blank card.
	*/
	public Card()
	{
		value     =  0;
		suit      = -1;
		color     = false;
		faceImage = null;
		backImage = null;
		rank      = -1;
	}
	
	/**
	* Creates a card with the specified rank.
	* 
	* @param r The rank to be assigned.
	*/
	public Card(int r)
	{
		rank      =  r;
		suit      =  r / 13;
		value     = (r % 13) + 2;
		
		faceImage = new ImageIcon("res/images/" + r + ".gif");
		backImage = new ImageIcon("res/images/back.gif");
	}
	
	/**
	* Returns the rank variable.
	* 
	* @return the rank
	*/
	public int getRank()
	{
		return rank;
	}
	
	/**
	* Returns the value variable.
	* 
	* @return the value
	*/
	public int getValue()
	{
		return value;
	}

	/**
	* Returns the suit variable.
	* 
	* @return the suit
	*/
	public int getSuit()
	{
		return suit;
	}

	/**
	* Returns the color variable.
	* 
	* @return the color
	*/
	public boolean getColor()
	{
		return color;
	}

	/**
	* Returns the faceImage variable.
	* 
	* @return the faceImage
	*/
	public ImageIcon getFrontImage()
	{
		return faceImage;
	}

	/**
	* Returns the backImage variable.
	* 
	* @return the backImage
	*/
	public ImageIcon getBackImage()
	{
		return backImage;
	}
}