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
	
	private TreeNode	leftNode, rightNode;
	
	public TreeNode(Comparable data)
	{
		this.data   = data;
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
	
	public Comparable getData()
	{
		return data;
	}
}