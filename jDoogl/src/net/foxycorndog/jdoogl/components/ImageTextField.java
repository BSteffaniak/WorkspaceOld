package net.foxycorndog.jdoogl.components;

import java.util.HashMap;

import org.lwjgl.input.Mouse;

import net.foxycorndog.jdoogl.Color;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.ImageMap;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.input.KeyboardInput;
import net.foxycorndog.jdoogl.listeners.ActionListener;
import net.foxycorndog.jdoogl.listeners.KeyListener;
import net.foxycorndog.jdoutil.StringUtil;
import net.foxycorndog.jdoutil.Util;

import static net.foxycorndog.jdoogl.components.ImageTextField.Margin.*;

public class ImageTextField extends ImageButton implements KeyListener, ActionListener
{
	private boolean flash;
	private boolean waited;
	
	private char    characterMask;
	
	private int     lastKeyPressed;
	private int     maxLength;
	
	private long    lastTime, lastKeyPress;
	private long    waitedTime, waitedMarker;
	
	private String  fieldText;
	
	private int     margins[];
	
	private HashMap<Integer, Boolean> keysPressed;
	
	public static enum Margin
	{
		LEFT(0), TOP(1), RIGHT(2), BOTTOM(3);
		
		private int index;
		
		private Margin(int index)
		{
			this.index = index;
		}
		
		public int getIndex()
		{
			return index;
		}
	}
	
	public ImageTextField(Texture texture)
	{
		super(texture);
		
		fieldText = "";
		
		this.addKeyListener(this);
		this.addActionListener(this);
		
		keysPressed = new HashMap<Integer, Boolean>();
		
		maxLength = Integer.MAX_VALUE;
		
		margins = new int[4];
		
		margins[LEFT.getIndex()]   = 2;
		margins[TOP.getIndex()]    = 2;
		margins[RIGHT.getIndex()]  = 2;
		margins[BOTTOM.getIndex()] = 2;
	}
	
	public String getText()
	{
		return fieldText;
	}
	
	public void setText(String text)
	{
		this.fieldText = text;
	}
	
	public int getMaxLength()
	{
		return maxLength;
	}
	
	public void setMaxLength(int maxLength)
	{
		if (maxLength < 0)
		{
			throw new IllegalArgumentException("The maxLength must be above 0.");
		}
		
		this.maxLength = maxLength;
	}
	
	public char getCharacterMask()
	{
		return characterMask;
	}
	
	public void setCharacterMask(char characterMask)
	{
		this.characterMask = characterMask;
	}
	
	public void render()
	{
		GL.beginManipulation();
		{
			String displayText = fieldText;
			
			if ((int)characterMask != 0)
			{
				displayText = StringUtil.replaceAll(displayText, characterMask + "");
			}
			
			int lm = (int)(margins[LEFT.getIndex()] * getScaleWidth());
			int rm = (int)(margins[RIGHT.getIndex()] * getScaleHeight());
			
			float halfHeight = getHeight() / 2;
			
			halfHeight = halfHeight >= Integer.MAX_VALUE ? 0 : halfHeight;
			
			float halfText = Frame.getFont().getHeight("A0|WL") / 2;
			
			halfText = halfText >= Integer.MAX_VALUE ? 0 : halfText;
			
			float renderX = getX() + getOffsetX() + margins[LEFT.getIndex()];
			float renderY = getY() + getOffsetY();
			renderY += halfHeight + halfText;
			
			boolean difSize = getWidth() != getImageMapWidth() || getHeight() != getImageMapHeight();
			
			if (difSize)
			{
				super.render(getWidth() / (float)getImageMapWidth(), getHeight() / (float)getImageMapHeight(), 1);
			}
			else
			{
				super.render();
			}
			
			GL.beginClipping((int)getScreenX() + lm, (int)getScreenY() + 1, getScreenWidth() - lm - rm, getScreenHeight());
			
			GL.translatef(renderX, Frame.getHeight() / getScaleHeight() - renderY - 1, 1);
			
			float offsetX = ((getScreenWidth() - lm - rm) - Frame.getFont().getLegitWidth(fieldText + "_") * getScaleWidth());
			
			if (offsetX < 0)
			{
				GL.translatef(offsetX / getScaleWidth(), 0, 0);
			}
			
			char underscore = ' ';
			
			if (!flash && isFocused() && fieldText.length() < maxLength)
			{
				if (fieldText.length() >= maxLength)
				{
					
				}
				else
				{
					underscore = '_';
				}
			}
			
			Frame.renderText(0, 0, displayText + underscore , Color.WHITE, 1);
			
			long time = System.currentTimeMillis();
			
			if (isFocused() && time >= lastTime + 5000l)
			{
				flash = !flash;
				
				lastTime = time;
			}
			
			GL.endClipping();
		}
		GL.endManipulation();
	}
	
	public void render(int r, int g, int b, int a)
	{
		GL.beginManipulation();
		{
			int lm = (int)(margins[LEFT.getIndex()] * getScaleWidth());
			int rm = (int)(margins[RIGHT.getIndex()] * getScaleHeight());
			
			float halfHeight = getHeight() / 2;
			
			halfHeight = halfHeight >= Integer.MAX_VALUE ? 0 : halfHeight;
			
			float halfText = Frame.getFont().getHeight("A0|WL") / 2;
			
			halfText = halfText >= Integer.MAX_VALUE ? 0 : halfText;
			
			float renderX = getX() + getOffsetX() + margins[LEFT.getIndex()];
			float renderY = getY() + getOffsetY();
			renderY += halfHeight + halfText;
			
			boolean difSize = getWidth() != getImageMapWidth() || getHeight() != getImageMapHeight();
			
			GL.setColori(r, g, b, a);
			
			if (difSize)
			{
				super.render(getWidth() / (float)getImageMapWidth(), getHeight() / (float)getImageMapHeight(), 1);
			}
			else
			{
				super.render();
			}
			
			GL.setColori(255, 255, 255, 255);
			
			GL.beginClipping((int)getScreenX() + lm, (int)getScreenY() + 1, getScreenWidth() - lm - rm, getScreenHeight());
			
			GL.translatef(renderX, Frame.getHeight() / getScaleHeight() - renderY - 1, 1);
			
			float offsetX = ((getScreenWidth() - lm - rm) - Frame.getFont().getLegitWidth(fieldText + "_") * getScaleWidth());
			
			if (offsetX < 0)
			{
				GL.translatef(offsetX / getScaleWidth(), 0, 0);
			}
			
			char underscore = ' ';
			
			if (!flash && isFocused() && fieldText.length() < maxLength)
			{
				if (fieldText.length() >= maxLength)
				{
					
				}
				else
				{
					underscore = '_';
				}
			}
			
			Frame.renderText(0, 0, fieldText + underscore , Color.WHITE, 1);

			long time = System.currentTimeMillis();
			
			if (isFocused() && time >= lastTime + 500000000l)
			{
				flash = !flash;
				
				lastTime = time;
			}
			
			GL.endClipping();
		}
		GL.endManipulation();
	}
	
	@Override
	public void onKeyPressed(int key)
	{
		boolean activatable = true;
		
		if (key == KeyboardInput.KEY_LEFT_SHIFT || key == KeyboardInput.KEY_RIGHT_SHIFT)
		{
			activatable = false;
		}
		
		if (!isFocused())
		{
			return;
		}
		
		long time = System.currentTimeMillis();
		
		if (!activatable || lastKeyPressed == key)
		{
			waitedTime = time - waitedMarker;
			
			if (waitedTime >= 500000000l)
			{
				waited = true;
			}
			else
			{
				waited = false;
			}
		}
		else
		{
			waitedTime = 0;
			waitedMarker = time;
			
			waited = false;
		}
		
		if ((waited && time >= lastKeyPress + 50000000l) || (keysPressed.containsKey(key) && !keysPressed.get(key)) || !keysPressed.containsKey(key))
		{
			
		}
		else
		{
//			if (Util.nanoTime < lastKeyPress + 500000000l)
//			{
				return;
//			}
		}
		
		if (activatable)
		{
			lastKeyPress = time;
		}
			
		String keyName = KeyboardInput.getKeyName(key);
		
		if (key == KeyboardInput.KEY_BACKSPACE && fieldText.length() > 0)
		{
			fieldText = fieldText.substring(0, fieldText.length() - 1);
		}
		else if (key == KeyboardInput.KEY_SPACE)
		{
			fieldText += " ";
		}
		else if (keyName.length() == 1 && !StringUtil.isNumber(keyName))
		{
//			lastKeyPress = Util.nanoTime;
			
			if (KeyboardInput.isKeyDown(KeyboardInput.KEY_LEFT_SHIFT) || KeyboardInput.isKeyDown(KeyboardInput.KEY_RIGHT_SHIFT) || KeyboardInput.isCapsLockOn())
			{
				
			}
			else
			{
				keyName = keyName.toLowerCase();
			}
			
			fieldText += keyName;
		}
		else
		{
			if (KeyboardInput.isKeyDown(KeyboardInput.KEY_LEFT_SHIFT) || KeyboardInput.isKeyDown(KeyboardInput.KEY_RIGHT_SHIFT) || KeyboardInput.isCapsLockOn())
			{
				if (key == KeyboardInput.KEY_PERIOD)
				{
					fieldText += ">";
				}
				else if (key == KeyboardInput.KEY_COMMA)
				{
					fieldText += "<";
				}
				else if (key == KeyboardInput.KEY_SLASH)
				{
					fieldText += "?";
				}
				else if (key == KeyboardInput.KEY_APOSTROPHE)
				{
					fieldText += "\"";
				}
				else if (key == KeyboardInput.KEY_SEMICOLON)
				{
					fieldText += ":";
				}
				else if (key == KeyboardInput.KEY_RIGHT_BRACKET)
				{
					fieldText += "}";
				}
				else if (key == KeyboardInput.KEY_LEFT_BRACKET)
				{
					fieldText += "{";
				}
				else if (key == KeyboardInput.KEY_EQUALS)
				{
					fieldText += "+";
				}
				else if (key == KeyboardInput.KEY_MINUS)
				{
					fieldText += "_";
				}
				else if (key == KeyboardInput.KEY_1)
				{
					fieldText += "!";
				}
				else if (key == KeyboardInput.KEY_2)
				{
					fieldText += "@";
				}
				else if (key == KeyboardInput.KEY_3)
				{
					fieldText += "#";
				}
				else if (key == KeyboardInput.KEY_4)
				{
					fieldText += "$";
				}
				else if (key == KeyboardInput.KEY_5)
				{
					fieldText += "%";
				}
				else if (key == KeyboardInput.KEY_6)
				{
					fieldText += "^";
				}
				else if (key == KeyboardInput.KEY_7)
				{
					fieldText += "&";
				}
				else if (key == KeyboardInput.KEY_8)
				{
					fieldText += "*";
				}
				else if (key == KeyboardInput.KEY_9)
				{
					fieldText += "(";
				}
				else if (key == KeyboardInput.KEY_0)
				{
					fieldText += ")";
				}
			}
			else
			{
				if (key == KeyboardInput.KEY_PERIOD)
				{
					fieldText += ".";
				}
				else if (key == KeyboardInput.KEY_COMMA)
				{
					fieldText += ",";
				}
				else if (key == KeyboardInput.KEY_SLASH)
				{
					fieldText += "/";
				}
				else if (key == KeyboardInput.KEY_APOSTROPHE)
				{
					fieldText += "'";
				}
				else if (key == KeyboardInput.KEY_SEMICOLON)
				{
					fieldText += ";";
				}
				else if (key == KeyboardInput.KEY_RIGHT_BRACKET)
				{
					fieldText += "]";
				}
				else if (key == KeyboardInput.KEY_LEFT_BRACKET)
				{
					fieldText += "[";
				}
				else if (key == KeyboardInput.KEY_EQUALS)
				{
					fieldText += "=";
				}
				else if (key == KeyboardInput.KEY_MINUS)
				{
					fieldText += "-";
				}
				else if (key == KeyboardInput.KEY_1)
				{
					fieldText += "1";
				}
				else if (key == KeyboardInput.KEY_2)
				{
					fieldText += "2";
				}
				else if (key == KeyboardInput.KEY_3)
				{
					fieldText += "3";
				}
				else if (key == KeyboardInput.KEY_4)
				{
					fieldText += "4";
				}
				else if (key == KeyboardInput.KEY_5)
				{
					fieldText += "5";
				}
				else if (key == KeyboardInput.KEY_6)
				{
					fieldText += "6";
				}
				else if (key == KeyboardInput.KEY_7)
				{
					fieldText += "7";
				}
				else if (key == KeyboardInput.KEY_8)
				{
					fieldText += "8";
				}
				else if (key == KeyboardInput.KEY_9)
				{
					fieldText += "9";
				}
				else if (key == KeyboardInput.KEY_0)
				{
					fieldText += "0";
				}
			}
			
			if (key == KeyboardInput.KEY_NUMPAD1)
			{
				fieldText += "1";
			}
			else if (key == KeyboardInput.KEY_NUMPAD2)
			{
				fieldText += "2";
			}
			else if (key == KeyboardInput.KEY_NUMPAD3)
			{
				fieldText += "3";
			}
			else if (key == KeyboardInput.KEY_NUMPAD4)
			{
				fieldText += "4";
			}
			else if (key == KeyboardInput.KEY_NUMPAD5)
			{
				fieldText += "5";
			}
			else if (key == KeyboardInput.KEY_NUMPAD6)
			{
				fieldText += "6";
			}
			else if (key == KeyboardInput.KEY_NUMPAD7)
			{
				fieldText += "7";
			}
			else if (key == KeyboardInput.KEY_NUMPAD8)
			{
				fieldText += "8";
			}
			else if (key == KeyboardInput.KEY_NUMPAD9)
			{
				fieldText += "9";
			}
			else if (key == KeyboardInput.KEY_NUMPAD0)
			{
				fieldText += "0";
			}
		}
		
		if (activatable)
		{
			keysPressed.put(key, true);
			
			lastKeyPressed = key;
		}
		
		if (fieldText.length() >= maxLength)
		{
			fieldText = fieldText.substring(0, maxLength);
		}
	}

	@Override
	public void onKeyReleased(int key)
	{
		if (!isFocused())
		{
			return;
		}
		
		waitedTime = 0;
		waitedMarker = System.currentTimeMillis();
		
		waited = false;
		
		keysPressed.put(key, false);
	}

	@Override
	public void onKeyTyped(int key)
	{
		
	}

	@Override
	public void onActionPerformed(Component source)
	{
		
	}

	@Override
	public void onHover(Component source)
	{
		
	}
}