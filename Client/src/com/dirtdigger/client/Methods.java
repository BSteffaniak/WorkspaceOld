/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dirtdigger.client;

/**
 *
 * @author BradenSteffaniak
 */
public class Methods {
	
	public static void stateError(Exception ex, int num, String className, boolean showInChat) {
		String message = "Error while running class \'" + className + "\' (" + num + "): " + ex.toString() + "\nContact the creator for help.\n";
		if (showInChat)
		{
			Main.chatted(message, "System", "");
		}
		System.err.println(message);
	}

	/*public void chatted(String message, String from) {
		if (from.equals("Client")) {
			c.chatLog.setText(c.chatLog.getText() + "<" + from + "> " + message + "\n");
			m.chatLog.setText(c.chatLog.getText() + "<" + from + "> " + message + "\n");
		}
		else if (from.equals("Main")) {
			m.chatLog.setText(m.chatLog.getText() + "<" + from + "> " + message + "\n");
			c.chatLog.setText(m.chatLog.getText() + "<" + from + "> " + message + "\n");
		}
		if (from.equals("Client")) {
			c.textField.setText("");
		}
		else if (from.equals("Main")) {
			m.textField.setText("");
		}
	}*/

}
