package common.commands.serialization;

import common.commands.Command;
import com.google.gson.Gson;

public class SerializeCommand implements Command {
    private Object toSerialize;

    public SerializeCommand(Object toSerialize) {
        this.toSerialize = toSerialize;
    }

    public String execute()
    {
        Gson gson = new Gson();
        return gson.toJson(toSerialize);
    }
}
