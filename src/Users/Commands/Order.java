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
    protected String reqType;
    protected int size;
    protected int treshold;
    protected String type;
    protected CommandBehaviour myBehaviour;
    protected ConcurrentHashMap<String, User> map;
    protected Orderbook orderbook;
    protected int unicode;
    
    public Order(String[] input){
        this.type = input[0];
        this.reqType = input[1];
        this.size = Integer.parseInt(input[2]);
        this.treshold = 0;
        if(input.length==4){
            this.treshold = Integer.parseInt(input[3]);
        }
        //settare behaviour
    }

    //costruttore MarketOrder
    public Order(String orderType, String type, int size){
        this.type = orderType;
        setBehaviour(new MarketOrder());
        this.reqType = type;
        this.size = size;
        this.treshold = 0;
        
    }

    //costruttore LimitOrder e StopOrder
    public Order(String orderType, String type, int size, int treshold){
        this.type = orderType;
        if (orderType.toLowerCase().contains("stoporder"))setBehaviour(new StopOrder());
        if (orderType.toLowerCase().contains("limitorder"))setBehaviour(new LimitOrder());
        this.reqType = type;
        this.size = size;
        this.treshold = treshold;
        //setBehaviour(new MarketOrder());
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
        info[0] = this.reqType;
        info[1] = ""+this.size;
        info[2] = ""+this.treshold;
        return info;
    }
    
    @Override
    public String getType() {
        return this.type;
    }

    public String toString() {
        return "Order{" +
               "orderType='" + this.type + '\'' +
               ", type='" + this.reqType + '\'' +
               ", size=" + this.size +
               ", treshold=" + this.treshold +
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

    
}
