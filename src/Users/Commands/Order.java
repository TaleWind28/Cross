package Users.Commands;
import java.util.concurrent.ConcurrentHashMap;

import Communication.Message;
import Users.User;
import Users.Commands.OrderBehaviours.LimitOrder;
import Users.Commands.OrderBehaviours.MarketOrder;
import Users.Commands.OrderBehaviours.OrderBehaviour;
import Users.Commands.OrderBehaviours.StopOrder;


public class Order implements UserCommand{
    protected String reqType;
    protected int size;
    protected int treshold;
    protected String type;
    protected OrderBehaviour myBehaviour;
    protected ConcurrentHashMap<String, User> map;
    
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
        this.myBehaviour = new MarketOrder();
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

    public void setBehaviour(OrderBehaviour behaviour){
        this.myBehaviour = behaviour;
        return;
    }

    @Override
    public Message execute() {
        //System.out.println(output.payload+output.code);
        return myBehaviour.executeOrder(this);
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

    
}
