package connections;

import common.commands.Command;;
import common.commands.serialization.SerializeCommand;
import common.model.Alert;
import common.model.AlertList;
import dao.daos.AlertDAO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerToClientConnection extends Thread {

    private Socket socket = null;
    private int id;
    private static ObjectOutputStream outputStream=null;
    private static ObjectInputStream inputStream=null;
    private Object readObject;

    public ServerToClientConnection(Socket socket, int id) {
        this.socket = socket;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            List<Alert> alerts = AlertDAO.findAllActive();

            AlertList alertList = new AlertList(alerts);
            Command command1 = new SerializeCommand(alertList);
            String sending = ((SerializeCommand) command1).execute();
            System.out.println(sending);
            outputStream.writeObject(sending);

            String received = (String) inputStream.readObject();
            System.out.println("Requested: " + received);
/*
            while (true) {
                String received = (String) inputStream.readObject();
                System.out.println("Requested: " + received);

                String[] args = received.split("\n");
                Command command = CommandFactory.getCommand(args);
            }*/
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
