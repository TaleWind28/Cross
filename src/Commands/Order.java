package Commands;
import Commands.OrderBehaviours.OrderBehaviour;


public class Order extends UserCommand{
    protected String type;
    protected int size;
    protected int treshold;
    protected OrderBehaviour myBehaviour;
    
    public Order(String[] input){
        super(input);
        this.type = super.getType();
        this.size = Integer.parseInt(this.getPayload()[0]);
        if(this.type.equals("marketorder")){
            this.treshold = 0;
        }else{
            this.treshold = Integer.parseInt(this.getPayload()[1]);
        }
        //settare behaviour
    }

    //costruttore MarketOrder
    public Order(String type, int size){
        this.type = type;
        this.size = size;
        this.treshold = 0;
    }

    //costruttore LimitOrder e StopOrder
    public Order(String type, int size, int treshold){
        this.type = type;
        this.size = size;
        this.treshold = treshold;
    }

    public void setBehaviour(OrderBehaviour behaviour){
        this.myBehaviour = behaviour;
        return;
    }

    @Override
    public void execute() {
        myBehaviour.executeOrder();
    }

    public String toString() {
        return "Order{" +
               "type='" + type + '\'' +
               ", size=" + size +
               ", treshold=" + treshold +
               ", myBehaviour=" + (myBehaviour != null ? myBehaviour.toString() : "null") +
               '}';
    }
}
