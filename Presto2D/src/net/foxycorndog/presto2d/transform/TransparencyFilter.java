package net.foxycorndog.presto2d.transform;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

/*********************************************************************
 * Used to set the transparency, or add a transparent layer, to an 
 * image.
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class TransparencyFilter
{

	/*****************************************************************
	 * The default blank constructor.
	 *****************************************************************/
	public TransparencyFilter()
	{
		
	}
	
	/*****************************************************************
	 * Used to set the transparency of a BufferedImage.
	 * 
	 * @param source The BufferedImage that is being transformed.
	 * @param alpha How much to make it transparent 
	 * (.25f = 75% opaque).
	 * @return The transformed BufferedImage.
	 *****************************************************************/
	public BufferedImage setTransparency(BufferedImage source, float alpha)
	{	
		Graphics2D g2d = source.createGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, alpha));
		g2d.drawImage(source, 0, 0, null);
		g2d.dispose();
		
		return source;
	}
	
	/*****************************************************************
	 * Used to set the transparency of an Image.
	 * 
	 * @param source The Image that is being transformed.
	 * @param alpha How much to make it transparent. 
	 * (.25f = 75% opaque).
	 * @return The transformed Image.
	 *****************************************************************/
	public Image setTransparency(Image source, float alpha)
	{	
		BufferedImage source2 = new BufferedImage(source.getWidth(null), source.getHeight(null), Transparency.BITMASK);
		
		Graphics2D g2d = source2.createGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, alpha));
		g2d.drawImage(source, 0, 0, null);
		g2d.dispose();
		
		return source2;
	}
	
	/*****************************************************************
	 * Used to add a transparent layer of any color over a 
	 * BufferedImage.
	 * 
	 * @param source The BufferedImage that is being transformed.
	 * @param alpha How transparent to make the overlay. 
	 * (.25f = 75% opaque).
	 * @param color The color of the transparent overlay to add.
	 * @return The transformed BufferedImage.
	 *****************************************************************/
	public BufferedImage overlayTransparency(BufferedImage source, float alpha, Color color)
	{
		BufferedImage transparent = new BufferedImage(source.getWidth(), source.getHeight(), Transparency.BITMASK);

		Graphics2D g2 = transparent.createGraphics();
		g2.setColor(color);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, alpha));
		g2.clearRect(0, 0, source.getWidth(), source.getHeight());
		g2.fillRect(0, 0, source.getWidth(), source.getHeight());
		g2.dispose();

		Graphics2D g2D = source.createGraphics();
		g2D.drawImage(source, 0, 0, null);
		g2D.drawImage(transparent, 0, 0, null);
		g2D.dispose();

		return source;
	}
	
	/*****************************************************************
	 * Used to add a transparent layer of any color over a 
	 * BufferedImage.
	 * 
	 * @param source The BufferedImage that is being transformed.
	 * @param alpha How transparent to make the overlay. 
	 * (.25f = 75% opaque).
	 * @param color The color of the transparent overlay to add.
	 * @return The transformed BufferedImage.
	 *****************************************************************/
	public Image overlayTransparency(Image source, float alpha, Color color)
	{
		BufferedImage transparent = new BufferedImage(source.getWidth(null), source.getHeight(null), Transparency.BITMASK);

		Graphics2D g2 = transparent.createGraphics();
		g2.setColor(color);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, alpha));
		g2.clearRect(0, 0, source.getWidth(null), source.getHeight(null));
		g2.fillRect(0, 0, source.getWidth(null), source.getHeight(null));
		g2.dispose();

		Graphics2D g2D = transparent.createGraphics();
		g2D.drawImage(source, 0, 0, null);
		g2D.drawImage(transparent, 0, 0, null);
		g2D.dispose();

		return transparent;
	}
	
	/*****************************************************************
	 * Used to add a transparent layer of any color under a 
	 * BufferedImage.
	 * 
	 * @param source The BufferedImage that is being transformed.
	 * @param alpha How transparent to make the underlay. 
	 * (.25f = 57% opaque).
	 * @param color The color of the transparent underlay to add.
	 * @return The transformed BufferedImage.
	 *****************************************************************/
	public BufferedImage underlayTransparency(BufferedImage source, float alpha, Color color)
	{
		BufferedImage transparent = new BufferedImage(source.getWidth(), source.getHeight(), Transparency.BITMASK);
		BufferedImage dest = new BufferedImage(source.getWidth(), source.getHeight(), Transparency.BITMASK);
		
		Graphics2D g2 = transparent.createGraphics();
		g2.setColor(color);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, alpha));
		g2.clearRect(0, 0, source.getWidth(), source.getHeight());
		g2.fillRect(0, 0, source.getWidth(), source.getHeight());
		
		
		Graphics2D g2D = dest.createGraphics();
		g2D.drawImage(transparent, 0, 0, null);
		g2D.drawImage(source, 0, 0, null);
		g2D.dispose();
		
		return dest;
	}
}
