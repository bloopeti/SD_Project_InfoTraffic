package common.commands.serialization;

import common.commands.Command;
import com.google.gson.Gson;

public class DeserializeCommand implements Command {
    private String toDeSerialize;
    private Object target;

    public DeserializeCommand(String toDeSerialize, Object target) {
        this.toDeSerialize = toDeSerialize;
        this.target = target;
    }

    public Object execute()
    {
        Gson gson = new Gson();
        return gson.fromJson(toDeSerialize, target.getClass());
    }
}
