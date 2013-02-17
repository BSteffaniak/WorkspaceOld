package net.foxycorndog.jfoxylib.components;

import org.eclipse.swt.SWT;

public class Label extends Component
{
	private org.eclipse.swt.widgets.Label label;
	
	public Label(Panel parent)
	{
		super(parent);
		
		label = new org.eclipse.swt.widgets.Label(parent.getComposite(), SWT.NONE);
		
		setControl(label);
	}
	
	public void setImage(Image image)
	{
		label.setImage(image.getImage());
	}
}