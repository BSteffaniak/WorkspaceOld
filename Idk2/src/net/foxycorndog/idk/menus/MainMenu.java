package net.foxycorndog.idk.menus;

import net.foxycorndog.idk.Idk;
import net.foxycorndog.jdoogl.components.Component;
import net.foxycorndog.jdoogl.components.TextButton;
import net.foxycorndog.jdoogl.components.Frame.Alignment;

public class MainMenu extends Menu
{
	TextButton playButton;
	
	public MainMenu()
	{
		playButton = new TextButton("Play", 2);
		playButton.setAlignment(Alignment.CENTER, Alignment.CENTER);
		playButton.addActionListener(this);
		
		addTextButton(playButton);
	}

	@Override
	public void onActionPerformed(Component source)
	{
		if (source == playButton)
		{
			Idk.createGame();
		}
	}

	@Override
	public void onHover(Component source)
	{
		
	}
}