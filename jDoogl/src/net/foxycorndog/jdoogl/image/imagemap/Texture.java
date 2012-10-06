package net.foxycorndog.jdoogl.image.imagemap;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import net.foxycorndog.jdoogl.GL;

import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Texture extends ImageMap
{
	private org.newdawn.slick.opengl.Texture texture;
	private String  location, format;
	private int     width, height;
	private boolean flipped;
	
	public Texture(String location, String format, boolean flipped, boolean stream)
	{
		this.location = location;
		this.format   = format;
		this.flipped  = flipped;
		
		init(stream);
	}
	
	public void init(boolean stream)
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
		
		this.width  = texture.getImageWidth();
		this.height = texture.getImageHeight();
	}
	
	public float[] getImageOffsetsf()
	{
		float offsets[] = new float[4];
		
		float w = texture.getWidth();
		float h = texture.getHeight();
		
		offsets[0] = 0;
		offsets[1] = 0;
		offsets[2] = w;
		offsets[3] = h;
		
		return offsets;
	}
	
	public double[] getImageOffsetsd()
	{
		double offsets[] = new double[4];
		
		double w = texture.getWidth();
		double h = texture.getHeight();
		
		offsets[0] = 0;
		offsets[1] = 0;
		offsets[2] = w;
		offsets[3] = h;
		
		return offsets;
	}
	
	public void bind()
	{
		texture.bind();
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}