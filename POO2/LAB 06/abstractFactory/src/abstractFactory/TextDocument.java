/*
 * Created on Jan 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package abstractFactory;

import javax.swing.filechooser.FileFilter;

/**
 * @author richie
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TextDocument extends Document {

	public TextDocument(String title, int x, int y) {
		super(title, x, y);
	}

	protected FileFilter createFileFilter() {
		return new TextFileFilter();
	}

}
