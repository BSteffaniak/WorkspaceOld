package net.foxycorndog.p1xelandroid.menus;

import android.app.Activity;
import android.content.Context;
import net.foxycorndog.jdooglandroid.GL;
import net.foxycorndog.jdooglandroid.components.Component;
import net.foxycorndog.jdooglandroid.components.Frame;
import net.foxycorndog.jdooglandroid.components.ImageButton;
import net.foxycorndog.jdooglandroid.components.Menu;
import net.foxycorndog.jdooglandroid.components.Frame.Alignment;
import net.foxycorndog.jdooglandroid.image.Image;
import net.foxycorndog.jdooglandroid.image.imagemap.Texture;
import net.foxycorndog.jdooglandroid.listeners.ActionListener;
import net.foxycorndog.p1xelandroid.P1xeland;
import net.foxycorndog.p1xelandroid.R;

public class MainMenu extends Menu
{
	private P1xeland    p1xeland;
	
	private Image       background;
	
	private ImageButton play, quit;
	
	public MainMenu(P1xeland p1xeland, Activity activity)
	{
		this.p1xeland = p1xeland;
		
		background    = new Image(new Texture(activity.getResources(), R.drawable.background), 50, 50);
		
		play = new ImageButton(new Texture(activity.getResources(), R.drawable.button));
		play.setHoverTexture(new Texture(activity.getResources(), R.drawable.buttonhover));
		play.setText("Play");
		play.setLocation(0, 20);
		play.setAlignment(Alignment.CENTER, Alignment.CENTER);
		play.addActionListener(this);
		Frame.add(play);
		
		quit = new ImageButton(new Texture(activity.getResources(), R.drawable.button));
		quit.setHoverTexture(new Texture(activity.getResources(), R.drawable.buttonhover));
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