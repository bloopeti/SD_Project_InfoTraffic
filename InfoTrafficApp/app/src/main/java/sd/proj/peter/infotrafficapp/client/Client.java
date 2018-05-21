package sd.proj.peter.infotrafficapp.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private ClientToServerConnection clientToServerConnection;
    private static Client instance;

    private Client() {
        try {
            clientSocket = new Socket("192.168.1.104", 6666);
            System.out.print("Starting client");
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            clientToServerConnection = new ClientToServerConnection(inputStream);
            clientToServerConnection.start();

            //TODO request data
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
