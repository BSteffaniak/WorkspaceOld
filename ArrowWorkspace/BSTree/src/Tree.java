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
	
	private TreeNode			root;
	
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
	
	public void insert(Comparable c)
	{
		
	}
	
	// need a generic implementation to add an object to the tree. 
	private TreeNode addValue(TreeNode r, Comparable cmp)
	{   
		// if the TreeNode is empty then create a new Node at the root, r 
		// else check if the object is smaller than the current object at TreeNode, r 
		//    if smaller then recursively call “addValue” to attach the object to the  
		//   left side of current TreeNode, r 
		//    if bigger then recursively call “addValue” to attach object to right side 
		if (r == null)
		{
			r = new TreeNode(cmp);
		}
		else
		{
			if ( cmp.compareTo( (Comparable) (r.getData()) ) < 0)
			{
				r.setLeftNode(addValue(r.getLeftNode(), cmp));
			}
			else
			{
				r.setRightNode(addValue(r.getRightNode(), cmp));
			}
		}
		
		return r; // reference to this TreeNode (needed to keep track of the real root) 
	}
	
	// the public interface to add an object to the tree 
	public void add(Comparable cmp) 
	{ 
		root = addValue(root, cmp);
	}
	
	public String toString() 
    { 
    	return toString(root); 
    } 
     
    private String toString(TreeNode root) 
    { 
		if (root == null)
		{
			return ""; 
		}
		else 
		{
			return "(" + toString(root.getLeftNode()) +
					" " + root.getData() +
					" " + toString(root.getRightNode()) + ")";
		}
    } 
	
//	/**
//	 * Searches the tree using the binary search method for the
//	 * specified Comparable.
//	 * 
//	 * @return Whether the Comparable is in the tree.
//	 */
//	public boolean search(Comparable c)
//	{
//		return false;
//	}
	
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
	
	// Does the following work on binary trees as well as binary search Trees????
    private boolean search(Comparable c, TreeNode r)
    {
		if (r == null)
		{
			return false;
		}
		else
		{
			if (c.equals((Comparable)r.getData()))
			{
				return true;
			}
			else
			{
				return (search(c, r.getLeftNode()) || search(c. r.getRightNode()));
			}
		}
		
		return false;
    }
	
	public boolean search(Comparable c)
	{
		return search(c, root);
	}
	
	/**
	 * Removes the specified Comparable from the tree.
	 */
	public boolean remove(Comparable c)
	{
		return false;
	}
}