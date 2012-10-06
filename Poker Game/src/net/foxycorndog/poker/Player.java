package net.foxycorndog.poker;

import java.io.Serializable;

/**
* File:  			Player.java HONORS
* Author:			Braden Steffaniak
* Programming:  	1st Hour
* Last Modified: 	30Mar2012
* 
* Description: Stores the cards for each player in an array
* and creates an easy way to access cards, along with a collection
* methods used for determining the type of hand the player has.
*/

public class Player implements Serializable
{
	private boolean switched;
	private boolean readyToDeal;
	private boolean readyForResults;
	
	private int     numCardsInHand;
	private int     wins;
	
	private Card    hand[];
	
	private String  name;
	
	/**
	* A String array used in conjunction with the rank of each hand
	* that is retrieved from the HandInfo object. Ranges from 0-10.
	* 
	* 0  - Nothing.
	* 1  - A High card.
	* 2  - A pair.
	* 3  - A two pair.
	* 4  - A three of a kind.
	* 5  - A straight.
	* 6  - A flush.
	* 7  - A full house.
	* 8  - A four of a kind.
	* 9  - A straight flush.
	* 10 - A royal flush.
	*/
	private static final String hands[] = new String[]
	{
		"nothing!!!",
		"the high card!",
		"a pair!",
		"a two pair!",
		"a three of a kind!",
		"a straight!",
		"a flush!",
		"a full house!",
		"a four of a kind!",
		"a straight flush!",
		"a royal fluuuush!!!!!!!",
	};

	/**
	* The default constructor that sets the name to Unnamed
	* and assigns numCardsInHand.
	*/
	public Player()
	{
		name           = "Unnamed";
		numCardsInHand = 0;
		
		hand           = new Card[5];
	}

	/**
	* Returns the card in hand, then decreases the value of
	* numCardsInHand by one.
	*
	* @return The card in hand
	*/
	public Card getCardFromHand()
	{
		if (numCardsInHand < 1)
		{
			numCardsInHand = 1;
		}
		
		return hand[-- numCardsInHand];
	}
	
	/**
	* Returns the specified index of the hand if in range
	* of the array.
	* 
	* @param index The card index.
	* @return The card
	*/
	public Card getCardFromHand(int index)
	{
		if (index >= hand.length || index < 0)
		{
			return null;
		}
		
		return hand[index];
	}
	
	/**
	* Sets the specified index to the specified card.
	* 
	* @param c The card to set to.
	* @param index The index in the hand.
	* @return Whether it set it successfully or not.
	*/
	public boolean setCardInHand(Card c, int index)
	{
		if (index < 0 || index >= hand.length)
		{
			return false;
		}
		
		hand[index] = c;
		
		return true;
	}

	/**
	* Sets the current place in the hand to the card.
	*
	* @param c The card to set it as.
	*/
	public void setCardInHand(Card c)
	{
		if (numCardsInHand > hand.length - 1)
		{
			numCardsInHand = hand.length - 1;
		}

		hand[numCardsInHand ++] = c;
	}

	/**
	* Set the name to the specified String.
	*
	* @param n The String to set the name as.
	*/
	public void setName(String n)
	{
		this.name = n;
	}

	/**
	* Returns the name of the player.
	*
	* @return The String name.
	*/
	public String getName()
	{
		return name;
	}

	/**
	* Sorts the hand by the value of the cards. Biggest first.
	*/
	public void sortHandByValue()
	{
		// loops through all of the values of the array
		for (int j = 0; j < hand.length; j ++)
		{
			// loops through all of the values of the array
			// to see if it is greater than the current value
			for (int a = 0; a < hand.length - j; a ++)
			{
				// checks whether the first value is greater than
				// the value it is currently looping through in the
				// nested loop
				if (hand[j].getValue() > hand[a + j].getValue())
				{
					// creates a variable to hold the value of the
					// number that will be switched to.
					Card temp   = hand[j + a];

					// sets the value of the variable in the nested
					// loop to the value of the variable in the outer loop
					hand[a + j] = hand[j];

					// sets the value of the variable in the outer loop
					// to the value of the temporary variable
					hand[j]     = temp;
				}
			}
		}
	}

	/**
	* Sorts the hand by the suit of the cards. Biggest first.
	*/
	public void sortHandBySuit()
	{
		// loops through all of the values of the array
		for (int j = 0; j < hand.length; j ++)
		{
			// loops through all of the values of the array
			// to see if it is greater than the current value
			for (int a = 0; a < hand.length - j; a ++)
			{
				// checks whether the first value is greater than
				// the value it is currently looping through in the
				// nested loop
				if (hand[j].getSuit() > hand[a + j].getSuit())
				{
					// creates a variable to hold the value of the
					// number that will be switched to.
					Card temp   = hand[j + a];

					// sets the value of the variable in the nested
					// loop to the value of the variable in the outer loop
					hand[a + j] = hand[j];

					// sets the value of the variable in the outer loop
					// to the value of the temporary variable
					hand[j]     = temp;
				}
			}
		}
	}

	/**
	* Sorts the hand by the rank of the cards. Biggest first.
	*/
	public void sortHandByRank()
	{
		// loops through all of the values of the array
		for (int j = 0; j < hand.length; j ++)
		{
			// loops through all of the values of the array
			// to see if it is greater than the current value
			for (int a = 0; a < hand.length - j; a ++)
			{
				// checks whether the first value is greater than
				// the value it is currently looping through in the
				// nested loop
				if (hand[j].getRank() > hand[a + j].getRank())
				{
					// creates a variable to hold the value of the
					// number that will be switched to.
					Card temp   = hand[j + a];

					// sets the value of the variable in the nested
					// loop to the value of the variable in the outer loop
					hand[a + j] = hand[j];

					// sets the value of the variable in the outer loop
					// to the value of the temporary variable
					hand[j]     = temp;
				}
			}
		}
	}
	
	/**
	* 
	* @return What to say for the player depending on their hand.
	*/
	public String getHandText()
	{
		int index = 0;
		
		if (getHand() != null)
		{
			index = getHand().getRank();
		}
		
		return /*name + " has " + */hands[index];
	}
	
	/**
	* Returns the type of hand the player has.
	* 
	* @return The HandInfo object.
	*/
	public HandInfo getHand()
	{
		if (royalFlush() != null)
		{
			return royalFlush();
		}
		else if (straightFlush() != null)
		{
			return straightFlush();
		}
		else if (fourOfKind() != null)
		{
			return fourOfKind();
		}
		else if (fullHouse() != null)
		{
			return fullHouse();
		}
		else if (flush() != null)
		{
			return flush();
		}
		else if (straight() != null)
		{
			return straight();
		}
		else if (threeOfKind() != null)
		{
			return threeOfKind();
		}
		else if (twoPair() != null)
		{
			return twoPair();
		}
		else if (pair() != null)
		{
			return pair();
		}
		else if (highCard() != null)
		{
			return highCard();
		}
		
		return null;
	}
	
	/**
	* Returns new HandInfo if has a royal flush. Else, it
	* returns null.
	* 
	* @return The royal flush HandInfo.
	*/
	public HandInfo royalFlush()
	{
		sortHandByRank();
		
		if ((hand[4].getValue() == 14) &&
				(hand[4].getRank()  == hand[3].getRank() + 1) &&
				(hand[3].getRank()  == hand[2].getRank() + 1) &&
				(hand[2].getRank()  == hand[1].getRank() + 1) &&
				(hand[1].getRank()  == hand[0].getRank() + 1))
		{
			
			return new HandInfo(10, hand[4].getValue(), hand);
		}
		
		return null;
	}

	/**
	* Returns new HandInfo if has a straight flush. Else, it
	* returns null.
	* 
	* @return The straight flush HandInfo.
	*/
	public HandInfo straightFlush()
	{
		sortHandByRank();
		
		if ((hand[0].getValue() >= 2 && hand[4].getValue() <= 14) &&
				 hand[4].getRank()  == hand[3].getRank() + 1 &&
				 hand[3].getRank()  == hand[2].getRank() + 1 &&
				 hand[2].getRank()  == hand[1].getRank() + 1 &&
				 hand[1].getRank()  == hand[0].getRank() + 1)
		{
			return new HandInfo(9, hand[4].getValue(), hand);
		}
		
		return null;
	}

	/**
	* Returns new HandInfo if has a four of a kind. Else, it
	* returns null.
	* 
	* @return The four of a kind HandInfo.
	*/
	public HandInfo fourOfKind()
	{
		sortHandByValue();
		
		if ((hand[1].getValue() == hand[2].getValue()  &&
				 hand[2].getValue() == hand[3].getValue()  &&
				 hand[3].getValue() == hand[4].getValue()) ||
				(hand[0].getValue() == hand[1].getValue()  &&
				 hand[1].getValue() == hand[2].getValue()  &&
				 hand[2].getValue() == hand[3].getValue()))
		{
			return new HandInfo(8, hand[4].getValue(), hand);
		}
		
		return null;
	}

	/**
	* Returns new HandInfo if has a full house. Else, it
	* returns null.
	* 
	* @return The full house HandInfo.
	*/
	public HandInfo fullHouse()
	{
		sortHandByValue();
		
		if (((hand[0].getValue() == hand[1].getValue()   &&
				  hand[1].getValue() == hand[2].getValue())  &&
				 (hand[3].getValue() == hand[4].getValue())) ||
				((hand[2].getValue() == hand[3].getValue()   &&
				  hand[3].getValue() == hand[4].getValue())  &&
				 (hand[0].getValue() == hand[1].getValue())))
		{
			return new HandInfo(7, hand[4].getValue(), hand);
		}
		
		return null;
	}

	/**
	* Returns new HandInfo if has a flush. Else, it
	* returns null.
	* 
	* @return The flush HandInfo.
	*/
	public HandInfo flush()
	{
		if (hand[0].getSuit() == hand[1].getSuit() &&
				hand[1].getSuit() == hand[2].getSuit() &&
				hand[2].getSuit() == hand[3].getSuit() &&
				hand[3].getSuit() == hand[4].getSuit())
		{
			return new HandInfo(6, hand[4].getValue(), hand);
		}
		
		return null;
	}

	/**
	* Returns new HandInfo if has a straight. Else, it
	* returns null.
	* 
	* @return The straight HandInfo.
	*/
	public HandInfo straight()
	{
		sortHandByValue();
		
		if (hand[4].getValue() == hand[3].getValue() + 1 &&
				hand[3].getValue() == hand[2].getValue() + 1 &&
				hand[2].getValue() == hand[1].getValue() + 1 &&
				hand[1].getValue() == hand[0].getValue() + 1)
		{
			return new HandInfo(5, hand[4].getValue(), hand);
		}
		
		return null;
	}

	/**
	* Returns new HandInfo if has a three of a kind. Else, it
	* returns null.
	* 
	* @return The three of a kind HandInfo.
	*/
	public HandInfo threeOfKind()
	{
		sortHandByValue();
		
		if ((hand[0].getValue() == hand[1].getValue()  &&
				 hand[1].getValue() == hand[2].getValue()) ||
				(hand[1].getValue() == hand[2].getValue()  &&
				 hand[2].getValue() == hand[3].getValue()) ||
				(hand[2].getValue() == hand[3].getValue()  &&
				 hand[3].getValue() == hand[4].getValue()))
		{
			return new HandInfo(4, hand[4].getValue(), hand);
		}
		
		return null;
	}

	/**
	* Returns new HandInfo if has a two pair. Else, it
	* returns null.
	* 
	* @return The two pair HandInfo.
	*/
	public HandInfo twoPair()
	{
		sortHandByValue();
		
		if (((hand[0].getValue() == hand[1].getValue()   &&
			  hand[2].getValue() == hand[3].getValue())  ||
				 
			 (hand[0].getValue() == hand[1].getValue()   &&
			  hand[3].getValue() == hand[4].getValue())) ||
				 
			((hand[1].getValue() == hand[2].getValue()   &&
			  hand[3].getValue() == hand[4].getValue())))
		{
			return new HandInfo(3, hand[4].getValue(), hand);
		}
		
		return null;
	}

	/**
	* Returns new HandInfo if has a pair. Else, it
	* returns null.
	* 
	* @return The pair HandInfo.
	*/
	public HandInfo pair()
	{
		sortHandByValue();
		
		if ((hand[0].getValue() == hand[1].getValue()) ||
				(hand[1].getValue() == hand[2].getValue()) ||
				(hand[2].getValue() == hand[3].getValue()) ||
				(hand[3].getValue() == hand[4].getValue()))
		{
			return new HandInfo(2, hand[4].getValue(), hand);
		}
		
		return null;
	}

	/**
	* Returns new HandInfo if has a high card. Else, it
	* returns null.
	* 
	* @return The high card HandInfo.
	*/
	public HandInfo highCard()
	{
		sortHandByValue();
		
		return new HandInfo(1, hand[4].getValue(), hand);
	}
	
	/**
	* Set whether the player has switched cards or not.
	* 
	* @param s Whether he switched or not.
	*/
	public void setSwitched(boolean s)
	{
		this.switched = s;
	}
	
	/**
	* Returns whether the player has switched or not.
	* 
	* @return Whether the player has switched or not.
	*/
	public boolean getSwitched()
	{
		return switched;
	}
	
	/**
	* Method that retrieves whether the Player is ready for the
	* Cards to be dealt.
	* 
	* @return Whether the Player is ready for the Cards to be dealt.
	*/
	public boolean getReadyToDeal()
	{
		return readyToDeal;
	}
	
	/**
	* Method that sets whether the Player is ready for the Cards to
	* be dealt or not.
	* 
	* @param ready Whether the Player is ready for the Cards to be
	* 		dealt or not.
	*/
	public void setReadyToDeal(boolean ready)
	{
		this.readyToDeal = ready;
	}
	
	/**
	* Method that retrieves whether the Player is ready for the
	* results of their hands.
	* 
	* @return Whether the Player is ready for the results of their hands.
	*/
	public boolean getReadyForResults()
	{
		return readyForResults;
	}
	
	/**
	* Method that sets whether the Player is ready for their hand
	* to be compared.
	* 
	* @param ready Whether the Player is ready for their hand to be
	* 		compared.
	*/
	public void setReadyForResults(boolean ready)
	{
		this.readyForResults = ready;
	}
	
	/**
	* Method that retrieves the amount of wins that the Player has.
	* 
	* @return The amount of wins the Player has against the opponent.
	*/
	public int getWins()
	{
		return wins;
	}
	
	/**
	* Method that is used to add a win for the Player to signify that
	* the Player's hand is better than the opponents.
	*/
	public void addWin()
	{
		wins ++;
	}
}