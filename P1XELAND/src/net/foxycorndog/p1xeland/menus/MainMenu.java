package net.foxycorndog.p1xeland.menus;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Component;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.components.ImageButton;
import net.foxycorndog.jdoogl.components.Menu;
import net.foxycorndog.jdoogl.components.Frame.Alignment;
import net.foxycorndog.jdoogl.image.Image;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.listeners.ActionListener;
import net.foxycorndog.p1xeland.P1xeland;

public class MainMenu extends Menu
{
	private P1xeland    p1xeland;
	
	private Image       background;
	
	private ImageButton play, quit;
	
	public MainMenu(P1xeland p1xeland)
	{
		this.p1xeland = p1xeland;
		
		background = new Image(new Texture("res/images/Background.png", "PNG", true, false), 199, 199);
		
		play = new ImageButton(new Texture("res/images/GUI/Button.png", "PNG", true, false));
		play.setHoverImageMap(new Texture("res/images/GUI/ButtonHover.png", "PNG", true, false));
		play.setText("Play");
		play.setLocation(0, 20);
		play.setAlignment(Alignment.CENTER, Alignment.CENTER);
		play.addActionListener(this);
		Frame.add(play);
		
		quit = new ImageButton(new Texture("res/images/GUI/Button.png", "PNG", true, false));
		quit.setHoverImageMap(new Texture("res/images/GUI/ButtonHover.png", "PNG", true, false));
		quit.setText("Quit");
		quit.setLocation(0, -20);
		quit.setAlignment(Alignment.CENTER, Alignment.CENTER);
		quit.addActionListener(this);
		Frame.add(quit);
	}
	
	@Override
	public void render()
	{
		GL.beginManipulation();
		{
			GL.translatef(0, 0, -1);
			
			background.render();
		}
		GL.endManipulation();
		
		play.render();
		
		quit.render();
	}

	@Override
	public void destroy()
	{
		play.removeActionListener(this);
		quit.removeActionListener(this);
		
		Frame.remove(play);
		Frame.remove(quit);
		
		play = null;
		quit = null;
	}
	
	@Override
	public void onActionPerformed(Component source)
	{
		if (source == play)
		{
			p1xeland.startGame();
		}
		else if (source == quit)
		{
			System.exit(0);
		}
	}
}