//Server
//package that gives access to protected variables and what not.

package com.dirtdigger.server;

//all of the imports... NEVER add *'s! You lazy bum! >:O

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author BradenSteffaniak
 */

//implementing Runnable and Actionlistener

//	Runnable: to run()
//	ActionListener: to actionPerformed()

public class Main implements Runnable, ActionListener
{

	//All of the instance variables. Only set the ones that have to be
	//used outside this class as "protected" else, set them as private.

	private String input = "";
	private boolean done = false;
	private Scanner scanner;

	private Thread thread;

	static protected JFrame mainFrame;
	static protected JTextField textField;
	static protected JButton send, startStop;
	static protected JTextArea chatLog;
	static protected JScrollPane sp;

	private Methods m;
	static protected ServerSocket ss;
	private boolean serverStarted = false;
	private JTextField ip;
	private String CLASSNAME = getClass().getName();

	//same stated above for the instance variables with the methods.

	//initiates all of the GUI features

	private void bootUp()
	{
		//creates the main frame, window, whatever you want to call it.
		mainFrame = new JFrame("Server");
		mainFrame.setSize(800, 500);
		mainFrame.setLayout(null);
		mainFrame.setResizable(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//The textfield thaChangelog.txt is used to chat with the client.

		textField = new JTextField("");
		textField.setSize(mainFrame.getWidth() - 100, 20);
		textField.setLocation(5, mainFrame.getHeight() - 45);
		textField.setVisible(true);
		textField.addActionListener(this);
		mainFrame.add(textField);

		//The send button that is useless if you have a working enter
		//button.

		send = new JButton("Send");
		send.setSize(80, 20);
		send.setLocation(710, 455);
		send.setVisible(true);
		send.addActionListener(this);
		mainFrame.add(send);

		//A chatLog that shows all that has happened... more of a
		//statusLog... anyway, also adds a JScrollPane that allows the
		//JTextArea to show all of the encapulated text if it ever reaches
		//the edge of it.

		chatLog = new JTextArea();
		chatLog.setEditable(false);
		chatLog.setWrapStyleWord(true);
		chatLog.setLineWrap(true);
		sp = new JScrollPane(chatLog);
		sp.setSize(700, 445);
		sp.setLocation(5, 5);
		mainFrame.add(sp);

		//start and stop button that is used for starting the server, and
		//stopping it after started.

		startStop = new JButton("Start");
		startStop.setSize(80, 20);
		startStop.setLocation(710, 10);
		startStop.setVisible(true);
		startStop.addActionListener(this);
		mainFrame.add(startStop);

		//sets the frame visible after all of the content has been added.

		mainFrame.setVisible(true);
		//new methods object for accessing 

		m = new Methods();
		thread = new Thread(this);
		thread.start();
		ServerThread st = new ServerThread();
	}

    /**
     * @param args the command line arguments
     */

	//Method that is called first thing and calls the bootUp() method.

	protected static void addMessage(String message, String from)
	{
		chatLog.setText(chatLog.getText() + message + "\n");
	}

    public static void main(String[] args)
	{
		Main s = new Main();

		s.bootUp();
    }

	//@Param message: the text in the textField JTextField when send is
	//pressed, or enter is pressed when in the textField.

	protected static void chatted(String message, String from, String ip)
	{
		//if there is a message and the socket is created

		if (message != null)
		{

				//try to write a String to whoever is waiting on the
				//other side. :)
				Connections.sendMessage(message, "Server", ip, 1);
		}

		//Set the textField text back to nothing so it is ready for typing
		//again.

		if (from.equals("Client"))
		{
			Connections.sendMessage(message, "Client", ip, 2);
		}

		textField.setText("");
	}

	//method called any time that an item with an actionListener is
	//activated.
	//@Param e: Basically an all about the author... only, the item that
	//was activated is the author and you have the info of it.

	public void actionPerformed(ActionEvent e)
	{

		//all of these check whether the ActionEvent (e)'s source is
		//the specified item and maybe some more conditions.

		if (e.getSource() == send && !textField.getText().equals("") &&
				textField.getText() != null)
		{
			chatted(textField.getText(), "Server", ss.getInetAddress().getHostAddress());
		}
		else if (e.getSource() == textField &&
				!textField.getText().equals("") &&
				textField.getText() != null)
		{
			chatted(textField.getText(), "Server", ss.getInetAddress().getHostAddress());
		}
		else if (e.getSource() == startStop)
		{
			if (startStop.getText().equals("Start"))
			{
				try
				{
					done = false;

					ss = new ServerSocket(9999);
					System.out.println("Started server on: " +
							InetAddress.getLocalHost().
							getCanonicalHostName() +
							":" + ss.getLocalPort());
					chatLog.setText(chatLog.getText() +
							"Started server on: " +
							InetAddress.getLocalHost().
							getCanonicalHostName() +
							":" + ss.getLocalPort() + "\n");

					startServer();

				}
				catch(IOException ex)
				{
					Methods.stateError(ex, 22, CLASSNAME, true);
				}
			}
			else if (startStop.getText().equals("Stop"))
			{
				try
				{
					done = true;

					Connections.sendMessage("", "Server", ss.getInetAddress().getHostAddress(), 3);

					ss.close();

					startStop.setText("Start");
				}
				catch (IOException ex)
				{
					Methods.stateError(ex, 23, CLASSNAME, true);
				}
			}
		}
	}

	//Method called when the "Start" button is activated in
	//actionPerformed(ActionEvent e).

	private void startServer()
	{
		startStop.setText("Stop");
		
		//Create new Connections to wait for Clients.
		new Connections();
	}

	//The run method that will run FOREVER until the program has been
	//exited or the boolean value of "done" has been set to true

	public void run()
	{
		while (!done)
		{
			try
			{

				//make the thread sleep for 50 milliseconds.

				Thread.sleep(50);
			}
			catch (InterruptedException ex)
			{
				Logger.getLogger(Main.class.getName()).
						log(Level.SEVERE, null, ex);
			}
		}
	}
}