import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Interface extends JFrame {

    JTextArea outputArea;

    public Interface()
    {
        super( "Teste" ); // set title of window



        JPanel aopa = (JPanel) this.getContentPane();
        aopa.setLayout(null);

        outputArea = new JTextArea(); // create JTextArea for output

        aopa.add(outputArea);
        outputArea.setBounds(10,10,300,200);
        outputArea.setText( "Server awaiting connections\n" );



        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize( 300, 300 ); // set size of window
        setVisible( true ); // show window
    }
}
