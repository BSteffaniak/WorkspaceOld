package net.foxycorndog.jdolua;

import org.luaj.vm2.LuaError;

public class LuaException extends RuntimeException
{
	public LuaException(String message)
	{
		super(message);
	}
}