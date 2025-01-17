package Users.Commands.OrderBehaviours;
import Communication.Message;
import Users.Commands.UserCommand;

public class LimitOrder extends OrderBehaviour{
    public LimitOrder(){
        super();
        setBehaviour("limit_order");
    }
    public Message executeOrder(UserCommand ord){
        System.out.println("limitorder: "+ord.getInfo());
        return new Message("limitorder",200);
    }
}
