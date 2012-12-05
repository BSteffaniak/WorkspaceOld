package net.foxycorndog.jdoogl.components;

import java.util.ArrayList;

import net.foxycorndog.jdoogl.Color;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.fonts.Font;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.StringUtil;
import net.foxycorndog.jdoutil.VerticesBuffer;

public class ToolTipText extends Component
{
	private int    leftMargin, rightMargin, topMargin, bottomMargin;
	private int    linePadding;
	
	private float  scale;

	private VerticesBuffer vertices;
	private LightBuffer    textures;
	
	private Font           font;
	
	private String text[];
	
	public ToolTipText(String text, Font font)
	{
		this.text = StringUtil.splitBetweenOccurrence(text, '\n');
		
		this.font = font;
		
		init();
	}
	
	public ToolTipText(String text[])
	{
		this.text = text;
		
		init();
	}
	
	private void init()
	{
//		scale = 1;
		
		vertices = new VerticesBuffer(4 * 3, 3);
		textures = new LightBuffer(4 * 2);
		
		int longestLine = 0;
		
		int tipHeight   = 0;
		
		for (String st : text)
		{
			int length  = 0;
			
			if ((length = font.getWidth(st)) > longestLine)
			{
				longestLine = length;
			}
			
			tipHeight += font.getGlyphHeight();
		}
		
		this.linePadding = 2;
		
		setMargins(4, 4, 4, 4);
		
		setWidth(leftMargin + rightMargin + longestLine);
		setHeight(topMargin + bottomMargin + tipHeight + linePadding * (text.length - 1));
		
		vertices.setData(0, GL.addRectVertexArrayf(0, 0, 0, getWidth(), getHeight(), 0, null));
		textures.setData(0, GL.addRectTextureArrayf(GL.white, 0, null));
	}
	
	private void calculateLines()
	{
		ArrayList<String> text2 = new ArrayList<String>();
		
		for (String tet : text)
		{
			int beginIndex = 0;
			
			for (int i = 0; i < tet.length(); i ++)
			{
				if (getScreenX() + leftMargin * scale + Frame.getFont().getLegitWidth(tet.substring(beginIndex, i)) * scale >= Frame.getWidth() - leftMargin * scale)
				{
					text2.add(tet.substring(beginIndex, i - 1));
					
					beginIndex = i;
				}
			}
			
			text2.add(tet.substring(beginIndex, tet.length()));
		}
		
		text = new String[text2.size()];
		
		for (int i = 0; i < text.length; i ++)
		{
			text[i] = text2.get(i);
		}
	}
	
	public void render()
	{
		GL.beginManipulation();
		{
			GL.translatef(getX(), getY(), 0);
			
			GL.scalef(scale, scale, 1);
			
			for (int i = 0; i < text.length; i ++)
			{
				font.render(text[i], leftMargin, topMargin + ((Frame.getFont().getHeight(text[i]) + linePadding) * i), 1);
			}
			
//			setOffsets();
		}
		GL.endManipulation();
		
		GL.beginManipulation();
		{
			double amountScaled[] = GL.getAmountScaled();
			
			double x = amountScaled[0];
			double y = amountScaled[1];
			
			float xoff = Frame.getCenterX() - (640 / 2);
			
			GL.translated(getX(), -GL.getAmountTranslated()[1] + Frame.getHeight() - getScreenY() - getHeight() * scale, 0);
			
			GL.scalef(scale, scale, 1);
			
			GL.setColori(0, 0, 0, 255);
			
			GL.renderQuads(vertices, textures, GL.white, 0, 1);
			
			GL.setColorf(1, 1, 1, 1);
		}
		GL.endManipulation();
	}
	
	public void setX(float x)
	{
		x = x + getWidth() * scale >= Frame.getWidth() ? Frame.getWidth() - getWidth() * scale : x;
		x = x < -getOffsetX() ? -getOffsetX() : x;
		
		super.setX(x);
	}
	
	public void setY(float y)
	{
		
		
		super.setY(y);
	}
	
	public void setScale(float scale)
	{
		this.scale = scale;
		
//		calculateLines();
	}
	
	public float getScale()
	{
		return scale;
	}
	
	private void setMargins(int top, int left, int bottom, int right)
	{
		this.topMargin    = top;
		this.leftMargin   = left;
		this.bottomMargin = bottom;
		this.rightMargin  = right;
	}
}