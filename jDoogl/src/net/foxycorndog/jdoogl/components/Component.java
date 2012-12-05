package net.foxycorndog.jdoogl.components;

import java.util.ArrayList;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame.Alignment;
import net.foxycorndog.jdoogl.fonts.Font;
import net.foxycorndog.jdoogl.listeners.ActionListener;
import net.foxycorndog.jdoogl.listeners.KeyListener;
import net.foxycorndog.jdoogl.listeners.MouseListener;

public abstract class Component
{
	private boolean   hovering;
	private boolean   focused;
	
	private float     scaleWidth, scaleHeight;
	private float     x, y;
	private float     screenX, screenY;
	private float     offsetX, offsetY;
	
	private int       width, height;
	
	private int       horizontalAlignment, verticalAlignment;
	
	private ArrayList<ActionListener> actionListeners;
	private ArrayList<MouseListener>  mouseListeners;
	private ArrayList<KeyListener>    keyListeners;
	
//	public static enum Alignment
//	{
//		LEFT, RIGHT, CENTER, TOP, BOTTOM;
//	}
	
	public Component()
	{
		actionListeners = new ArrayList<ActionListener>();
		mouseListeners  = new ArrayList<MouseListener>();
		keyListeners    = new ArrayList<KeyListener>();
	}
	
	public void render()
	{
		double renderLoc[] = GL.getRenderLocation();
		double scaled[]    = GL.getAmountScaled();
		
		this.screenX       = (float)(x + renderLoc[0]);
		this.screenY       = (float)(y + renderLoc[1]);
		
		this.scaleWidth    = (float)scaled[0];
		this.scaleHeight   = (float)scaled[1];
		
		refreshAlignment();
		
		screenX += offsetX;
		screenY += offsetY;
		
//		if (horizontalAlignment != null)
//		{
//			if (horizontalAlignment == Alignment.CENTER)
//			{
//				screenX += (Frame.getWidth()) / 2 - (getScreenWidth() / 2);
//			}
//			else if (horizontalAlignment == Alignment.RIGHT)
//			{
//				
//			}
//		}
//		if (verticalAlignment != null)
//		{
//			if (verticalAlignment == Alignment.CENTER)
//			{
//				screenY += (Frame.getHeight()) / 2 - (getScreenHeight() / 2) + Frame.getNormalFontHeight() * scaleHeight;
//			}
//			else if (verticalAlignment == Alignment.TOP)
//			{
//				
//			}
//		}
	}
	
	public void setSize(int width, int height)
	{
		this.width  = width;
		this.height = height;
	}
	
	public void setLocation(float x, float y)
	{
		setX(x);
		setY(y);
	}
	
	protected void setScreenLocation(float x, float y)
	{
		setScreenX(x);
		setScreenY(y);
	}
	
	public void setOffsetLocation(float x, float y)
	{
		setOffsetX(x);
		setOffsetY(y);
	}
	
	public void move(float dx, float dy)
	{
		this.x += dx;
		this.y += dy;
	}
	
	private void refreshAlignment()
	{
		if (width == 0 || height == 0)
		{
			return;
		}
		
		if (horizontalAlignment == Font.CENTER)
		{
			offsetX = (Frame.getWidth() / 2) - (getWidth() / 2);
		}
		else if (horizontalAlignment == Font.RIGHT)
		{
			
		}
		
		if (verticalAlignment == Font.CENTER)
		{
			offsetY = (Frame.getHeight()) / 2 - (getHeight() / 2) + Frame.getNormalFontHeight() * scaleHeight;
		}
		else if (verticalAlignment == Font.TOP)
		{
			
		}
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public float getX()
	{
		return x;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
//	public void setOffsets()
//	{
//		double offsets[] = GL.getRenderLocation();//GL.getAmountTranslated();
//		
//		offsetX = (float)offsets[0];
//		offsetY = (float)offsets[1];
//	}
	
	public float getOffsetX()
	{
		return offsetX;
	}
	
	public void setOffsetX(float x)
	{
		this.offsetX = x;
	}
	
	public float getOffsetY()
	{
		return offsetY;
	}
	
	public void setOffsetY(float y)
	{
		this.offsetY = y;
	}
	
	public float getScreenX()
	{
		return screenX;// + offsetX;
	}
	
	protected void setScreenX(float x)
	{
		this.screenX = x;
	}
	
	public float getScreenY()
	{
		return screenY;// + offsetY;
	}
	
	protected void setScreenY(float y)
	{
		this.screenY = y;
	}
	
	public int getMouseX()
	{
		return (int)(screenX);
	}
	
	public int getMouseY()
	{
		//return (int)(Frame.getHeight() - screenY - 1);
		return (int)(screenY);
	}
	
	public int getScreenWidth()
	{
		return (int)(width * scaleWidth);
	}
	
	public int getScreenHeight()
	{
		return (int)(height * scaleHeight);
	}
	
	public float getScaleWidth()
	{
		return scaleWidth;
	}
	
	public float getScaleHeight()
	{
		return scaleHeight;
	}
	
	public boolean isHovering()
	{
		return hovering;
	}
	
	public void setHovering(boolean hovering)
	{
		this.hovering = hovering;
	}
	
	public boolean isFocused()
	{
		return focused;
	}
	
	public void setFocused(boolean focused)
	{
		if (focused && !this.focused)
		{
			Frame.setFocused(this, focused);
		}
		
		this.focused = focused;
	}
	
	public int getHorizontalAlignment()
	{
		return horizontalAlignment;
	}
	
	public void setHorizontalAlignment(int alignment)
	{
		this.horizontalAlignment = alignment;
	}
	
	public int getVerticalAlignment()
	{
		return verticalAlignment;
	}
	
	public void setVerticalAlignment(int alignment)
	{
		this.verticalAlignment = alignment;
	}
	
	public void setAlignment(int horizontal, int vertical)
	{
		this.horizontalAlignment = horizontal;
		this.verticalAlignment   = vertical;
	}
	
	public ArrayList<ActionListener> getActionListeners()
	{
		return actionListeners;
	}
	
	public void addActionListener(ActionListener actionListener)
	{
		actionListeners.add(actionListener);
	}
	
	public void removeActionListener(ActionListener actionListener)
	{
		actionListeners.remove(actionListener);
	}
	
	public ArrayList<MouseListener> getMouseListeners()
	{
		return mouseListeners;
	}
	
	public void addActionListener(MouseListener mouseListener)
	{
		mouseListeners.add(mouseListener);
	}
	
	public void removeActionListener(MouseListener mouseListener)
	{
		mouseListeners.remove(mouseListener);
	}
	
	public ArrayList<KeyListener> getKeyListeners()
	{
		return keyListeners;
	}
	
	public void addKeyListener(KeyListener KeyListener)
	{
		keyListeners.add(KeyListener);
	}
	
	public void removeKeyListener(KeyListener KeyListener)
	{
		keyListeners.remove(KeyListener);
	}
}