import javax.swing.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends JFrame{

    public JTextArea outputArea;
    public JButton jButton1;
    public ArrayList<String> sum_list;
    public float sum = 0;
    public int qnt = 0;

    public Server(){
        super( "Server" ); // set title of window


        JPanel server_panel = (JPanel) this.getContentPane();
        server_panel.setLayout(null);

        outputArea = new JTextArea(); // create JTextArea for output

        server_panel.add(outputArea);
        outputArea.setBounds(10,10,365,340);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize( 400, 400 ); // set size of window
        setVisible( true ); // show window

        try {
            ServerSocket server = new ServerSocket(12345);
            outputArea.setText("Servidor iniciado na porta 12345\n\n");
            outputArea.append( "Server awaiting connections\n" );

            Socket cliente = server.accept();
            outputArea.append("Cliente conectado do IP "+cliente.getInetAddress().
                    getHostAddress() + "\n\n");

            Scanner entrada = new Scanner(cliente.getInputStream());
            PrintStream saida = new PrintStream(cliente.getOutputStream());

            while(entrada.hasNextLine()){
                sum_list = new ArrayList<String>(Arrays.asList(entrada.nextLine().split(" ")));
                sum = 0;
                qnt = 0;


                Iterator<String> iterator = sum_list.iterator();
                String aux;
                while (iterator.hasNext()) {
                    if(qnt == 0) saida.append("( ");
                    aux = iterator.next();

                    saida.append(aux);
                    if(iterator.hasNext()){
                        saida.append(" + ");
                    }
                    qnt++;
                    sum += Integer.parseInt(aux);
                }
                sum = sum / qnt;
                saida.append(" ) / " + qnt + " = " + sum + "\n");

                outputArea.append("Média efetuada\n");
            }

            entrada.close();
            server.close();

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String args[]){

        Server server = new Server();

    }

}