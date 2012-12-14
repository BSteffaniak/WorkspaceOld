package net.foxycorndog.glshaderide.components;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class CodeField
{
	private int tabCount;
	
	private StyledText textField;
	
	private ArrayList<ArrayList<Boolean>> tabs;
	
	public CodeField(Display display, Composite comp)
	{
		textField = new StyledText(comp, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		textField.setText("");
		textField.setBounds(new Rectangle(0, 0, 100, 100));
	    textField.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, true, 1, 1));
	    
	    FontData fontData[] = textField.getFont().getFontData();
	    fontData[0].setHeight(16);
	    
	    textField.setFont(new Font(display, fontData[0]));
	    
	    tabs = new ArrayList<ArrayList<Boolean>>();
	    tabs.add(new ArrayList<Boolean>());
	    textField.setTabs(4);
	    textField.addKeyListener(new KeyListener()
	    {
			@Override
			public void keyPressed(KeyEvent e)
			{
				int caretPosition = getCaretPosition();
				
				// If enter pressed
				if (e.character == 13)
				{//textField.setSelection(textField.getText().length() - 2);
					tabs.add(getCaretLineNumber(), new ArrayList<Boolean>());
					
					String tabsStr = "";
					
					int tabsCount = previousLineTabCount();
					
					System.out.println("Tabs count: " + tabsCount);
					
					for (int i = 0; i < tabsCount; i ++)
					{
						tabsStr += "\t";
						tabs.get(getCaretLineNumber()).add(true);
					}

					int length = textField.getText().length();
					
					textField.setText(textField.getText().substring(0, caretPosition - 1) + tabsStr + textField.getText().substring(caretPosition - 0));
					
					textField.setCaretOffset(caretPosition + tabsStr.length() - 1);
				}
				// If backspace pressed
				else if (e.character == 8)
				{
					if (getCaretXPosition() > 0)
					{
						System.out.println("BEfore: " + getCaretLineNumber() + ", " + getCaretXPosition());
						tabs.get(getCaretLineNumber()).remove(getCaretXPosition() - 1);
					}
				}
				else if (e.character == '\t')
				{
					tabs.get(getCaretLineNumber()).add(lastCharacterIsTab(1));
				}
				else
				{
					if (isPrintable(e.character))
					{
						tabs.get(getCaretLineNumber()).add(false);
					}
					else
					{
						
					}
				}
				
				System.out.println("L:" + getCaretLineNumber() + ", X:" + getCaretXPosition() + ", P:" + getCaretPosition());
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				
			}
	    });
	    
//	    textField.addLineStyleListener(new LineStyleListener()
//	    {
//			@Override
//			public void lineGetStyle(LineStyleEvent e)
//			{
//				e.bulletIndex = textField.getLineAtOffset(e.lineOffset);
//				
//				String count = textField.getLineCount() - 1 + "";
//				
//				String prefix = "";
//				
//				if (count.length() == 1)
//				{
//					prefix = "0";
//				}
//				
//				StyleRange style = new StyleRange();
//				style.metrics = new GlyphMetrics(0, 0, (prefix.length() + count.length()) * 12);
//				
//				Bullet bullet = new Bullet(ST.BULLET_TEXT, style);
//				
//				bullet.text = prefix + (textField.getLineCount() - 1) + ".";
//				
//				e.bullet = bullet;
//			}
//	    });
	}
	
	private int getCaretLineNumber()
	{
		return textField.getLineAtOffset(getCaretPosition());
	}
	
	private int getCaretPosition()
	{
		return textField.getCaretOffset();
	}
	
	private int getCaretXPosition()
	{
		int xPos = 0;
		int countedCharacters = 0;
		
		for (int i = 0; i < tabs.size(); i ++)
		{
			if (i == getCaretLineNumber())// || countedCharacters >= getCaretPosition())
			{
				xPos = countedCharacters - (getCaretPosition()) + i;
				xPos = Math.abs(xPos);
				
				break;
			}
			
			countedCharacters += tabs.get(i).size();
		}
		
		return xPos;
	}
	
	private char lastCharacter(int charactersBack)
	{
		int textLength = textField.getText().length();
		
		if (textLength >= charactersBack)
		{
			return textField.getText().charAt(textLength - charactersBack);
		}
		
		return 0;
	}
	
	private boolean lastCharacterIsTab(int charactersBack)
	{
		int size = tabs.get(getCaretLineNumber()).size();
		
		if (size >= charactersBack)
		{
			return tabs.get(getCaretLineNumber()).get(size - charactersBack);
		}
		else if (size == 0)
		{
			return true;
		}
		
		return false;
	}
	
	private int previousLineTabCount()
	{
		int size    = tabs.get(getCaretLineNumber() - 1).size();
		int tabsNum = 0;
		
		for (int i = size - 1; i >= 0; i --)
		{
			if (tabs.get(getCaretLineNumber() - 1).get(i))
			{
				tabsNum = i + 1;
				
				break;
			}
		}
		
		return tabsNum;
	}
	
	private boolean isPrintable(char c)
	{
		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
		
		return (!Character.isISOControl(c) && block != null && block != Character.UnicodeBlock.SPECIALS);
	}
	
//	private char lastCharacterIsTab(int charactersBack)
//	{
//		return tabs.get(textField.getText().length() - charactersBack);
//	}
	
	public Rectangle getBounds()
	{
		return textField.getBounds();
	}
	
	public int getWidth()
	{
		return getBounds().width;
	}
	
	public int getHeight()
	{
		return getBounds().height;
	}
	
	public void setSize(int width, int height)
	{
		textField.setSize(width, height);
	}
	
	public void setLocation(int x, int y)
	{
		textField.setLocation(x, y);
	}
}