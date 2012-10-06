package net.foxycorndog.poker;
public class HandInfo
{
	private int   highCard;
	private int   rank;
	private int[] values;
	private int[] suits;
	
	/**
	* The Constructor method that assigns all of the variables
	* to the fields.
	* 
	* @param rank The rank of the hand.
	* @param highCard The highest value card in the hand.
	* @param cards The Cards in the form of a Card array.
	*/
	public HandInfo(int rank, int highCard, Card[] cards)
	{
		this.rank     = rank;
		this.highCard = highCard;
		
		values = new int[cards.length];
		
		for (int i = 0; i < cards.length; i ++)
		{
			values[i] = cards[i].getValue();
		}
		
		suits = new int[cards.length];
		
		for (int i = 0; i < cards.length; i ++)
		{
			suits[i] = cards[i].getSuit();
		}
	}

	/**
	* The method to retrieve the high card in the hand.
	* 
	* @return The highCard of the hand.
	*/
	public int getHighCard()
	{
		return highCard;
	}

	/**
	* Method to set the highCard to a certain value.
	* 
	* @param highCard The highCard to set in the hand.
	*/
	public void setHighCard(int highCard)
	{
		this.highCard = highCard;
	}

	/**
	* Method to retrieve the rank of the hand.
	* 
	* @return The rank of the hand.
	*/
	public int getRank()
	{
		return rank;
	}

	/**
	* Method to set the rank of the hand to a certain value.
	* 
	* @param rank The rank to set the hand as.
	*/
	public void setRank(int rank)
	{
		this.rank = rank;
	}
	
	/**
	* Method to retrieve the values of the cards.
	* 
	* @return The values of the Cards in the form of an integer array.
	*/
	public int[] getValues()
	{
		return values;
	}
	
	/**
	* Method to retrieve the suits of the cards.
	* 
	* @return The suits of the Cards in the form of an integer array.
	*/
	public int[] getSuits()
	{
		return suits;
	}
	
	/**
	* Method that compares two hands to see which one is superior.
	* 
	* @param hand1 The first HandInfo.
	* @param hand2 The second HandInfo.
	* @return Which HandInfo is superior.
	*/
	public static HandInfo compare(HandInfo hand1, HandInfo hand2)
	{
		if (hand1 == null)
		{
			if (hand2 == null)
			{
				return null;
			}
			else
			{
				return hand2;
			}
		}
		else
		{
			if (hand2 == null)
			{
				return hand1;
			}
		}
		
		if (hand1.getRank() > hand2.getRank())
		{
			return hand1;
		}
		else if (hand1.getRank() == hand2.getRank())
		{
				int cardValues[][] = new int[2][];
				
				cardValues[0] = hand1.getValues();
				cardValues[1] = hand2.getValues();
				
				int cardSuits[][] = new int[2][];
				
				cardSuits[0] = hand1.getSuits();
				cardSuits[1] = hand2.getSuits();
				
				for (int plr = 0; plr < cardValues.length; plr ++)
				{
					orderHandsByPairs(cardValues[plr], cardSuits[plr]);
				}

				for (int i = 0; i < cardValues[0].length; i ++)
				{
					if (cardValues[0][i] > cardValues[1][i])
					{
						return hand1;
					}
					else if (cardValues[0][i] < cardValues[1][i])
					{
						return hand2;
					}
				}
				
				for (int i = 0; i < cardSuits[0].length; i ++)
				{
					if (cardSuits[0][i] > cardSuits[1][i])
					{
						return hand1;
					}
					else if (cardSuits[0][i] < cardSuits[1][i])
					{
						return hand2;
					}
				}
				
				return null;
			}
		else
		{
			return hand2;
		}
	}
	
	/**
	* Method that orders the integer array passed by the following
	* hierarchy:
	* 
	* 1. Largest pair.
	* 2. Highest pair.
	* 3. High card.
	* 
	* @param hand The hand that is going to be ordered as an integer
	* 		array.
	* @return The ordered hand as an integer array.
	*/
	public static int[] orderHandByPairs(int hand[])
	{
		int ret[] = new int[hand.length];
		
		for (int i = 0; i < ret.length; i ++)
		{
			ret[i] = hand[i];
		}
		
		// loops through all of the values of the array
		for (int j = 0; j < ret.length; j ++)
		{
			// loops through all of the values of the array
			// to see if it is greater than the current value
			for (int a = 0; a < ret.length - j; a ++)
			{
				// checks whether the first value is greater than
				// the value it is currently looping through in the
				// nested loop
				if (ret[j] < ret[a + j])
				{
					// creates a variable to hold the value of the
					// number that will be switched to.
					int temp = ret[j + a];

					// sets the value of the variable in the nested
					// loop to the value of the variable in the outer loop
					ret[a + j] = ret[j];

					// sets the value of the variable in the outer loop
					// to the value of the temporary variable
					ret[j] = temp;
				}
			}
		}
		
		int length[] = new int[ret.length];
		
		for (int i = 0; i < ret.length; i ++)
		{
			for (int j = i; j < ret.length - 1; j ++)
			{
				if (ret[j] == ret[j + 1])
				{
					length[i] += 1;
				}
				else
				{
					break;
				}
			}
		}

		for (int m = 0; m < length.length; m ++)
		{
			for (int i = 0; i < length.length - 1; i ++)
			{
				for (int j = 0; j < length[i] + 1; j ++)
				{
					int index1 = i;
					int index2 = i + length[i] + 1;
					
					if (index2 < length.length && length[index1] < length[index2])
					{
						for (int k = 0; k < length[index1] + length[index2] + 1; k ++)
						{
							int temp = ret[index1 + k];
							
							ret[index1 + k] = ret[index1 + k + 1];
							
							ret[index1 + k + 1] = temp;
						}
						
						int temp = length[index1];
						
						length[index1] = length[index2];
						
						length[index2] = temp;
					}
				}
			}
		}
		
		return ret;
	}
	
	/**
	* Method that orders the integer arrays passed by the following
	* hierarchy:
	* 
	* 1. Largest pair.
	* 2. Highest pair.
	* 3. High card.
	* 
	* @param hand1 The first hand that is going to be ordered as an
	* 		integer array.
	* @param hand2 The second hand that is going to be ordered as an
	* 		integer array.
	*/
	public static void orderHandsByPairs(int hand1[], int hand2[])
	{
		// loops through all of the values of the array
		for (int j = 0; j < hand1.length; j ++)
		{
			// loops through all of the values of the array
			// to see if it is greater than the current value
			for (int a = 0; a < hand1.length - j; a ++)
			{
				// checks whether the first value is greater than
				// the value it is currently looping through in the
				// nested loop
				if (hand1[j] < hand1[a + j])
				{
					// creates a variable to hold the value of the
					// number that will be switched to.
					int temp = hand1[j + a];

					// sets the value of the variable in the nested
					// loop to the value of the variable in the outer loop
					hand1[a + j] = hand1[j];

					// sets the value of the variable in the outer loop
					// to the value of the temporary variable
					hand1[j] = temp;
					
					
					// creates a variable to hold the value of the
					// number that will be switched to.
					temp = hand2[j + a];

					// sets the value of the variable in the nested
					// loop to the value of the variable in the outer loop
					hand2[a + j] = hand2[j];

					// sets the value of the variable in the outer loop
					// to the value of the temporary variable
					hand2[j] = temp;
				}
			}
		}
		
		int length[] = new int[hand1.length];
		
		for (int i = 0; i < hand1.length; i ++)
		{
			for (int j = i; j < hand1.length - 1; j ++)
			{
				if (hand1[j] == hand1[j + 1])
				{
					length[i] += 1;
				}
				else
				{
					break;
				}
			}
		}

		for (int m = 0; m < length.length; m ++)
		{
			for (int i = 0; i < length.length - 1; i ++)
			{
				for (int j = 0; j < length[i] + 1; j ++)
				{
					int index1 = i;
					int index2 = i + length[i] + 1;
					
					if (index2 < length.length && length[index1] < length[index2])
					{
						for (int k = 0; k < length[index1] + length[index2] + 1; k ++)
						{
							int temp = hand1[index1 + k];
							
							hand1[index1 + k] = hand1[index1 + k + 1];
							
							hand1[index1 + k + 1] = temp;
							
							
							temp = hand2[index1 + k];
							
							hand2[index1 + k] = hand2[index1 + k + 1];
							
							hand2[index1 + k + 1] = temp;
						}
						
						int temp = length[index1];
						
						length[index1] = length[index2];
						
						length[index2] = temp;
					}
				}
			}
		}
	}
}