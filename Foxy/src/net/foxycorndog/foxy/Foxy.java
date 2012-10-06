package net.foxycorndog.foxy;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.foxycorndog.foxy.compiler.Parser;



public class Foxy
{
	private int                windowXOffset;
	private int                windowYOffset;
	
	private JFrame             frame;
	private JButton            parse;
	private JTextField         fileName;
	private JTextArea          codeArea;
	private JScrollPane        codeAreaPane;
//	private GridBagLayout      layout;
	private GridBagConstraints gbc;
	
	private ActionHandler      actionHandler;
	
	public static final String PROGRAM_NAME = "Foxy";
	
	public Foxy()
	{
		windowXOffset = -15;
		windowYOffset = -37;
		
		actionHandler = new ActionHandler();
		
//		layout = new GridBagLayout();
//		gbc = new GridBagConstraints();
		
		frame = new JFrame(PROGRAM_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setSize(640, 512);
		frame.setLayout(null);
		
		codeArea = new JTextArea();
		
		codeAreaPane = new JScrollPane(codeArea);
		codeAreaPane.setLocation(140, 0);
		codeAreaPane.setSize(getDimension(codeAreaPane));
		frame.add(codeAreaPane);
		
		parse = new JButton("Parse");
		parse.setLocation(0, 50);
		parse.setSize(70, 20);
		parse.addActionListener(actionHandler);
		frame.add(parse);
		
		fileName = new JTextField("FileName");
		fileName.setSize(100, 20);
		fileName.setLocation(5, 5);
		frame.add(fileName);
		
		frame.setVisible(true);
		
		init();
	}
	
	public void init()
	{
		
	}
	
	public static void main(String args[])
	{
		Foxy foxy = new Foxy();
		foxy.init();
	}
	
	public Dimension getDimension(int x, int y)
	{
		return new Dimension(frame.getWidth() - x + windowXOffset, frame.getHeight() - y + windowYOffset);
	}
	
	public Dimension getDimension(Component comp)
	{
		int x = comp.getX();
		int y = comp.getY();
		
		return new Dimension(frame.getWidth() - x + windowXOffset, frame.getHeight() - y + windowYOffset);
	}
	
	class ActionHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == parse)
			{
				Parser.parse(fileName.getText(), codeArea.getText());
			}
		}
	}
}