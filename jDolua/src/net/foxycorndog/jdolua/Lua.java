package net.foxycorndog.jdolua;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.Attributes.Name;

import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class Lua
{
	private static final LuaValue GLOBALS = JsePlatform.standardGlobals();
	
	public static void loadScript(String location)
	{
		location.replace("\\", "/");
		
		String name = location.substring(location.lastIndexOf("/") + 1, location.length() - 1);
		
		try
		{
			LoadState.load(new FileInputStream(location), name, GLOBALS).call();
		}
		catch (LuaError e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void runFunction(String name)
	{
		GLOBALS.get(name).invoke();
	}
	
	public static void runFunction(String name, Object param)
	{
		if (param == null)
		{
			runFunction(name);
		}
		
		GLOBALS.get(name).invoke(CoerceJavaToLua.coerce(param));
	}
	
	public static void runFunction(String name, Object params[])
	{
		if (params == null || params.length == 0)
		{
			runFunction(name);
		}
		
		
		
		LuaValue paramValues[] = new LuaValue[params.length];
		
		for (int i = 0; i < params.length; i ++)
		{
//			if (params[i].getClass().isPrimitive())
//			{
//				paramValues[i] = LuaValue.val
//			}
			
			paramValues[i] = CoerceJavaToLua.coerce(params[i]);
		}
		
		GLOBALS.get(name).invoke(paramValues);
	}
}