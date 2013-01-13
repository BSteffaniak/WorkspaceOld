package net.foxycorndog.arrowide.dialog.preferencesdialogpanel;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.components.FileLocator;
import net.foxycorndog.arrowide.dialog.PreferencesDialogPanel;
import net.foxycorndog.arrowide.file.FileUtils;

public class GeneralPanel extends PreferencesDialogPanel
{
	private FileLocator workspaceLocator;
	
	private ArrowIDE    ide;
	
	public GeneralPanel(Composite parent, ArrowIDE ide)
	{
		super(parent, "General");
		
		this.ide = ide;
		
		int width = getSize().x;
		
		workspaceLocator = new FileLocator(this, parent.getShell(), "Workspace Location:", FileLocator.DIRECTORY);
		workspaceLocator.setLocation(width - workspaceLocator.getSize().x, 50);
		
		if (CONFIG_DATA.containsKey("workspace.location"))
		{
			workspaceLocator.setText(CONFIG_DATA.get("workspace.location"));
		}
	}

	public void apply()
	{
		if (workspaceLocator.getText() != null && !workspaceLocator.getText().equals(CONFIG_DATA.get("workspace.location")))
		{
			String location = FileUtils.removeEndingSlashes(workspaceLocator.getText().replace('\\', '/'));
			
			ArrowIDE.setConfigDataValue("workspace.location", location);
		
			ide.removeAllTreeItems();
			ide.refreshFileViewer(true);
		}
	}

	public void revert()
	{
		if (CONFIG_DATA.containsKey("workspace.location"))
		{
			workspaceLocator.setText(CONFIG_DATA.get("workspace.location"));
		}
	}

	public void update()
	{
		revert();
	}

	public void open()
	{
		
	}
}