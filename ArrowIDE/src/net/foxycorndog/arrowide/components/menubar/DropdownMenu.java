package net.foxycorndog.arrowide.components.menubar;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DropdownMenu
{
	private int							textMargin;
	private int							separatorHeight;
	
	private GC							gc;
	
	private Composite					contentPanel;
	
	private Shell						shell;
	
	private HashMap<String, Integer>	ids;
	private ArrayList<Composite>		separators;
	
	private static int					staticId;
	
	public DropdownMenu()
	{
		shell = new Shell(SWT.NO_TRIM);
		shell.setSize(0, 0);
		
		contentPanel = new Composite(shell, SWT.NONE);
		contentPanel.setSize(0, 0);
		
		ids        = new HashMap<String, Integer>();
		separators = new ArrayList<Composite>();
		
		gc = new GC(shell);
		
		textMargin = 2;
		
		separatorHeight = 5;
		
		contentPanel.addControlListener(new ControlListener()
		{
			public void controlResized(ControlEvent e)
			{
				shell.setSize(contentPanel.getSize());
			}
			
			public void controlMoved(ControlEvent e)
			{
				
			}
		});
	}
	
	public void addMenuItem(String text, String id)
	{
		int textWidth  = gc.stringExtent(text).x;
		int textHeight = gc.stringExtent(text).y;
		
		Label label = new Label(contentPanel, SWT.NONE);
		label.setSize(textWidth, textHeight);
		label.setLocation(0, contentPanel.getSize().y);
		label.setText(text);
		
		if (textWidth > contentPanel.getSize().x)
		{
			contentPanel.setSize(textWidth, contentPanel.getSize().y);
		}
		
		contentPanel.setSize(contentPanel.getSize().x, contentPanel.getSize().y + textHeight + textMargin);
	}
	
	public void addSeparator()
	{
		contentPanel.setSize(contentPanel.getSize().x, contentPanel.getSize().y + separatorHeight);
		
		int height = separatorHeight % 2 == 0 ? 2 : 1;
		
		Composite sep = new Composite(contentPanel, SWT.NONE);
		sep.setLocation(0, contentPanel.getSize().y - separatorHeight / 2);
		sep.setBackground(new Color(Display.getDefault(), 100, 100, 100));
		
		separators.add(sep);
		
		for (int i = separators.size() - 1; i >= 0; i--)
		{
			separators.get(i).setSize(contentPanel.getSize().x - 2, height);
		}
	}
	
	public void setLocation(int x, int y)
	{
		shell.setLocation(x, y);
	}
	
	public void open()
	{
		shell.open();
	}
	
	public void close()
	{
		shell.close();
	}
	
	public void dispose()
	{
		shell.dispose();
	}
}