package net.foxycorndog.jdoogl.input;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import org.lwjgl.input.Keyboard;

public class KeyboardInput
{
	public static final int KEY_ESCAPE = Keyboard.KEY_ESCAPE;
	public static final int KEY_F1 = Keyboard.KEY_F1;
	public static final int KEY_F2 = Keyboard.KEY_F2;
	public static final int KEY_F3 = Keyboard.KEY_F3;
	public static final int KEY_F4 = Keyboard.KEY_F4;
	public static final int KEY_F5 = Keyboard.KEY_F5;
	public static final int KEY_F6 = Keyboard.KEY_F6;
	public static final int KEY_F7 = Keyboard.KEY_F7;
	public static final int KEY_F8 = Keyboard.KEY_F8;
	public static final int KEY_F9 = Keyboard.KEY_F9;
	public static final int KEY_F10 = Keyboard.KEY_F10;
	public static final int KEY_F11 = Keyboard.KEY_F11;
	public static final int KEY_F12 = Keyboard.KEY_F12;
	public static final int KEY_TICK = Keyboard.KEY_NONE;
	public static final int KEY_1 = Keyboard.KEY_1;
	public static final int KEY_2 = Keyboard.KEY_2;
	public static final int KEY_3 = Keyboard.KEY_3;
	public static final int KEY_4 = Keyboard.KEY_4;
	public static final int KEY_5 = Keyboard.KEY_5;
	public static final int KEY_6 = Keyboard.KEY_6;
	public static final int KEY_7 = Keyboard.KEY_7;
	public static final int KEY_8 = Keyboard.KEY_8;
	public static final int KEY_9 = Keyboard.KEY_9;
	public static final int KEY_0 = Keyboard.KEY_0;
	public static final int KEY_NUMPAD1 = Keyboard.KEY_NUMPAD1;
	public static final int KEY_NUMPAD2 = Keyboard.KEY_NUMPAD2;
	public static final int KEY_NUMPAD3 = Keyboard.KEY_NUMPAD3;
	public static final int KEY_NUMPAD4 = Keyboard.KEY_NUMPAD4;
	public static final int KEY_NUMPAD5 = Keyboard.KEY_NUMPAD5;
	public static final int KEY_NUMPAD6 = Keyboard.KEY_NUMPAD6;
	public static final int KEY_NUMPAD7 = Keyboard.KEY_NUMPAD7;
	public static final int KEY_NUMPAD8 = Keyboard.KEY_NUMPAD8;
	public static final int KEY_NUMPAD9 = Keyboard.KEY_NUMPAD9;
	public static final int KEY_NUMPAD0 = Keyboard.KEY_NUMPAD0;
	public static final int KEY_MINUS = Keyboard.KEY_MINUS;
	public static final int KEY_EQUALS = Keyboard.KEY_EQUALS;
	public static final int KEY_BACKSPACE = Keyboard.KEY_BACK;
	public static final int KEY_TAB = Keyboard.KEY_TAB;
	public static final int KEY_Q = Keyboard.KEY_Q;
	public static final int KEY_W = Keyboard.KEY_W;
	public static final int KEY_E = Keyboard.KEY_E;
	public static final int KEY_R = Keyboard.KEY_R;
	public static final int KEY_T = Keyboard.KEY_T;
	public static final int KEY_Y = Keyboard.KEY_Y;
	public static final int KEY_U = Keyboard.KEY_U;
	public static final int KEY_I = Keyboard.KEY_I;
	public static final int KEY_O = Keyboard.KEY_O;
	public static final int KEY_P = Keyboard.KEY_P;
	public static final int KEY_LEFT_BRACKET = Keyboard.KEY_LBRACKET;
	public static final int KEY_RIGHT_BRACKET = Keyboard.KEY_RBRACKET;
	public static final int KEY_BACKSLASH = Keyboard.KEY_BACKSLASH;
	public static final int KEY_CAPS_LOCK = Keyboard.KEY_CAPITAL;
	public static final int KEY_A = Keyboard.KEY_A;
	public static final int KEY_S = Keyboard.KEY_S;
	public static final int KEY_D = Keyboard.KEY_D;
	public static final int KEY_F = Keyboard.KEY_F;
	public static final int KEY_G = Keyboard.KEY_G;
	public static final int KEY_H = Keyboard.KEY_H;
	public static final int KEY_J = Keyboard.KEY_J;
	public static final int KEY_K = Keyboard.KEY_K;
	public static final int KEY_L = Keyboard.KEY_L;
	public static final int KEY_SEMICOLON = Keyboard.KEY_SEMICOLON;
	public static final int KEY_APOSTROPHE = Keyboard.KEY_APOSTROPHE;
	public static final int KEY_ENTER = Keyboard.KEY_RETURN;
	public static final int KEY_RETURN = KEY_ENTER;
	public static final int KEY_LEFT_SHIFT = Keyboard.KEY_LSHIFT;
	public static final int KEY_Z = Keyboard.KEY_Z;
	public static final int KEY_X = Keyboard.KEY_X;
	public static final int KEY_C = Keyboard.KEY_C;
	public static final int KEY_V = Keyboard.KEY_V;
	public static final int KEY_B = Keyboard.KEY_B;
	public static final int KEY_N = Keyboard.KEY_N;
	public static final int KEY_M = Keyboard.KEY_M;
	public static final int KEY_COMMA = Keyboard.KEY_COMMA;
	public static final int KEY_PERIOD = Keyboard.KEY_PERIOD;
	public static final int KEY_SLASH = Keyboard.KEY_SLASH;
	public static final int KEY_RIGHT_SHIFT = Keyboard.KEY_RSHIFT;
//	public static final int KEY_FUNCTION = Keyboard.KEY;
	public static final int KEY_CONTROL = Keyboard.KEY_LCONTROL;
	public static final int KEY_ALT = Keyboard.KEY_LMENU;
	public static final int KEY_COMMAND = Keyboard.KEY_LWIN;
	public static final int KEY_LWIN = KEY_COMMAND;
	public static final int KEY_SPACE = Keyboard.KEY_SPACE;
	public static final int KEY_LEFT = Keyboard.KEY_LEFT;
	public static final int KEY_DOWN = Keyboard.KEY_DOWN;
	public static final int KEY_RIGHT = Keyboard.KEY_RIGHT;
	public static final int KEY_UP = Keyboard.KEY_UP;
	
	private static final boolean keys[];
	private static final boolean next[];
	
	private static int length;
	
	static
	{
		length = 100;
		
		keys = new boolean[length];
		next = new boolean[length];
	}
	
	public static boolean isKeyDown(int key)
	{
		return Keyboard.isKeyDown(key);
	}
	
	public static int getEventKey()
	{
		return Keyboard.getEventKey();
	}
	
	public static boolean next()
	{
		refresh();
		
		return Keyboard.next();
	}
	
	public static boolean next(int keyId)
	{
		boolean nxt = keys[keyId] && next[keyId];
		
		keys[keyId] = false;
		
		return nxt;
	}
	
	private static void refresh()
	{
		for (int i = 0; i < keys.length; i ++)
		{
			if (!keys[i] && Keyboard.isKeyDown(i))
			{
				next[i] = true;
			}
			else
			{
				next[i] = false;
			}
			
			keys[i] = Keyboard.isKeyDown(i);
		}
	}
	
	public static boolean[] getKeys()
	{
		refresh();
		
		return keys;
	}
	
	public static String getKeyName(int key)
	{
		return Keyboard.getKeyName(key);
	}
	
	public static int getKey(String name)
	{
		name = name.toUpperCase();
		
		for (int i = 0; i < length; i ++)
		{
			if (getKeyName(i).equals(name))
			{
				return i;
			}
		}
		
		if (name.equals("ENTER"))
		{
			return KEY_ENTER;
		}
		
		return 0;
	}
	
	public static boolean isCapsLockOn()
	{
		return Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
	}
	
	public static int keysAmount()
	{
		return length;
	}

	public static void poll()
	{
		Keyboard.poll();
	}
}