package net.foxycorndog.arrowide.dialog;

import java.io.File;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.file.FileUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
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

public class FileInputDialog extends TextInputDialog
{
	private boolean directory;
	
	private String  text;
	private String  preLocation;
	
	private Text    locationEditor;
	
	private Label   error;
	
	private Shell   window;
	
	private DialogListener listener;
	
	public FileInputDialog(Display display, DialogListener listener, String windowInstruction, String textFieldInstruction, boolean directory, String preLocation, boolean overwrite)
	{
		this(display, listener, windowInstruction, textFieldInstruction, "", directory, preLocation, overwrite);
	}
	
	public FileInputDialog(Display display, DialogListener listener, String windowInstruction, String textFieldInstruction, String defaultTextField, boolean directory, String preLocation, boolean overwrite)
	{
		super(display, listener, windowInstruction, textFieldInstruction, defaultTextField, directory);
		
		this.listener    = listener;
		this.directory   = directory;
		this.preLocation = FileUtils.removeEndingSlashes(preLocation) + "/";
		
		Rectangle screenBounds = display.getMonitors()[0].getBounds();
		
		window = new Shell(display, SWT.SYSTEM_MODAL | SWT.CLOSE);
		window.setSize(470, 180);
		window.setLocation(screenBounds.width / 2 - window.getBounds().width / 2, screenBounds.height / 2 - window.getBounds().height / 2);
		
		Label instructionsLabel = new Label(window, SWT.NONE);
		instructionsLabel.setText(windowInstruction);
		instructionsLabel.setSize(220, 20);
		instructionsLabel.setLocation(100, 30);
		
		Label textFieldInstructionLabel = new Label(window, SWT.NONE);
		textFieldInstructionLabel.setText(textFieldInstruction);
		textFieldInstructionLabel.setSize(70, 20);
		textFieldInstructionLabel.setLocation(20, 80);
		
		error = new Label(window, SWT.NONE);
		error.setText("");
		error.setSize(240, 20);
		error.setLocation(100, 115);
		error.setForeground(new Color(display, 220, 0, 0));
		
		final Button continueButton = new Button(window, SWT.PUSH);
		continueButton.setSize(80, 20);
		continueButton.setLocation(360, 80);
		continueButton.setText("Continue");
		
		locationEditor = new Text(window, SWT.SINGLE | SWT.BORDER);
		locationEditor.setSize(250, 20);
		locationEditor.setLocation(100, 80);
		locationEditor.setText(defaultTextField);
		locationEditor.setSelection(0, defaultTextField.length());
		
		Listener componentListener = new Listener()
		{
			public void handleEvent(Event e)
			{
				if (e.widget == continueButton)
				{
					continuePressed();
				}
			}
		};
		
		continueButton.addListener(SWT.Selection, componentListener);
		
		locationEditor.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				if (e.keyCode == 13)
				{
					continuePressed();
				}
			}

			public void keyReleased(KeyEvent e)
			{
				
			}
		});
	}
	
	private void continuePressed()
	{
		String location = locationEditor.getText().replace("\\", "/");
		
		if (location.length() <= 0)
		{
			error.setText("You must enter the " + (directory ? "directory" : "file") + " name.");
			
			return;
		}
		
		location = preLocation + FileUtils.removeEndingSlashes(location);
		
		File file = new File(location);
		
		if (!file.exists())
		{
			boolean isDirectory = !FileUtils.isFileName(location);
			
			if ((!directory && !isDirectory) || (directory && isDirectory))
			{
				text = location;
				
				DialogEvent event = new DialogEvent();
				event.setSource(this);
				
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
	
	public void open()
	{
		window.open();
		
		locationEditor.setFocus();
	}
	
	public String getText()
	{
		return text;
	}
}