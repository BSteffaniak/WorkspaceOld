package net.foxycorndog.presto2d.transform;

import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/*********************************************************************
 * Used to mirror images.
 * @author BradenSteffaniak
 *********************************************************************/
public class Mirror
{	
	public static final int MIRROR_HORIZONTAL = 0, MIRROR_VERTICAL = 1;
	
	/*****************************************************************
	 * The default blank constructor.
	 *****************************************************************/
	public Mirror()
	{
		
	}
	
	/*****************************************************************
	 * Used to mirror an Image.
	 * @param img The image to mirror.
	 * @param orientation Horizontally or Vertically (0 or 1).
	 * @return The mirrored Image.
	 *****************************************************************/
	public Image mirrorImage(Image img, int orientation)
	{
		// The final image that will be returned as a mirror of the first.
		Image destImg;

		// BufferedImage used for the sake of compatibility.
		BufferedImage bufferedImageMirror;

		// Create a new AffineTransform object that will be used to mirror the image.
		AffineTransform transform = new AffineTransform();
		
		if (orientation == 0)
		{
			// Prepare to mirror.
			transform.scale(-1.0, 1.0);

			// Put into the right position.
			transform.translate(-(img.getWidth(null)), 0);
		}
		else if (orientation == 1)
		{
			// Prepare to mirror.
			transform.scale(1.0, -1.0);

			// Put into the right position.
			transform.translate(0, -(img.getHeight(null)));
		}
		
		// Create a BufferedImage of the right size.
		bufferedImageMirror = new BufferedImage(img.getWidth(null), img.getHeight(null), Transparency.TRANSLUCENT);

		// Draws the image of the material on the BufferedImage.
		bufferedImageMirror.getGraphics().drawImage(img, 0, 0, null);

		// Create the thing that will actually carry through with the transformation.
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

		// Transforms the material (mirrors it).
		bufferedImageMirror = op.filter(bufferedImageMirror, null); 

		// Sets the materialMirror Image variable as the BufferedImage created and transformed earlier.
		destImg = bufferedImageMirror;

		// Return the mirrored Image.
		return destImg;
	}
	
	/*****************************************************************
	 * Used for mirroring BufferedImages.
	 * 
	 * @param img The BufferedImage to be mirrored.
	 * @param orientation Horizontally or Vertically (0 or 1).
	 * @return The mirrored BufferedImage.
	 *****************************************************************/
	public BufferedImage mirrorImage(BufferedImage img, int orientation)
	{
		// The final image that will be returned as a mirror of the first.
		BufferedImage destImg;

		// BufferedImage used for the sake of compatibility.
		BufferedImage bufferedImageMirror;

		// Create a new AffineTransform object that will be used to mirror the image.
		AffineTransform transform = new AffineTransform();
		
		if (orientation == 0)
		{
			// Prepare to mirror.
			transform.scale(-1.0, 1.0);

			// Put into the right position.
			transform.translate(-(img.getWidth()), 0);
		}
		else if (orientation == 1)
		{
			// Prepare to mirror.
			transform.scale(1.0, -1.0);

			// Put into the right position.
			transform.translate(0, -(img.getHeight()));
		}
		
		// Create a BufferedImage of the right size.
		bufferedImageMirror = new BufferedImage(img.getWidth(), img.getHeight(), Transparency.TRANSLUCENT);

		// Draws the image of the material on the BufferedImage.
		bufferedImageMirror.getGraphics().drawImage(img, 0, 0, null);

		// Create the thing that will actually carry through with the transformation.
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

		// Transforms the material (mirrors it).
		bufferedImageMirror = op.filter(bufferedImageMirror, null); 

		// Sets the materialMirror Image variable as the BufferedImage created and transformed earlier.
		destImg = bufferedImageMirror;

		// Return the mirrored Image.
		return destImg;
	}
}
