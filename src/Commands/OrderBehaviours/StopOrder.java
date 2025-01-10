package Commands.OrderBehaviours;

public class StopOrder extends OrderBehaviour{
    public StopOrder(){
        super();
        setBehaviour("stop_order");
    }

    public int executeOrder(){
        return 0;
    }
}
