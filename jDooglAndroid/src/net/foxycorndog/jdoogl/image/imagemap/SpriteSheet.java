package net.foxycorndog.jdoogl.image.imagemap;

import java.io.File;
import java.io.IOException;

import android.content.res.Resources;
import android.graphics.Bitmap;

import net.foxycorndog.jdoogl.GL;

public class SpriteSheet extends Texture
{
	private boolean flipped;
	
	private int     rows, cols;
	
	private String  location, format;
	
	public SpriteSheet(Resources res, int id, int cols, int rows)
	{
		super(res, id);
		
		this.rows     = rows;
		this.cols     = cols;
	}
	
	public float[] getImageOffsetsf(int x, int y, int width, int height)
	{
		float offsets[] = new float[4];
		
		float xo = x % cols;
		float yo = y % rows;
		
		float w = 1;//getWidth();
		float h = 1;//getHeight();
		
		offsets[0] = (xo / cols) * w;
		offsets[1] = (yo / rows) * h;
		offsets[2] = ((xo + (float)width) / cols) * w;
		offsets[3] = ((yo + (float)height) / rows) * h;
		
		return offsets;
	}
	
//	public int getImageWidth()
//	{
//		return 0;//texture.getImageWidth();
//	}
//	
//	public int getImageHeight()
//	{
//		return 0;//texture.getImageHeight();
//	}
	
	public int getCols()
	{
		return cols;
	}
	
	public int getRows()
	{
		return rows;
	}
}