package net.foxycorndog.idk;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.foxycorndog.presto2d.graphics.PixelPanel;

public class Display extends JPanel
{
	JFrame frame;
	
	PixelPanel image;
//	Graphics   backg;
	
	public Display(int width, int height)
	{
		
		init(width, height);
	}
	
	private void init(int width, int height)
	{
		frame = new JFrame(Idk.GAME_TITLE);
		
		image = new PixelPanel(width, height);
		
//		ba
		
		frame.setVisible(true);
	}
	
	public void paint(Graphics g)
	{
		super.paintComponent(g);
		
//		backg.setColor(Color.BLACK);
		
		backg.fillRect(0, 0, getWidth(), getHeight());
		
		image.getPixelGraphics().fillRect(0, 0, w, h, color)
		
		updateScreen();
		
		backg.drawImage(terrainPanel.getBufferedImage(), 0, 0, pixelPanel.getWidth(), pixelPanel.getHeight(), null);
		backg.drawImage(pixelPanel.getBufferedImage()  , 0, 0, null);
		
		backg.setColor(Color.WHITE);
		
		backg.drawString("Fps: " + fps, 5, 15);
		
		g.drawImage(buffer, 0, 0, null);
		
		dfps ++;
	}
}