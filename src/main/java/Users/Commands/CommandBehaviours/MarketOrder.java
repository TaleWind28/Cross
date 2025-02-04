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
        //System.out.println("marketorder: "+cmd.getInfo());
        Orderbook ordb = ord.getOrderbook();
        String exchangetype = null;
        switch (ord.getExchangeType()) {
            case "ask":
                exchangetype = "bid";
                break;
            case "bid":
                exchangetype = "ask";
                break;
        }
        //cerco il miglior prezzo per la qtà di bitcoin che voglio comprare
        String orderbookEntry = ordb.getBestPriceAvailable(ord.getSize(), exchangetype);
        //System.out.println("entry: "+orderbookEntry);
        //rimuovo l'ordine dall'orderbook
        Order evadedOrder = ordb.removeData(exchangetype,orderbookEntry);
        //controllo che l'ordine sia stato evaso
        if(evadedOrder == null)return new Message("[404] Non sono stati trovati ordini per le tue esigenze",-1);
        
        return new Message("[200]: MarketOrder Correttamente Evaso",200);
    }

    @Override
    public int getUnicode() {
        return 100;    
    }

}
