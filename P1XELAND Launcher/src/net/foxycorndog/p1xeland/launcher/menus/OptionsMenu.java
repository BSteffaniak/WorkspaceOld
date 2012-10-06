package net.foxycorndog.p1xeland.launcher.menus;

import net.foxycorndog.jdoogl.components.Component;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.components.ImageButton;
import net.foxycorndog.jdoogl.components.Menu;
import net.foxycorndog.jdoogl.components.Frame.Alignment;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.listeners.ActionListener;
import net.foxycorndog.p1xeland.launcher.Launcher;

public class OptionsMenu extends Menu
{
	private ImageButton   update, back;
	
	private ActionListener listener;
	
	public OptionsMenu(ActionListener listener)
	{
		this.listener = listener;
		
		update = new ImageButton(new Texture("res/images/GUI/Button.png", "PNG", true, false));
		update.setHoverImageMap(new Texture("res/images/GUI/ButtonHover.png", "PNG", true, false));
		update.setText("Update");
		update.setLocation(0, 10);
		update.setAlignment(Alignment.LEFT, Alignment.CENTER);
		update.addActionListener(listener);
		Frame.add(update);
		
		back = new ImageButton(new Texture("res/images/GUI/Button.png", "PNG", true, false));
		back.setHoverImageMap(new Texture("res/images/GUI/ButtonHover.png", "PNG", true, false));
		back.setText("Back");
		back.setLocation(0, -20);
		back.setAlignment(Alignment.LEFT, Alignment.CENTER);
		back.addActionListener(listener);
		Frame.add(back);
	}

	@Override
	public void onActionPerformed(Component source)
	{
		
	}

	@Override
	public void render()
	{
		update.render();
		back.render();
	}

	@Override
	public void destroy()
	{
		update.removeActionListener(listener);
		back.removeActionListener(listener);
		
		Frame.remove(update);
		Frame.remove(back);
		
		update = null;
		back   = null;
	}
}