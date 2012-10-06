package com.dirtdigger.server;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author BradenSteffaniak
 */
public class Methods {
	
	public static void stateError(Exception ex, int num, String className, boolean showInChat)
	{
		String message = "Error while running class \'" + className + "\' (" + num + "): " + ex.toString() + "\nContact the creator for help.\n";
		if (showInChat)
		{
			Main.chatted(message, "System", "");
		}
		System.err.println(message);
	}

}
