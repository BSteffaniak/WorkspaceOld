package net.foxycorndog.jfoxylibpixel.components;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image
{
	private BufferedImage image;
	
	public Image(String location) throws IOException
	{
		new Image(new File(location));
	}
	
	public Image(File file) throws IOException
	{
		image = ImageIO.read(file);
	}
}