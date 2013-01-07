package net.foxycorndog.arrowide.dialog.preferencesdialogpanel;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;

import net.foxycorndog.arrowide.dialog.PreferencesDialogPanel;

public class CppPanel extends PreferencesDialogPanel
{
	private ArrayList<PreferencesDialogPanel> panels;
	
	public CppPanel(Composite parent)
	{
		super(parent, "C++ Compiler");
	}
}