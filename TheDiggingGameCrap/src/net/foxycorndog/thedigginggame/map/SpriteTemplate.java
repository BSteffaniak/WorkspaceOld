package net.foxycorndog.thedigginggame.map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import net.foxycorndog.thedigginggame.TheDiggingGame;

public class SpriteTemplate
{
	private BufferedImage spriteSheet;
	private int           width, height;
	private int           pixels[];
	
	public SpriteTemplate(String file)
	{
		Image img = null;
		
		try
		{
			if (TheDiggingGame.applet)
			{
				ClassLoader cl = Thread.currentThread().getContextClassLoader();
				URL url = cl.getResource("" + file);
				img = ImageIO.read(url);
			}
			else
			{
				img = ImageIO.read(new File(file));
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		spriteSheet = new BufferedImage(img.getWidth(null), img.getHeight(null), Transparency.BITMASK);
		
		Graphics g = spriteSheet.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		
		pixels = ((DataBufferInt) spriteSheet.getRaster().getDataBuffer()).getData();
		
		this.width  = spriteSheet.getWidth();
		this.height = spriteSheet.getHeight();
	}
	
	public int[] getSubimage(int x, int y, int width, int height)
	{
		int pix[] = new int[width * height];
		
		for (int yy = 0; yy < height; yy ++)
		{
			for (int xx = 0; xx < width; xx ++)
			{
				int tx = x + xx;
				int ty = y + yy;
				
				pix[xx + yy * width] = pixels[tx + ty * this.width];
			}
		}
		
		return pix;
	}
}