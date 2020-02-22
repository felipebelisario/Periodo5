package abstractFactory;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

/** ************ */
//Abstract Factory for the Editor products
public abstract class EditorFactory {
	/** **************************** */
	//Factory Methods
	public abstract JFrame createFrame();

	public abstract JDesktopPane createDesktopPane();

	public abstract JMenuBar createMenuBar(ActionListener listener);

	public abstract JToolBar createToolBar(ActionListener listener);

	public abstract WindowAdapter createWindowAdapter(JDesktopPane pane);

	public abstract ActionListener createActionListener(JDesktopPane pane);
}