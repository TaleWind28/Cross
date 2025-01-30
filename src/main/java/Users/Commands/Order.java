package Users.Commands;
import java.util.concurrent.ConcurrentHashMap;

import Communication.Message;
import JsonMemories.JsonAccessedData;
import JsonMemories.Orderbook;
import ServerTasks.GenericTask;
import Users.User;
import Users.Commands.CommandBehaviours.LimitOrder;
import Users.Commands.CommandBehaviours.MarketOrder;
import Users.Commands.CommandBehaviours.CommandBehaviour;
import Users.Commands.CommandBehaviours.StopOrder;


public class Order implements UserCommand{
    protected int orderID;//CODICE UNIVOCO DI RICONOSCIMENTO DELL'ORDINE
    protected String type;//tipo di ordine
    protected String exchangeType;//modalità ordine: ask/bid
    protected int size;//qtà di bitcoin
    protected int price;//soglia di prezzo
    ///campi non visibili nel Json
    transient protected CommandBehaviour myBehaviour;//comportamento ordinw
    transient protected ConcurrentHashMap<String, User> map;//interessante
    transient protected Orderbook orderbook;//orderbook dove infilare gli ordini
    transient protected int unicode;//unicode degli ordini -> NON è IL CODICE UNIVOCO DELL'ORDINE SINGOLO
    
    
    public Order(String[] input){
        this.type = input[0];
        this.exchangeType = input[1];
        this.size = Integer.parseInt(input[2]);
        this.price = 0;
        if(input.length==4){
            this.price = Integer.parseInt(input[3]);
        }
        //settare behaviour
    }

    //costruttore MarketOrder
    public Order(String orderType, String type, int size, int orderNumber){
        this.type = orderType;
        setBehaviour(new MarketOrder());
        this.exchangeType = type;
        this.size = size;
        this.price = 0;
        this.orderID = orderNumber;
        
    }

    //costruttore LimitOrder e StopOrder
    public Order(String orderType, String type, int size, int price,int orderNumber){
        this.type = orderType;
        if (orderType.toLowerCase().contains("stoporder"))setBehaviour(new StopOrder());
        if (orderType.toLowerCase().contains("limitorder"))setBehaviour(new LimitOrder());
        this.exchangeType = type;
        this.size = size;
        this.price = price;
        this.orderID = orderNumber;
    }

    public void setBehaviour(CommandBehaviour behaviour){
        this.myBehaviour = behaviour;
        this.unicode = behaviour.getUnicode();
        return;
    }

    @Override
    public Message execute(GenericTask context) {
        //System.out.println(output.payload+output.code);
        return myBehaviour.executeOrder(this,context);
    }

    @Override
    public String[] getInfo(){
        String[] info = new String[4];
        info[0] = this.exchangeType;
        info[1] = ""+this.size;
        info[2] = ""+this.price;
        return info;
    }
    
    @Override
    public String getType() {
        return this.type;
    }

    public String toString() {
        return "Order{" +
               "orderType='" + this.type + '\'' +
               ", type='" + this.exchangeType + '\'' +
               ", size=" + this.size +
               ", price=" + this.price +
               ", myBehaviour=" + (this.myBehaviour != null ? this.myBehaviour.toString() : "null") +
               '}';
    }

    @Override
    public JsonAccessedData getJsonAccessedData() {
        return this.orderbook;
    }

    @Override
    public int getUnicode() {
        return this.unicode;
    }

    @Override
    public int validateCommand(UserCommand cmd) {
        //if(cmd.getInfo()[0])
        //al momento non so cosa devo controllare
        return this.getUnicode();
    }

    public int getorderID() {
        return orderID;
    }

    
}
