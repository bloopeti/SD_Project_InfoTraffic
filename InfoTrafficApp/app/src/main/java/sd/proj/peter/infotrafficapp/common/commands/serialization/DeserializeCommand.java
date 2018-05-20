package sd.proj.peter.infotrafficapp.common.commands.serialization;

import com.google.gson.Gson;
import sd.proj.peter.infotrafficapp.common.commands.Command;

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
