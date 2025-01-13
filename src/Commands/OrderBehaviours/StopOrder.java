package Commands.OrderBehaviours;
import Commands.Order;
import Communication.Message;
public class StopOrder extends OrderBehaviour{
    public StopOrder(){
        super();
        setBehaviour("stop_order");
    }

    public Message executeOrder(Order ord){
        return new Message("stoporder",200);
    }
}
