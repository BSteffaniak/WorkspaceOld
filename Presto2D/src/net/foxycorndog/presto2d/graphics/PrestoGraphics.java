package net.foxycorndog.presto2d.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/*********************************************************************
 * 
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class PrestoGraphics
{
	public static final int CENTER = 0, STRETCHED = 1,
			UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3,
			HORIZONTAL = 0, VERTICAL = 1;
	
	public PrestoGraphics()
	{
		
	}
	
	public void drawLine(int x1, int y1, int x2, int y2, int thickness, int[] pixels, int width, int col)
	{
		int height = pixels.length / width;
		
		float xslope = x1 - x2;
		float yslope = y1 - y2;
		
//		int xlength = (int)xslope;
//		int ylength = (int)yslope;
		
		float hypot = (float)Math.sqrt((Math.pow(xslope, 2) + Math.pow(yslope, 2)));
		
		xslope /= hypot;
		yslope /= hypot;
		
		for (int off = (thickness >= 2 ? -(thickness / 2) : 0); off < (thickness > 1 ? thickness : 1); off ++)
		{
			float x = x2;
			float y = y2;
			
			while (Math.abs(x - x2) <= Math.abs(xslope * hypot) && Math.abs(y - y2) <= Math.abs(yslope * hypot))
			{
				int xx = (int)(x - (off * yslope));
				int yy = (int)(y + (off * xslope));
				
				if (xx >= width || xx < 0 || yy >= height || yy < 0)
				{
					x += xslope;
					y += yslope;
				
					continue;
				}
				
				pixels[xx + yy * width] = col;
				
				x += xslope;
				y += yslope;
			}
		}
	}
	
	public void fillCircle(int x, int y, int r, int[] pixels, int width, int col)
	{
		int w = r * 2;
		int h = r * 2;
		
		for (int yy = 0; yy < h; yy ++)
		{
			for (int xx = 0; xx < w; xx ++)
			{
				if ((xx + x) + (yy + y) * width >= 0 && (xx + x) + (yy + y) * width < pixels.length)
				{
					if (pixels[(xx + x) + (yy + y) * width] == col) continue;
					if (Math.pow(xx - r, 2) + Math.pow(yy - r, 2) <= Math.pow(r, 2))
					{
						pixels[(xx + x) + (yy + y) * width] = col;
					}
				}
			}
		}
	}
	
	public void drawCircle(int x, int y, int r, int thickness, int[] pixels, int width, int col)
	{
		int w = r * 2;
		int h = r * 2;
		
		for (int yy = 0; yy < h; yy ++)
		{
			for (int xx = 0; xx < w; xx ++)
			{
				if ((xx + x) + (yy + y) * width >= 0 && (xx + x) + (yy + y) * width < pixels.length)
				{
					if (pixels[(xx + x) + (yy + y) * width] == col) continue;
					if (Math.pow(xx - r, 2) + Math.pow(yy - r, 2) <= Math.pow(r, 2) && Math.pow(xx - r, 2) + Math.pow(yy - r, 2) > Math.pow(r - thickness, 2))
					{
						pixels[(xx + x) + (yy + y) * width] = col;
					}
				}
			}
		}
	}
	
	public void fillOval(int x, int y, int w, int h, int[] pixels, int width, int col)
	{
		for (int yy = 0; yy < h; yy ++)
		{
			for (int xx = 0; xx < w; xx ++)
			{
				if ((xx + x) + (yy + y) * width >= 0 && (xx + x) + (yy + y) * width < pixels.length)
				{
					if (pixels[(xx + x) + (yy + y) * width] == col) continue;
					
					if (Math.pow(xx - w / 2, 2) + Math.pow(yy - h / 2, 2) <= (w / 2) * (h / 2))
					{
						pixels[(xx + x) + (yy + y) * width] = col;
					}
				}
			}
		}
	}
	
	public BufferedImage scale(int[] pixels, int width, int height, int w, int h, int type)
	{
		BufferedImage d = new BufferedImage(width, height, BufferedImage.BITMASK);
		int[] px = new int[width * height];
		
		float scale = (float)((float)width / (float)w);
		float remainder = 0;
		
		for (int y = 0; y < h; y ++)
		{
			for (int x = 0; x < w; x ++)
			{
				for (int i = 0; i < scale + decimal(remainder); i ++)
				{
					px[(x + i) + y * width] = pixels[x + y * w];
					px[x + (y + i) * width] = pixels[x + y * w];
				}
				remainder += decimal(scale);
			}
		}
		
		d.setRGB(0, 0, width, height, px, 0, width);
		
		return d;
	}
	
	public float decimal(float t)
	{
		return (float)(t - Math.floor(t));
	}
	
	public int[] mirror(int[] pix, int w, int h, int dir)
	{
		int[] p = new int[pix.length];
		
		if (dir == HORIZONTAL)
		{
			for (int y = 0; y < h; y ++)
			{
				for (int x = 0; x < w; x ++)
				{
					p[x + y * w] = pix[(w - x - 1) + y * w];
				}
			}
		}
		if (dir == VERTICAL)
		{
			for (int y = 0; y < h; y ++)
			{
				for (int x = 0; x < w; x ++)
				{
					p[x + y * w] = pix[x + (h - y - 1) * w];
				}
			}
		}
		
		return p;
	}
	
	public int[] rotate(int[] pix, int w, int h, int dir)
	{
		int[] p = new int[pix.length];
		
		if (dir == RIGHT)
		{
			for (int y = 0; y < h; y ++)
			{
				for (int x = 0; x < w; x ++)
				{
					p[x + y * w] = pix[y + (w - x - 1) * w];
				}
			}
		}
		else if (dir == LEFT)
		{
			for (int y = 0; y < h; y ++)
			{
				for (int x = 0; x < w; x ++)
				{
					p[x + y * w] = pix[y + x * w];
				}
			}
		}
		else if (dir == DOWN)
		{
			for (int y = 0; y < h; y ++)
			{
				for (int x = 0; x < w; x ++)
				{
					p[x + y * w] = pix[(w - x - 1) + (h - y - 1) * w];
				}
			}
		}
		else if (dir == UP)
		{
			for (int y = 0; y < h; y ++)
			{
				for (int x = 0; x < w; x ++)
				{
					p[x + y * w] = pix[(w - x - 1) + (h - y - 1) * w];
				}
			}
		}
		
		return p;
	}
	
	public void rotate(int[][] sprite, int[] sp, int w, int h)
	{
		if (sprite.length >= 2)
		{
			sprite[0] = sp;
			sprite[1] = rotate(sp, w, h, RIGHT);
			
			if (sprite.length >= 4)
			{
				sprite[2] = rotate(sp, w, h, DOWN);
				sprite[3] = rotate(sp, w, h, LEFT);
			}
		}
	}

	public void mirror(int[][] image, int[] sp, int w, int h)
	{
		if (image.length >= 2)
		{
			image[0] = sp;
			image[1] = mirror(sp, w, h, HORIZONTAL);
			
			if (image.length >= 4)
			{
				image[2] = rotate(sp, w, h, DOWN);
				image[3] = mirror(image[2], w, h, HORIZONTAL);
				
				if (image.length >= 8)
				{
					image[4] = rotate(sp, w, h, RIGHT);
					image[5] = mirror(image[4], w, h, VERTICAL);
					
					image[6] = rotate(sp, w, h, LEFT);
					image[7] = mirror(image[6], w, h, VERTICAL);
				}
			}
		}
	}
	
	public void drawPixels(int[] pixels, int pixelsWidth, int x, int y, int w, int h, int[] pix, int width)
	{
		int wdt = pixelsWidth;
		
		for (int yy = 0; yy < h; yy ++)
		{
			for (int xx = 0; xx < w; xx ++)
			{
				if (pixels[xx + yy * wdt] == 0x00000000) continue;
				
				pix[(xx + x) + (yy + y) * width] = pixels[xx + yy * wdt];
			}
		}
	}
	
	public void drawRect(int x, int y, int w, int h, int[] pixels, int width, int color)
	{
		for (int times = 0; times < 2; times ++)
		{
			int xx = x + (times * w);
			for (int yy = y; yy < h + y; yy ++)
			{
				pixels[xx + yy * width] = color;
			}
		}
		for (int times = 0; times < 2; times ++)
		{
			int yy = y + (times * h);
			for (int xx = x; xx < w + x; xx ++)
			{
				pixels[xx + yy * width] = color;
			}
		}
		
		pixels[(x + w) + (y + h) * width] = color;
	}
	
	public void drawImage(BufferedImage image, int x, int y, int w, int h, int[] pixels, int width)
	{
		int wdt = image.getWidth();
		int[] pix = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		for (int yy = 0; yy < h; yy ++)
		{
			for (int xx = 0; xx < w; xx ++)
			{
				if (pix[xx + yy * wdt] == 0x00000000) continue;
				
				pixels[(xx + x) + (yy + y) * width] = pix[xx + yy * wdt];
			}
		}
	}

	public void fillRect(int x, int y, int w, int h, int[] pixels, int width, int color)
	{
		int height = pixels.length / width;
		
		for (int yy = y; yy < h + y; yy ++)
		{
			for (int xx = x; xx < w + x; xx ++)
			{
				if (xx >= width || yy >= height) continue;
				
				pixels[xx + yy * width] = color;
			}
		}
	}
	
	public void rotateImage(int[] pixels, int w, int degree)
	{
		int h = pixels.length / w;
//		int[] pixels2 = new int[pixels.length];
		
		for (int y = 0; y < h; y ++)
		{
			for (int x = 0; x < w; x ++)
			{
				try
				{
					
				}
				catch (IndexOutOfBoundsException ex)
				{
					
				}
			}
		}
	}
}
