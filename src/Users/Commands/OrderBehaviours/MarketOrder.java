package Users.Commands.OrderBehaviours;
import Communication.Message;
import Users.Commands.UserCommand;

public class MarketOrder extends OrderBehaviour {
    public MarketOrder(){
        super();
        setBehaviour("market_order");
    }
    @Override
    public Message executeOrder(UserCommand ord){
        System.out.println("marketorder: "+ord.getInfo());
        //output.payload = "ordine inserito correttamente";
        return new Message("MarketOrder Correttamente Inserito",200);
    }
}
