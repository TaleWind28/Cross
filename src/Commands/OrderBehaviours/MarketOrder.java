package Commands.OrderBehaviours;

public class MarketOrder extends OrderBehaviour {
    public MarketOrder(){
        super();
        setBehaviour("market_order");
    }
    public int executeOrder(){
        return 200;
    }
}
