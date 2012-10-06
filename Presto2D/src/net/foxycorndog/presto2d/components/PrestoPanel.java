package net.foxycorndog.presto2d.components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PrestoPanel extends JPanel
{
	private Graphics      bufferGraphics;
	private BufferedImage bufferImage;
	private Graphics      graphics;
	
	private static final long serialVersionUID	= -767092183946687957L;
	
	/**
	* Default blank constructor method.
	*/
	public PrestoPanel()
	{
		resize();
		graphics = getGraphics();
	}
	
	private void resize()
	{
		if (getWidth() > 0 && getHeight() > 0)
		{
			bufferImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.BITMASK);
			bufferGraphics = bufferImage.createGraphics();
		}
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		
		resize();
	}
	
	public void render(Graphics g)
	{
		
	}
	
	public void paint(Graphics g)
	{
		//super.paint(g);
	}
	
	public void render()
	{
		if (graphics == null)
		{
			graphics = getGraphics();
		}

		if (graphics != null)
		{
			render(bufferGraphics);
			graphics.drawImage(bufferImage, 0, 0, null);
		}
	}
}