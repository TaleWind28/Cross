package Commands.OrderBehaviours;

public class LimitOrder extends OrderBehaviour{
    public LimitOrder(){
        super();
        setBehaviour("limit_order");
    }
    public int executeOrder(){
        return 0;
    }
}
