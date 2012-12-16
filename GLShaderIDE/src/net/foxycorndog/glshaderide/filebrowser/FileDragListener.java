package net.foxycorndog.glshaderide.filebrowser;

import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

class FileDragListener implements DragSourceListener
{
	private FileBrowser browser;

	public FileDragListener(FileBrowser browser) {
		this.browser = browser;
	}

	public void dragStart(DragSourceEvent event) {
		event.doit = true;
	}

	public void dragSetData(DragSourceEvent event) {
		event.data = browser.getSelectedFiles();
	}

	public void dragFinished(DragSourceEvent event) {
	}

}