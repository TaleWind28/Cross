package Users.Commands.CommandBehaviours;
import Communication.Message;
import Users.Commands.UserCommand;
public class StopOrder implements CommandBehaviour{
    public Message executeOrder(UserCommand cmd){
        return new Message("stoporder",200);
    }
}
