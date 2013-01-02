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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class FileBrowseDialog extends TextInputDialog
{
	public FileBrowseDialog(Shell parent, String windowInstruction, String textFieldInstruction, boolean directory)
	{
		this(parent, windowInstruction, textFieldInstruction, "", directory);
	}
	
	public FileBrowseDialog(Shell parent, String windowInstruction, String textFieldInstruction, String defaultTextField, final boolean directory)
	{
		super(parent, windowInstruction, textFieldInstruction);
		
		getWindow().setSize(480, 230);
		getErrorLabel().setLocation(100, 140);
		getContinueButton().setLocation(360, 115);
		
		final Button browse = new Button(getWindow(), SWT.PUSH);
		browse.setSize(100, 25);
		browse.setLocation(360, 80);
		browse.setText("Browse");
		
		Listener componentListener = new Listener()
		{
			public void handleEvent(Event e)
			{
				String location = null;
					
				if (directory)
				{
					DirectoryDialog dialog = new DirectoryDialog(getWindow(), SWT.OPEN);
					
					location = dialog.open();
				}
				else
				{
					FileDialog dialog = new FileDialog(getWindow(), SWT.OPEN);
					
					location = dialog.open();
				}
				
				if (location != null)
				{
					setText(location, true);
				}
			}
		};
		
		browse.addListener(SWT.Selection, componentListener);
		
		addDialogFilter(new DialogFilter()
		{
			public String filter(String text)
			{
				String location = text.replace("\\", "/");
				
				if (location.length() <= 0)
				{
					return "You must enter the " + (directory ? "directory" : "file") + " location.";
				}
				
				location = FileUtils.removeEndingSlashes(location);
				
				File file = new File(location);
				
				if (file.exists())
				{
					boolean isDirectory = file.isDirectory();
					
					if ((!directory && !isDirectory) || (directory && isDirectory))
					{
						setText(location);
					}
					else if (!directory && isDirectory)
					{
						return "Must be a file name, not a directory name.";
					}
					else if (directory && !isDirectory)
					{
						return "Must be a directory name, not a file name.";
					}
					else
					{
						return "An unknown error has ocurred.";
					}
				}
				else
				{
					return "A " + (directory ? "directory" : "file") + " already exists at that location.";
				}
				
				return null;
			}
		});
	}
}