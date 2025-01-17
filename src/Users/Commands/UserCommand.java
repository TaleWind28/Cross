package Users.Commands;

import Communication.Message;
import JsonMemories.JsonAccessedData;

public interface UserCommand {
    public Message execute();
    public String[] getInfo();
    public String getType();
    public JsonAccessedData getJsonAccessedData();
}
