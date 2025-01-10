package Commands;
import Commands.OrderBehaviours.LimitOrder;
import Commands.OrderBehaviours.MarketOrder;
import Commands.OrderBehaviours.OrderBehaviour;
import Commands.OrderBehaviours.StopOrder;
import Communication.Message;

public class Order extends UserCommand{
    protected String reqType;
    protected int size;
    protected int treshold;
    protected OrderBehaviour myBehaviour;
    
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
    public void execute(Message output) {
        output.code =  myBehaviour.executeOrder();
        output.payload = "ordine inserito correttamente";
        System.out.println("eseguito");
        return;
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
