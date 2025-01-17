package Users.Commands.CommandBehaviours;
import Communication.Message;
import Users.Commands.UserCommand;

public interface CommandBehaviour {
    public  Message executeOrder(UserCommand cmd);
    
}
