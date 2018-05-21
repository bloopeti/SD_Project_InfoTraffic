package sd.proj.peter.infotrafficapp.client;

import java.io.IOException;
import java.io.ObjectInputStream;

import sd.proj.peter.infotrafficapp.common.commands.Command;
import sd.proj.peter.infotrafficapp.common.commands.CommandFactory;

public class ClientToServerConnection extends Thread {
    private ObjectInputStream inputStream;

    public ClientToServerConnection(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void run() {
        try {
            while (true) {
                String received = (String) inputStream.readObject();
                System.out.println("Received: " + received);
                String[] args = received.split("\n");

                //TODO: process input
                Command command = CommandFactory.getCommand(args);
                System.out.println(command.execute().toString());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
