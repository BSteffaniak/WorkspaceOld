/**
 * File:          Tree.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 11Feb2013
 * Description:   
 */
public class Tree implements BSTree
{
	private int					numNodes;
	
	private Comparable			buffer[];
	
	private static final int	MAX_SIZE = 100;
	
	public Tree()
	{
		buffer = new Comparable[MAX_SIZE];
	}
	
	public boolean isFull()
	{
		return numNodes >= MAX_SIZE;
	}
	
	public boolean insert(Comparable c)
	{
		// postcondition: return true if d is inserted into heap otherwise return false
		if (isFull())
		{
			return false;
		}
	
		numNodes++; // use next slot in array
		
		// starting at last node, go from node i (last node) to
		// its parent node (pi) and swap with any parent smaller
		int i = numNodes;
		int pi;
		
		while (i > 1)
		{
			pi = i / 2;
			
			if (c.compareTo(buffer[pi]) <= 0) // if data is at right location
			{
				break; // skip out of loop
			}
			
			buffer[i] = buffer[pi]; // move parent down
			i = pi;
		}
		
		buffer[i] = c; // insert new data into correct spot
		
		return true;
	}
	
	/**
	 * Adds a Comparable to the tree using the compare method
	 * to determine where to put it.
	 */
	public void add(Comparable c)
	{
//		TreeNode node = new TreeNode(last, c);
//		
//		if (root == null)
//		{
//			root = node;
//			last = root;
//		}
//		else
//		{
//			if (last.getLeftNode() == null)
//			{
//				last.setLeftNode(node);
//			}
//			else
//			{
//				last.setRightNode(node);
//			}
//		}
	}
	
	/**
	 * Searches the tree using the binary search method for the
	 * specified Comparable.
	 * 
	 * @return Whether the Comparable is in the tree.
	 */
	public boolean search(Comparable c)
	{
		return false;
	}
	
	/**
	 * Returns the maximum length of any leaf in the tree.
	 */
	public int height()
	{
		return 0;
	}
	
	/**
	 * Prints the Tree using Pre-Order traversal.
	 */
	public String toStringPreOrder()
	{
		return null;
	}
	
	/**
	 * Prints the Tree elements using In-Order traversal.
	 */
	public String toStringInOrder()
	{
		return null;
	}
	
	public boolean isEmpty()
	{
		return numNodes == 0;
	}
	
	public boolean contains(Comparable c)
	{
		for (int i = 0; i < buffer.length; i++)
		{
			if (buffer[i] != null && buffer[i].equals(c))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Removes the specified Comparable from the tree.
	 */
	public boolean remove(Comparable c)
	{
		// postcondition: returns true if largest element (root node) is deleted
		// false otherwise
		if (isEmpty() || !contains(c))
		{
			return false;
		}
		
		// get top element
		Comparable d = buffer[1];
		
		// starting from vacant root (ip), go from parent node (ip) to its
		// largest child (i) and, as long as ip has a larger child than last
		// element of heap, move child up
		int ip = 1; // root
		int i  = 2; // start at left child
		
		while (i <= numNodes && i < buffer.length)
		{
			// set i to right child (i+1) if it exists and is larger
			if ((i < numNodes) && (buffer[i].compareTo(buffer[i + 1]) < 0))
			{
				i++;
			}
			
			// if this last node is bigger than largest child then get out of loop
			if (buffer[i].compareTo(buffer[numNodes]) <= 0)
			{
				break;
			}
			
			buffer[ip] = buffer[i]; // move large child up
			
			ip = i; // look at node down one level
			i *= 2; // i now is at left child
		} // while
		
		// move last node to the correct slot in heap
		if (numNodes > 1)
		{
			buffer[ip] = buffer[numNodes];
		}
			
		numNodes--; // one less node
		
		return true; // deleted
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < MAX_SIZE; i++)
		{
			if (buffer[i] != null)
			{
				builder.append(i + ": " + buffer[i] + ", ");
			}
		}
		
		if (builder.length() > 0)
		{
			builder.deleteCharAt(builder.length() - 1);
			builder.deleteCharAt(builder.length() - 1);
		}
		
		builder.insert(0, this.getClass().getSimpleName() + " { ");
		builder.append(" }");
		
		return builder.toString();
	}
}