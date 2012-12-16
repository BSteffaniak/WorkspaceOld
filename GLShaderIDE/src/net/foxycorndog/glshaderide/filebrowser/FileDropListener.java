package net.foxycorndog.glshaderide.filebrowser;

import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;

class FileDropListener implements DropTargetListener
{
	private final FileBrowser browser;

	FileDropListener(FileBrowser browser) {
		this.browser = browser;
	}

	public void dragEnter(DropTargetEvent event) {
	}

	public void dragLeave(DropTargetEvent event) {
	}

	public void dragOperationChanged(DropTargetEvent event) {
	}

	public void dragOver(DropTargetEvent event) {
	}

	public void dropAccept(DropTargetEvent event) {
	}

	public void drop(DropTargetEvent event) {
		String[] sourceFileList = (String[]) event.data;
		browser.copyFiles(sourceFileList);
	}
}