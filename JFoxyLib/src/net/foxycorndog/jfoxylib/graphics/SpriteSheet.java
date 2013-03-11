package net.foxycorndog.jfoxylib.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet extends Texture
{
	private int	cols, rows;
	
	public SpriteSheet(String location, int cols, int rows) throws IOException
	{
		this(ImageIO.read(new File(location)), cols, rows);
	}
	
	public SpriteSheet(BufferedImage image, int cols, int rows)
	{
		super(image);
		
		this.cols = cols;
		this.rows = rows;
	}
	
	public float[] getImageOffsets(int x, int y, int width, int height)
	{
		float offsets[] = new float[4];
		
		float xo = x % cols;
		float yo = (rows - y - height) % rows;
		
		float w = 1;
		float h = 1;
		
		offsets[0] = (xo / cols) * w;
		offsets[1] = (yo / rows) * h;
		offsets[2] = ((xo + (float)width) / cols) * w;
		offsets[3] = ((yo + (float)height) / rows) * h;
		
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