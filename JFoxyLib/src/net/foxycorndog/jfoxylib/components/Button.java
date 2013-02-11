package net.foxycorndog.jfoxylib.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

public class Button extends Component
{
	private String	text;
	
	private Label	label;
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public void addTo(Panel panel)
	{
		label = new Label(panel.getComposite(), SWT.NONE);
		
	}
}