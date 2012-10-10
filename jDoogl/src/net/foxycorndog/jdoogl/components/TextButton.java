package net.foxycorndog.jdoogl.components;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import net.foxycorndog.jdoogl.Color;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.components.Frame.Alignment;
import net.foxycorndog.jdoutil.StringUtil;

public class TextButton extends Button
{
	private int         leftMargin, rightMargin, topMargin, bottomMargin;
	
	private float       scale;
	
	private Color       foreground, background;
	
	private String      text;
	
	private ToolTipText toolTipText;
	
	public TextButton(String text, float scale)
	{
		this.text       = text;
		
		this.scale      =  scale;
		
		this.foreground = Color.WHITE;
		this.background = Color.BLACK;
		
		setWidth((int)((Frame.getFont().getLegitWidth(text)  + leftMargin + rightMargin)));
		setHeight((int)((Frame.getNormalFontHeight() + topMargin  + bottomMargin)));
	}
	
	public void setMargins(int left, int right, int top, int bottom)
	{
		leftMargin   = left;
		rightMargin  = right;
		topMargin    = top;
		bottomMargin = bottom;
	}
	
	public void setMargins(int leftRight, int topBottom)
	{
		leftMargin   = leftRight;
		rightMargin  = leftRight;
		topMargin    = topBottom;
		bottomMargin = topBottom;
	}
	
	public void setMargins(int margin)
	{
		leftMargin   = margin;
		rightMargin  = margin;
		topMargin    = margin;
		bottomMargin = margin;
	}
	
	public void setBackground(Color color)
	{
		background = color;
	}
	
	public void setForeground(Color color)
	{
		foreground = color;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public void setScale(int scale)
	{
		this.scale = scale;
	}
	
	public void setX(float x)
	{
		super.setX(x);
		
		if (toolTipText != null)
		{
			toolTipText.setX(x);
		}
	}
	
	public void setY(float y)
	{
		super.setY(y);
		
		if (toolTipText != null)
		{
			toolTipText.setY(y + getHeight());
		}
	}
	
	public void render()
	{
		GL.beginManipulation();
		{
			GL.scalef(scale, scale, 1);
			
			super.render();
		}
		GL.endManipulation();
		
		float location[] = Frame.renderText(getX(), getY(), text, foreground, scale, getHorizontalAlignment(), getVerticalAlignment());
		
//		if (isMouseOver())
//		{
//			if (toolTipText != null)
//			{
//				float tipX = Mouse.getX() - getOffsetX();
//				float tipY = Frame.getHeight() - Mouse.getY() - getOffsetY() + 30;
//				
//				toolTipText.setX(tipX);//getX());
//				toolTipText.setY(tipY);//getY() + getHeight());
//				
//				toolTipText.render();
//			}
//		}
		
//		setScreenLocation();//location[0], location[1]);
		
//		setOffsets();
	}
	
	public void setToolTipText(ToolTipText tip)
	{
		tip.setScale(1);
		
		toolTipText = tip;
	}
}