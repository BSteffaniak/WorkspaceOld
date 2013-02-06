package net.test;

import javax.swing.JFrame;

public class Asdf
{
	public static void main(String args[])
	{
		new Asdf();
	}
	
	public Asdf()
	{
		JFrame frame = new JFrame("This is the titleeee");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setLocation(10, 10);
		frame.setTitle("new Title");
		
		frame.setVisible(true);
	}
}