package abstractFactory;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

//listens to closing of frame
public class WindowCloser extends WindowAdapter {

	private JDesktopPane pane;

	public WindowCloser(JDesktopPane pane) {
		this.pane = pane;
	}

	public void windowClosing(WindowEvent event) {
		JInternalFrame[] frames = pane.getAllFrames();
		for (int i = 0; i < frames.length; i++) {
			//if (frames[i].isSelected())
			((Document) frames[i]).saveAs();
		}
		System.exit(0); //kills the program when frame closed
	}
}