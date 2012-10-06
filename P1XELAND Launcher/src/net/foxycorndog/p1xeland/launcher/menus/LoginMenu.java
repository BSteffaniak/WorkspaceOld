package net.foxycorndog.p1xeland.launcher.menus;

import java.net.UnknownHostException;

import net.foxycorndog.jdoogl.Color;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Component;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.components.ImageButton;
import net.foxycorndog.jdoogl.components.Menu;
import net.foxycorndog.jdoogl.components.Frame.Alignment;
import net.foxycorndog.jdoogl.components.ImageTextField;
import net.foxycorndog.jdoogl.image.Image;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoutil.web.WebPage;
import net.foxycorndog.p1xeland.launcher.Launcher;

public class LoginMenu extends Menu
{
	private boolean        rUp, gUp, bUp;
	private boolean        back;
	
	private int            r, g, b;
	
	private String         status;
	
	private Color          labelColor;
	
	private Image          logo;
	private Image          background;
	
	private ImageButton    login, quit, options;
	
	private ImageTextField username, password;
	
	private Launcher       launcher;
	
	private Menu           optionsMenu;
	
	public LoginMenu(Launcher launcher)
	{
		this.launcher = launcher;
		
		background = new Image(new Texture("res/images/Background.png", "PNG", true, false), 199, 199);
		
		login = new ImageButton(new Texture("res/images/GUI/Button.png", "PNG", true, false));
		login.setHoverImageMap(new Texture("res/images/GUI/ButtonHover.png", "PNG", true, false));
		login.setText("Login");
		login.setLocation(0, -45);
		login.setAlignment(Alignment.LEFT, Alignment.CENTER);
		login.addActionListener(this);
		Frame.add(login);
		login.render();
		
		quit = new ImageButton(new Texture("res/images/GUI/SmallButton.png", "PNG", true, false));
		quit.setHoverImageMap(new Texture("res/images/GUI/SmallButtonHover.png", "PNG", true, false));
		quit.setText("Quit");
		quit.setLocation(login.getWidth() - quit.getWidth(), -70);
		quit.setAlignment(Alignment.LEFT, Alignment.CENTER);
		quit.addActionListener(this);
		Frame.add(quit);
		
		options = new ImageButton(new Texture("res/images/GUI/SmallButton.png", "PNG", true, false));
		options.setHoverImageMap(new Texture("res/images/GUI/SmallButtonHover.png", "PNG", true, false));
		options.setText("Options");
		options.setLocation(0, -70);
		options.setAlignment(Alignment.LEFT, Alignment.CENTER);
		options.addActionListener(this);
		Frame.add(options);
		
		username = new ImageTextField(new Texture("res/images/GUI/TextField.png", "PNG", true, false));
		username.setHoverImageMap(new Texture("res/images/GUI/TextFieldHover.png", "PNG", true, false));
		username.setLocation(0, 10);
		username.setAlignment(Alignment.LEFT, Alignment.CENTER);
		username.setMaxLength(14);
		Frame.add(username);
		
		password = new ImageTextField(new Texture("res/images/GUI/TextField.png", "PNG", true, false));
		password.setHoverImageMap(new Texture("res/images/GUI/TextFieldHover.png", "PNG", true, false));
		password.setLocation(0, -20);
		password.setAlignment(Alignment.LEFT, Alignment.CENTER);
		password.setMaxLength(150);
		password.setCharacterMask('*');
		Frame.add(password);
		
		logo     = new Image(new Texture("res/images/Logo.png", "PNG", true, false));
		logo.setLocation(0, 155);
		logo.setAlignment(Alignment.CENTER, Alignment.CENTER);
		
		username.setFocused(true);
		
		r = (int)(Math.random() * 256);
		g = (int)(Math.random() * 256);
		b = (int)(Math.random() * 256);
		
		r = r <= 100 ? 100 : r;
		g = g <= 100 ? 100 : g;
		b = b <= 100 ? 100 : b;
	}
	
	@Override
	public void render()
	{
		GL.beginManipulation();
		{
			GL.beginManipulation();
			{
				GL.translatef(0, 0, -1);
				
				background.render();
			}
			GL.endManipulation();
			
			GL.translatef(Frame.getWidth() / 3f / 2f - login.getWidth() / 2f, 0, 0);
			
			if (back)
			{
				back = false;
				
				login.addActionListener(this);
				quit.addActionListener(this);
				options.addActionListener(this);
				username.addActionListener(this);
				password.addActionListener(this);
			}
			
			if (optionsMenu != null)
			{
				optionsMenu.render();
			}
			else
			{
				GL.beginManipulation();
				{
					GL.translatef(-(Frame.getWidth() / 3f / 2f - login.getWidth() / 2f), 0, 0);
					
					GL.scalef(1 / 3f, 1 / 3f, 1);
					
//					GL.translatef(Frame.getWidth() / 2f - logo.getWidth() / 2f, Frame.getHeight() - logo.getHeight() - 55, 0);
					
					logo.render();
				}
				GL.endManipulation();
				
				login.render();
				quit.render();
				options.render();
				username.render();
				password.render();
				
				float scaleX = (float)GL.getAmountScaled()[0];
				float scaleY = (float)GL.getAmountScaled()[1];
				
				GL.beginManipulation();
				{
					GL.translatef(0, 0, 10);
					
					r = rUp ? r + 1 : r - 1;
					r = r >= 256 ? 255 : r;
					r = r <= 100 ? 100 : r;
					
					g = gUp ? g + 1 : g - 1;
					g = g >= 256 ? 255 : g;
					g = g <= 100 ? 100 : g;
					
					b = bUp ? b + 1 : b - 1;
					b = b >= 256 ? 255 : b;
					b = b <= 100 ? 100 : b;
					
					if ((int)(Math.random() * 100) == 0)
					{
						rUp = !rUp;
					}
					if ((int)(Math.random() * 100) == 0)
					{
						gUp = !gUp;
					}
					if ((int)(Math.random() * 100) == 0)
					{
						bUp = !bUp;
					}
					
					labelColor = new Color(r, g, b);
					
					Frame.renderText(-3, Frame.getHeight() / scaleY - username.getScreenY() / scaleY - 1 - (username.getScreenHeight() / scaleY) - 6, "Username", labelColor, 1);
					Frame.renderText(-2, Frame.getHeight() / scaleY - username.getScreenY() / scaleY - 1 - (username.getScreenHeight() / scaleY) - 5, "Username", Color.BLACK, 1);
					
					Frame.renderText(-3, Frame.getHeight() / scaleY - password.getScreenY() / scaleY - 1 - (password.getScreenHeight() / scaleY) - 6, "Password", labelColor, 1);
					Frame.renderText(-2, Frame.getHeight() / scaleY - password.getScreenY() / scaleY - 1 - (password.getScreenHeight() / scaleY) - 5, "Password", Color.BLACK, 1);
				
					if (status != null && !status.equals(""))
					{
						Frame.renderText(0, Frame.getHeight() / scaleY - password.getScreenY() / scaleY - 1 + 3, status, Color.WHITE, 1);
					}
				}
				GL.endManipulation();
			}
		}
		GL.endManipulation();
	}

	@Override
	public void destroy()
	{
		login.removeActionListener(this);
		quit.removeActionListener(this);
		options.removeActionListener(this);
		username.removeActionListener(this);
		password.removeActionListener(this);
		
		Frame.remove(login);
		Frame.remove(quit);
		Frame.remove(options);
		Frame.remove(username);
		Frame.remove(password);
		
		login    = null;
		quit     = null;
		options  = null;
		username = null;
		password = null;
	}

	@Override
	public void onActionPerformed(Component source)
	{
		if (source == login)
		{
			boolean loggedIn = false;
			
			try
			{
				loggedIn = WebPage.getFirstOutput("http://crapbuntu/braden/login/login.php?username=" + username.getText() + "&password=" + password.getText()).equals("true");
			
				if (loggedIn)
				{
					launcher.launch();
				}
				else
				{
					setStatus("Invalid login.");
				}
			}
			catch (Exception e)
			{
				if (e instanceof RuntimeException)
				{
					setStatus("Could not connect to server.");
				}
			}
		}
		else if (source == options)
		{
			login.removeActionListener(this);
			quit.removeActionListener(this);
			options.removeActionListener(this);
			username.removeActionListener(this);
			password.removeActionListener(this);
			
			optionsMenu = new OptionsMenu(this);
			
			setStatus(null);
		}
		else if (source == quit)
		{
			System.exit(0);
		}
		else if (source instanceof ImageButton)
		{
			if (((ImageButton)source).getText().equals("Update"))
			{
				launcher.update();
			}
			else if (((ImageButton)source).getText().equals("Back"))
			{
				optionsMenu.destroy();
				
				optionsMenu = null;
				
				back = true;
			}
		}
	}
	
	public void setStatus(String status)
	{
		if (status == null || status.equals(""))
		{
			if (this.status != null && !this.status.equals(""))
			{
				username.move(0, -15);
				password.move(0, -15);
				
				logo.move(0, -15 * 3);
			}
		}
		else if (this.status == null || this.status.equals(""))
		{
			username.move(0, 15);
			password.move(0, 15);
			
			logo.move(0, 15 * 3);
		}
		
		this.status = status;
	}
}