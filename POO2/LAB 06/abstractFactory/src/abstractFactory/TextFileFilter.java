package abstractFactory;

import java.io.File;

import javax.swing.filechooser.FileFilter;

//For dialog box to filter files for display
public class TextFileFilter extends FileFilter {
	public boolean accept(File f) {
		return f.getName().endsWith(".txt") || f.isDirectory();
	}

	public String getDescription() {
		return "Text files";
	}
}