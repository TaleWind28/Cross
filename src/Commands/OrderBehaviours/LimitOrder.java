package Commands.OrderBehaviours;
import Commands.Order;
import Communication.Message;

public class LimitOrder extends OrderBehaviour{
    public LimitOrder(){
        super();
        setBehaviour("limit_order");
    }
    public Message executeOrder(Order ord){
        System.out.println("limitorder: "+ord.getInfo());
        return new Message("limitorder",200);
    }
}
