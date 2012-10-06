package net.foxycorndog.presto2d.graphics;

import java.awt.Dimension;

import javax.swing.JButton;

/*********************************************************************
 * 
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class CustomButton extends JButton
{
	String text = "";
	CustomFont customFont = null;
	private int scale = 0;
	//int[] pixels;
	//BufferedImage tex;
	int bgcolor = 0xffdfdfdf;
	int fgcolor = 0xff000000;
	int borderColor = 0xff000000;
	int characterLimit;
//	PixelGraphics pg;
	
	public CustomButton()
	{
		super();
		
		init();
	}
	
	public void init()
	{
//		pg = new PixelGraphics(pixels, width);
	}
	
	public CustomButton(String text)
	{
		super();
		
		this.text = text;
		
		init();
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
	
	public void requestTypingFocus()
	{
		super.requestFocus();
	}
	
	@Override
	public String getText()
	{
		return text;
	}
	
	@Override
	public void setText(String text)
	{
		this.text = text;
	}
	
	public void setCustomFont(CustomFont costumFont, boolean bestFit)
	{
		this.customFont = costumFont;
		
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
		this.customFont = costumFont;
		
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
			if (customFont != null)
			{
				int wid = (customFont.getLetterWidth() * scale * text.length()) + (scale * text.length());
				int hei = customFont.getLetterHeight() * scale;
				
				customFont.draw(text, scale, getX() + (getWidth() / 2) - (wid / 2), getY() + (getHeight() / 2) - (hei / 2), getWidth(), getHeight(), pixels, width, fgcolor, CustomFont.NONE, CustomFont.NONE);
			}
		}
		
		pg.drawRect(getX(), getY(), getWidth(), getHeight(), borderColor);
	}
	
	/*@Override
	public void mouseClicked(MouseEvent e)
	{
		System.out.println("Click");
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
		int code = e.getKeyChar();
		
		if (code == KeyEvent.VK_ENTER)
		{
			Main.m.paintingBoard.color = Integer.decode(Main.m.colorChoose.getText());
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}*/
}
