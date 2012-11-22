package net.foxycorndog.jdoutil.color;

public class ColorUtil
{
	public static final int RGB = 3, RGBA = 4;
	
	public static int[] toRGB(int colorArray[])
	{
		int colorArray2[] = new int[colorArray.length * 3];
		
		for (int i = 0; i < colorArray.length; i ++)
		{
			int rgb[] = toRGB(colorArray[i]);
			
			colorArray2[i * 3 + 0] = rgb[0];
			colorArray2[i * 3 + 1] = rgb[1];
			colorArray2[i * 3 + 2] = rgb[2];
		}
		
		return colorArray2;
	}
	
	public static int[] toRGBA(int colorArray[])
	{
		int colorArray2[] = new int[colorArray.length * 4];
		
		for (int i = 0; i < colorArray.length; i ++)
		{
			int rgba[] = toRGBA(colorArray[i]);
			
			colorArray2[i * 4 + 0] = rgba[0];
			colorArray2[i * 4 + 1] = rgba[1];
			colorArray2[i * 4 + 2] = rgba[2];
			colorArray2[i * 4 + 3] = rgba[3];
		}
		
		return colorArray2;
	}
	
	public static int[] toRGB(int color)
	{
		return new int[] { getRed(color), getGreen(color), getBlue(color) };
	}
	
	public static int[] toRGBA(int color)
	{
		return new int[] { getRed(color), getGreen(color), getBlue(color), getAlpha(color) };
	}
	
	public static int getAlpha(int color)
	{
		int av = ((color >> 24) & 0xff);
		
//		int var = (av >= 128 ? -(256 - av) : (av)) * 0x1000000;
		
		return av;
	}
	
	public static int getRed(int color)
	{
		int rv = ((color >> 16) & 0xff);
		
		return rv;// * 0x10000;
	}
	
	public static int getGreen(int color)
	{
		int gv = ((color >> 16) & 0xff);
		
		return gv;// * 0x100;
	}
	
	public static int getBlue(int color)
	{
		int bv = ((color >> 16) & 0xff);
		
		return bv;// * 0x1;
	}
}