package Users.Commands.Factory;

import Users.Commands.UserCommand;

public interface UserCommandFactory {
    public UserCommand createUserCommand(String[] command);
}
