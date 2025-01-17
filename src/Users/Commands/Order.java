package Users.Commands;
import java.util.concurrent.ConcurrentHashMap;

import Communication.Message;
import Users.User;
import Users.Commands.OrderBehaviours.LimitOrder;
import Users.Commands.OrderBehaviours.MarketOrder;
import Users.Commands.OrderBehaviours.OrderBehaviour;
import Users.Commands.OrderBehaviours.StopOrder;


public class Order extends UserCommand{
    protected String reqType;
    protected int size;
    protected int treshold;
    protected OrderBehaviour myBehaviour;
    protected ConcurrentHashMap<String, User> map;
    
    public Order(String[] input){
        super(input);
        this.reqType = this.getPayload()[0];
        this.size = Integer.parseInt(this.getPayload()[1]);
        this.treshold = 0;
        if(this.getPayload().length==3){
            this.treshold = Integer.parseInt(this.getPayload()[2]);
        }
        //settare behaviour
    }

    //costruttore MarketOrder
    public Order(String orderType, String type, int size){
        super();
        super.setType(orderType);
        this.myBehaviour = new MarketOrder();
        this.reqType = type;
        this.size = size;
        this.treshold = 0;
        
    }

    //costruttore LimitOrder e StopOrder
    public Order(String orderType, String type, int size, int treshold){
        super();
        super.setType(orderType);
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
    
    public String toString() {
        return "Order{" +
               "orderType='" + this.getType() + '\'' +
               ", type='" + reqType + '\'' +
               ", size=" + size +
               ", treshold=" + treshold +
               ", myBehaviour=" + (myBehaviour != null ? myBehaviour.toString() : "null") +
               '}';
    }

    
}
