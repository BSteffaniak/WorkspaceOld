package net.foxycorndog.thedigginggame.applet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import net.foxycorndog.thedigginggame.TheDiggingGame;

/*********************************************************************
 * 
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class GameApplet extends Applet
{
	private static final long serialVersionUID = 1L;
	
	TheDiggingGame game;
	JPanel panel;
	
	private int wid;
	private int hei;
	
	@Override
	public void init()
	{
		int wid = Integer.valueOf(getParameter("width"));
		int hei = Integer.valueOf(getParameter("height"));
		
		wid = wid < 640 ? 640 : wid;
		hei = hei < 512 ? 512 : hei;
		
		setSize(wid, hei);
		
		game = new TheDiggingGame();
		
		setLayout(new BorderLayout());
		
		panel = new JPanel(new BorderLayout());
		
		panel.add(game.getDisplay(), BorderLayout.CENTER);
		
		panel.setBackground(Color.BLACK);
		
		this.setBackground(Color.BLACK);
		
		//game.addComponentListener(game.getComponentHandler());
		//this.addKeyListener(game.keyHandler);
		//this.addMouseListener(game.mouseButtonHandler);
		//this.addMouseMotionListener(game.mouseMotionHandler);
		
		
		//game.setBufferBufferedImage(new BufferedImage(game.getWidth(), game.getHeight(), BufferedImage.BITMASK));//(BufferedImage)createImage(game.getWidth(), game.getHeight()));
//		game.setBuffer(game.getBackBuffer().getGraphics());
		
		
		panel.requestFocus();
		
		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void start()
	{
		game.init(wid, hei, true);
		
		this.addKeyListener(game.getDisplay().getKeyHandler());
	}
	
	@Override
	public void stop()
	{
		//game.setRunnable(false);
	}
	
	@Override
	public void destroy()
	{
		
	}
}