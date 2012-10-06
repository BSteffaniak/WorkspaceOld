// Client
// package that gives access to protected variables and what not.
package com.dirtdigger.client;

// all of the individual imports

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author BradenSteffaniak
 */
public class Main implements Runnable, ActionListener, WindowListener
{

	// instance variables for the client class

	private String host = "";
	private int port = 0;
	private boolean done = false;
	private String message;
	private String input;
	private Scanner scanner;
	private String tmp;

	private Thread thread;

	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	private static JFrame mainFrame;
	private static JTextField textField;
	private static JButton send, connectDisconnect;
	private JMenuBar menu;
	private static JTextArea chatLog;
	private static JScrollPane sp;
	private static Socket skt;

	private Methods m;
	private JTextField ip;
	private JTextField pn;
	private static int mode = 1;
	private static String ipp;

	private final String CLASSNAME = getClass().getName();

	// method that creates the frame and all thats in it.

	public void bootUp()
	{
		mainFrame = new JFrame("Client");
		mainFrame.setSize(800, 500);
		mainFrame.addWindowListener(this);
		mainFrame.setLayout(null);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textField = new JTextField("");
		textField.setSize(700, 20);
		textField.setLocation(mainFrame.getWidth() / 2 -
		textField.getWidth() / 2 - 50, 455);
		textField.setVisible(true);
		textField.addActionListener(this);
		// textField.setOpaque(false);
        // textField.setBorder(BorderFactory.createEmptyBorder());
        // textField.setBackground(new Color(0,0,0,0));
		mainFrame.add(textField);

		send = new JButton("Send");
		send.setSize(80, 20);
		send.setLocation(710, 455);
		send.setVisible(true);
		send.addActionListener(this);
		mainFrame.add(send);

		chatLog = new JTextArea();
		chatLog.setEditable(false);
		chatLog.setWrapStyleWord(true);
		chatLog.setLineWrap(true);
		sp = new JScrollPane(chatLog);
		sp.setSize(700, 75);
		sp.setLocation(5, 375);
		mainFrame.add(sp);

		connectDisconnect = new JButton("Connect");
		connectDisconnect.setSize(80, 20);
		connectDisconnect.setLocation(710, 10);
		connectDisconnect.setVisible(true);
		connectDisconnect.addActionListener(this);
		mainFrame.add(connectDisconnect);

		ip = new JTextField("0.0.0.0");
		ip.setSize(90, 20);
		ip.setLocation(705, 40);
		ip.setVisible(true);
		mainFrame.add(ip);

		pn = new JTextField("9999");
		pn.setSize(90, 20);
		pn.setLocation(705, 70);
		pn.setVisible(true);
		mainFrame.add(pn);

		// Canvas3D c3d = new Canvas3D(null);
		// BranchGroup group = createSceneGraph();
		// group.compile();

		//  SimpleUniverse is a Convenience Utility class
		// SimpleUniverse universe = new SimpleUniverse(c3d);

		//  This moves the ViewPlatform back a bit so the
		//  objects in the scene can be viewed.
		// universe.getViewingPlatform().setNominalViewingTransform();

		// universe.addBranchGraph(group);

		// mainFrame.add(c3d);

		mainFrame.setVisible(true);

		m = new Methods();
		thread = new Thread(this);
	}

	// main method that runs first thing

	public static void main(String[] args)
	{

		// create object of this class to access instance variables

		Main c = new Main();

		// call bootup to create the frame

		c.bootUp();

	}

	// public BranchGroup createSceneGraph() {
		//  Create the root of the branch graph 3.
	// 	BranchGroup objRoot = new BranchGroup();
		//  Create a simple shape leaf node, add it to the scene graph.
		//  ColorCube is a Convenience Utility class
	// 	objRoot.addChild(new ColorCube(0.4));
	// 	return objRoot;
	// }
	//  end of createSceneGraph method of HelloJava3Da
	
	protected static void chatted(String message, String from, String ip)
	{

		// tries to send the message that was chatted to the server.

		Main ma = new Main();
System.out.println("1");
		if (out != null && !skt.isInputShutdown() && !skt.isOutputShutdown() && skt.isConnected() && in != null)
		{
			System.out.println("2");
			try
			{
				out.writeInt(1);
				out.writeObject(message);
				out.writeObject(ip);
				
				Random random = new Random();
				ArrayList<Integer> j = new ArrayList<Integer>();
				
				j.add(random.nextInt(5));
				j.add(random.nextInt(5));
					
				out.writeObject(j);
			}
			catch (IOException ex)
			{
				Methods.stateError(ex, 22, ma.CLASSNAME, false);
				ma.closingConnection(false);
				chatLog.setText(chatLog.getText() + "<Server (" + ipp + ") has terminated>\n");
			}

		}
		textField.setText("");
	}

	// method called when an action is performed e.g. when send is pressed

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == send && !textField.getText().equals(""))
		{

			// calls the method chatted and sends it who its from & what
			// what the message is.

			chatted(textField.getText(), "Client", skt.getInetAddress().getHostAddress());
		}
		else if (e.getSource() == textField && !textField.getText().equals
				(""))
		{

			// calls the method chatted and sends it who its from & what
			// what the message is.

			chatted(textField.getText(), "Client", skt.getInetAddress().getHostAddress());
		}
		else if (e.getSource() == connectDisconnect)
		{
			if (connectDisconnect.getText().equals("Connect"))
			{
				host = ip.getText();
				try
				{
					// Tries to convert the value of the String given in
					// the JTextField to an integer. Next, it assignes
					// it to the int variable 'port'.
					String temp = pn.getText();
					port = Integer.valueOf(temp);
				}
				catch (NumberFormatException ex)
				{
					Methods.stateError(ex, 23, CLASSNAME, true);
				}
				try
				{
					done = false;

					System.out.println("Attempting to connect to server: "
							+ host + ":" + port);
					chatLog.setText(chatLog.getText() +
							"Attempting to connect to server: " +
							host + ":" + port + "\n");

					// Attempts to connect to the given host and port.

					skt = new Socket(host, port);
					if (skt.isConnected())
					{
						System.out.println
								("Successfully connected to server: " +
								skt.getLocalSocketAddress());
						chatLog.setText(chatLog.getText() +
								"Successfully connected to server: "
								+ skt.getLocalSocketAddress() + "\n");

						// Create an objectinputstream and an
						// objectoutputstream to pass variables to and from
						// the server.
						out = new ObjectOutputStream
								(skt.getOutputStream());
						out.flush();
						in = new ObjectInputStream
								(skt.getInputStream());

						// starts the thread
						thread.start();
						connectDisconnect.setText("Disconnect");
					}
				}
				catch (UnknownHostException ex)
				{
					Methods.stateError(ex, 24, CLASSNAME, true);
				}
				catch (IOException ex)
				{
					Methods.stateError(ex, 25, CLASSNAME, true);
				}
				catch (Exception ex)
				{
					Methods.stateError(ex, 34, CLASSNAME, true);
				}

			}
			else if (connectDisconnect.getText().equals("Disconnect"))
			{
				closingConnection(true);
			}
		}
	}

	public void closingConnection(boolean clientClosed)
	{
		try
		{
			if (clientClosed)
			{
				// makes the thread stop running.
				done = true;
				out.writeInt(3);
				out.writeObject("");
				out.writeObject(skt.getInetAddress().getHostAddress());
			}
			out.close();
			in.close();
			skt.close();
			if (clientClosed)
			{
				System.out.println
						("You have successfully"
						+ " disconnected from the server.");
				chatLog.setText(chatLog.getText() +
						"You have successfully disconnected from"
						+ " the server.\n");
			}
			connectDisconnect.setText("Connect");
		}
		catch (IOException ex)
		{
			Methods.stateError(ex, 26, CLASSNAME, true);
		}
	}

	// run method that runs forever.

	public void run()
	{
		while (!done)
		{
			try
			{
				if (skt != null && out != null && in != null)
				{
					if (skt.isConnected() && !skt.isInputShutdown() && !skt.isOutputShutdown())
					{
						// checks to see if there is any new input from
						// the server.
						// if so, it chats it on the chatLog.
						mode = in.readInt();
						input = (String) in.readObject();
						String from = (String) in.readObject();
						ipp = (String) in.readObject();
						ArrayList<Integer> j = (ArrayList<Integer>)in.readObject();
						
						if (mode == 1)
						{
							chatLog.setText(chatLog.getText() + "<" + from + " (" + ipp + ")> " + input +
									"\n");
						}
						else if (mode == 2)
						{
							chatLog.setText(chatLog.getText() + "<" + from + " (" + ipp + ")> " + input +
									"\n");
						}
						else if (mode == 3)
						{
							chatLog.setText(chatLog.getText() + "<Server (" + ipp + ") has terminated>\n");
						}
						else if (mode == 4) {/*Assign IP*/}
						else
						{
							chatLog.setText(chatLog.getText() + "<" + from + " (" + ipp + ")> " + input +
									"\n");
						}
						
						chatLog.setText(chatLog.getText() + "<" + from + " (" + ipp + ")> " + j.get(0) + ", " + j.get(1)
								 +
								"\n");
					}
				}
			}
			catch (ClassNotFoundException ex)
			{
				Methods.stateError(ex, 27, CLASSNAME, true);
			}
			catch (IOException ex)
			{
				Methods.stateError(ex, 28, CLASSNAME, true);
				ex.printStackTrace();
			}
		}
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		closingConnection(true);
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}
}