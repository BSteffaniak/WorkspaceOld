package net.foxycorndog.jdooglandroid.fonts;

import java.util.HashMap;

public class Unicode
{
	private static boolean initialized;
	
	private static HashMap<Integer, Character> codePoints;
	private static HashMap<Character, Integer> characters;
	
	private static void init()
	{
		if (initialized)
		{
			return;
		}
		
		codePoints = new HashMap<Integer, Character>();
		
		int index = 32;
		
		codePoints.put(index ++, ' ');
		codePoints.put(index ++, '!');
		codePoints.put(index ++, '"');
		codePoints.put(index ++, '#');
		codePoints.put(index ++, '$');
		codePoints.put(index ++, '%');
		codePoints.put(index ++, '&');
		codePoints.put(index ++, '\'');
		codePoints.put(index ++, '(');
		codePoints.put(index ++, ')');
		codePoints.put(index ++, '*');
		codePoints.put(index ++, '+');
		codePoints.put(index ++, ',');
		codePoints.put(index ++, '-');
		codePoints.put(index ++, '.');
		codePoints.put(index ++, '/');
		codePoints.put(index ++, '0');
		codePoints.put(index ++, '1');
		codePoints.put(index ++, '2');
		codePoints.put(index ++, '3');
		codePoints.put(index ++, '4');
		codePoints.put(index ++, '5');
		codePoints.put(index ++, '6');
		codePoints.put(index ++, '7');
		codePoints.put(index ++, '8');
		codePoints.put(index ++, '9');
		codePoints.put(index ++, ':');
		codePoints.put(index ++, ';');
		codePoints.put(index ++, '<');
		codePoints.put(index ++, '=');
		codePoints.put(index ++, '>');
		codePoints.put(index ++, '?');
		codePoints.put(index ++, '@');
		codePoints.put(index ++, 'A');
		codePoints.put(index ++, 'B');
		codePoints.put(index ++, 'C');
		codePoints.put(index ++, 'D');
		codePoints.put(index ++, 'E');
		codePoints.put(index ++, 'F');
		codePoints.put(index ++, 'G');
		codePoints.put(index ++, 'H');
		codePoints.put(index ++, 'I');
		codePoints.put(index ++, 'J');
		codePoints.put(index ++, 'K');
		codePoints.put(index ++, 'L');
		codePoints.put(index ++, 'M');
		codePoints.put(index ++, 'N');
		codePoints.put(index ++, 'O');
		codePoints.put(index ++, 'P');
		codePoints.put(index ++, 'Q');
		codePoints.put(index ++, 'R');
		codePoints.put(index ++, 'S');
		codePoints.put(index ++, 'T');
		codePoints.put(index ++, 'U');
		codePoints.put(index ++, 'V');
		codePoints.put(index ++, 'W');
		codePoints.put(index ++, 'X');
		codePoints.put(index ++, 'Y');
		codePoints.put(index ++, 'Z');
		codePoints.put(index ++, '[');
		codePoints.put(index ++, '\\');
		codePoints.put(index ++, ']');
		codePoints.put(index ++, '^');
		codePoints.put(index ++, '_');
		codePoints.put(index ++, '`');
		codePoints.put(index ++, 'a');
		codePoints.put(index ++, 'b');
		codePoints.put(index ++, 'c');
		codePoints.put(index ++, 'd');
		codePoints.put(index ++, 'e');
		codePoints.put(index ++, 'f');
		codePoints.put(index ++, 'g');
		codePoints.put(index ++, 'h');
		codePoints.put(index ++, 'i');
		codePoints.put(index ++, 'j');
		codePoints.put(index ++, 'k');
		codePoints.put(index ++, 'l');
		codePoints.put(index ++, 'm');
		codePoints.put(index ++, 'n');
		codePoints.put(index ++, 'o');
		codePoints.put(index ++, 'p');
		codePoints.put(index ++, 'q');
		codePoints.put(index ++, 'r');
		codePoints.put(index ++, 's');
		codePoints.put(index ++, 't');
		codePoints.put(index ++, 'u');
		codePoints.put(index ++, 'v');
		codePoints.put(index ++, 'w');
		codePoints.put(index ++, 'x');
		codePoints.put(index ++, 'y');
		codePoints.put(index ++, 'z');
		codePoints.put(index ++, '{');
		codePoints.put(index ++, '|');
		codePoints.put(index ++, '}');
		codePoints.put(index ++, '~');
		
		
		characters = new HashMap<Character, Integer>();
		
		index = 32;
		
		characters.put(' ', index ++);
		characters.put('!', index ++);
		characters.put('"', index ++);
		characters.put('#', index ++);
		characters.put('$', index ++);
		characters.put('%', index ++);
		characters.put('&', index ++);
		characters.put('\'', index ++);
		characters.put('(', index ++);
		characters.put(')', index ++);
		characters.put('*', index ++);
		characters.put('+', index ++);
		characters.put(',', index ++);
		characters.put('-', index ++);
		characters.put('.', index ++);
		characters.put('/', index ++);
		characters.put('0', index ++);
		characters.put('1', index ++);
		characters.put('2', index ++);
		characters.put('3', index ++);
		characters.put('4', index ++);
		characters.put('5', index ++);
		characters.put('6', index ++);
		characters.put('7', index ++);
		characters.put('8', index ++);
		characters.put('9', index ++);
		characters.put(':', index ++);
		characters.put(';', index ++);
		characters.put('<', index ++);
		characters.put('=', index ++);
		characters.put('>', index ++);
		characters.put('?', index ++);
		characters.put('@', index ++);
		characters.put('A', index ++);
		characters.put('B', index ++);
		characters.put('C', index ++);
		characters.put('D', index ++);
		characters.put('E', index ++);
		characters.put('F', index ++);
		characters.put('G', index ++);
		characters.put('H', index ++);
		characters.put('I', index ++);
		characters.put('J', index ++);
		characters.put('K', index ++);
		characters.put('L', index ++);
		characters.put('M', index ++);
		characters.put('N', index ++);
		characters.put('O', index ++);
		characters.put('P', index ++);
		characters.put('Q', index ++);
		characters.put('R', index ++);
		characters.put('S', index ++);
		characters.put('T', index ++);
		characters.put('U', index ++);
		characters.put('V', index ++);
		characters.put('W', index ++);
		characters.put('X', index ++);
		characters.put('Y', index ++);
		characters.put('Z', index ++);
		characters.put('[', index ++);
		characters.put('\\', index ++);
		characters.put(']', index ++);
		characters.put('^', index ++);
		characters.put('_', index ++);
		characters.put('`', index ++);
		characters.put('a', index ++);
		characters.put('b', index ++);
		characters.put('c', index ++);
		characters.put('d', index ++);
		characters.put('e', index ++);
		characters.put('f', index ++);
		characters.put('g', index ++);
		characters.put('h', index ++);
		characters.put('i', index ++);
		characters.put('j', index ++);
		characters.put('k', index ++);
		characters.put('l', index ++);
		characters.put('m', index ++);
		characters.put('n', index ++);
		characters.put('o', index ++);
		characters.put('p', index ++);
		characters.put('q', index ++);
		characters.put('r', index ++);
		characters.put('s', index ++);
		characters.put('t', index ++);
		characters.put('u', index ++);
		characters.put('v', index ++);
		characters.put('w', index ++);
		characters.put('x', index ++);
		characters.put('y', index ++);
		characters.put('z', index ++);
		characters.put('{', index ++);
		characters.put('|', index ++);
		characters.put('}', index ++);
		characters.put('~', index ++);
		
		initialized = true;
	}
	
	public static char getCodePointChar(int codePoint)
	{
		init();
		
		char ch = codePoints.get(codePoint) == null ? (char)0 : (char)codePoints.get(codePoint);
		
		return ch;
	}
	
	public static int getCharCodePoint(char ch)
	{
		init();
		
		int codePoint = characters.get(ch) == null ? 0 : characters.get(ch);
		
		return codePoint;
	}
}
