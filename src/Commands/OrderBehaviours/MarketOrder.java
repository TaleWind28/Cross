package Commands.OrderBehaviours;
import Commands.Order;
import Communication.Message;

public class MarketOrder extends OrderBehaviour {
    public MarketOrder(){
        super();
        setBehaviour("market_order");
    }
    @Override
    public Message executeOrder(Order ord){
        System.out.println("marketorder: "+ord.getInfo());
        //output.payload = "ordine inserito correttamente";
        return new Message("MarketOrder Correttamente Inserito",200);
    }
}
