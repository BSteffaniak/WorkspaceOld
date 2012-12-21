package net.foxycorndog.arrowide.dialog;

import java.io.File;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.file.FileUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewFileDialog implements Dialog
{
	private String text;
	
	private Shell  window;
	
	public NewFileDialog(Display display, final DialogListener listener, String windowInstruction, String textFieldInstruction, final boolean directory)
	{
		Rectangle screenBounds = display.getMonitors()[0].getBounds();
		
		window = new Shell(display, SWT.SYSTEM_MODAL | SWT.CLOSE);
		window.setSize(470, 180);
		window.setLocation(screenBounds.width / 2 - window.getBounds().width / 2, screenBounds.height / 2 - window.getBounds().height / 2);
		
		Label instructions = new Label(window, SWT.NONE);
		instructions.setText(windowInstruction);
		instructions.setSize(220, 20);
		instructions.setLocation(100, 30);
		
		Label workspaceLabel = new Label(window, SWT.NONE);
		workspaceLabel.setText(textFieldInstruction);
		workspaceLabel.setSize(70, 20);
		workspaceLabel.setLocation(20, 80);
		
		final Label error = new Label(window, SWT.NONE);
		error.setText("");
		error.setSize(240, 20);
		error.setLocation(100, 115);
		error.setForeground(new Color(display, 220, 0, 0));
		
		final Button continueButton = new Button(window, SWT.PUSH);
		continueButton.setSize(80, 20);
		continueButton.setLocation(360, 80);
		continueButton.setText("Continue");
		
		final Text locationEditor = new Text(window, SWT.SINGLE | SWT.BORDER);
		locationEditor.setSize(250, 20);
		locationEditor.setLocation(100, 80);
		
		final Dialog thisDialog = this;
		
		Listener componentListener = new Listener()
		{
			public void handleEvent(Event e)
			{
				if (e.widget == continueButton)
				{
					String location = locationEditor.getText().replace("\\", "/");
					
					if (location.length() <= 0)
					{
						error.setText("You must enter the " + (directory ? "directory" : "file") + " name.");
						
						return;
					}
					
					while (location.charAt(location.length() - 1) == '/')
					{
						location = location.substring(0, location.length() - 1);
					}
					
					File file = new File(location);
					
					if (!file.exists())
					{
						boolean isDirectory = FileUtils.isFileName(location);
						
						if ((!directory && !isDirectory) || (directory && isDirectory))
						{
							text = location;
							
							DialogEvent event = new DialogEvent();
							event.setSource(thisDialog);
							
							listener.dialogCompleted(event);
							
							window.dispose();
						}
						else if (!directory && isDirectory)
						{
							error.setText("Must be a file name, not a directory name.");
						}
						else if (directory && !isDirectory)
						{
							error.setText("Must be a directory name, not a file name.");
						}
						else
						{
							error.setText("An unknown error has ocurred.");
						}
					}
					else
					{
						error.setText("A " + (directory ? "directory" : "file") + " already exists at that location.");
					}
				}
			}
		};
		
		continueButton.addListener(SWT.Selection, componentListener);
	}
	
	public void open()
	{
		window.open();
	}
	
	public String getText()
	{
		return text;
	}
}