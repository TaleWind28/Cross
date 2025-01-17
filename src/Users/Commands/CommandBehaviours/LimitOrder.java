package Users.Commands.CommandBehaviours;
import Communication.Message;
import Users.Commands.UserCommand;

public class LimitOrder implements CommandBehaviour{
    public Message executeOrder(UserCommand ord){
        System.out.println("limitorder: "+ord.getInfo());
        return new Message("limitorder",200);
    }
}
