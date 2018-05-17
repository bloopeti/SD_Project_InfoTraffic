package common.commands;

import common.commands.serialization.DeserializeCommand;

import common.model.Alert;
import common.model.User;

public class CommandFactory {

    public static Command getCommand(String[] args) {
        try {
            Object target;
            switch (args[0]) {
                case "alert":
                    target = new Alert();
                    break;
                case "user":
                    target = new User();
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
            switch (args[1]) {
                case "deserialize":
                    return new DeserializeCommand(args[2], target);
                case "serialize":
//                    return new SerializeCommand(//ai belit pulentiu//);
                default:
                    return null;

            }
        } catch (ArrayIndexOutOfBoundsException arrE) {
            return null;
        }

    }

}