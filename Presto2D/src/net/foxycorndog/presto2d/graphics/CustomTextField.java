package net.foxycorndog.presto2d.graphics;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

/*********************************************************************
 * 
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class CustomTextField extends JPanel implements MouseListener, KeyListener
{
	String text = "";
	int carrotPos = 0;
	CustomFont costumFont = null;
	private int scale = 0;
	boolean blink = true;
	boolean active = false;
	//int[] pixels;
	//BufferedImage tex;
	int leftMargin = 3, topMargin = 3;
	int bgcolor = 0xffdfdfdf;
	int fgcolor = 0xff000000;
	int borderColor = 0xff000000;
	int characterLimit;
//	PixelGraphics pg;
	
	public CustomTextField()
	{
		super();
		
//		pg = new PixelGraphics();
		
		super.addMouseListener(this);
		super.addKeyListener(this);
		
		Timer timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run()
			{
				if (active)
				{
					blink = !blink;
				}
			}
		}, 0, 500);
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void setMargins(int left, int right, int top, int bottom)
	{
		leftMargin = left;
		topMargin = top;
	}
	
	@Override
	public void setSize(int w, int h)
	{
		super.setSize(w, h);
		
//		tex = new BufferedImage((w - leftMargin - rightMargin), (h - topMargin - bottomMargin), BufferedImage.BITMASK);
//		pixels = ((DataBufferInt) tex.getRaster().getDataBuffer()).getData();
	}
	
	@Override
	public void setPreferredSize(Dimension d)
	{
		super.setPreferredSize(d);
		
//		tex = new BufferedImage((d.width - leftMargin - rightMargin), (d.height - topMargin - bottomMargin), BufferedImage.BITMASK);
//		pixels = ((DataBufferInt) tex.getRaster().getDataBuffer()).getData();
	}
	
	@Override
	public void setMaximumSize(Dimension d)
	{
		super.setMaximumSize(d);
		
//		tex = new BufferedImage((d.width - leftMargin - rightMargin), (d.height - topMargin - bottomMargin), BufferedImage.BITMASK);
//		pixels = ((DataBufferInt) tex.getRaster().getDataBuffer()).getData();
	}
	
	public void setCharacterLimit(int limit)
	{
		characterLimit = limit;
	}
	
	@Override
	public void setMinimumSize(Dimension d)
	{
		super.setMinimumSize(d);
		
//		tex = new BufferedImage((d.width - leftMargin - rightMargin), (d.height - topMargin - bottomMargin), BufferedImage.BITMASK);
//		pixels = ((DataBufferInt) tex.getRaster().getDataBuffer()).getData();
	}
	
	@Override
	public void setSize(Dimension d)
	{
		super.setSize(d);
		
//		tex = new BufferedImage((d.width - leftMargin - rightMargin), (d.height - topMargin - bottomMargin), BufferedImage.BITMASK);
//		pixels = ((DataBufferInt) tex.getRaster().getDataBuffer()).getData();
	}
	
	public int getLeftMargin()
	{
		return leftMargin;
	}
	
	public int getTopMargin()
	{
		return topMargin;
	}
	
	public void requestTypingFocus()
	{
		super.requestFocus();
		active = true;
		blink = true;
	}
	
	public void removeTypingFocus()
	{
		active = false;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
		
		carrotPos = text.length() * ((costumFont.getLetterWidth() * scale) + scale);
	}
	
	public void setCustomFont(CustomFont costumFont, boolean bestFit)
	{
		this.costumFont = costumFont;
		
		if (bestFit)
		{
			scale = (getHeight() - 6) / costumFont.getLetterHeight();
			scale = scale >= 1 ? scale : 1;
		}
		else
		{
			scale = 1;
		}
	}
	
	public void setCustomFont(CustomFont costumFont, int scale)
	{
		this.costumFont = costumFont;
		
		this.scale = scale;
	}
	
	public void setBackgroundColor(int color)
	{
		bgcolor = color;
	}
	
	public void setForegroundColor(int color)
	{
		fgcolor = color;
	}
	
	public void setBorderColor(int color)
	{
		borderColor = color;
	}
	
	public void render(int pixels[], int width)
	{
		PixelGraphics pg = new PixelGraphics(pixels, width);
		
		for (int y = 0; y < getHeight(); y ++)
		{
			for (int x = 0; x < getWidth(); x ++)
			{
//				int xx = x + getX();
//				int yy = y + getY();
				
				pg.fillRect(0, 0, getWidth(), getHeight(), bgcolor);
			}
		}
		
		if (text.length() > 0)
		{
			if (costumFont != null)
			{
				costumFont.draw(text, scale, leftMargin + getX(), topMargin + getY(), getWidth() - leftMargin, getHeight(), pixels, width, fgcolor, CustomFont.NONE, CustomFont.NONE);
			}
		}
		
		if (active && blink && costumFont != null)
		{
			pg.fillRect(carrotPos + leftMargin + getX(), topMargin + getY(), 1, scale * costumFont.getLetterHeight(), fgcolor);
		}
		
		pg.drawRect(getX(), getY(), getWidth(), getHeight(), borderColor);
	}
	
	public void moveCarrot(String direction)
	{
		if (direction.equalsIgnoreCase("left"))
		{
			if (costumFont != null)
			{
				carrotPos -= scale + (costumFont.getLetterWidth() * scale);
			}
		}
		else if (direction.equalsIgnoreCase("right"))
		{
			if (costumFont != null)
			{
				carrotPos += scale + (costumFont.getLetterWidth() * scale);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		System.out.println("Clicked");
		
		requestTypingFocus();
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyChar();
		if (key >= 32 && key <= 126)
		{
			if (text.length() >= characterLimit) return;
			
			text += (char)key;
			moveCarrot("right");
		}
		else if (key == 8)
		{
			if (text.length() > 0)
			{
				text = text.substring(0, text.length() - 1);
				moveCarrot("left");
			}
		}
		else if (key == 127)
		{
			//delete button
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}
}
