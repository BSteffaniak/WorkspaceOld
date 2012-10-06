package net.foxycorndog.poker;

/**
* File:  			PokerGame.java HONORS
* Author:			Braden Steffaniak
* Programming:  	1st Hour
* Last Modified: 	4May2012
* Description: The main class for the poker game. Handles the
* GUI and actions.
*/

/*
* -------------------------------------------------------------------------
* -*-*-*-*-*-*-*-*-*-*-*---------------------------------------------------
* --*******************----------------------------------------------------
* -*********************---------------------------------------------------
* --***BBBBB**RRRR***AAA--DDDD--EEEEE-NN---N-------------------------------
* -****B****B*R***R*A***A-D---D-E-----N-N--N-------------------------------
* --***BBBBB**RRRRR*AAAAA-D---D-EEEEE-N-NN-N-------------------------------
* -****B****B*R***R*A***A-D---D-E-----N--N-N-------------------------------
* --***BBBBB**R***R*A**-A-DDDD--EEEEE-N---NN-------------------------------
* -*-*-*-*-*-*-*-*-*-*-*---------------------------------------------------
* --SSSSS-TTTTTTT-EEEEEE-FFFFFF-FFFFFF--AAAA--NN----N-IIIIIII--AAAA--K---K-
* -S---------T----E------F------F------A----A-N-N---N----I----A----A-K--K--
* -S---------T----E------F------F------A----A-N-NN--N----I----A----A-K-K---
* --SSSS-----T----EEEEEE-FFFFFF-FFFFFF-AAAAAA-N--N--N----I----AAAAAA-KK----
* ------S----T----E------F------F------A----A-N--NN-N----I----A----A-K-K---
* ------S----T----E------F------F------A----A-N---N-N----I----A----A-K--K--
* -SSSSS-----T----EEEEEE-F------F------A----A-N----NN-IIIIIII-A----A-K---K-
* -------------------------------------------------------------------------
* -------------------------------------------------------------------------
* -------------------------------------------------------------------------
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.foxycorndog.poker.components.BackgroundPanel;
import net.foxycorndog.poker.multiplayer.Client;
import net.foxycorndog.poker.multiplayer.Network;
import net.foxycorndog.poker.multiplayer.Server;
import net.foxycorndog.poker.packet.Packet;

public class PokerGame implements ActionListener, KeyListener
{
	private BackgroundPanel gamePanel, mainMenuPanel, singlePlayerMenuPanel, multiPlayerMenuPanel, helpMenuPanel;
	
	private Deck            deck;
	
	private Player          player1, player2;

	private JFrame          frame;
	
	private GridBagLayout   layout;
	
	private int             width, height;
	private int             gameMode;
	private int             interactiveButtonMode;
	private int             shufflePhase;
	
	private boolean         dealingCards;
	private boolean         playerConnected;
	private boolean         waitingOnPlayer, playerWaitingOn;
	private boolean         dealt, shownResults;
	
	private ImageIcon       background;
	
	private Server          server;
	
	private Client          client;
	
	private Network         network;
	
	private PokerGame       thisPokerGame;
	
	/***************************** The main menu components */
	private ImageIcon pokerLogo;
	
	private JLabel    pokerLabel;
	
	private JPanel    mainMenuButtonPanel;
	
	private JButton   singlePlayerButton, multiPlayerButton, help;
	/*****************************/
	
	/***************************** The single player menu components*/
	private JButton    start;
	
	private JLabel     player1nmlbl, player2nmlbl;
	
	private JTextField player1NameTextField, player2NameTextField;
	/*****************************/
	
	/***************************** The multi player menu components*/
	private JButton    connectServerButton, createServerButton,
						connectServerMenuButton, createServerMenuButton;
	
	private JLabel     serverLabel;
	
	private JTextField serverIPTextField;
	/*****************************/
	
	/***************************** The help menu components */
	private int        page;
	
	private JButton    next, previous, exit;
	
	private JLabel     hands[], hands2[];
	/*****************************/
	
	/***************************** These are all game components */
	// buttons
	private JButton[]          player1CheckButtons, player2CheckButtons;
	private JButton[]          player1CardButtons,  player2CardButtons;
	private JButton            interactiveButton, switchCardsButton;
	
	private JPanel             buttonsPanel, statusPanel;
	
	private ImageIcon          checkmark, blankCheckmark;
	
	private boolean            player1Checked[], player2Checked[];
	
	private GridBagConstraints gbc;
	
	private JLabel             player1ScoreboardNameLabel,  player2ScoreboardNameLabel;
	private JLabel             player1ScoreboardScoreLabel, player2ScoreboardScoreLabel;
	private JLabel             gameVictorLabel;
	private JLabel             gameStatusLabel;
	
	private JPanel             p1Side, p2Side;
	
	/*****************************  Ending the components */
	
	private static final int SINGLE_PLAYER = 0, LOCAL_MULTI_PLAYER = 1, SERVER_MULTI_PLAYER = 2, CLIENT_MULTI_PLAYER = 3;
	
	/**
	* First method to run. Instantiates a new PokerGame.
	*/
	public static void main(String args[])
	{
		new PokerGame();
	}

	/**
	* Constructor for PokerGame. Instantiates and initializes the
	* necessary components.
	*/
	public PokerGame()
	{
		interactiveButtonMode = 0;
		
		width  = 450;
		height = 500;
		
		// this will allow us to try to use the user interface manager
		// to change the look and feel of the windows
		try
		{
			UIManager.setLookAndFeel(
		                UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch (Exception e)
		{
			// do nothing if a problem...just skip it
		}
		
		frame  = new JFrame("Poooooker");
		
		frame.setSize(width, height);
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setLayout(new GridLayout(1, 1));
		
		layout = new GridBagLayout();
		
		gbc    = new GridBagConstraints();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		frame.addKeyListener(this);
		
		background = new ImageIcon("res/images/background.png");
		
		createMainMenu();
		
     	frame.setVisible(true);
     	
     	frame.requestFocus();
	}
	
	/**
	* Creates a menu and switches the frames view to it.
	*/
	public void createMainMenu()
	{
		mainMenuPanel = new BackgroundPanel(background.getImage());
		mainMenuPanel.setBackgroundImageAutoResize(true);
		mainMenuPanel.setBackground(new Color(0, 160, 0));
		mainMenuPanel.setSize(width, height);
		mainMenuPanel.setLayout(layout);
		mainMenuPanel.setParentComponent(frame);
		
		GridLayout glayout = new GridLayout(3, 1);
		glayout.setVgap(5);
		
		mainMenuButtonPanel = new JPanel();
		mainMenuButtonPanel.setLayout(glayout);
		mainMenuButtonPanel.setOpaque(false);
		
		pokerLogo  = new ImageIcon("res/images/poker.png");
		
		pokerLabel = new JLabel(pokerLogo);
		pokerLabel.setSize(pokerLogo.getIconWidth(),
				pokerLogo.getIconHeight());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.insets = new Insets(0, 0, 50, 0);
		
		mainMenuPanel.add(pokerLabel, gbc);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		
		singlePlayerButton = new JButton("Single-Player");
		singlePlayerButton.addActionListener(this);
		
		mainMenuButtonPanel.add(singlePlayerButton);
		
		multiPlayerButton = new JButton("Multi-Player");
		multiPlayerButton.addActionListener(this);
		
		mainMenuButtonPanel.add(multiPlayerButton);
		
		help = new JButton("Help");
		help.addActionListener(this);
		
		mainMenuButtonPanel.add(help);
		
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		mainMenuPanel.add(mainMenuButtonPanel, gbc);
		
		
		frame.setContentPane(mainMenuPanel);
	}
	
	/**
	* Creates the intermediate menu between the main menu and a
	* single player game.
	*/
	public void createSinglePlayerMenu()
	{
		singlePlayerMenuPanel = new BackgroundPanel(background.getImage());
		singlePlayerMenuPanel.setBackgroundImageAutoResize(true);
		singlePlayerMenuPanel.setBackground(new Color(0, 160, 0));
		singlePlayerMenuPanel.setSize(frame.getWidth(), frame.getHeight());
		singlePlayerMenuPanel.setLayout(layout);
		singlePlayerMenuPanel.setParentComponent(frame);
		
		
		player1nmlbl = new JLabel("Your name:");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		singlePlayerMenuPanel.add(player1nmlbl, gbc);
		
		
		player1NameTextField = new JTextField(10);
		player1NameTextField.setText("Player 1");
		player1NameTextField.addKeyListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		singlePlayerMenuPanel.add(player1NameTextField, gbc);
		
		
		player2nmlbl = new JLabel("Opponents name:");
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		gbc.insets = new Insets(15, 0, 0, 0);
		
		singlePlayerMenuPanel.add(player2nmlbl, gbc);
		
		
		player2NameTextField = new JTextField(10);
		player2NameTextField.setText("Opponent");
		player2NameTextField.addKeyListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		
		gbc.insets = new Insets(0, 0, 0, 0);
		
		singlePlayerMenuPanel.add(player2NameTextField, gbc);
		
		
		start = new JButton("Start");
		start.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		
		gbc.insets = new Insets(30, 0, 0, 0);
		
		singlePlayerMenuPanel.add(start, gbc);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		
		
		frame.setContentPane(singlePlayerMenuPanel);
	}
	
	/**
	* Creates an intermediate menu between the main menu and
	* the connectServerMenu or the createServerMenu.
	*/
	public void createMultiPlayerMenu()
	{
		multiPlayerMenuPanel = new BackgroundPanel(background.getImage());
		multiPlayerMenuPanel.setBackgroundImageAutoResize(true);
		multiPlayerMenuPanel.setBackground(new Color(0, 160, 0));
		multiPlayerMenuPanel.setSize(frame.getWidth(), frame.getHeight());
		multiPlayerMenuPanel.setLayout(layout);
		multiPlayerMenuPanel.setParentComponent(frame);
		
		
		connectServerMenuButton = new JButton("Connect");
		connectServerMenuButton.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		multiPlayerMenuPanel.add(connectServerMenuButton, gbc);
		
		
		createServerMenuButton = new JButton("Create");
		createServerMenuButton.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		gbc.insets = new Insets(30, 0, 0, 0);
		
		multiPlayerMenuPanel.add(createServerMenuButton, gbc);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		
		
		frame.setContentPane(multiPlayerMenuPanel);
	}
	
	/**
	* Creates an intermediate menu between the multi player menu
	* and the multi player game.
	*/
	public void createConnectServerMenu()
	{
		multiPlayerMenuPanel = new BackgroundPanel(background.getImage());
		multiPlayerMenuPanel.setBackgroundImageAutoResize(true);
		multiPlayerMenuPanel.setBackground(new Color(0, 160, 0));
		multiPlayerMenuPanel.setSize(frame.getWidth(), frame.getHeight());
		multiPlayerMenuPanel.setLayout(layout);
		multiPlayerMenuPanel.setParentComponent(frame);
		
		
		player1nmlbl = new JLabel("Your name:");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		multiPlayerMenuPanel.add(player1nmlbl, gbc);
		
		
		player1NameTextField = new JTextField(10);
		player1NameTextField.setText("Joe");
		player1NameTextField.addKeyListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		multiPlayerMenuPanel.add(player1NameTextField, gbc);
		
		
		serverLabel = new JLabel("Server IP:");
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		gbc.insets = new Insets(15, 0, 0, 0);
		
		multiPlayerMenuPanel.add(serverLabel, gbc);
		
		
		serverIPTextField = new JTextField(10);
		serverIPTextField.setText("");
		serverIPTextField.addKeyListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		
		gbc.insets = new Insets(0, 0, 0, 0);
		
		multiPlayerMenuPanel.add(serverIPTextField, gbc);
		
		
		connectServerButton = new JButton("Connect");
		connectServerButton.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		
		gbc.insets = new Insets(30, 0, 0, 0);
		
		multiPlayerMenuPanel.add(connectServerButton, gbc);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		
		
		frame.setContentPane(multiPlayerMenuPanel);
	}
	
	/**
	* Creates an intermediate menu between the multi player menu
	* and the multi player game.
	*/
	public void createCreateServerMenu()
	{
		multiPlayerMenuPanel = new BackgroundPanel(background.getImage());
		multiPlayerMenuPanel.setBackgroundImageAutoResize(true);
		multiPlayerMenuPanel.setBackground(new Color(0, 160, 0));
		multiPlayerMenuPanel.setSize(frame.getWidth(), frame.getHeight());
		multiPlayerMenuPanel.setLayout(layout);
		multiPlayerMenuPanel.setParentComponent(frame);
		
		
		player1nmlbl = new JLabel("Your name:");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		multiPlayerMenuPanel.add(player1nmlbl, gbc);
		
		
		player1NameTextField = new JTextField(10);
		player1NameTextField.setText("Joe");
		player1NameTextField.addKeyListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		multiPlayerMenuPanel.add(player1NameTextField, gbc);
		
		
		createServerButton = new JButton("Create");
		createServerButton.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		gbc.insets = new Insets(30, 0, 0, 0);
		
		multiPlayerMenuPanel.add(createServerButton, gbc);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		
		
		frame.setContentPane(multiPlayerMenuPanel);
	}
	
	/**
	* Creates a help menu that shows the possible card combinations.
	*/
	public void createHelpMenu()
	{
		page = 1;
		
		hands     = new JLabel[5 * 10];
		
		int index = 0;
		
		// Royal flush
		hands[index ++] = new JLabel(new Card(51).getFrontImage());
		hands[index ++] = new JLabel(new Card(50).getFrontImage());
		hands[index ++] = new JLabel(new Card(49).getFrontImage());
		hands[index ++] = new JLabel(new Card(48).getFrontImage());
		hands[index ++] = new JLabel(new Card(47).getFrontImage());
		
		// Straight flush
		hands[index ++] = new JLabel(new Card(45 - 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(44 - 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(43 - 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(42 - 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(41 - 13).getFrontImage());
		
		// Four of a kind
		hands[index ++] = new JLabel(new Card(7).getFrontImage());
		hands[index ++] = new JLabel(new Card(7 + 13 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(7 + 13 + 13 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(7 + 13 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(3 + 13 + 13 + 13).getFrontImage());
		
		// Full house
		hands[index ++] = new JLabel(new Card(3 + 13 + 13 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(3 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(3).getFrontImage());
		hands[index ++] = new JLabel(new Card(9 + 13 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(9 + 13).getFrontImage());
		
		// Flush
		hands[index ++] = new JLabel(new Card(3).getFrontImage());
		hands[index ++] = new JLabel(new Card(6).getFrontImage());
		hands[index ++] = new JLabel(new Card(1).getFrontImage());
		hands[index ++] = new JLabel(new Card(0).getFrontImage());
		hands[index ++] = new JLabel(new Card(11).getFrontImage());
		
		// Straight
		hands[index ++] = new JLabel(new Card(3 + 13 + 13 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(4 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(5).getFrontImage());
		hands[index ++] = new JLabel(new Card(6 + 13 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(7).getFrontImage());
		
		// Three of a kind
		hands[index ++] = new JLabel(new Card(9 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(9).getFrontImage());
		hands[index ++] = new JLabel(new Card(9 + 13 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(5).getFrontImage());
		hands[index ++] = new JLabel(new Card(4 + 13).getFrontImage());
		
		// Two pair
		hands[index ++] = new JLabel(new Card(11).getFrontImage());
		hands[index ++] = new JLabel(new Card(11 + 13 + 13 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(3 + 13 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(3).getFrontImage());
		hands[index ++] = new JLabel(new Card(8 + 13).getFrontImage());
		
		// One pair
		hands[index ++] = new JLabel(new Card(12).getFrontImage());
		hands[index ++] = new JLabel(new Card(12 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(6).getFrontImage());
		hands[index ++] = new JLabel(new Card(3 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(11 + 13 + 13).getFrontImage());
		
		// High card
		hands[index ++] = new JLabel(new Card(8 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(6 + 13 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(4).getFrontImage());
		hands[index ++] = new JLabel(new Card(2 + 13).getFrontImage());
		hands[index ++] = new JLabel(new Card(0 + 13 + 13 + 13).getFrontImage());
		
		
		helpMenuPanel = new BackgroundPanel(background.getImage());
		helpMenuPanel.setBackgroundImageAutoResize(true);
		helpMenuPanel.setParentComponent(frame);
		helpMenuPanel.setLayout(layout);
		
		hands2 = new JLabel[hands.length / 5];
		
		hands2[0] = new JLabel("Royal Flush");
		hands2[1] = new JLabel("Straight Flush");
		hands2[2] = new JLabel("Four of a Kind");
		hands2[3] = new JLabel("Full House");
		hands2[4] = new JLabel("Flush");
		hands2[5] = new JLabel("Straight");
		hands2[6] = new JLabel("Three of a Kind");
		hands2[7] = new JLabel("Two pair");
		hands2[8] = new JLabel("One pair");
		hands2[9] = new JLabel("High card");
		
		for (int i = 0; i < hands2.length; i ++)
		{
			hands2[i].setVisible(i < 3);
			
			gbc.gridx = 0;
			gbc.gridy = (((i) * 2) % (3 * 2));
			
			gbc.gridwidth = 5;
			
			helpMenuPanel.add(hands2[i], gbc);
		}
		
		gbc.gridwidth = 1;
		
		for (int i = 0; i < hands.length; i ++)
		{
			hands[i].setVisible(i < 5 * 3);
			
			gbc.gridx = (((i % 5)));
			gbc.gridy = (((i / 5) * 2) % (3 * 2)) + 1;
			
			gbc.insets = new Insets(0, 0, 10, 0);
			
			helpMenuPanel.add(hands[i], gbc);
		}
		
		gbc.insets = new Insets(0, 0, 0, 0);
		
		next       = new JButton("Next");
		next.addActionListener(this);
		
		gbc.gridx  = 4;
		gbc.gridy  = 6;
		
		helpMenuPanel.add(next, gbc);
		
		
		previous   = new JButton("Prev");
		previous.addActionListener(this);
		previous.setVisible(false);
		
		gbc.gridx  = 0;
		gbc.gridy  = 6;
		
		helpMenuPanel.add(previous, gbc);
		
		
		exit   = new JButton("Exit");
		exit.addActionListener(this);
		
		gbc.gridx  = 0;
		gbc.gridy  = 6;
		
		helpMenuPanel.add(exit, gbc);
		
		
		frame.setContentPane(helpMenuPanel);
		
		frame.validate();
	}
	
	/**
	* Creates a poker game and switches to the view of one.
	*/
	public void createSinglePlayerPokerGame()
	{
		gameMode = SINGLE_PLAYER;
		
		interactiveButtonMode = 0;
		
		deck     = new Deck();
		
		player1  = new Player();
		player2  = new Player();
		
		player1.setName(player1NameTextField.getText());
		player2.setName(player2NameTextField.getText());
		
		dealCards(true);
		
		gamePanel = new BackgroundPanel(background.getImage());//, frame.getWidth(), frame.getHeight());
		gamePanel.setBackgroundImageAutoResize(true);
		gamePanel.setBackground(new Color(0, 160, 0));
		gamePanel.setParentComponent(frame);
		gamePanel.setSize(frame.getWidth(), frame.getHeight());
		gamePanel.setLayout(layout);
		
		player1Checked = new boolean[5];
		player2Checked = new boolean[5];
		
		buttonsPanel   = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2));
		buttonsPanel.setOpaque(false);
		
		gbc.gridx      = 0;
		gbc.gridy      = 3;
		
		gbc.gridwidth  = 5;
		
		gamePanel.add(buttonsPanel, gbc);
		
		gbc.gridwidth  = 1;
		
		statusPanel    = new JPanel();
		statusPanel.setLayout(layout);
		statusPanel.setOpaque(false);
		
		addButtonsToContentPanel();
		addLabelsToContentPanel();
		
		gbc.gridx      = 0;
		gbc.gridy      = 6;
		
		gbc.gridwidth  = 5;
		
		
		
		gamePanel.add(statusPanel, gbc);
		
		gbc.gridwidth = 1;
		
		calculateCards();
		
		frame.setContentPane(gamePanel);
		
		frame.validate();
	}
	
	/**
	* Creates a poker game and switches to the view of one.
	*/
	public void createServerMultiPlayerPokerGame()
	{
		gameMode = SERVER_MULTI_PLAYER;
		
		interactiveButtonMode = 0;
		
		deck     = new Deck();
		
		player1  = new Player();
		
		player1.setName(player1NameTextField.getText());
		
		dealCards(true);
		
		gamePanel = new BackgroundPanel(background.getImage());//, frame.getWidth(), frame.getHeight());
		gamePanel.setBackgroundImageAutoResize(true);
		gamePanel.setBackground(new Color(0, 160, 0));
		gamePanel.setParentComponent(frame);
		gamePanel.setSize(frame.getWidth(), frame.getHeight());
		gamePanel.setLayout(layout);
		
		player1Checked = new boolean[5];
		player2Checked = new boolean[5];
		
		buttonsPanel   = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2));
		buttonsPanel.setOpaque(false);
		
		gbc.gridx      = 0;
		gbc.gridy      = 3;
		
		gbc.gridwidth  = 5;
		
		gamePanel.add(buttonsPanel, gbc);
		
		gbc.gridwidth  = 1;
		
		statusPanel    = new JPanel();
		statusPanel.setLayout(layout);
		statusPanel.setOpaque(false);
		
		addButtonsToContentPanel();
		addLabelsToContentPanel();
		
		gbc.gridx      = 0;
		gbc.gridy      = 6;
		
		gbc.gridwidth  = 5;
		
		
		
		gamePanel.add(statusPanel, gbc);
		
		gbc.gridwidth = 1;
		
		calculateCards();
		
		frame.setContentPane(gamePanel);
		
		frame.validate();
	}
	
	/**
	* Creates a poker game and switches to the view of one.
	*/
	public void createClientMultiPlayerPokerGame()
	{
		gameMode = CLIENT_MULTI_PLAYER;
		
		interactiveButtonMode = 0;
		
		deck     = new Deck();
		
		player1  = new Player();
		
		player1.setName(player1NameTextField.getText());
		
		dealCards(true);
		
		gamePanel = new BackgroundPanel(background.getImage());
		gamePanel.setBackgroundImageAutoResize(true);
		gamePanel.setBackground(new Color(0, 160, 0));
		gamePanel.setParentComponent(frame);
		gamePanel.setSize(frame.getWidth(), frame.getHeight());
		gamePanel.setLayout(layout);
		
		player1Checked = new boolean[5];
		player2Checked = new boolean[5];
		
		buttonsPanel   = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2));
		buttonsPanel.setOpaque(false);
		
		gbc.gridx      = 0;
		gbc.gridy      = 3;
		
		gbc.gridwidth  = 5;
		
		gamePanel.add(buttonsPanel, gbc);
		
		gbc.gridwidth  = 1;
		
		statusPanel    = new JPanel();
		statusPanel.setLayout(layout);
		statusPanel.setOpaque(false);
		
		addButtonsToContentPanel();
		addLabelsToContentPanel();
		
		gbc.gridx      = 0;
		gbc.gridy      = 6;
		
		gbc.gridwidth  = 5;
		
		
		
		gamePanel.add(statusPanel, gbc);
		
		gbc.gridwidth = 1;
		
		calculateCards();
		
		frame.setContentPane(gamePanel);
		
		frame.validate();
	}
	
	/**
	* Adds the labels used in the game to the buttonsPanel.
	*/
	public void addLabelsToContentPanel()
	{
		p1Side = new JPanel();
		p1Side.setLayout(layout);
		p1Side.setOpaque(false);
		
		p2Side = new JPanel();
		p2Side.setLayout(layout);
		p2Side.setOpaque(false);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		// Blank space
		statusPanel.add(new JLabel("                             "), gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		
		statusPanel.add(new JLabel("                             "), gbc);
		//
		
		gameVictorLabel = new JLabel("Press " + interactiveButton.getText() + "!");
		
		gbc.gridx       = 0;
		gbc.gridy       = 0;
		
		gbc.insets      = new Insets(20, 0, 0, 0);
		
		gbc.gridwidth   = 4;
		
		statusPanel.add(gameVictorLabel, gbc);
		
		gbc.insets      = new Insets(0, 0, 0, 0);
		
		gbc.gridwidth   = 1;
		
		
		if (gameMode == SERVER_MULTI_PLAYER || gameMode == CLIENT_MULTI_PLAYER)
		{
			gameStatusLabel = new JLabel();
			
			gbc.gridx       = 0;
			gbc.gridy       = 1;
			
			gbc.insets      = new Insets(0, 0, 10, 0);
			
			gbc.gridwidth   = 4;
			
			statusPanel.add(gameStatusLabel, gbc);
			
			gbc.insets      = new Insets(0, 0, 0, 0);
			
			gbc.gridwidth   = 1;
		}
		
		
		// Left side
		player1ScoreboardNameLabel = new JLabel(player1.getName());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		p1Side.add(player1ScoreboardNameLabel, gbc);
		
		player1ScoreboardScoreLabel = new JLabel(player1.getWins() + "");
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		p1Side.add(player1ScoreboardScoreLabel, gbc);
		//
		
		// Right side
		player2ScoreboardNameLabel = new JLabel(player2 == null ? "" : player2.getName());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		p2Side.add(player2ScoreboardNameLabel, gbc);
		
		player2ScoreboardScoreLabel = new JLabel((player2 == null ? 0 : player2.getWins()) + "");
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		p2Side.add(player2ScoreboardScoreLabel, gbc);
		//
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		gbc.insets = new Insets(0, 0, 0, 20);
		
		gbc.gridwidth = 2;
		
		statusPanel.add(p1Side, gbc);
		
		gbc.gridwidth = 1;
		
		gbc.insets = new Insets(0, 0, 0, 0);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		
		gbc.gridwidth = 2;
		
		statusPanel.add(p2Side, gbc);
		
		gbc.gridwidth = 1;
	}

	/** 
	* Adds all of the buttons used in the game to the gamePanel.
	*/
	public void addButtonsToContentPanel()
	{
		player1CardButtons = new JButton[5];
		player2CardButtons = new JButton[5];
		
		if (player1 != null && player2 != null)
		{
			for (int i = 0; i < 5; i ++)
			{
				Card card = player1.getCardFromHand(i);
				
				player1CardButtons[i] = new JButton(card.getFrontImage());
				player1CardButtons[i].setOpaque(false);
				player1CardButtons[i].setContentAreaFilled(false);
				player1CardButtons[i].setBorderPainted(false);
				player1CardButtons[i].setBorder(
						BorderFactory.createEmptyBorder(0, 0, 0, 0));
				
				gbc.gridx = i;
				gbc.gridy = 5;
				
				gamePanel.add(player1CardButtons[i], gbc);
				
				player1CardButtons[i].addActionListener(this);
			}
			
			for (int i = 0; i < 5; i ++)
			{
				Card card = player2.getCardFromHand(i);
				
				player2CardButtons[i] = new JButton(card.getFrontImage());
				player2CardButtons[i].setOpaque(false);
				player2CardButtons[i].setContentAreaFilled(false);
				player2CardButtons[i].setBorderPainted(false);
				player2CardButtons[i].setBorder(
						BorderFactory.createEmptyBorder(0, 0, 0, 0));
				
				gbc.gridx = i;
				gbc.gridy = 0;
				
				gamePanel.add(player2CardButtons[i], gbc);
				
				player2CardButtons[i].addActionListener(this);
			}
		}
		else
		{
			Card card = new Card(0);
			
			for (int i = 0; i < 5; i ++)
			{
				player1CardButtons[i] = new JButton(card.getBackImage());
				player1CardButtons[i].setOpaque(false);
				player1CardButtons[i].setContentAreaFilled(false);
				player1CardButtons[i].setBorderPainted(false);
				player1CardButtons[i].setBorder(
						BorderFactory.createEmptyBorder(0, 0, 0, 0));
				
				gbc.gridx = i;
				gbc.gridy = 5;
				
				gamePanel.add(player1CardButtons[i], gbc);
				
				player1CardButtons[i].addActionListener(this);
			}
			
			for (int i = 0; i < 5; i ++)
			{
				player2CardButtons[i] = new JButton(card.getBackImage());
				player2CardButtons[i].setOpaque(false);
				player2CardButtons[i].setContentAreaFilled(false);
				player2CardButtons[i].setBorderPainted(false);
				player2CardButtons[i].setBorder(
						BorderFactory.createEmptyBorder(0, 0, 0, 0));
				
				gbc.gridx = i;
				gbc.gridy = 0;
				
				gamePanel.add(player2CardButtons[i], gbc);
				
				player2CardButtons[i].addActionListener(this);
			}
		}
		
		interactiveButton  = new JButton("Results");
		interactiveButton.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		buttonsPanel.add(interactiveButton, gbc);
		
		switchCardsButton  = new JButton("Switch");
		switchCardsButton.addActionListener(this);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		buttonsPanel.add(switchCardsButton, gbc);
		
		checkmark = new ImageIcon("res/images/checkmark.png");
		blankCheckmark = new ImageIcon("res/images/blankCheckmark.png");
		
		player1CheckButtons = new JButton[5];
		player2CheckButtons = new JButton[5];
		
		for (int i = 0; i < player1CheckButtons.length; i ++)
		{
			player1CheckButtons[i] = new JButton();
			player1CheckButtons[i].setOpaque(false);
			player1CheckButtons[i].setContentAreaFilled(false);
			player1CheckButtons[i].setBorderPainted(false);
			player1CheckButtons[i].setBorder(
					BorderFactory.createEmptyBorder(0, 0, 0, 0));
			
			gbc.gridx = i;
			gbc.gridy = 4;
			
			gamePanel.add(player1CheckButtons[i], gbc);
			
			player2CheckButtons[i] = new JButton();
			player2CheckButtons[i].setOpaque(false);
			player2CheckButtons[i].setContentAreaFilled(false);
			player2CheckButtons[i].setBorderPainted(false);
			player2CheckButtons[i].setBorder(
					BorderFactory.createEmptyBorder(0, 0, 0, 0));
			
			gbc.gridx = i;
			gbc.gridy = 1;
			
			gamePanel.add(player2CheckButtons[i], gbc);
		}
		
		calculateChecks();
	}
	
	/**
	* Calculates what to show on the help menu based on what page
	* the menu is on.
	*/
	private void calculateHelpMenu()
	{
		for (int i = 0; i < hands2.length; i ++)
		{
			hands2[i].setVisible(false);
		}
		
		for (int i = 0; i < hands.length; i ++)
		{
			hands[i].setVisible(false);
		}
		
		for (int i = 0; i < hands2.length; i ++)
		{
			hands2[i].setVisible(i > 3 * (page - 1) - 1 && i < 3 * page);
		}
		
		for (int i = 0; i < hands.length; i ++)
		{
			hands[i].setVisible(i > (5 * 3) * (page - 1) - 1 && i < (5 * 3) * page);
		}
		
		if (page > 1)
		{
			previous.setVisible(true);
			exit.setVisible(false);
			
			if (page > 3)
			{
				next.setVisible(false);
			}
		}
		
		if (page < 4)
		{
			next.setVisible(true);
			
			if (page < 2)
			{
				previous.setVisible(false);
				exit.setVisible(true);
			}
		}
	}
	
	/**
	* Sets the icons on the cards to what game configuration you
	* are playing.
	*/
	public void calculateCards()
	{
		for (int i = 0; i < player1CardButtons.length; i ++)
		{
			Card card1 = player1.getCardFromHand(i);
			
			player1CardButtons[i].setIcon(card1.getFrontImage());
			
			Card card2 = player2 == null ? new Card(0) : player2.getCardFromHand(i);
			
			ImageIcon image = null;
			
			if (gameMode == SINGLE_PLAYER)
			{
				image = card2.getBackImage();
			}
			else if (gameMode == LOCAL_MULTI_PLAYER)
			{
				image = card2.getFrontImage();
			}
			else if (gameMode == SERVER_MULTI_PLAYER || gameMode == CLIENT_MULTI_PLAYER)
			{
				image = card2.getBackImage();
			}
			
			player2CardButtons[i].setIcon(image);
		}
	}
	
	/**
	* Sets the icons of the checks according to whether they
	* have been clicked or not.
	*/
	private void calculateChecks()
	{
		for (int i = 0; i < player1CheckButtons.length; i ++)
		{
			if (player1Checked[i])
			{
				player1CheckButtons[i].setIcon(checkmark);
			}
			else
			{
				player1CheckButtons[i].setIcon(blankCheckmark);
			}
			
			if (player2Checked[i])
			{
				player2CheckButtons[i].setIcon(checkmark);
			}
			else
			{
				player2CheckButtons[i].setIcon(blankCheckmark);
			}
		}
	}

	/**
	* Handles what to do when a button is pressed.
	*/
	public void actionPerformed(ActionEvent e)
	{
		frame.requestFocus();
		
		if (e.getSource() == createServerMenuButton)
		{
			createCreateServerMenu();
		}
		else if (e.getSource() == connectServerMenuButton)
		{
			createConnectServerMenu();
		}
		else if (e.getSource() == createServerButton)
		{
			server = new Server(9999, this);
					
			new Thread()
			{
				public void run()
				{
					createServerMultiPlayerPokerGame();
					
					boolean connection = server.waitPlayer();
					
					if (connection)
					{
						System.out.println("Successfully accepted player!");
					}
					else
					{
						System.out.println("Problem accepting player!");
					}
				}
			}.start();
			
			network = server;
		}
		else if (e.getSource() == connectServerButton)
		{
			thisPokerGame = this;
			
			new Thread()
			{
				public void run()
				{
					client = new Client(serverIPTextField.getText(), 9999, thisPokerGame);
					
					createClientMultiPlayerPokerGame();
					
					client.start();
					
					network = client;
				}
			}.start();
		}
		else if (e.getSource() == multiPlayerButton)
		{
			createMultiPlayerMenu();
		}
		else if (e.getSource() == exit)
		{
			frame.setContentPane(mainMenuPanel);
		}
		else if (e.getSource() == next)
		{
			page = (page + 1) % 5;
			
			calculateHelpMenu();
		}
		else if (e.getSource() == previous)
		{
			page = page - 1 < 1 ? 1 : page - 1;
			
			calculateHelpMenu();
		}
		else if (e.getSource() == help)
		{
			createHelpMenu();
		}
		else if (e.getSource() == start)
		{
			createSinglePlayerPokerGame();
		}
		else if (e.getSource() == singlePlayerButton)
		{
			createSinglePlayerMenu();
		}
		else if (e.getSource() == switchCardsButton)
		{
			if (interactiveButtonMode == 1)
			{
				return;
			}
			
			boolean p1swt = player1.getSwitched();
			boolean p2swt = player2 == null ? false : player2.getSwitched();
			
			for (int i = 0; i < player1Checked.length; i ++)
			{
				if (!player1.getSwitched() && player1Checked[i])
				{
					player1.setCardInHand(deck.dealCard(), i);
					player1Checked[i] = false;
					
					p1swt = true;
				}
				else
				{

				}
				if (player2 != null && !player2.getSwitched() && player2Checked[i])
				{
					player2.setCardInHand(deck.dealCard(), i);
					player2Checked[i] = false;
					
					p2swt = true;
				}
				else
				{

				}
			}
			
			player1.setSwitched(p1swt);
			
			if (player2 != null)
			{
				player2.setSwitched(p2swt);
			}
			
			calculateChecks();
			calculateCards();
		}
		else if (e.getSource() == interactiveButton)
		{
			if (gameMode == SERVER_MULTI_PLAYER || gameMode == CLIENT_MULTI_PLAYER)
			{
				if (!playerConnected)
				{
					return;
				}
				else if (player1.getReadyForResults() || player1.getReadyToDeal())
				{
					return;
				}
			}
			
			if (dealingCards)
			{
				return;
			}
			
			if (interactiveButtonMode == 1)
			{
				player1.setReadyToDeal(true);
				
				if (gameMode == SERVER_MULTI_PLAYER || gameMode == CLIENT_MULTI_PLAYER)
				{
					network.sendRequest(Packet.DEAL);
				}
				else if (gameMode == SINGLE_PLAYER || gameMode == LOCAL_MULTI_PLAYER)
				{
					deal();
				}
			}
			else if (interactiveButtonMode == 0)
			{
				if (network != null)
				{
					int playerHand[] = new int[5];
					
					for (int i = 0; i < 5; i ++)
					{
						playerHand[i] = player1.getCardFromHand(i).getRank();
					}
				
					network.sendHand(playerHand, 2);
				}
				
				player1.setReadyForResults(true);
				
				
				
				if (gameMode == SERVER_MULTI_PLAYER || gameMode == CLIENT_MULTI_PLAYER)
				{
					network.sendRequest(Packet.RESULTS);
				}
				else if (gameMode == SINGLE_PLAYER || gameMode == LOCAL_MULTI_PLAYER)
				{
					results();
				}
			}
		}
		
		if (player1CheckButtons != null)
		{
			for (int i = 0; i < player1CheckButtons.length; i ++)
			{
				if (interactiveButtonMode == 1)
				{
					return;
				}
				
				if (e.getSource() == player1CheckButtons[i] ||
						e.getSource() == player1CardButtons[i])
				{
					if (player1.getSwitched())
					{
						return;
					}
					
					if (player1Checked[i])
					{
						player1CheckButtons[i].setIcon(blankCheckmark);
					}
					else
					{
						player1CheckButtons[i].setIcon(checkmark);
					}
					
					player1Checked[i] = !player1Checked[i];
				}
				else if ((gameMode == LOCAL_MULTI_PLAYER) && (e.getSource() == player2CheckButtons[i] ||
						e.getSource() == player2CardButtons[i]))
				{
					if (player2 == null || gameMode == SINGLE_PLAYER || player2.getSwitched())
					{
						return;
					}
					
					if (player2Checked[i])
					{
						player2CheckButtons[i].setIcon(blankCheckmark);
					}
					else
					{
						player2CheckButtons[i].setIcon(checkmark);
					}
					
					player2Checked[i] = !player2Checked[i];
				}
			}
		}
	}
	
	/**
	* Deals the cards in a pretty looking fashion.
	*/
	public void deal()
	{
		if (gameMode == SERVER_MULTI_PLAYER || gameMode == CLIENT_MULTI_PLAYER)
		{
			gameStatusLabel.setText("");
		}
		
		dealCards((shufflePhase = (shufflePhase + 1) % 5) == 0);
		
		putCardsOnTable();
		
		interactiveButton.setText("Results");
		
		player1.setSwitched(false);
		player2.setSwitched(false);
		
		player1.setReadyToDeal(false);
		player2.setReadyToDeal(false);
		
		interactiveButtonMode = (interactiveButtonMode + 1) % 2;
		
		dealt                 = true;
		shownResults          = false;
	}
	
	/**
	* Flips the cards to face up, and displays the winner out of the
	* two.
	*/
	public void results()
	{
		flipCards(true, true);
		
		HandInfo p1hand = player1.getHand();
		HandInfo p2hand = player2.getHand();
		
		HandInfo winningHand = HandInfo.compare(p1hand, p2hand);
		
		if (winningHand == p1hand)
		{
			gameVictorLabel.setText(player1.getName() + " wins with " + player1.getHandText());
			
			player1.addWin();
		}
		else if (winningHand == p2hand)
		{
			gameVictorLabel.setText(player2.getName() + " wins with " + player2.getHandText());
			
			player2.addWin();
		}
		else
		{
			gameVictorLabel.setText("Tie!");
		}
		
		for (int i = 0; i < player1Checked.length; i ++)
		{
			player1Checked[i] = false;
			player2Checked[i] = false;
		}
		
		player1ScoreboardScoreLabel.setText("" + player1.getWins());
		player2ScoreboardScoreLabel.setText("" + player2.getWins());
		
		calculateChecks();
		
		interactiveButton.setText("Deal");
		
		player1.setReadyForResults(false);
		player2.setReadyForResults(false);
		
		interactiveButtonMode = (interactiveButtonMode + 1) % 2;
		
		dealt        = false;
		shownResults = true;
	}
	
	/**
	* Flips the cards over depending on the parameters given.
	* If true, then it shows the front image.
	* 
	* @param plr1 whether to show front side or not
	* @param plr2 whether to show front side or not
	*/
	public void flipCards(boolean plr1, boolean plr2)
	{
		for (int i = 0; i < player1CardButtons.length; i ++)
		{
			Card card1 = player1.getCardFromHand(i);
			
			ImageIcon image = null;
			
			if (!plr1)
			{
				image = card1.getBackImage();
			}
			else
			{
				image = card1.getFrontImage();
			}
			
			player1CardButtons[i].setIcon(image);
			
			Card card2 = player2.getCardFromHand(i);
			
			ImageIcon image2 = null;
			
			if (!plr2)
			{
				image2 = card2.getBackImage();
			}
			else
			{
				image2 = card2.getFrontImage();
			}
			
			player2CardButtons[i].setIcon(image2);
		}
	}
	
	/**
	* Deals new cards to each of the players.
	* 
	* @param shuffle Whether to shuffle the deck or not
	*/
	public void dealCards(boolean shuffle)
	{
		if (shuffle)
		{
			deck.shuffle();
		}
		
		int player1Hand[] = null;
		int player2Hand[] = null;
		
		if (gameMode == SERVER_MULTI_PLAYER)
		{
			player1Hand = new int[5];
			
			if (player2 != null)
			{
				player2Hand = new int[5];
			}
		}
		
		for (int i = 0; i < 5; i ++)
		{
			if (gameMode == SINGLE_PLAYER)
			{
				player1.setCardInHand(deck.dealCard(), i);
				
				player2.setCardInHand(deck.dealCard(), i);
			}
			else if (gameMode == SERVER_MULTI_PLAYER)
			{
				Card p1Card = deck.dealCard();
				
				player1Hand[i] = p1Card.getRank();
				
				player1.setCardInHand(p1Card, i);
				
				if (player2 != null)
				{
					Card p2Card = deck.dealCard();
					
					player2Hand[i] = p2Card.getRank();
					
					player2.setCardInHand(p2Card, i);
				}
			}
			else if (player2 == null && gameMode == CLIENT_MULTI_PLAYER)
			{
				Card p1Card = deck.dealCard();
				
				player1.setCardInHand(p1Card, i);
			}
			else if (gameMode == LOCAL_MULTI_PLAYER)
			{
				player2.setCardInHand(deck.dealCard(), i);
			}
		}
		
		if (player2 != null && gameMode == SERVER_MULTI_PLAYER)
		{
			server.sendHand(player1Hand, 2);
			server.sendHand(player2Hand, 1);
		}
	}
	
	/**
	* This method recalculates the images that show on the cards.
	* It also uses a thread to create an effect as if the dealer
	* was actually dealing the cards.
	*/
	public void putCardsOnTable()
	{
		new Thread()
		{
			public void run()
			{
				dealingCards = true;
				
				if (gameMode == CLIENT_MULTI_PLAYER)
				{
					while (!client.hasReceivedCards())
					{
						try
						{
							Thread.sleep(1);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}
				
				for (int i = 0; i < 5; i ++)
				{
					Card card1 = player1.getCardFromHand(i);
					
					player1CardButtons[i].setIcon(card1.getFrontImage());
					
					try
					{
						Thread.sleep(200);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					
					Card card2 = player2.getCardFromHand(i);
					
					ImageIcon image = null;
					
					if (gameMode == SINGLE_PLAYER)
					{
						image = card2.getBackImage();
					}
					else if (gameMode == LOCAL_MULTI_PLAYER)
					{
						image = card2.getFrontImage();
					}
					else if (gameMode == SERVER_MULTI_PLAYER || gameMode == CLIENT_MULTI_PLAYER)
					{
						image = new Card(0).getBackImage();
					}
					
					player2CardButtons[i].setIcon(image);
					
					try
					{
						Thread.sleep(200);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				
				if (gameMode == CLIENT_MULTI_PLAYER)
				{
					client.setReceivedCards(false);
				}
				
				dealingCards = false;
			}
		}.start();
	}
	
	/**
	* Creates a message in the status bar that alerts the user that
	* he is waiting on player2.
	* 
	* @param waiting Whether he is waiting or not.
	*/
	public void setWaitingOnPlayer(boolean waiting)
	{
		if (waitingOnPlayer && waiting)
		{
			return;
		}
		
		this.waitingOnPlayer = waiting;
		
		if (waiting)
		{
			new Thread()
			{
				public void run()
				{
					String periods = "";
					
					while (waitingOnPlayer)
					{
						gameStatusLabel.setText("Waiting on " + player2.getName() + "" + periods);
						
						periods += ".";
						
						periods = periods.length() >= 4 ? "" : periods;
						
						try
						{
							Thread.sleep(250);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
					gameStatusLabel.setText("");
				}
			}.start();
		}
	}
	
	/**
	* Creates a message on the status bar that tells the player that
	* player2 is waiting on him to move.
	* 
	* @param waiting Whether he is waiting or not.
	*/
	public void setPlayerWaitingOn(boolean waiting)
	{
		if (playerWaitingOn && waiting)
		{
			return;
		}
		
		this.playerWaitingOn = waiting;
		
		if (waiting)
		{
			new Thread()
			{
				public void run()
				{
					String periods = "";
					
					while (playerWaitingOn)
					{
						gameStatusLabel.setText(player2.getName() + " is waiting on you" + periods);
						
						periods += ".";
						
						periods = periods.length() >= 4 ? "" : periods;
						
						try
						{
							Thread.sleep(250);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
					gameStatusLabel.setText("");
				}
			}.start();
		}
	}
	
	/**
	* Sets the opponent (player2) in the game. Used only in
	* multi-player situations.
	* 
	* @param p What to set player2 to.
	*/
	public void setOpponent(Player p)
	{
		this.player2 = p;

		player2ScoreboardNameLabel.setText (player2.getName());
		player2ScoreboardScoreLabel.setText(player2.getWins() + "");
	}
	
	/**
	* Set whether the opponent has connected. Used only in multi-player
	* situations.
	* 
	* @param connected Whether or not the player is connected.
	*/
	public void setPlayerConnected(boolean connected)
	{
		this.playerConnected = connected;
	}

	/**
	* Gets the player1 object.
	* 
	* @return The player1 object.
	*/
	public Player getPlayer1()
	{
		return player1;
	}
	
	/**
	* Gets the player2 object.
	* 
	* @return The player2 object.
	*/
	public Player getPlayer2()
	{
		return player2;
	}
	
	/**
	* Returns whether the player has dealt the cards. Useful
	* so that the player can't exploit the program by spamming the
	* deal button.
	* 
	* @return Whether he has dealt.
	*/
	public boolean hasDealt()
	{
		return dealt;
	}
	
	/**
	* Returns whether the player has shown the results. Useful
	* so that the player can't exploit the program by spamming the
	* results button.
	* 
	* @return Whether he has shown the results.
	*/
	public boolean hasShownResults()
	{
		return shownResults;
	}
	
	/**
	* Method used so you can retrieve the Deck that the PokerGame
	* uses for some purpose.
	* 
	* @return The deck that the PokerGame uses.
	*/
	public Deck getDeck()
	{
		return deck;
	}

	/**
	* What to do when a key is typed. (Pressed and then Released)
	*/
	public void keyTyped(KeyEvent e)
	{
		
	}

	/**
	* What to do when a key is pressed.
	*/
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		
		if (e.getSource() == player1NameTextField)
		{
			String text = player1NameTextField.getText();
			
			if (text.length() > 15)
			{
				player1NameTextField.setText(text.substring(0, 16));
			}
		}
		
		else if (e.getSource() == player2NameTextField)
		{
			String text = player2NameTextField.getText();
			
			if (text.length() > 15)
			{
				player2NameTextField.setText(text.substring(0, 16));
			}
		}
		
		if (code == KeyEvent.VK_ESCAPE)
		{
			createMainMenu();
		}
	}

	/**
	* What to do when a key is released.
	*/
	public void keyReleased(KeyEvent e)
	{
		
	}
}