package net.foxycorndog.p1xelandroid.menus;

import android.app.Activity;
import android.content.Context;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Component;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.components.ImageButton;
import net.foxycorndog.jdoogl.components.Menu;
import net.foxycorndog.jdoogl.components.Frame.Alignment;
import net.foxycorndog.jdoogl.image.Image;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.listeners.ActionListener;
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