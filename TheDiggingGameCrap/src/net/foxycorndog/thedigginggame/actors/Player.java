package net.foxycorndog.thedigginggame.actors;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import net.foxycorndog.presto2d.graphics.PixelGraphics;
import net.foxycorndog.presto2d.graphics.PixelPanel;
import net.foxycorndog.thedigginggame.Display;
import net.foxycorndog.thedigginggame.TheDiggingGame;

public class Player extends Actor
{
	private Image         bodyPartImage [][]   = new Image        [6][6];
	private BufferedImage bodyPart      [][]   = new BufferedImage[6][6];
	private int           bodyPartPixels[][][] = new int          [6][6][];
	private Point         bodyPartOffset[][]   = new Point        [6][6];
	
	private static final int HEAD = 0, TORSO = 1, LEFT_ARM = 2,
			RIGHT_ARM = 3, LEFT_LEG = 4, RIGHT_LEG = 5;
	
	public Player(int x, int y, int scale, TheDiggingGame tdg)
	{
		//   |x||y||w ||h| |                 JumpHeight                          ||scale||tdg|
		super(x, y, 16, 32, tdg.getDisplay().getBlockShowSize() + (int)(tdg.getDisplay().getBlockShowSize() / 1.1f), scale, tdg);
		
		setFacing(FRONT);
		setOldFacing(getFacing());
		
		BufferedImage usertmp = null;

		String loc = "res/images/character/skins/Default.png";
		
		try
		{
			if (TheDiggingGame.applet)
			{
				ClassLoader cl = Thread.currentThread().getContextClassLoader();
				URL url = cl.getResource("" + loc);
				usertmp = ImageIO.read(url);
			}
			else
			{
				usertmp = ImageIO.read(new File(loc));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		bodyPartImage[HEAD][FRONT]       = usertmp.getSubimage(8, 8, 8, 8);
		bodyPartImage[HEAD][TOP]         = usertmp.getSubimage(8, 0, 8, 8);
		bodyPartImage[HEAD][BOTTOM]      = usertmp.getSubimage(16, 0, 8, 8);
		bodyPartImage[HEAD][LEFT]        = usertmp.getSubimage(16, 8, 8, 8);
		bodyPartImage[HEAD][RIGHT]       = usertmp.getSubimage(0, 8, 8, 8);
		bodyPartImage[HEAD][BACK]        = usertmp.getSubimage(24, 8, 8, 8);

		bodyPartImage[TORSO][FRONT]      = usertmp.getSubimage(20, 20, 8, 12);
		bodyPartImage[TORSO][TOP]        = usertmp.getSubimage(20, 16, 8, 4);
		bodyPartImage[TORSO][BOTTOM]     = usertmp.getSubimage(28, 16, 8, 4);
		bodyPartImage[TORSO][LEFT]       = usertmp.getSubimage(28, 20, 4, 12);
		bodyPartImage[TORSO][RIGHT]      = usertmp.getSubimage(16, 20, 4, 12);
		bodyPartImage[TORSO][BACK]       = usertmp.getSubimage(32, 20, 8, 12);

		bodyPartImage[RIGHT_ARM][FRONT]  = usertmp.getSubimage(44, 20, 4, 12);
		bodyPartImage[RIGHT_ARM][TOP]    = usertmp.getSubimage(44, 16, 4, 4);
		bodyPartImage[RIGHT_ARM][BOTTOM] = usertmp.getSubimage(48, 16, 4, 4);
		bodyPartImage[RIGHT_ARM][LEFT]   = usertmp.getSubimage(48, 20, 4, 12);
		bodyPartImage[RIGHT_ARM][RIGHT]  = usertmp.getSubimage(40, 20, 4, 12);
		bodyPartImage[RIGHT_ARM][BACK]   = usertmp.getSubimage(52, 20, 4, 12);
		
		bodyPartImage[LEFT_ARM][FRONT]   = usertmp.getSubimage(44, 20, 4, 12);
		bodyPartImage[LEFT_ARM][TOP]     = usertmp.getSubimage(44, 16, 4, 4);
		bodyPartImage[LEFT_ARM][BOTTOM]  = usertmp.getSubimage(48, 16, 4, 4);
		bodyPartImage[LEFT_ARM][LEFT]    = usertmp.getSubimage(48, 20, 4, 12);
		bodyPartImage[LEFT_ARM][RIGHT]   = usertmp.getSubimage(40, 20, 4, 12);
		bodyPartImage[LEFT_ARM][BACK]    = usertmp.getSubimage(52, 20, 4, 12);

		bodyPartImage[RIGHT_LEG][FRONT]  = usertmp.getSubimage(4, 20, 4, 12);
		bodyPartImage[RIGHT_LEG][TOP]    = usertmp.getSubimage(4, 8, 4, 4);
		bodyPartImage[RIGHT_LEG][BOTTOM] = usertmp.getSubimage(8, 8, 4, 4);
		bodyPartImage[RIGHT_LEG][LEFT]   = usertmp.getSubimage(8, 20, 4, 12);
		bodyPartImage[RIGHT_LEG][RIGHT]  = usertmp.getSubimage(0, 20, 4, 12);
		bodyPartImage[RIGHT_LEG][BACK]   = usertmp.getSubimage(12, 20, 4, 12);
		
		bodyPartImage[LEFT_LEG][FRONT]   = usertmp.getSubimage(4, 20, 4, 12);
		bodyPartImage[LEFT_LEG][TOP]     = usertmp.getSubimage(4, 8, 4, 4);
		bodyPartImage[LEFT_LEG][BOTTOM]  = usertmp.getSubimage(8, 8, 4, 4);
		bodyPartImage[LEFT_LEG][LEFT]    = usertmp.getSubimage(8, 20, 4, 12);
		bodyPartImage[LEFT_LEG][RIGHT]   = usertmp.getSubimage(0, 20, 4, 12);
		bodyPartImage[LEFT_LEG][BACK]    = usertmp.getSubimage(12, 20, 4, 12);
		
		
		
		bodyPartOffset[HEAD][FRONT]       = new Point(4, 0);
		bodyPartOffset[HEAD][TOP]         = new Point(0, 0);
		bodyPartOffset[HEAD][BOTTOM]      = new Point(0, 0);
		bodyPartOffset[HEAD][LEFT]        = new Point(4, 0);
		bodyPartOffset[HEAD][RIGHT]       = new Point(4, 0);
		bodyPartOffset[HEAD][BACK]        = new Point(4, 0);
		
		bodyPartOffset[TORSO][FRONT]      = new Point(4, 8);
		bodyPartOffset[TORSO][TOP]        = new Point(0, 0);
		bodyPartOffset[TORSO][BOTTOM]     = new Point(0, 0);
		bodyPartOffset[TORSO][LEFT]       = new Point(6, 8);
		bodyPartOffset[TORSO][RIGHT]      = new Point(6, 8);
		bodyPartOffset[TORSO][BACK]       = new Point(4, 8);
		
		bodyPartOffset[RIGHT_ARM][FRONT]  = new Point(12, 8);
		bodyPartOffset[RIGHT_ARM][TOP]    = new Point(0, 0);
		bodyPartOffset[RIGHT_ARM][BOTTOM] = new Point(0, 0);
		bodyPartOffset[RIGHT_ARM][LEFT]   = new Point(6, 8);
		bodyPartOffset[RIGHT_ARM][RIGHT]  = new Point(6, 8);
		bodyPartOffset[RIGHT_ARM][BACK]   = new Point(12, 8);
		
		bodyPartOffset[LEFT_ARM][FRONT]   = new Point(0, 8);
		bodyPartOffset[LEFT_ARM][TOP]     = new Point(0, 0);
		bodyPartOffset[LEFT_ARM][BOTTOM]  = new Point(0, 0);
		bodyPartOffset[LEFT_ARM][LEFT]    = new Point(6, 8);
		bodyPartOffset[LEFT_ARM][RIGHT]   = new Point(6, 8);
		bodyPartOffset[LEFT_ARM][BACK]    = new Point(0, 8);
		
		bodyPartOffset[RIGHT_LEG][FRONT]  = new Point(8, 20);
		bodyPartOffset[RIGHT_LEG][TOP]    = new Point(0, 0);
		bodyPartOffset[RIGHT_LEG][BOTTOM] = new Point(0, 0);
		bodyPartOffset[RIGHT_LEG][LEFT]   = new Point(6, 20);
		bodyPartOffset[RIGHT_LEG][RIGHT]  = new Point(6, 20);
		bodyPartOffset[RIGHT_LEG][BACK]   = new Point(8, 20);
		
		bodyPartOffset[LEFT_LEG][FRONT]   = new Point(4, 20);
		bodyPartOffset[LEFT_LEG][TOP]     = new Point(0, 0);
		bodyPartOffset[LEFT_LEG][BOTTOM]  = new Point(0, 0);
		bodyPartOffset[LEFT_LEG][LEFT]    = new Point(6, 20);
		bodyPartOffset[LEFT_LEG][RIGHT]   = new Point(6, 20);
		bodyPartOffset[LEFT_LEG][BACK]    = new Point(4, 20);
		
		
		
		for (int i = 0; i < bodyPart.length; i ++)
		{
			for (int j = 0; j < bodyPart[0].length; j ++)
			{
				bodyPart[i][j] = new BufferedImage(bodyPartImage[i][j].getWidth(null), bodyPartImage[i][j].getHeight(null), Transparency.BITMASK);
			}
		}
		
		Graphics g;
		
		for (int i = 0; i < bodyPart.length; i ++)
		{
			for (int j = 0; j < bodyPart[0].length; j ++)
			{
				g = bodyPart[i][j].createGraphics();
				g.drawImage(bodyPartImage[i][j], 0, 0, null);
				g.dispose();
			}
		}
		
		for (int i = 0; i < bodyPart.length; i ++)
		{
			for (int j = 0; j < bodyPart[0].length; j ++)
			{
				bodyPartPixels[i][j] = ((DataBufferInt) bodyPart[i][j].getRaster().getDataBuffer()).getData();
			}
		}
	}
	
	public void move(float dx, float dy)
	{
		float dx2 = calculateXOffset(dx);
		float dy2 = calculateYOffset(dy);
		
		if (dx == 0 && dy == 0) return;
		
		float oldx = getX();
		float oldy = getY();
		
		if ((dx2 > 0 && getX() + getWidth() >= getTdg().getDisplay().getWidth() - 100) || (dx2 < 0 && getX() <= 100) ||
				(dy2 > 0 && getY() + getHeight() >= getTdg().getDisplay().getHeight() - 100))// || (dy2 < 0 && getY() <= 100))
		{
			if (!getTdg().getMap().collided(this, dx2, dy2))
			{
				//System.out.println(getY() + ", " + getHeight());
				getTdg().getMap().move(-dx2, -dy2);
				getTdg().getDisplay().setMouseX(getTdg().getDisplay().getMouseX());
				getTdg().getDisplay().setMouseY(getTdg().getDisplay().getMouseY());
			}
		}
		else
		{
			super.move(dx, dy);
		}
	}
	
	public void clear(PixelPanel pixels)
	{
		if (!getRendered())
		{
			pixels.getPixelGraphics().fillRect((bodyPartOffset[HEAD][getOldFacing()].x      * getScale()) + (int)getOldRenderX(), (bodyPartOffset[HEAD][getOldFacing()].y      * getScale()) + (int)getOldRenderY(), bodyPart[HEAD][getOldFacing()].getWidth()      * getScale(), bodyPart[HEAD][getOldFacing()].getHeight()      * getScale(), 0x00000000);
			pixels.getPixelGraphics().fillRect((bodyPartOffset[TORSO][getOldFacing()].x     * getScale()) + (int)getOldRenderX(), (bodyPartOffset[TORSO][getOldFacing()].y     * getScale()) + (int)getOldRenderY(), bodyPart[TORSO][getOldFacing()].getWidth()     * getScale(), bodyPart[TORSO][getOldFacing()].getHeight()     * getScale(), 0x00000000);
			pixels.getPixelGraphics().fillRect((bodyPartOffset[LEFT_ARM][getOldFacing()].x  * getScale()) + (int)getOldRenderX(), (bodyPartOffset[LEFT_ARM][getOldFacing()].y  * getScale()) + (int)getOldRenderY(), bodyPart[LEFT_ARM][getOldFacing()].getWidth()  * getScale(), bodyPart[LEFT_ARM][getOldFacing()].getHeight()  * getScale(), 0x00000000);
			pixels.getPixelGraphics().fillRect((bodyPartOffset[RIGHT_ARM][getOldFacing()].x * getScale()) + (int)getOldRenderX(), (bodyPartOffset[RIGHT_ARM][getOldFacing()].y * getScale()) + (int)getOldRenderY(), bodyPart[RIGHT_ARM][getOldFacing()].getWidth() * getScale(), bodyPart[RIGHT_ARM][getOldFacing()].getHeight() * getScale(), 0x00000000);
			pixels.getPixelGraphics().fillRect((bodyPartOffset[LEFT_LEG][getOldFacing()].x  * getScale()) + (int)getOldRenderX(), (bodyPartOffset[LEFT_LEG][getOldFacing()].y  * getScale()) + (int)getOldRenderY(), bodyPart[LEFT_LEG][getOldFacing()].getWidth()  * getScale(), bodyPart[LEFT_LEG][getOldFacing()].getHeight()  * getScale(), 0x00000000);
			pixels.getPixelGraphics().fillRect((bodyPartOffset[RIGHT_LEG][getOldFacing()].x * getScale()) + (int)getOldRenderX(), (bodyPartOffset[RIGHT_LEG][getOldFacing()].y * getScale()) + (int)getOldRenderY(), bodyPart[RIGHT_LEG][getOldFacing()].getWidth() * getScale(), bodyPart[RIGHT_LEG][getOldFacing()].getHeight() * getScale(), 0x00000000);
		}
	}
	
	public void render(PixelPanel pixels)
	{
		if (!getRendered())
		{
			pixels.getPixelGraphics().drawPixels(bodyPartPixels[HEAD][getFacing()]     , bodyPart[HEAD][getFacing()].getWidth()     , (bodyPartOffset[HEAD][getFacing()].x      * getScale()) + (int)getX(), (bodyPartOffset[HEAD][getFacing()].y      * getScale()) + (int)getY(), getScale());
			pixels.getPixelGraphics().drawPixels(bodyPartPixels[TORSO][getFacing()]    , bodyPart[TORSO][getFacing()].getWidth()    , (bodyPartOffset[TORSO][getFacing()].x     * getScale()) + (int)getX(), (bodyPartOffset[TORSO][getFacing()].y     * getScale()) + (int)getY(), getScale());
			pixels.getPixelGraphics().drawPixels(bodyPartPixels[LEFT_ARM][getFacing()] , bodyPart[LEFT_ARM][getFacing()].getWidth() , (bodyPartOffset[LEFT_ARM][getFacing()].x  * getScale()) + (int)getX(), (bodyPartOffset[LEFT_ARM][getFacing()].y  * getScale()) + (int)getY(), getScale());
			pixels.getPixelGraphics().drawPixels(bodyPartPixels[RIGHT_ARM][getFacing()], bodyPart[RIGHT_ARM][getFacing()].getWidth(), (bodyPartOffset[RIGHT_ARM][getFacing()].x * getScale()) + (int)getX(), (bodyPartOffset[RIGHT_ARM][getFacing()].y * getScale()) + (int)getY(), getScale());
			pixels.getPixelGraphics().drawPixels(bodyPartPixels[LEFT_LEG][getFacing()] , bodyPart[LEFT_LEG][getFacing()].getWidth() , (bodyPartOffset[LEFT_LEG][getFacing()].x  * getScale()) + (int)getX(), (bodyPartOffset[LEFT_LEG][getFacing()].y  * getScale()) + (int)getY(), getScale());
			pixels.getPixelGraphics().drawPixels(bodyPartPixels[RIGHT_LEG][getFacing()], bodyPart[RIGHT_LEG][getFacing()].getWidth(), (bodyPartOffset[RIGHT_LEG][getFacing()].x * getScale()) + (int)getX(), (bodyPartOffset[RIGHT_LEG][getFacing()].y * getScale()) + (int)getY(), getScale());
			
			setOldRenderX(getX());
			setOldRenderY(getY());
			
			setOldFacing(getFacing());
			
			setRendered(true);
		}
	}
}