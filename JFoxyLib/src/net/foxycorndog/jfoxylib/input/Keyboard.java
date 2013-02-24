package net.foxycorndog.jfoxylib.input;

import java.awt.DefaultKeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import net.foxycorndog.jfoxylib.graphics.opengl.GL;

import org.lwjgl.LWJGLException;

public class Keyboard
{
	private static org.lwjgl.input.Keyboard keyboard;
	
	static
	{
		try
		{
			keyboard.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static final int KEY_ESCAPE = keyboard.KEY_ESCAPE;
	public static final int KEY_F1 = keyboard.KEY_F1;
	public static final int KEY_F2 = keyboard.KEY_F2;
	public static final int KEY_F3 = keyboard.KEY_F3;
	public static final int KEY_F4 = keyboard.KEY_F4;
	public static final int KEY_F5 = keyboard.KEY_F5;
	public static final int KEY_F6 = keyboard.KEY_F6;
	public static final int KEY_F7 = keyboard.KEY_F7;
	public static final int KEY_F8 = keyboard.KEY_F8;
	public static final int KEY_F9 = keyboard.KEY_F9;
	public static final int KEY_F10 = keyboard.KEY_F10;
	public static final int KEY_F11 = keyboard.KEY_F11;
	public static final int KEY_F12 = keyboard.KEY_F12;
	public static final int KEY_TICK = keyboard.KEY_NONE;
	public static final int KEY_1 = keyboard.KEY_1;
	public static final int KEY_2 = keyboard.KEY_2;
	public static final int KEY_3 = keyboard.KEY_3;
	public static final int KEY_4 = keyboard.KEY_4;
	public static final int KEY_5 = keyboard.KEY_5;
	public static final int KEY_6 = keyboard.KEY_6;
	public static final int KEY_7 = keyboard.KEY_7;
	public static final int KEY_8 = keyboard.KEY_8;
	public static final int KEY_9 = keyboard.KEY_9;
	public static final int KEY_0 = keyboard.KEY_0;
	public static final int KEY_NUMPAD1 = keyboard.KEY_NUMPAD1;
	public static final int KEY_NUMPAD2 = keyboard.KEY_NUMPAD2;
	public static final int KEY_NUMPAD3 = keyboard.KEY_NUMPAD3;
	public static final int KEY_NUMPAD4 = keyboard.KEY_NUMPAD4;
	public static final int KEY_NUMPAD5 = keyboard.KEY_NUMPAD5;
	public static final int KEY_NUMPAD6 = keyboard.KEY_NUMPAD6;
	public static final int KEY_NUMPAD7 = keyboard.KEY_NUMPAD7;
	public static final int KEY_NUMPAD8 = keyboard.KEY_NUMPAD8;
	public static final int KEY_NUMPAD9 = keyboard.KEY_NUMPAD9;
	public static final int KEY_NUMPAD0 = keyboard.KEY_NUMPAD0;
	public static final int KEY_MINUS = keyboard.KEY_MINUS;
	public static final int KEY_EQUALS = keyboard.KEY_EQUALS;
	public static final int KEY_BACKSPACE = keyboard.KEY_BACK;
	public static final int KEY_TAB = keyboard.KEY_TAB;
	public static final int KEY_Q = keyboard.KEY_Q;
	public static final int KEY_W = keyboard.KEY_W;
	public static final int KEY_E = keyboard.KEY_E;
	public static final int KEY_R = keyboard.KEY_R;
	public static final int KEY_T = keyboard.KEY_T;
	public static final int KEY_Y = keyboard.KEY_Y;
	public static final int KEY_U = keyboard.KEY_U;
	public static final int KEY_I = keyboard.KEY_I;
	public static final int KEY_O = keyboard.KEY_O;
	public static final int KEY_P = keyboard.KEY_P;
	public static final int KEY_LEFT_BRACKET = keyboard.KEY_LBRACKET;
	public static final int KEY_RIGHT_BRACKET = keyboard.KEY_RBRACKET;
	public static final int KEY_BACKSLASH = keyboard.KEY_BACKSLASH;
	public static final int KEY_CAPS_LOCK = keyboard.KEY_CAPITAL;
	public static final int KEY_A = keyboard.KEY_A;
	public static final int KEY_S = keyboard.KEY_S;
	public static final int KEY_D = keyboard.KEY_D;
	public static final int KEY_F = keyboard.KEY_F;
	public static final int KEY_G = keyboard.KEY_G;
	public static final int KEY_H = keyboard.KEY_H;
	public static final int KEY_J = keyboard.KEY_J;
	public static final int KEY_K = keyboard.KEY_K;
	public static final int KEY_L = keyboard.KEY_L;
	public static final int KEY_SEMICOLON = keyboard.KEY_SEMICOLON;
	public static final int KEY_APOSTROPHE = keyboard.KEY_APOSTROPHE;
	public static final int KEY_ENTER = keyboard.KEY_RETURN;
	public static final int KEY_RETURN = KEY_ENTER;
	public static final int KEY_LEFT_SHIFT = keyboard.KEY_LSHIFT;
	public static final int KEY_Z = keyboard.KEY_Z;
	public static final int KEY_X = keyboard.KEY_X;
	public static final int KEY_C = keyboard.KEY_C;
	public static final int KEY_V = keyboard.KEY_V;
	public static final int KEY_B = keyboard.KEY_B;
	public static final int KEY_N = keyboard.KEY_N;
	public static final int KEY_M = keyboard.KEY_M;
	public static final int KEY_COMMA = keyboard.KEY_COMMA;
	public static final int KEY_PERIOD = keyboard.KEY_PERIOD;
	public static final int KEY_SLASH = keyboard.KEY_SLASH;
	public static final int KEY_RIGHT_SHIFT = keyboard.KEY_RSHIFT;
//	public static final int KEY_FUNCTION = keyboard.KEY;
	public static final int KEY_CONTROL = keyboard.KEY_LCONTROL;
	public static final int KEY_ALT = keyboard.KEY_LMENU;
	public static final int KEY_COMMAND = keyboard.KEY_LWIN;
	public static final int KEY_LWIN = KEY_COMMAND;
	public static final int KEY_SPACE = keyboard.KEY_SPACE;
	public static final int KEY_LEFT = keyboard.KEY_LEFT;
	public static final int KEY_DOWN = keyboard.KEY_DOWN;
	public static final int KEY_RIGHT = keyboard.KEY_RIGHT;
	public static final int KEY_UP = keyboard.KEY_UP;
	
	private static final boolean keys[];
	private static final boolean next[];
	
	private static int length;
	
	static
	{
		length = 100;
		
		keys = new boolean[length];
		next = new boolean[length];
	}
	
	public static void destroy()
	{
		keyboard.destroy();
	}
	
	public static boolean isKeyDown(int key)
	{
		return keyboard.isKeyDown(key);
	}
	
	public static int getEventKey()
	{
		return keyboard.getEventKey();
	}
	
	public static boolean next()
	{
		return keyboard.next();
	}
	
	public static boolean next(int keyId)
	{
		boolean nxt = keys[keyId] && next[keyId];
		
//		keys[keyId] = false;
		
		return nxt;
	}
	
	public static void update()
	{
		for (int i = 0; i < keys.length; i ++)
		{
			if (!keys[i] && keyboard.isKeyDown(i))
			{
				next[i] = true;
			}
			else
			{
				next[i] = false;
			}
			
			keys[i] = keyboard.isKeyDown(i);
		}
	}
	
//	public static boolean[] getKeys()
//	{
//		refresh();
//		
//		return keys;
//	}
	
	public static String getKeyName(int key)
	{
		return keyboard.getKeyName(key);
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
		keyboard.poll();
	}
}