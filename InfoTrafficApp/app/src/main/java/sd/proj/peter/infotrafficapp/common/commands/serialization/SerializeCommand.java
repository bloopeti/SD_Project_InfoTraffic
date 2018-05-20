package sd.proj.peter.infotrafficapp.common.commands.serialization;

import com.google.gson.Gson;
import sd.proj.peter.infotrafficapp.common.commands.Command;

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
