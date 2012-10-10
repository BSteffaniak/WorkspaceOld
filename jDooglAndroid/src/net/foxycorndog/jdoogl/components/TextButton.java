package net.foxycorndog.jdoogl.components;

import java.util.ArrayList;

import net.foxycorndog.jdoogl.Color;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.components.Frame.Alignment;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.StringUtil;
import net.foxycorndog.jdoutil.VerticesBuffer;

public class TextButton extends Button
{
	private int         leftMargin, rightMargin, topMargin, bottomMargin;
	
	private float       scale;
	
	private Color       foreground, background;
	
	private String      text;
	
	private ToolTipText toolTipText;
	
	private Alignment   horizontalAlignment, verticalAlignment;
	
	private VerticesBuffer v;
	private LightBuffer    t;
	
	public TextButton(String text, float scale)
	{
		this.text       = text;
		
		this.scale      =  scale;
		
		this.foreground = Color.WHITE;
		this.background = Color.BLACK;
		
		v = new VerticesBuffer(4 * 2, 2);
		v.addData(GL.addRectVertexArrayf(0, 0, 10, 10, 0, null));
		
		t = new LightBuffer(4 * 2, 2);
		t.addData(GL.addRectTextureArrayf(GL.white, 0, null));
		
//		setWidth((int)((Frame.getFont().getLegitWidth(text)  + leftMargin + rightMargin) * scale));
//		setHeight((int)((Frame.getFont().getLegitHeight(text) + topMargin  + bottomMargin) * scale));
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
	
	public void setAlignment(Alignment horizontal, Alignment vertical)
	{
		this.horizontalAlignment = horizontal;
		this.verticalAlignment   = vertical;
	}
	
	public void render()
	{
		super.render();
		
		float location[] = Frame.renderText(getX(), getY(), text, foreground, scale, horizontalAlignment, verticalAlignment);
		
		GL.beginManipulation();
		{
			GL.translatef(getX(), getY(), 0);
			
			GL.renderQuad(v, t, GL.white);
		}
		GL.endManipulation();
		
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
//		
//		setScreenLocation(location[0], location[1]);
//		
//		setOffsets();
	}
	
	public void setToolTipText(ToolTipText tip)
	{
		tip.setScale(1);
		
		toolTipText = tip;
	}
}