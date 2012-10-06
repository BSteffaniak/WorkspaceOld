

package net.foxycorndog.presto2d.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/*********************************************************************
 * 
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class CustomFont
{
	private BufferedImage font = null;//, text = null;
	private int letterWidth, letterHeight;
	private String prototype;
	private int lineLength;
	private int[] pixels = null;
//	private int[] shadowPixels = null;
	private boolean shadows;
//	private Color shadowColor = Color.LIGHT_GRAY;
	public static final int NONE = -1, CENTER = 0, LEFT = 1, RIGHT = 2, TOP = 3, BOTTOM = 4;
	
	public CustomFont(String location, String prototype, int letterWidth, int letterHeight)
	{
		Image b = null;
		
		try
		{
			b = ImageIO.read(new File(location));
		}
		catch (IOException ex)
		{
			Logger.getLogger(CustomFont.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		int width = b.getWidth(null);
		int height = b.getHeight(null);
		
		pixels = new int[width * height];
//		shadowPixels = new int[(width * height) + (width * 1) + (height * 1)];
		
		font = new BufferedImage(width, height, Transparency.BITMASK);
		
		Graphics g = font.getGraphics();
		g.drawImage(b, 0, 0, null);
		g.dispose();
		
		pixels = font.getRGB(0, 0, width, height, pixels, 0, width);
		
		for (int i = 0; i < pixels.length; i++)
		{
			int col = pixels[i];
			
			//System.out.println("pix before: " + col);
			
			int ca = (col >> 24) & 0xff;
			int cr = (col >> 16) & 0xff;
			int cg = (col >>  8) & 0xff;
			int cb = (col      ) & 0xff;
			
			//  238 - 255 = 17
			
			cr = Math.abs(cr - 0xff);
			cg = Math.abs(cg - 0xff);
			cb = Math.abs(cb - 0xff);
			
			pixels[i] = ARGBToInt(ca, cr, cg, cb);
			
		//	1048575
			
//			int right = -986896;
		}
		
		getShadows();
		
		this.letterWidth = letterWidth;
		this.letterHeight = letterHeight;
		this.prototype = prototype;
		
		lineLength = width / (letterWidth + 1);
	}
	
	public CustomFont(BufferedImage b, String prototype, int letterWidth, int letterHeight)
	{
		int width = b.getWidth();
		int height = b.getHeight();
		
		pixels = new int[width * height];
//		shadowPixels = new int[(width * height) + (width * 1) + (height * 1)];
		
		font = b;
		
		pixels = font.getRGB(0, 0, width, height, pixels, 0, width);
		
		this.letterWidth = letterWidth;
		this.letterHeight = letterHeight;
		this.prototype = prototype;
		
		lineLength = width / (letterWidth + 1);
		
	//	System.out.println(lineLength);
	}
	
	
	public CustomFont(Image b, String prototype, int letterWidth, int letterHeight)
	{
		
		int width = b.getWidth(null);
		int height = b.getHeight(null);
		
		pixels = new int[width * height];
//		shadowPixels = new int[(width * height) + (width * 1) + (height * 1)];
		
		font = new BufferedImage(width, height, Transparency.BITMASK);
		
		Graphics g = font.getGraphics();
		g.drawImage(b, 0, 0, null);
		g.dispose();
		
		pixels = font.getRGB(0, 0, width, height, pixels, 0, width);
	}
	
	
	
	
	public String ARGBToString(int a, int r, int g, int b)
	{
		String ca = Integer.toHexString(a);
		String cr = Integer.toHexString(r);
		String cg = Integer.toHexString(g);
		String cb = Integer.toHexString(b);
		
		ca = ca.length() == 1 ? ca + "0" : ca;
		cr = cr.length() == 1 ? cr + "0" : cr;
		cg = cg.length() == 1 ? cg + "0" : cg;
		cb = cb.length() == 1 ? cb + "0" : cb;
		
		return ca + cr + cg + cb;
	}
	
	public int ARGBToInt(int a, int r, int g, int b)
	{	
		return new BigInteger(ARGBToString(a, r, g, b), 16).intValue();
	}
	
	private void getShadows()
	{
		for (int i = 0; i < pixels.length; i ++)
		{
			if (pixels[i] != -16711936)
			{
				
			}
		}
	}
	
	public int getLetterWidth()
	{
		return letterWidth;
	}
	
	public int getLetterHeight()
	{
		return letterHeight;
	}
	
	public void setShadows(boolean shadows)
	{
		this.shadows = shadows;
	}
	
	public void setShadowColor(Color color)
	{
//		shadowColor = color;
	}
	
	public int getRGB(int rgb)
	{
		if (rgb <= 16777215 && rgb >= -16777216)
		{
			return rgb;
		}
		
		return 0;
	}
	
	private void drawCharacter(int xoff, int yoff, int xo, int yo, int w, int h, int width, int[] px, int col)
	{
		for (int y = 0; y < h; y ++)
		{
			int yPix = y + yoff;
		//	if (yPix < 0 || yPix >= letterHeight - yoff) continue;
			
			for (int x = 0; x < w; x ++)
			{
				int xPix = x + xoff;
		//		if (xPix < 0 || xPix >= letterWidth - xoff) continue;
				
				//System.out.println((xPix + yPix * font.getWidth()) + ", " + font.getWidth());
				int src = pixels[(x + xo) + ((y + yo) * font.getWidth())];
				if (src != -16711936)
				{
					if (shadows)
					{
						try
						{
						//	System.out.println("0x" + Integer.toHexString((((col >> 24) & 0xff) - 125)) + Integer.toHexString(col).substring(2));
							/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////0xff8aae11
					//		px[(xPix + (yPix * width)) + 1] = darkenColor(ARGBToInt(((((col >> 24) & 0xff) - 175)), (col >> 16) & 0xff, (col >> 8) & 0xff, (col) & 0xff), src);
						}
						catch (IndexOutOfBoundsException ex)
						{
							
						}
					}
					
					if (xPix + (yPix * width) >= px.length) continue;
					px[xPix + (yPix * width)] = PrestoColor.darkenColor(col, src);
				}
			}
		}
	}
	
	private void drawCharacter(int xoff, int yoff, int xo, int yo, int w, int h, int widthLimit, int heightLimit, int width, int[] px, int col)
	{
		for (int y = 0; y < h; y ++)
		{
			int yPix = y + yoff;
			
			for (int x = 0; x < w; x ++)
			{
				int xPix = x + xoff;
				
				int src = pixels[(x + xo) + ((y + yo) * font.getWidth())];
				if (src != -16711936)
				{
					if (shadows)
					{
						try
						{
							
						}
						catch (IndexOutOfBoundsException ex)
						{
							
						}
					}
					
					if (xPix + (yPix * width) >= px.length || xPix >= widthLimit) continue;
					
					px[xPix + (yPix * width)] = PrestoColor.darkenColor(col, src);
				}
			}
		}
	}
	
	private void drawCharacter(int xoff, int yoff, int scale, int xo, int yo, int w, int h, int width, int[] px, int col)
	{
		for (int y = 0; y < h * scale; y ++)
		{
			int yPix = y + yoff;
			
			for (int x = 0; x < w * scale; x ++)
			{
				int xPix = x + xoff;
				
				int src = pixels[((x / scale) + xo) + (((y / scale) + yo) * font.getWidth())];
				if (src != -16711936)
				{
					try
					{
						for (int i = 1; i <= scale; i ++)
						{
							
						}
						
						px[xPix + (yPix * width)] = col;
					}
					catch (IndexOutOfBoundsException ex)
					{
						
					}
				}
			}
		}
	}
	
	private void drawCharacter(int xoff, int yoff, int scale, int xo, int yo, int w, int h, int widthLimit, int heightLimit, int width, int[] px, int col)
	{
		for (int y = 0; y < h * scale; y ++)
		{
			int yPix = y + yoff;
			
			for (int x = 0; x < w * scale; x ++)
			{
				int xPix = x + xoff;
				
				int src = pixels[((x / scale) + xo) + (((y / scale) + yo) * font.getWidth())];
				if (src != -16711936)
				{
					try
					{
						for (int i = 1; i <= scale; i ++)
						{
							
						}
						if (xPix + (yPix * width) >= px.length || xPix - letterWidth - scale >= widthLimit) continue;
						
						px[xPix + (yPix * width)] = col;
					}
					catch (IndexOutOfBoundsException ex)
					{
						
					}
				}
			}
		}
	}
	
	public void draw(String s, int x, int y, int[] pxs, int width, int col, int halignment, int valignment)
	{
		int length = s.length();
		
		if (halignment != NONE)
		{
			if (halignment == CENTER)
			{
				x = x + (width / 2 - ((length / 2) * (letterWidth + 1)) - (length % 2 != 0 ? letterWidth / 2 : 0));
			}
			else if (halignment == RIGHT)
			{
				x = width - (length * (letterWidth + 1));
			}
		}
		if (valignment != NONE)
		{
			
		}
		
		for (int i = 0; i < length; i ++)
		{	
			int ch = prototype.indexOf(s.charAt(i));
			if (ch < 0) continue;
			
			int xx = ch % lineLength;
			int yy = ch / lineLength;
			
			drawCharacter((i * (letterWidth + 1)) + x, y, xx * (letterWidth + 1), yy * (letterHeight + 1), letterWidth, letterHeight, width, pxs, col);
		}
	}
	
	public void draw(String s, int x, int y, int widthLimit, int heightLimit, int[] pxs, int width, int col, int halignment, int valignment)
	{
		int length = s.length();
		
		if (halignment != NONE)
		{
			if (halignment == CENTER)
			{
				x = x + (width / 2 - ((length / 2) * (letterWidth + 1)) - (length % 2 != 0 ? letterWidth / 2 : 0));
			}
		}
		if (valignment != NONE)
		{
			
		}
		
		for (int i = 0; i < length; i ++)
		{	
			int ch = prototype.indexOf(s.charAt(i));
			if (ch < 0) continue;
			
			int xx = ch % lineLength;
			int yy = ch / lineLength;
			
			drawCharacter((i * (letterWidth + 1)) + x, y, xx * (letterWidth + 1), yy * (letterHeight + 1), letterWidth, letterHeight, widthLimit, heightLimit, width, pxs, col);
		}
	}
	
	public void draw(String s, int scale, int x, int y, int[] pxs, int width, int col, int halignment, int valignment)
	{
		int length = s.length();
		
		if (halignment != NONE)
		{
			if (halignment == CENTER)
			{
				x = x + (width / 2 - ((length / 2) * ((letterWidth * scale) + scale)) - (length % 2 != 0 ? (letterWidth * scale) / 2 : 0));
			}
		}
		if (valignment != NONE)
		{
			
		}
		
		for (int i = 0; i < length; i ++)
		{	
			int ch = prototype.indexOf(s.charAt(i));
			if (ch < 0) continue;
			
			int xx = ch % lineLength;
			int yy = ch / lineLength;
			
			drawCharacter(i * ((letterWidth * scale) + scale) + x, y, scale, xx * (letterWidth + 1), yy * (letterHeight + 1), letterWidth, letterHeight, width, pxs, col);
		}
	}
	
	public void draw(String s, int scale, int x, int y, int[] pxs, int width, int col, int characterLimit, int halignment, int valignment)
	{
		int length = s.length() <= characterLimit ? s.length() : characterLimit;
		
		if (halignment != NONE)
		{
			if (halignment == CENTER)
			{
				x = x + (width / 2 - ((length / 2) * ((letterWidth * scale) + scale)) - (length % 2 != 0 ? (letterWidth * scale) / 2 : 0));
			}
		}
		if (valignment != NONE)
		{
			
		}
		
		for (int i = 0; i < length; i ++)
		{	
			int ch = prototype.indexOf(s.charAt(i));
			if (ch < 0) continue;
			
			int xx = ch % lineLength;
			int yy = ch / lineLength;
			
			drawCharacter(i * ((letterWidth * scale) + scale) + x, y, scale, xx * (letterWidth + 1), yy * (letterHeight + 1), letterWidth, letterHeight, width, pxs, col);
		}
	}
	
	public void draw(String s, int scale, int x, int y, int widthLimit, int heightLimit, int[] pxs, int width, int col, int halignment, int valignment)
	{
		int length = s.length();
		
		if (halignment != NONE)
		{
			if (halignment == CENTER)
			{
				x = x + (width / 2 - ((length / 2) * ((letterWidth * scale) + scale)) - (length % 2 != 0 ? (letterWidth * scale) / 2 : 0));
			}
		}
		if (valignment != NONE)
		{
			
		}
		
		for (int i = 0; i < length; i ++)
		{	
			int ch = prototype.indexOf(s.charAt(i));
			if (ch < 0) continue;
			
			int xx = ch % lineLength;
			int yy = ch / lineLength;
			
			drawCharacter(i * ((letterWidth * scale) + scale) + x, y, scale, xx * (letterWidth + 1), yy * (letterHeight + 1), letterWidth, letterHeight, widthLimit + x - scale - letterWidth, heightLimit, width, pxs, col);
		}
	}
}