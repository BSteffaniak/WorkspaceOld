package net.foxycorndog.presto2d.transform;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/*********************************************************************
 * Used to resize different types of images.
 * @author BradenSteffaniak
 *********************************************************************/
public class Resizer
{

	public static final int SCALE_DEFAULT = 1, SCALE_FAST = 2, SCALE_SMOOTH = 4, SCALE_REPLICATE = 8, SCALE_AREA_AVERAGING = 16;
	
	/*****************************************************************
	 * The default blank constructor.
	 *****************************************************************/
	public Resizer()
	{
		
	}
	
	/*****************************************************************
	 * Used to resize Images to a specific pixel width and height.
	 * 
	 * @param width The width to resize to.
	 * @param height The height to resize to.
	 * @param img The Image to resize.
	 * @return The resized image.
	 *****************************************************************/
	public Image resize(int width, int height, Image img, int hints)
	{
		img = img.getScaledInstance(width, height, hints);
		
		return img;
	}
	
	/*****************************************************************
	 * Used to resize BufferedImages to a specific pixel width and 
	 * height.
	 * 
	 * @param width The width to resize to.
	 * @param height The height to resize to.
	 * @param img The BufferedImage to resize.
	 * @param imageType The type of BufferedImage to create.
	 * @return The resized BufferedImage.
	 *****************************************************************/
	public BufferedImage resize(int width, int height, BufferedImage img, int imageType, int hints)
	{
		Image img2 = img;
		img2 = img2.getScaledInstance(width, height, hints);
		
		BufferedImage dest = new BufferedImage(width, height, imageType);
		
		Graphics g = dest.createGraphics();
		
		g.drawImage(img2, 0, 0, null);
		
		return dest;
	}
}