package net.foxycorndog.p1xeland.map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.Task;
import net.foxycorndog.jdoutil.VerticesBuffer;
import net.foxycorndog.p1xeland.P1xeland;
import net.foxycorndog.p1xeland.items.tiles.Tile;

public class DayCycle
{
	private int           hours, minutes, seconds;
	private int           time;
	
	private float         dtime;
	
	private BufferedImage cycleImage;
	
	private VerticesBuffer skyVerticesBuffer, sunMoonVerticesBuffer, cloudsVerticesBuffer;
	
	private LightBuffer    skyTexturesBuffer, sunMoonTexturesBuffer, cloudsTexturesBuffer;
	
	private int           imageData[];
	private int           currentColor[], actualColor[];
	
	// Float x, Float y, Integer width, Integer height, Float speed, Float offset, Float scale, Boolean rising
	private ArrayList<Object[]> cloudsProperties;
	
	public DayCycle()
	{
		hours   = 0;
		minutes = 24;
		seconds = 0;
		
		try
		{
			Image img  = ImageIO.read(new File("res/images/DayNight.png"));
			
			cycleImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
			
			Graphics g = cycleImage.createGraphics();
			g.drawImage(img, 0, 0, null);
			g.dispose();
			
			imageData  = ((DataBufferInt)cycleImage.getRaster().getDataBuffer()).getData();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		cloudsProperties       = new ArrayList<Object[]>();
		
		skyVerticesBuffer     = new VerticesBuffer(4 * 2, 2);
		skyTexturesBuffer     = new LightBuffer(4 * 2);
		
		skyVerticesBuffer.setData(0, GL.addRectVertexArrayf(-5, -5, Frame.getWidth() + 10, Frame.getHeight() + 10, 0, null));
		
		sunMoonVerticesBuffer = new VerticesBuffer(4 * 2 * 2, 2);
		sunMoonTexturesBuffer = new LightBuffer(4 * 2 * 2);
		
		sunMoonVerticesBuffer.setData(0 * 4 * 2, GL.addRectVertexArrayf(0, 0, P1xeland.textureSize * 2, P1xeland.textureSize * 2, 0, null));
		sunMoonVerticesBuffer.setData(1 * 4 * 2, GL.addRectVertexArrayf(0, 0, P1xeland.textureSize * 2, P1xeland.textureSize * 2, 0, null));
		
		sunMoonTexturesBuffer.setData(0 * 4 * 2, GL.addRectTextureArrayf(Tile.getTerrain(), 3, 15, 2, 2, 0, null));
		sunMoonTexturesBuffer.setData(1 * 4 * 2, GL.addRectTextureArrayf(Tile.getTerrain(), 5, 15, 2, 2, 0, null));
		
		cloudsVerticesBuffer = new VerticesBuffer(4 * 2 * 16, 2);
		cloudsTexturesBuffer = new LightBuffer(4 * 2 * 16);
		
		for (int i = 0; i < 16; i ++)
		{
			float   x      = (int)(Math.random() * (Frame.getWidth()  / P1xeland.scale));
			float   y      = (int)(Math.random() * (Frame.getHeight() / P1xeland.scale));
			
			int     len1   = (int)(Math.random() * 45) + 5;
			int     len2   = (int)(Math.random() * 45) + 5;
			
			int     width  = Math.max(len1, len2);
			int     height = Math.min(len1, len2);
			
			float   speed  = ((int)(Math.random() * 5) + 1) / 100f;
			
			float   offset = (int)(Math.random() * 5) - 2;
			
			float   scale  = 1;
			
			boolean rising = (int)(Math.random()) == 0;
			
			cloudsProperties.add(new Object[] { x, y, width, height, speed, offset, scale, rising });
			
			cloudsVerticesBuffer.setData(i * 4 * 2, GL.addRectVertexArrayf(0, 0, width, height, 0, null));
			
			cloudsTexturesBuffer.setData(i * 4 * 2, GL.addRectTextureArrayf(GL.white, 0, null));
		}
		
		time         = 0;
		dtime        = 0;
		
		currentColor = toRGB(imageData[1500]);
		actualColor  = toRGB(imageData[1500]);
	}
	
	public void tick(float delta)
	{
		dtime       += (((delta * 1f) / P1xeland.targetFps) * imageData.length) / ((hours * 60 * 60) + (minutes * 60) + (seconds));
		
		dtime        = dtime >= imageData.length ? dtime % imageData.length : dtime;
		
		time         = ((int)dtime);
		
//		Calendar c = Calendar.getInstance();
//		
//		setTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
		
//		System.out.println(c.get(Calendar.HOUR_OF_DAY) + ", " + c.get(Calendar.MINUTE) + ", " + c.get(Calendar.SECOND));
		
		actualColor = toRGB(imageData[(int)time]);
		
		for (Object properties[] : cloudsProperties)
		{
			if (Frame.wasResized())
			{
				float resizeScale = ((float)Frame.getWidth() / P1xeland.oldWidth);
				
				properties[0]     = (Float)properties[0] * resizeScale;
				
				properties[6]     = (Frame.getWidth() / 640f);
			}
			
			properties[0] = (Float)properties[0] + ((Float)properties[4] * delta);
		
			if ((Boolean)(properties[7]))
			{
				properties[5] = (Float)properties[5] + .01f * delta;
				
				if ((Float)properties[5] >= 2)
				{
					properties[7] = false;
				}
			}
			else
			{
				properties[5] = (Float)properties[5] - .01f * delta;
				
				if ((Float)properties[5] <= -2)
				{
					properties[7] = true;
				}
			}
		
			if ((Float)properties[0] > Frame.getWidth() / P1xeland.scale)
			{
				properties[0] = (float)(-(Integer)properties[2]) * (Float)properties[6];
			}
		}
	}
	
	public void render(float mapY)
	{
		GL.beginManipulation();
		{
			if (actualColor != null && currentColor != null)
			{
				GL.translatef(0, 0, -5);
				
				renderSkyColor();
				
				GL.translatef(0, (mapY / 15f), 0);
				
//				GL.scalef(P1xeland.scale, P1xeland.scale, 1);
				
				renderSunAndMoon();
				
				renderClouds();
			}
		}
		GL.endManipulation();
	}
	
	public void renderSkyColor()
	{
		int r = currentColor[0];
		int g = currentColor[1];
		int b = currentColor[2];
		
		GL.translatef(0, 0, -15f);
		
		GL.setColori(r, g, b, 255);
		
		GL.renderQuad(skyVerticesBuffer, skyTexturesBuffer, GL.white);
		
		GL.setColori(255, 255, 255, 255);
	}
	
	public void renderSunAndMoon()
	{
		GL.renderQuads(sunMoonVerticesBuffer, sunMoonTexturesBuffer, Tile.getTerrain(), 0, 2, new Task()
		{
			public boolean run(int index)
			{
				float dtime2 = dtime;
				
				dtime2 += 2137.47f;
				
				dtime2 %= imageData.length;
				
				float x = ((((dtime2 / (float)imageData.length) * Frame.getWidth() * 2) / P1xeland.scale)) - (index * (Frame.getWidth() / P1xeland.scale));
				
				if (index == 0)
				{
					if (x > Frame.getWidth() / P1xeland.scale)
					{
						x -= (Frame.getWidth() * 2) / P1xeland.scale;
					}
				}
				
				float y = (Frame.getHeight() / P1xeland.scale - P1xeland.textureSize * 2);
				
				float x2 = x - ((Frame.getWidth() / P1xeland.scale) / 2) + (P1xeland.textureSize);
				
				y -= .004f * (x2 * x2);
				
				if (index == 1)
				{
					GL.endManipulation();
				}
				
				GL.beginManipulation();
				
				GL.translatef(x, y, 1);
				
				return true;
			}
		});
		
		GL.endManipulation();
	}
	
	public void renderClouds()
	{
		GL.translatef(0, (Frame.getHeight() - 512f) / P1xeland.scale, 2f);
		
		int time2 = time;
		
		time2 = time2 <= 650  ? 3000 + time2 : time2;
		
		time2 = time2 >= 2200 ? time2 - 2200 : 0;
		
		int darkness = (int)(time2 / 2f);
		
		darkness = darkness > 230 ? 230 : darkness;
		
		darkness /= 1.8f;
		
		GL.setColori(160 - darkness, 160 - darkness, 160 - darkness, 26);
		
//		GL.renderQuads(cloudsVerticesBuffer, cloudsTexturesBuffer, GL.white, 0, cloudsProperties.size(), new Task()
//		{
//			public boolean run(int index)
//			{
//				if (index > 0)
//				{
//					GL.endManipulation();
//				}
//				
//				GL.beginManipulation();
//				
//				Object properties[] = cloudsProperties.get(index);
//				
//				GL.translatef((Float)properties[0], (Float)properties[1] + (Float)properties[5], 0);
//				
//				GL.scalef((Float)properties[6], (Float)properties[6], 1);
//				
//				return true;
//			}
//		});
		
		GL.endManipulation();
		
		GL.setColori(255, 255, 255, 255);
	}
	
	public int[] getActualColor()
	{
		return actualColor;
	}
	
	public void setCurrentColor(int r, int g, int b)
	{
		currentColor[0] = r;
		currentColor[1] = g;
		currentColor[2] = b;
	}
	
	public void setTime(int hours, int minutes, int seconds)
	{
		if (hours < 0 || minutes < 0 || seconds < 0)
		{
			return;
		}
		
		dtime = ((float)(((float)hours * (imageData.length / 24f)) + ((float)minutes * ((float)imageData.length / 24f / 60f)) + ((float)seconds * ((float)imageData.length / 24f / 60f / 60f))));
		
		this.time  = (int)dtime;
	}
	
	private int[] toRGB(int color)
	{
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8)  & 0xFF;
		int b = (color)       & 0xFF;
		
		return new int[] { r, g, b };
	}
	
	public int getTime()
	{
		return time;
	}
	
//	public int[] getTimeArray()
//	{
//		int hours   = 0;
//		int minutes = 0;
//		int seconds = 0;
//		
//		hours = (int)(dtime / 60 / 60) % 24;
//		minutes = (int)(dtime / 60) % 60;
//		seconds = (int)(dtime) % 60;
//		
//		return new int[] { hours, minutes, seconds };
//	}
}