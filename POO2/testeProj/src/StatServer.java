import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class StatServer {
    private ObjectOutputStream outputToClient;
    private ObjectInputStream inputFromClient;
    Socket socket;
    ServerSocket serverSocket;

    public static void main(String[] args) {
        new StatServer();
    }
    public StatServer() {
        try {
            // Create a server socket
            serverSocket = new ServerSocket(8000,1);
            System.out.println("Server started ");
            while (true) {
                // Listen for a new connection request
                socket = serverSocket.accept();
                System.out.println("Conexao aceita");
                // Create an input stream from the socket
                inputFromClient = new ObjectInputStream(socket.getInputStream());
                outputToClient = new ObjectOutputStream(socket.getOutputStream());
                // Read from input
                double soma = 0;
                System.out.println("Tentando ler");
                int n = inputFromClient.readInt();
                System.out.println("Li " + n);

                for (int i = 0; i < n; i++) {
                    soma += inputFromClient.readDouble();
                    System.out.println("Li e somei " + soma);
                }
                outputToClient.writeDouble(soma/n); outputToClient.flush();
                System.out.println("Mandei " + (soma/n));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                inputFromClient.close();
                outputToClient.close();
                socket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}