package abstractFactory;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.*;

//Creates components and places them appropriately in the frame

public class EditorApplication {
	public EditorApplication(EditorFactory factory) {
		this.factory = factory;

		/** ***************** */
		//call Factory Methods to create products
		frame = factory.createFrame();
		pane = factory.createDesktopPane();
		listener = factory.createActionListener(pane);
		menuBar = factory.createMenuBar(listener);
		toolBar = factory.createToolBar(listener);
		windowAdapter = factory.createWindowAdapter(pane);

		//add components to frame
		frame.setJMenuBar(menuBar);
		frame.addWindowListener(windowAdapter);
		frame.getContentPane().add(toolBar, "North");
		frame.getContentPane().add(pane);

		frame.show(); //display the frame
	}

	private JFrame frame;

	private JMenuBar menuBar;

	private JToolBar toolBar;

	private WindowAdapter windowAdapter;

	private JDesktopPane pane;

	private ActionListener listener;

	/** ***************** */
	//Factory for creating editor components
	private EditorFactory factory;

	public static void main(String[] args) {

		String[] options = {"Basic Editor", "HTML Editor"};

		int op = JOptionPane.showOptionDialog(null, "Select the editor to open:", "SELECT",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		if(op == 0){
			EditorApplication editor = new EditorApplication(
					new TextEditorFactory());
		} else{

		EditorApplication editor = new EditorApplication(
				new HTMLEditor());
		}
	}
}