package abstractFactory;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;

import javax.swing.*;

//listens to the buttons pressed on the tool bar

public class ButtonListener implements ActionListener {

	public ButtonListener(JDesktopPane pane, JTextField url) {
		this.pane = pane;
		this.url = url;
		newWindowX = 0;
		newWindowY = 0;
		docCount = 0;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("New")) { //New button pressed
			TextDocument d = new TextDocument("untitled" + docCount, newWindowX,
					newWindowY);
			this.addDocument(d);
			docCount++;
		} else if (e.getActionCommand().equals("Open")) { //Open button pressed
			TextDocument d = new TextDocument("", newWindowX, newWindowY); //dummy
																   // document
			if (d.open())
				this.addDocument(d);
		} else if (e.getActionCommand().equals("Save")) { //save button pressed
			TextDocument current = this.getSelectedDocument();
			if (current != null)
				if (current.saved())
					current.save();
				else
					current.saveAs();
		} else if (e.getActionCommand().equals("Save As")) {// save as pressed
			TextDocument current = this.getSelectedDocument();
			if (current != null)
				current.saveAs();
		} else if (e.getActionCommand().equals("Copy")) {// copy pressed
			TextDocument current = this.getSelectedDocument();
			if (current != null)
				current.copy();
		} else if (e.getActionCommand().equals("Paste")) {// paste pressed
			TextDocument current = this.getSelectedDocument();
			if (current != null)
				current.paste();
		} else if (e.getActionCommand().equals("Cut")) {// cut pressed
			TextDocument current = this.getSelectedDocument();
			if (current != null)
				current.cut();
		} else if (e.getActionCommand().equals("Select All")) {// select all
															   // pressed
			TextDocument current = this.getSelectedDocument();
			if (current != null)
				current.selectAll();

		} else if (e.getActionCommand().equals("Go")){
			try {
				URL user_url = new URL(url.getText());

				// Cria novo documento
				TextDocument d = new TextDocument("untitled" + docCount, newWindowX,
						newWindowY);

				docCount++;

				BufferedReader in = new BufferedReader(
						new InputStreamReader(user_url.openStream()));

				String inputLine;

				// Para cada linha HTML lida da URL faz append na textArea do documento
				while ((inputLine = in.readLine()) != null)
					d.setTextArea(inputLine + '\n');

				in.close();

				this.addDocument(d);

				url.setText("");


			} catch (MalformedURLException ex) {
				JOptionPane.showMessageDialog(null, "Invalid URL!", "ERROR",
						JOptionPane.ERROR_MESSAGE);

				url.setText("");

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}


	//adds document to desktop pane
	private void addDocument(TextDocument d) {
		pane.add(d);
		pane.getDesktopManager().activateFrame(d);
		try { //ignore exception
			d.setSelected(true);
		} catch (Exception ex) {
		}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.height = screenSize.height - (25 * screenSize.height / 768); //acount
																				// for
																				// task
																				// bar
		//compute coords for posn of next new window
		newWindowX = (newWindowX + 20) % (int) (screenSize.width - 100);
		newWindowY = (newWindowY + 20) % (int) (screenSize.height - 100);
		d.grabFocus();
	}

	private TextDocument getSelectedDocument() {
		JInternalFrame[] frames = pane.getAllFrames();
		for (int i = 0; i < frames.length; i++) {
			if (frames[i].isSelected())
				return (TextDocument) frames[i];
		}
		return null;
	}


	private JDesktopPane pane;

	private int newWindowX, newWindowY, docCount;
	private JTextField url;

}