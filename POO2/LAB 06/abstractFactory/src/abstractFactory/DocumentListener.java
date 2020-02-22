package abstractFactory;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

//listens to closing of document
//and prompts save of individual document
public class DocumentListener extends InternalFrameAdapter {

	public DocumentListener(Document d) {
		this.doc = d;
	}

	public void internalFrameClosing(InternalFrameEvent e) {
		doc.saveAs();
	}

	private Document doc;
}