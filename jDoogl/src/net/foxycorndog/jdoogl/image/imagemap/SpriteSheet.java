package net.foxycorndog.jdoogl.image.imagemap;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import net.foxycorndog.jdoogl.GL;

public class SpriteSheet extends Texture
{
	private boolean flipped;
	
	private int     rows, cols;
	
	private String  location;
	
	public SpriteSheet(String location, int cols, int rows)
	{
		super(location);
		
		this.location = location;
		this.rows     = rows;
		this.cols     = cols;
	}
	
	public SpriteSheet(String location, Class clazz, int cols, int rows)
	{
		super(location, clazz);
		
		this.location = location;
		this.rows     = rows;
		this.cols     = cols;
	}
	
	public SpriteSheet(Image image, int cols, int rows)
	{
		super(image);
		
		this.rows     = rows;
		this.cols     = cols;
	}
	
	public float[] getImageOffsetsf(int x, int y, int width, int height)
	{
		float offsets[] = new float[4];
		
		float xo = x % cols;
		float yo = (rows - y - height) % rows;
		
		float w = getTextureWidth();
		float h = getTextureHeight();
		
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
		
		double w = getTextureWidth();
		double h = getTextureHeight();
		
		offsets[0] = (xo / cols) * w;
		offsets[1] = (yo / rows) * h;
		offsets[2] = ((xo + (double)width) / cols) * w;
		offsets[3] = ((yo + (double)height) / rows) * h;
		
		return offsets;
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