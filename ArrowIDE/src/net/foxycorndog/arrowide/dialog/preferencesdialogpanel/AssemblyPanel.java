package net.foxycorndog.arrowide.dialog.preferencesdialogpanel;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;
import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.components.DropdownMenu;
import net.foxycorndog.arrowide.components.FileLocator;
import net.foxycorndog.arrowide.dialog.PreferencesDialogPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;

public class AssemblyPanel extends PreferencesDialogPanel
{
	private FileLocator		dosboxLocator;

	private DropdownMenu	compilerChooser;
	
	public AssemblyPanel(Shell parent)
	{
		super(parent, "Assembly");
		
		FillLayout layout   = new FillLayout(SWT.VERTICAL);
		layout.marginHeight = 5;
		layout.marginWidth  = 5;
		layout.spacing      = 1;
		
		setLayout(layout);
		
		int width = getSize().x;
		
		dosboxLocator = new FileLocator(this, parent, "DOSBox Location:", FileLocator.FILE);
		dosboxLocator.setLocation(width - dosboxLocator.getSize().x, 50);
		
		compilerChooser = new DropdownMenu(this);
		compilerChooser.setSize(100, 25);
		compilerChooser.addItem("FASM");
		compilerChooser.addItem("NASM");
		compilerChooser.setSelection("FASM");
		
		if (CONFIG_DATA.containsKey("dosbox.location"))
		{
			dosboxLocator.setText(CONFIG_DATA.get("dosbox.location"));
		}
	}

	public void apply()
	{
		if (dosboxLocator.getText() != null && !CONFIG_DATA.get("dosbox.location").equals(dosboxLocator.getText()))
		{
			ArrowIDE.setConfigDataValue("dosbox.location", dosboxLocator.getText());
		}
	}

	public void revert()
	{
		if (CONFIG_DATA.containsKey("dosbox.location"))
		{
			dosboxLocator.setText(CONFIG_DATA.get("dosbox.location"));
		}
	}
	
	public void update()
	{
		revert();
	}
}