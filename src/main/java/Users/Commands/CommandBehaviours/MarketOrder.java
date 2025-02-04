package Users.Commands.CommandBehaviours;
import Communication.Message;
import JsonMemories.Orderbook;
import ServerTasks.GenericTask;
import Users.Commands.Order;
import Users.Commands.UserCommand;

public class MarketOrder implements CommandBehaviour {
    
    @Override
    public Message executeOrder(UserCommand cmd,GenericTask context){
        if(context.onlineUser.equals(""))return new Message("[401]: Per effettuare ordini bisogna creare un account o accedervi",401);
        Order ord = (Order)cmd;
        if(ord.getorderID() == -1)return new Message("[400]: ord non correttamente formato");
        System.out.println("marketorder: "+cmd.getInfo());
        Orderbook ordb = ord.getOrderbook();
        Order evadedOrder = ordb.removeData(ord.getExchangeType());
        if(evadedOrder == null)return new Message("[404] Non sono stati trovati ordini per le tue esigenze",-1);
        return new Message("[200]: MarketOrder Correttamente Inserito",200);
    }

    @Override
    public int getUnicode() {
        return 100;    
    }

}
