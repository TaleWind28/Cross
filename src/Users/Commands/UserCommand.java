package Users.Commands;

import Communication.Message;

public interface UserCommand {
    public Message execute();
    public String[] getInfo();
    public String getType();
}
