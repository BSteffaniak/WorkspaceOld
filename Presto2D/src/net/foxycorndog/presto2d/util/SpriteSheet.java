package net.foxycorndog.presto2d.util;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SpriteSheet
{
	private Texture texture;
	private String  location, format;
	private float   rows, cols;
	private boolean flipped;
	
	public SpriteSheet(String location, String format, int cols, int rows, boolean flipped)
	{
		this.location = location;
		this.format   = format;
		this.rows     = rows;
		this.cols     = cols;
		this.flipped  = flipped;
		
		init();
	}
	
	public void init()
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
	
	public float[] getImageOffsets(int x, int y, int width, int height)
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
}