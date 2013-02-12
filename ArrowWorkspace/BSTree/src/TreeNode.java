/**
 * File:          TreeNode.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 11Feb2013
 * Description:   
 */
public class TreeNode
{
	private Comparable	data;
	
	private TreeNode	leftNode, rightNode, parent;
	
	public TreeNode(TreeNode parent, Comparable data)
	{
		this.data   = data;
		this.parent = parent;
	}
	
	public TreeNode getParent()
	{
		return parent;
	}
	
	public TreeNode getLeftNode()
	{
		return leftNode;
	}
	
	public void setLeftNode(TreeNode leftNode)
	{
		this.leftNode = leftNode;
	}
	
	public TreeNode getRightNode()
	{
		return rightNode;
	}
	
	public void setRightNode(TreeNode rightNode)
	{
		this.rightNode = rightNode;
	}
}