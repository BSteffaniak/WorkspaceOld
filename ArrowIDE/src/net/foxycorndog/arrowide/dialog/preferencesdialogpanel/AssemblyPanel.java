package net.foxycorndog.arrowide.dialog.preferencesdialogpanel;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;
import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.components.DropdownMenu;
import net.foxycorndog.arrowide.components.FileLocator;
import net.foxycorndog.arrowide.dialog.PreferencesDialogPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
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
		compilerChooser.addItem("NASM");
		compilerChooser.addItem("FASM");
		compilerChooser.setLocation(width - compilerChooser.getWidth() - 20, 85);
		
		Label compilerLabel = new Label(this, SWT.NONE);
		compilerLabel.setText("Assembler:");
		compilerLabel.setSize(70, 25);
		compilerLabel.setLocation(compilerChooser.getX() - compilerLabel.getSize().x, compilerChooser.getY() + 3);
		
		if (CONFIG_DATA.containsKey("dosbox.location"))
		{
			dosboxLocator.setText(CONFIG_DATA.get("dosbox.location"));
		}
		if (CONFIG_DATA.containsKey("assembly.compiler"))
		{
			compilerChooser.setSelection(CONFIG_DATA.get("assembly.compiler"));
		}
	}

	public void apply()
	{
		if (dosboxLocator.getText() != null && !CONFIG_DATA.get("dosbox.location").equals(dosboxLocator.getText()))
		{
			ArrowIDE.setConfigDataValue("dosbox.location", dosboxLocator.getText());
		}
		
		ArrowIDE.setConfigDataValue("assembly.compiler", compilerChooser.getSelection());
	}

	public void revert()
	{
		if (CONFIG_DATA.containsKey("dosbox.location"))
		{
			dosboxLocator.setText(CONFIG_DATA.get("dosbox.location"));
		}
		if (CONFIG_DATA.containsKey("assembly.compiler"))
		{
			compilerChooser.setSelection(CONFIG_DATA.get("assembly.compiler"));
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