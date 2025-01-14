package Users.Commands.OrderBehaviours;
import Users.Commands.Order;
import Users.Communication.Message;
public class StopOrder extends OrderBehaviour{
    public StopOrder(){
        super();
        setBehaviour("stop_order");
    }

    public Message executeOrder(Order ord){
        return new Message("stoporder",200);
    }
}
