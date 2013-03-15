package net.foxycorndog.thedigginggame.launcher.menu;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.foxycorndog.jfoxylib.Display;
import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.components.Button;
import net.foxycorndog.jfoxylib.components.Component;
import net.foxycorndog.jfoxylib.components.Image;
import net.foxycorndog.jfoxylib.components.Panel;
import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.events.FrameEvent;
import net.foxycorndog.jfoxylib.events.FrameListener;
import net.foxycorndog.jfoxylib.font.Font;

public class UpdateMenu extends Menu
{
	private String	text;
	
	private Image	backgroundImage;
	
	private Button	yesButton, noButton;
	
	private Font	font;
	
	public UpdateMenu(String text, Font font, Panel parent)
	{
		super(parent);
		
		this.text = text;
		
		this.font = font;
		
		BufferedImage smallButtonImage      = null;
		BufferedImage smallButtonHoverImage = null;
		BufferedImage background            = null;
		
		try
		{
			smallButtonImage      = ImageIO.read(new File("res/images/GUI/SmallButton.png"));
			smallButtonHoverImage = ImageIO.read(new File("res/images/GUI/SmallButtonHover.png"));
			background            = ImageIO.read(new File("res/images/background.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		int max = Math.max(Display.getWidth(), Display.getHeight());
		
		setSize(Frame.getWidth(), Frame.getHeight());
		
		Frame.addFrameListener(new FrameListener()
		{
			public void frameResized(FrameEvent e)
			{
				setSize(Frame.getWidth(), Frame.getHeight());
			}
		});
		
		yesButton = new Button(this);
		yesButton.setImage(smallButtonImage);
		yesButton.setHoverImage(smallButtonHoverImage);
		yesButton.setFont(font);
		yesButton.setText("Yes");
		yesButton.setAlignment(Component.CENTER, Component.CENTER);
		yesButton.setLocation(-50, -10);
		
		noButton = new Button(this);
		noButton.setImage(smallButtonImage);
		noButton.setHoverImage(smallButtonHoverImage);
		noButton.setFont(font);
		noButton.setText("No");
		noButton.setAlignment(Component.CENTER, Component.CENTER);
		noButton.setLocation(50, -10);
		
		backgroundImage = new Image(this);
		backgroundImage.setSize(max, max);
		backgroundImage.setImage(background, 75, 75);
		
		ButtonListener buttonListener = new ButtonListener()
		{
			public void buttonUnHovered(ButtonEvent event)
			{
				
			}
			
			public void buttonReleased(ButtonEvent event)
			{
				if (event.getSource() == yesButton)
				{
					
				}
				else if (event.getSource() == noButton)
				{
					dispose();
				}
			}
			
			public void buttonPressed(ButtonEvent event)
			{
				
			}
			
			public void buttonHovered(ButtonEvent event)
			{
				
			}
		};
		
		yesButton.addButtonListener(buttonListener);
		noButton.addButtonListener(buttonListener);
	}
	
	public void render()
	{
		super.render();
		
		font.render(text, 0, 40, 1, 1, Font.CENTER, Font.CENTER, null);
	}
}