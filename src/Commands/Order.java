package Commands;
import Commands.OrderBehaviours.MarketOrder;
import Commands.OrderBehaviours.OrderBehaviour;
import Communication.Message;

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
            setBehaviour(new MarketOrder());
        }else{
            this.treshold = Integer.parseInt(this.getPayload()[1]);
        }
        //settare behaviour
    }

    //costruttore MarketOrder
    public Order(String type, int size){
        super();
        super.setType(type);
        this.size = size;
        this.treshold = 0;
        setBehaviour(new MarketOrder());
    }

    //costruttore LimitOrder e StopOrder
    public Order(String type, int size, int treshold){
        super();
        super.setType(type);
        this.size = size;
        this.treshold = treshold;
        setBehaviour(new MarketOrder());
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
        info[0] = this.type;
        info[1] = ""+this.size;
        info[2] = ""+this.treshold;
        return info;
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
