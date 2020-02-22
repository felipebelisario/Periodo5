package abstractFactory;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.*;

//Implements Text editor specific components required by EditorFactory

public class TextEditorFactory extends EditorFactory {
	/** *********************** */
	//create Frame

	private JTextField url;

	public JFrame createFrame() {
		JFrame frame = new JFrame("Basic Editor");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.height = screenSize.height - (25 * screenSize.height / 768);
		frame.setSize(screenSize);
		return frame;
	}

	/** *********************** */
	//create Menu Bar
	public JMenuBar createMenuBar(ActionListener listener) {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		String[] fileMenuItems = new String[] { "New", "Open", "Save",
				"Save As" };
		for (int i = 0; i < fileMenuItems.length; i++) {
			JMenuItem menuItem = new JMenuItem(fileMenuItems[i]);
			fileMenu.add(menuItem);
			menuItem.addActionListener(listener); //listens to button press
		}
		menuBar.add(fileMenu);

		//edit menu
		JMenu editMenu = new JMenu("Edit");
		String[] editMenuItems = new String[] { "Copy", "Cut", "Paste",
				"Select All" };
		for (int i = 0; i < editMenuItems.length; i++) {
			JMenuItem menuItem = new JMenuItem(editMenuItems[i]);
			editMenu.add(menuItem);
			menuItem.addActionListener(listener); //listens to button press
		}
		menuBar.add(editMenu);

		return menuBar;
	}

	/** *********************** */
	//create Tool Bar
	public JToolBar createToolBar(ActionListener listener) {
		JToolBar toolBar = new JToolBar();
		String[] buttons = new String[] { "New", "Open", "Save", "Copy", "Cut",
				"Paste" };
		for (int i = 0; i < buttons.length; i++) {
			JButton button = new JButton(buttons[i], new ImageIcon("src/abstractFactory/" + buttons[i]
					+ ".jpg"));
			toolBar.add(button);
			button.addActionListener(listener);
			if (i == 2)
				toolBar.addSeparator(new Dimension(10, toolBar.getHeight()));
		}
		return toolBar;
	}

	/** *********************** */
	//create Desktop Pane to hold documents
	public JDesktopPane createDesktopPane() {
		JDesktopPane pane = new JDesktopPane();
		return pane;
	}

	/** *********************** */
	//create Action Listener
	public ActionListener createActionListener(JDesktopPane pane) {
		ButtonListener listener = new ButtonListener(pane, url);
		return listener;
	}

	/** *********************** */
	//create Window Adapter
	public WindowAdapter createWindowAdapter(JDesktopPane pane) {
		WindowCloser windowAdapter = new WindowCloser(pane);
		return windowAdapter;
	}

}