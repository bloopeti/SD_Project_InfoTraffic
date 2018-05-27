package connections;

import common.commands.Command;
import common.commands.serialization.SerializeCommand;
import common.model.Alert;
import dao.daos.AlertDAO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    public static final int port = 6666;
    private List<ServerToClientConnection> connections;
    private static Server instance;
    private boolean running = false;

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
        running = true;
        int connectedClients = 0;
        ServerSocket serverSocket = null;
        try {
            System.out.println("Starting server at port " + port);
            serverSocket = new ServerSocket(port);
            while (running) {
                Socket client = serverSocket.accept();
                System.out.println("New client: " + client.toString());
/*
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                List<Alert> alerts = AlertDAO.findAllActive();
                Command command1 = new SerializeCommand(alerts);
                String response = (String) command1.execute();
                outputStream.writeObject(command1.execute());
*/
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
