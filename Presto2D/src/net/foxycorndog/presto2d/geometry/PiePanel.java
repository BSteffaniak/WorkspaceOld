package net.foxycorndog.presto2d.geometry;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;

import javax.swing.JPanel;

/*********************************************************************
 * Created with help from "Sam's teach yourself java in 24 hours"
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class PiePanel extends JPanel
{
	private PieSlice[] slice;
	private int current = 0;
	private float /*totalSize = 0,*/ alpha = 1;
	//private Color background;
	
	public PiePanel(int sliceCount)
	{
		slice = new PieSlice[sliceCount];
	//	background = getBackground();
	}
	
	public void addSlice(Color sColor, float sSize)
	{
		if (current <= slice.length)
		{
			slice[current] = new PieSlice(sColor, sSize);
			//totalSize += sSize;
			current++;
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if (alpha > 0)
		{
			Graphics2D g2D = (Graphics2D)g;

			int width = getSize().width - 10;
			int height = getSize().height - 15;
			int xInset = 5;
			int yInset = 5;

			if (width < 5)
			{
				xInset = width;
			}
			if (height < 5)
			{
				yInset = height;
			}

		//	g2D.setColor(background);
			//g2D.fillRect(0, 0, getSize().width, getSize().height);
			g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			//g2D.setColor(Color.lightGray);
			
			//Ellipse2D.Float pie = new Ellipse2D.Float(xInset + getX(), yInset + getY(), width, height);
			//g2D.fill(pie);

			float start = 0;

			for (int i = 0; i < slice.length; i ++)
			{
				if (slice[i] != null)
				{
					float extent = slice[i].size * 360f;

					g2D.setColor(slice[i].color);

					Arc2D.Float drawSlice = new Arc2D.Float(xInset + getX(), yInset + getY(), width, height, start, extent, Arc2D.PIE);

					start += extent;

					g2D.fill(drawSlice);
				}
			}
		}
	}
	
	public void setSliceColor(int index, Color c)
	{
		slice[index].color = c;
	}
	
	public void setSliceSize(int index, int size)
	{
		slice[index].size = size;
	}
	
	public void setAlpha(float alpha)
	{
		this.alpha = alpha >= 0 ? alpha : this.alpha;
	}
}

class PieSlice
{
	Color color = Color.lightGray;
	float size = 0;
	
	PieSlice(Color pColor, float pSize)
	{
		color = pColor;
		size = pSize;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
}