package net.foxycorndog.presto2d.graphics;

/*********************************************************************
 * 
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class PrestoColor
{
	public static final int WHITE = 0xffffff, BLACK = 0x000000, RED = 0xff0000,
			GREEN = 0x00ff00, BLUE = 0x0000ff;
	
	public PrestoColor()
	{
		
	}
	
	/**
	 * @deprecated not finished....
	 * 
	 * @param col1 the color to be darkened.
	 * @param col2 the color to darken with.
	 * @return the darkened color.
	 */
	@Deprecated
	public static int darkenAlpha(int col1, int col2)
	{
//		int av1 = ((col1 >> 24) & 0xff);
//		int av2 = ((col2 >> 24) & 0xff);
//		
//		int val1 = (av1 >= 128 ? -(256 - av1) : av1) * 0x1000000;
//		int val2 = (av2 >= 128 ? -(256 - av2) : av2) * 0x1000000;
//		
//		col1 -= val1;
//		
//		int adder = val1 + val2
		
		return col1;
	}
	
	public static int getInverse(int col)
	{
		int a = getAlpha(col);
		int r = (col >> 16) & 0xff;
		int g = (col >>  8) & 0xff;
		int b = (col      ) & 0xff;
		
		r = 255 - r;
		g = 255 - g;
		b = 255 - b;
		
		r *= 0x10000;
		g *= 0x100;
		b *= 0x1;
		
		return a + r + g + b;
	}
	
	public static int getColor(int r, int g, int b)
	{
		return getColor(r, g, b, 255);
	}
	
	public static int getColor(int r, int g, int b, int a)
	{
		a *= 0x1000000;
		r *= 0x10000;
		g *= 0x100;
		b *= 0x1;
		
		return r + g + b + a;
	}
	
	public static int decode(String text)
	{
//		String nums = "";
//		
//		if (text.startsWith("0x"))
//		{
//			nums = text.substring(2);
//		}
//		else if (text.startsWith("#"))
//		{
//			nums = text.substring(1);
//		}
//		
//		int num = Integer.valueOf(nums);
		
		System.out.println(Integer.decode("0xff000000"));
		
		return 8;
	}
	
	public static int getAlpha(int color)
	{
		int av = ((color >> 24) & 0xff);
		
		int var = (av >= 128 ? -(256 - av) : (av)) * 0x1000000;
		
		return var;
	}
	
	public static int getRed(int color)
	{
		int rv = ((color >> 16) & 0xff);
		
		return rv * 0x10000;
	}
	
	public static int getGreen(int color)
	{
		int gv = ((color >> 16) & 0xff);
		
		return gv * 0x100;
	}
	
	public static int getBlue(int color)
	{
		int bv = ((color >> 16) & 0xff);
		
		return bv * 0x1;
	}
	
	public static int getColor(int c, int offset)
	{
		//0x55
		int r = (c >> 16) & 0xff;
		int g = (c >> 8) & 0xff;
		int b = (c) & 0xff;

		r = r * offset / 0xff;
		g = g * offset / 0xff;
		b = b * offset / 0xff;

		return r << 16 | g << 8 | b;
	}
	
	public static int colorizeColor(int col, int r, int g, int b)
	{
		int ca = getAlpha(col);//(col >> 24) & 0xff;
		int cr = (col >> 16) & 0xff;
		int cg = (col >>  8) & 0xff;
		int cb = (col      ) & 0xff;
		
		//int aa = (a - da) * 16777216;
		int ar = (cr + r) * 0x10000;
		int ag = (cg + g) * 0x100;
		int ab = (cb + b) * 0x1;
		
		
		return ca + (ar > 0xff0000 ? 0xff0000 : ar) + (ag > 0x00ff00 ? 0x00ff00 : ag) + (ab > 0x0000ff ? 0x0000ff : ab);
	}
	
	public static int darkenColor(int col, int darken)
	{
		//alpha value = (av >= 128 ? -(256 - av) : av) * 16777216
		//+
		//red value = rv * 65536
		//+
		//green value = gv * 256
		//+
		//blue value = bv
		
		int a = getAlpha(col);
		int r = (col >> 16) & 0xff;
		int g = (col >>  8) & 0xff;
		int b = (col      ) & 0xff;
		
		//int da = (darken >> 24) & 0xff;
		int dr = (darken >> 16) & 0xff;
		int dg = (darken >>  8) & 0xff;
		int db = (darken      ) & 0xff;
		
		//int aa = (a - da) * 0x1000000;
		int ar = (r - dr) * 0x10000;
		int ag = (g - dg) * 0x100;
		int ab = (b - db) * 0x1;
		
		
		return a + (ar < 0 ? 0 : ar) + (ag < 0 ? 0 : ag) + (ab < 0 ? 0 : ab);
	}
	
	public static int lightenColor(int col, int lighten)
	{
		//?alpha value = av * 16777215/6?
		//red value = rv * 65536
		//green value = gv * 256
		//blue value = bv
		
		int a = getAlpha(col);
		int r = (col >> 16) & 0xff;
		int g = (col >>  8) & 0xff;
		int b = (col      ) & 0xff;
		
		//int da = (darken >> 24) & 0xff;
		int lr = (lighten >> 16) & 0xff;
		int lg = (lighten >>  8) & 0xff;
		int lb = (lighten      ) & 0xff;
		
		//int aa = (a - da) * 16777216;
		int ar = (r + lr) * 0x10000;
		int ag = (g + lg) * 0x100;
		int ab = (b + lb) * 0x1;
		
		
		return a + (ar > 0xff0000 ? 0xff0000 : ar) + (ag > 0x00ff00 ? 0x00ff00 : ag) + (ab > 0x0000ff ? 0x0000ff : ab);
	}
}
