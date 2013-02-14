/**
 * File:          Tree.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 11Feb2013
 * Description:   
 */
public class Tree implements BSTree
{
	private TreeNode	root;
	
	/**
	 * Default constructor in place just in case I need to add anything.
	 */
	public Tree()
	{
		
	}
	
	/**
	 * Not implemented yet...
	
	 */
	public void insert(Comparable c)
	{
		
	}
	
	/**
	 * The generic implementation to add an object to the tree.
	 */
	private TreeNode addData(TreeNode r, Comparable cmp)
	{   
		/* if the TreeNode is empty then create a new Node at the root, r
		 * else check if the object is smaller than the current object at TreeNode, r
		 * if smaller then recursively call “addValue” to attach the object to the
		 * left side of current TreeNode, r
		 * if bigger then recursively call “addValue” to attach object to right side
		 */
		if (r == null)
		{
			r = new TreeNode(cmp);
		}
		else
		{
			if (cmp.compareTo((Comparable)r.getData()) < 0)
			{
				r.setLeftNode(addData(r.getLeftNode(), cmp));
			}
			else
			{
				r.setRightNode(addData(r.getRightNode(), cmp));
			}
		}
		
		// reference to this TreeNode (needed to keep track of the real root)
		return r;
	}
	
	/**
	 * The public interface to add an object to the tree.
	 */
	public void add(Comparable cmp)
	{
		root = addData(root, cmp);
	}
	
	/**
	 * @return A string representation of the tree lexicographically.
	 */
	public String toString() 
    { 
    	return this.getClass().getSimpleName() + " { " + toString(root) + " }"; 
    }
	
	/**
	 * @return A string representation of the node's left, right nodes and data.
	 */
    private String toString(TreeNode node)
    {
		if (node == null)
		{
			return "";
		}
		else
		{
			return "(" + toString(node.getLeftNode()) +
					" " + node.getData() +
					" " + toString(node.getRightNode()) + ")";
		}
    }
	
	/**
	 * @return The maximum length of any leaf in the tree.
	 */
	public int height()
	{
		return 0;
	}
	
	/**
	 * @return The Tree using Pre-Order traversal.
	 */
	public String toStringPreOrder()
	{
		return null;
	}
	
	/**
	 * @return The Tree elements using In-Order traversal.
	 */
	public String toStringInOrder()
	{
		return toString();
	}
	
	/**
	 * @return Whether the tree is empty or not.
	 */
	public boolean isEmpty()
	{
		return root == null;
	}
	
	/**
	 * Searches the tree using the binary search method for the
	 * specified Comparable.
	 * 
	 * @return Whether the Comparable is in the tree.
	 */
    private boolean search(Comparable c, TreeNode r)
    {
		if (r == null)
		{
			return false;
		}
		else
		{
			if (c.equals(r.getData()))
			{
				return true;
			}
			else
			{
				return (search(c, r.getLeftNode()) || search(c, r.getRightNode()));
			}
		}
    }

	/**
	 * Searches the tree using the binary search method for the
	 * specified Comparable's parent.
	 * 
	 * @return The parent of the Comparable's node.
	 */
    private TreeNode searchParent(Comparable c)
    {
		return searchParent(c, root);
    }

	/**
	 * Searches the tree using the binary search method for the
	 * specified Comparable's parent.
	 * 
	 * @return WThe parent of the Comparable's node.
	 */
    private TreeNode searchParent(Comparable c, TreeNode r)
    {
		if (r == null)
		{
			return null;
		}
		else
		{
			TreeNode left  = r.getLeftNode();
			TreeNode right = r.getRightNode();
			
			if ((left != null && left.getData().compareTo(c) == 0))
			{
				return r;
			}
			else if (right != null && right.getData().compareTo(c) == 0)
			{
				return r;
			}
			else
			{
				TreeNode result = null;
				
				if (left != null)
				{
					result = searchParent(c, left);
					
					if (result != null)
					{
						return result;
					}
				}
				if (right != null)
				{
					result = searchParent(c, right);
					
					if (result != null)
					{
						return result;
					}
				}
				
				return null;
			}
		}
    }
	
//	/**
//	 * Searches the tree using the binary search method for the
//	 * specified Comparable's parent.
//	 * 
//	 * @return The parent of the Comparable's node.
//	 */
//    private TreeNode searchParent(Comparable c, TreeNode parent)
//    {
//		if (parent == null)
//		{
//			return null;
//		}
//		else
//		{
//			TreeNode left  = parent.getLeftNode();
//			TreeNode right = parent.getRightNode();
//			
//			if (left != null && c.compareTo(left.getData()) == 0)
//			{
//				return parent;
//			}
//			else if (right != null && c.compareTo(right.getData()) == 0)
//			{
//				return parent;
//			}
//			if (c.compareTo(parent.getData()) == 0)
//			{
//				return true;
//			}
//			else
//			{
//				return (searchParent(c, parent.getLeftNode()) || searchParent(c, parent.getRightNode()));
//			}
//		}
//    }
	
	/**
	 * Searches the tree using the binary search method for the
	 * specified Comparable.
	 * 
	 * @return Whether the Comparable is in the tree.
	 */
	public boolean search(Comparable c)
	{
		return search(c, root);
	}
	
	public boolean remove(Comparable c)
	{
		return remove(c, root);
	}
	
	/**
	 * Removes the specified Comparable from the tree.
	 */
	public boolean remove(Comparable c, TreeNode delNode)
	{
		if (delNode == null)
		{
			return false;
		}
		else
		{
			delNode = null;
		
			TreeNode parent = searchParent(c, delNode);
			
			if (c.equals(delNode.getData()))
			{
				return true;
			}
			else
			{
				return (remove(c, delNode.getLeftNode()) || remove(c, delNode.getRightNode()));
			}
		}
	}
}