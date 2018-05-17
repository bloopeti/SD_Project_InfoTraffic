package connections;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    public static final int port = 6666;
    private List<ServerToClientConnection> connections;
    private static Server instance;

    private Server() {
        super();
        connections = new ArrayList<ServerToClientConnection>();
    }

    public static Server getInstance() {
        if(instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public void run() {
        int connectedClients = 0;
        System.out.println("Starting server at port " + port);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("New client: " + client.toString());
                ServerToClientConnection newConnection = new ServerToClientConnection(client, ++connectedClients);
                connections.add(newConnection);
                newConnection.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null)
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public List<ServerToClientConnection> getConnections() {
        return connections;
    }

    public static void main(String[] args) {
        Server server = Server.getInstance();
        server.start();
    }
}
