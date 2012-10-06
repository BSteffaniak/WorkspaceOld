package net.foxycorndog.poker;

/**
* File:  			Deck.java HONORS
* Author:			Braden Steffaniak
* Programming:  	1st Hour
* Last Modified: 	8Mar2012
* Description: DCreates an array of 52 cards complete with
* methods to shuffle, sort, and deal one card.
*/

public class Deck
{
	private Card cards[];
	private int  topSlot;
	
	/**
	* Initializes and assigns all of the variables in the class.
	*/
	public Deck()
	{
		cards = new Card[52];
		
		topSlot = 0;
		
		for (int i = 0; i < cards.length; i ++)
		{
			cards[i] = new Card(i);
		}
		
		shuffle();
	}
	
	/**
	* Shuffles all the cards in random order.
	*/
	public void shuffle()
	{
		topSlot = 0;
		
		Card temp;
		
		int randInt;
		int randInt2;
		
		for (int i = 0; i < 1000; i ++)
		{
			randInt  = (int)(Math.random() * 52);
			randInt2 = (int)(Math.random() * 52);
			
			temp = cards[randInt];
			
			cards[randInt]  = cards[randInt2];
			
			cards[randInt2] = temp;
		}
	}
	
	/**
	* Returns a random card to the user.
	* 
	* @return The card
	*/
	public Card dealCard()
	{
		return cards[(topSlot ++) % 52];
	}
	
	/**
	* sorts the array given from least to greatest
	*/
	public void sort()
	{
		topSlot = 0;
		
		// loops through all of the values of the array
		for (int j = 0; j < cards.length; j ++)
		{
			// loops through all of the values of the array
			// to see if it is greater than the current value
			for (int a = 0; a < cards.length - j; a ++)
			{
				// checks whether the first value is greater than
				// the value it is currently looping through in the
				// nested loop
				if (cards[j].getRank() > cards[a + j].getRank())
				{
					// creates a variable to hold the value of the
					// number that will be switched to.
					Card temp = cards[j + a];

					// sets the value of the variable in the nested
					// loop to the value of the variable in the outer loop
					cards[a + j] = cards[j];

					// sets the value of the variable in the outer loop
					// to the value of the temporary variable
					cards[j] = temp;
				}
			}
		}
	}
}