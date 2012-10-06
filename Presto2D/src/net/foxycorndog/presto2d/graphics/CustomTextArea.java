package net.foxycorndog.presto2d.graphics;

import java.awt.Graphics;
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
public class CustomTextArea extends JPanel implements MouseListener, KeyListener
{
	String text = "";
	int carrotPos = 0;
	CustomFont costumFont = null;
	private int scale = 0;
	boolean blink = true;
	boolean active = false;
	int leftMargin = 3, rightMargin = 3, topMargin = 3, bottomMargin = 3;
	
	public CustomTextArea()
	{
		super();
		
		super.addMouseListener(this);
		
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
	
	public void setMargins(int left, int right, int top, int bottom)
	{
		leftMargin = left;
		rightMargin = right;
		topMargin = top;
		bottomMargin = bottom;
	}
	
	public int getLeftMargin()
	{
		return leftMargin;
	}
	
	public int getRightMargin()
	{
		return rightMargin;
	}
	
	public int getTopMargin()
	{
		return topMargin;
	}
	
	public int getBottomMargin()
	{
		return bottomMargin;
	}
	
	public void requestTypingFocus()
	{
		super.addKeyListener(this);
		active = true;
		blink = true;
	}
	
	public void removeTypingFocus()
	{
		super.removeKeyListener(this);
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
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.clearRect(0, 0, getWidth(), getHeight());
		
		g.setColor(getForeground());
		
		if (text.length() > 0)
		{
			if (costumFont != null)
			{
		//		costumFont.draw(text, scale, leftMargin, topMargin, g, 0x111111);
			}
		}
		
		if (active && blink && costumFont != null)
		{
			g.fillRect(carrotPos + leftMargin, topMargin, 1, scale * costumFont.getLetterHeight());
		}
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
				carrotPos = carrotPos % getWidth();
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
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}
}
