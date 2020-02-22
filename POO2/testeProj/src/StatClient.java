import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
public class StatClient {
    // Host name or ip
    String host = "localhost";
    public StatClient() {
        double v[] = {4., 6., 8., 100.};
        Socket socket;
        try {
            // Establish connection with the server
            socket = new Socket(host, 8000);
            System.out.println("Connected to server!");
            // Create an output stream to the server
            ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
            toServer.writeInt(4); toServer.flush();
            System.out.println("Escrevi " + 4);
            for (int i=0; i<4; i++) {
                toServer.writeDouble(v[i]); toServer.flush();
                System.out.println("Escrevi " + v[i]);
            }
            System.out.println("A media = " + fromServer.readDouble());
            socket.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    public static void main(String[] args) {
        new StatClient();
    }
}
