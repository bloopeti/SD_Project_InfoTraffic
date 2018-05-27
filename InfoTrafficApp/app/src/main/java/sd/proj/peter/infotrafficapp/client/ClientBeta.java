package sd.proj.peter.infotrafficapp.client;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import sd.proj.peter.infotrafficapp.common.commands.serialization.DeserializeCommand;
import sd.proj.peter.infotrafficapp.common.model.Alert;
import sd.proj.peter.infotrafficapp.common.model.AlertList;
import sd.proj.peter.infotrafficapp.uicontrollers.AlertListActivity;

public class ClientBeta extends AsyncTask<Void, Void, String> {
    private final String SERVER_IP = "192.168.1.100";
    //private final String SERVER_IP = "localhost"; //"192.168.1.104"
    private final int SERVER_PORT = 6666;
    private String response = "";

    private AlertListActivity alertListActivity;

    public ClientBeta(AlertListActivity alertListActivity) {
        this.alertListActivity = alertListActivity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Socket socket = null;

        try {
            Log.d("My stuff","Starting client socket");
            socket = new Socket(SERVER_IP, SERVER_PORT);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            String received = (String) inputStream.readObject();
            Log.d("My stuff","Received: " + received);

            Log.d("My stuff","Writing stuffy");
            outputStream.writeObject("stuffy");
            Log.d("My stuff","Done");
/*
            while (true) {
                received = (String) inputStream.readObject();
                System.out.println("Received: " + received);
                Log.d("My stuff","Received: " + received);
                String[] args = received.split("\n");


                response = received;

                //TODO: process input
                Command command = CommandFactory.getCommand(args);
                System.out.println(command.execute().toString());
            }*/
            DeserializeCommand command = new DeserializeCommand(received, new AlertList());
            alertListActivity.setAlerts((AlertList) command.execute());
            return received;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        DeserializeCommand command = new DeserializeCommand(s, new AlertList());
        alertListActivity.setAlerts((AlertList) command.execute());
    }
}
