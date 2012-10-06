package net.foxycorndog.presto2d.graphics;

import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.Random;

/*********************************************************************
 * 
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class NoiseMap
{
//	private int w, h;
	private BufferedImage map;
	private int[] pixels;
//	private int roughness;
	private int[] colors, colorsProminence, cols;
	private float[] colorsPercent;
	
	public NoiseMap(int w, int h)
	{
//		this.w = w;
//		this.h = h;
		
//		roughness = 1;
		
		colors = new int[5];
		colorsProminence = new int[5];
		colorsPercent = new float[5];
		
		colors[0] = 0xff000000;
		colors[1] = 0xffff0000;
		colors[2] = 0xff00ff00;
		colors[3] = 0xff0000ff;
		colors[4] = 0xffffffff;
		
		colorsProminence[0] = 10;
		colorsProminence[1] = 10;
		colorsProminence[2] = 10;
		colorsProminence[3] = 50;
		colorsProminence[4] = 10;
		
		int total = colorsProminence[0] + colorsProminence[1] + colorsProminence[2] + colorsProminence[3] + colorsProminence[4];
		
		colorsPercent[0] = colorsPercent[0] / total;
		colorsPercent[1] = colorsPercent[1] / total;
		colorsPercent[2] = colorsPercent[2] / total;
		colorsPercent[3] = colorsPercent[3] / total;
		colorsPercent[4] = colorsPercent[4] / total;
		
//		for (int i = 0; i < colorsPercent.length; i ++)
//		{
//			colorsPercent[i] = 5;
//		}
		
		
		
		map = new BufferedImage(w, h, Transparency.BITMASK);
		
		//pixels = map.getRGB(0, 0, w, h, pixels, 0, w);
		
		pixels = new int[w * h];
		cols = new int[w * h];
		
		Random random = new Random();
		
		//pixels = map.getData()
		
		int startx = random.nextInt(w / 7);
		int starty = random.nextInt(h / 7);
		
//		int nextx = startx + random.nextInt(w / 7);
		int nexty = starty + random.nextInt(h / 7);
		
//		int length = 0;
		
		for (int y = 0; y < h; y ++)
		{
			for (int x = 0; x < w; x ++)
			{
				if (y >= starty && y <= nexty)
				{
					if (y == starty && x < startx)
					{
						pixels[x + y * w] = colors[3];
						cols[x + y * w] = 3;
					}
					else if (y == starty && x >= startx)
					{
						pixels[x + y * w] = colors[1];
						cols[x + y * w] = 3;
					}
			//		else if ()
				}
				else
				{
					pixels[x + y * w] = colors[3];
					cols[x + y * w] = 3;
				}
			}
		}
		
		map.setRGB(0, 0, w, h, pixels, 0, w);
	}
	
	public BufferedImage getImage()
	{
		return map;
	}
}
