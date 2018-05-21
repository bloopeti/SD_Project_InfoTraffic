package sd.proj.peter.infotrafficapp.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client{
    private final String SERVER_IP = "localhost"; //"192.168.1.104"
    private final int SERVER_PORT = 6666;
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private ClientToServerConnection clientToServerConnection;
    private boolean isRunning = false;
    private String messageToServer;
    private String messageFromServer;
    private static Client instance;


    private Client() {
        try {
            //clientSocket = new Socket("192.168.1.104", 6666);
            clientSocket = new Socket("localhost", SERVER_PORT);
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

    public void sendMessage(final String msg) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (outputStream != null) {
                        System.out.println("Sending: " + msg);
                        outputStream.writeObject(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void stopClient() {
        isRunning = false;

        inputStream = null;
        outputStream = null;
        messageToServer = null;
    }

    public void run() {

        isRunning = true;

        try {
            //here you must put your computer's IP address.

            System.out.println("TCP Client conneting to " + SERVER_IP + " on " + SERVER_PORT);

            //create a socket to make the connection with the server
            clientSocket = new Socket(SERVER_IP, SERVER_PORT);

            try {
                //sends the message to the server
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

                //receives the message which the server sends back
                inputStream = new ObjectInputStream(clientSocket.getInputStream());

                //in this while the client listens for the messages sent by the server
                while (isRunning) {

                    messageFromServer = (String) inputStream.readObject();
/*
                    if (mServerMessage != null && mMessageListener != null) {
                        //call the method messageReceived from MyActivity class
                        mMessageListener.messageReceived(mServerMessage);
                    }
*/
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Client getInstance() {
        if (instance == null)
            instance = new Client();

        return instance;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ClientToServerConnection getClientToServerConnection() {
        return clientToServerConnection;
    }
}
