package Commands.Factory;

import Commands.UserCommand;

public interface UserCommandFactory {
    public UserCommand createUserCommand(String[] command);
}
