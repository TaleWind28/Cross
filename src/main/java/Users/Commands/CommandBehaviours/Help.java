package Users.Commands.CommandBehaviours;

import Communication.Message;
import ServerTasks.GenericTask;
import Users.Commands.UserCommand;

public class Help implements CommandBehaviour{

    @Override
    public Message executeOrder(UserCommand cmd, GenericTask context) {
        return new Message(context.helpMessage,200);
    }

    @Override
    public int getUnicode() {
        return 121;
    }

}
