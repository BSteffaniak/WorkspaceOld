package net.foxycorndog.presto2d.graphics;

public class TextButton
{
//	private CustomFont font;
//	private String     text;
	private int        /*width, */height;
	private int        background;//, foreground;
	private float      x, y;
//	private int        leftMargin, rightMargin, topMargin, bottomMargin;
	
	public TextButton(String text)
	{
//		this.text = text;
		
		x            = 0;
		y            = 0;
		
//		font         = null;
//		
//		leftMargin   = 0;
//		rightMargin  = 0;
//		topMargin    = 0;
//		bottomMargin = 0;
	}
	
//	public void setCustomFont(CustomFont font)
//	{
//		this.font = font;
//	}
	
	public void setSize(int width, int height)
	{
//		this.width  = width;
		this.height = height;
	}
	
	public void setLocation(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void move(float dx, float dy)
	{
		this.x += dx;
		this.y += dy;
	}
	
//	public void setMargins(int left, int right, int top, int bottom)
//	{
//		leftMargin   = left;
//		rightMargin  = right;
//		topMargin    = top;
//		bottomMargin = bottom;
//	}
//	
//	public void setMargins(int leftRight, int topBottom)
//	{
//		leftMargin   = leftRight;
//		rightMargin  = leftRight;
//		topMargin    = topBottom;
//		bottomMargin = topBottom;
//	}
//	
//	public void setMargins(int margin)
//	{
//		leftMargin   = margin;
//		rightMargin  = margin;
//		topMargin    = margin;
//		bottomMargin = margin;
//	}
	
	public void setBackground(int color)
	{
		background = color;
	}
	
//	public void setForeground(int color)
//	{
//		foreground = color;
//	}
	
	public void paint(int pixels[], int width)
	{
		int xo = 0;
		int yo = 0;
		
		for (int yy = 0; yy < height; yy ++)
		{
			for (int xx = 0; xx < width; xx ++)
			{
				xo = (int)(xx + x);
				yo = (int)(yy + y);
				
				if (xo + yo * width >= pixels.length || xo + yo * width < 0)
				{
					continue;
				}
				
				pixels[xo + yo * width] = background;
			}
		}
		
		
	}
}