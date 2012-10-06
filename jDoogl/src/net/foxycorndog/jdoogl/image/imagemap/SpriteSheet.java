package net.foxycorndog.jdoogl.image.imagemap;

import java.io.File;
import java.io.IOException;

import net.foxycorndog.jdoogl.GL;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SpriteSheet extends ImageMap
{
	private boolean flipped;
	
	private int     rows, cols;
	
	private String  location, format;
	
	private Texture texture;
	
	public SpriteSheet(String location, String format, int cols, int rows, boolean flipped, boolean stream)
	{
		this.location = location;
		this.format   = format;
		this.rows     = rows;
		this.cols     = cols;
		this.flipped  = flipped;
		
		init(stream);
	}
	
	private void init(boolean stream)
	{
		if (!stream)
		{
			try
			{
				texture = TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(location), flipped);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				texture = TextureLoader.getTexture(format, this.getClass().getClassLoader().getResourceAsStream(location), flipped);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public float[] getImageOffsetsf(int x, int y, int width, int height)
	{
		float offsets[] = new float[4];
		
		float xo = x % cols;
		float yo = (rows - y - height) % rows;
		
		float w = texture.getWidth();
		float h = texture.getHeight();
		
		offsets[0] = (xo / cols) * w;
		offsets[1] = (yo / rows) * h;
		offsets[2] = ((xo + (float)width) / cols) * w;
		offsets[3] = ((yo + (float)height) / rows) * h;
		
		return offsets;
	}
	
	public double[] getImageOffsetsd(int x, int y, int width, int height)
	{
		double offsets[] = new double[4];
		
		double xo = x % cols;
		double yo = (rows - y - height) % rows;
		
		double w = texture.getWidth();
		double h = texture.getHeight();
		
		offsets[0] = (xo / cols) * w;
		offsets[1] = (yo / rows) * h;
		offsets[2] = ((xo + (double)width) / cols) * w;
		offsets[3] = ((yo + (double)height) / rows) * h;
		
		return offsets;
	}
	
	public void bind()
	{
		texture.bind();
	}
	
	public int getImageWidth()
	{
		return texture.getImageWidth();
	}
	
	public int getImageHeight()
	{
		return texture.getImageHeight();
	}
	
	public int getCols()
	{
		return cols;
	}
	
	public int getRows()
	{
		return rows;
	}
}