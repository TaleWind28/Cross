package Users.Commands.OrderBehaviours;
import Communication.Message;
import Users.Commands.Order;
public class StopOrder extends OrderBehaviour{
    public StopOrder(){
        super();
        setBehaviour("stop_order");
    }

    public Message executeOrder(Order ord){
        return new Message("stoporder",200);
    }
}
