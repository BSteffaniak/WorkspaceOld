package net.foxycorndog.glshaderide.filebrowser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.dnd.Transfer;

public class FileViewer extends Composite
{
	private FileBrowser browser;

	public FileViewer(Composite parent)
	{
		super(parent, SWT.NONE);
	
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		setLayout(layout);
	
		Button copyButton = new Button(this, SWT.PUSH);
		copyButton.setText("Copy");
		copyButton.addSelectionListener(new SelectionListener()
		{
		
			public void widgetSelected(SelectionEvent e)
			{
				Clipboard clipboard = new Clipboard(getDisplay());
			
				FileTransfer transfer = FileTransfer.getInstance();
				clipboard.setContents(
				new Object[] { browser.getSelectedFiles() },
				new Transfer[] { transfer });
				clipboard.dispose();
			}
		
			public void widgetDefaultSelected(SelectionEvent e)
			{
				
			}
		});

		Button pasteButton = new Button(this, SWT.PUSH);
		pasteButton.setText("Paste");
		pasteButton.addSelectionListener(new SelectionListener() {
	
		public void widgetSelected(SelectionEvent e) {
		Clipboard clipboard = new Clipboard(getDisplay());
		FileTransfer transfer = FileTransfer.getInstance();
	
		Object data = clipboard.getContents(transfer);
		if (data != null) {
		browser.copyFiles((String[]) data);
		}
		clipboard.dispose();
		}
	
		public void widgetDefaultSelected(SelectionEvent e) {
		}
		});
	
		browser = new FileBrowser(this);
		new FileBrowser(this);
	}
}