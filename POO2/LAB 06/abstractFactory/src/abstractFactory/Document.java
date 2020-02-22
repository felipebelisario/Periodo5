package abstractFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

//holds text and performs functionality - save, open etc.

public abstract class Document extends JInternalFrame {

	public Document(String title, int x, int y) {
		super(title, true, true, true, true);
		JScrollPane internalScrollPane = new JScrollPane(textArea);
		super.getContentPane().add(internalScrollPane);
		this.setSize(200, 200); //small frame
		this.setLocation(x, y);
		this.saved = false;
		this.addInternalFrameListener(new DocumentListener(this)); //listens to
																   // this
																   // document
																   // closing
		textArea.grabFocus();
		chooser = new JFileChooser();
		
		//factory method
		chooser.setFileFilter(this.createFileFilter());
		this.setVisible(true);
	}

	//open text file
	public boolean open() {
		chooser.showOpenDialog(this.textArea);
		File choice = chooser.getSelectedFile();
		if (choice != null) {
			this.setTitle(choice.getAbsolutePath());
			textArea.setText("");
			try {
				textArea.read(new FileReader(choice), null);
			} catch (Exception e) { //ignore exception
			}
		}
		this.saved = true;
		textArea.grabFocus();
		return choice != null;
	}

	//saves text to specified text file
	public void saveAs() {
		chooser.setDialogTitle("Save As - " + this.title);
		chooser.showSaveDialog(this.textArea);
		File choice = chooser.getSelectedFile();
		if (choice != null) {
			this.setTitle(choice.getAbsolutePath());
			save();
		}
		this.saved = true;
		textArea.grabFocus();
	}

	//saves text area to text file
	public void save() {
		try {
			textArea.write(new FileWriter(this.getTitle()));
		} catch (Exception e) { //ignore exception
		}
		textArea.grabFocus();
	}

	//tells difference between document thats open but never saved
	public boolean saved() {
		return this.saved;
	}

	//Editor abstraction
	public void copy() {
		this.textArea.copy();
		textArea.grabFocus();
	}

	public void cut() {
		this.textArea.cut();
		textArea.grabFocus();
	}

	public void paste() {
		this.textArea.paste();
		textArea.grabFocus();
	}

	public void selectAll() {
		this.textArea.selectAll();
		textArea.grabFocus();
	}

	public void setTextArea(String textArea) {
		this.textArea.append(textArea);
	}
	
	protected abstract FileFilter createFileFilter();

	private JTextArea textArea = new JTextArea();;

	private boolean saved;

	private JFileChooser chooser;
}