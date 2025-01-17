package Users.Commands.OrderBehaviours;
import Communication.Message;
import Users.Commands.UserCommand;
public class StopOrder extends OrderBehaviour{
    public StopOrder(){
        super();
        setBehaviour("stop_order");
    }

    public Message executeOrder(UserCommand cmd){
        return new Message("stoporder",200);
    }
}
