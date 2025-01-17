package Users.Commands.OrderBehaviours;
import Communication.Message;
import Users.Commands.Order;

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
