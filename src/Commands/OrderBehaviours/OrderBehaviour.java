package Commands.OrderBehaviours;
import Commands.Order;
import Communication.Message;

public abstract class OrderBehaviour {
    //implementare a modo passando un riferimento all'oggetto
    public int orderBook;
    
    private String behaviour;
    
    public abstract Message executeOrder(Order ord);
    
    public String getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(String behaviour) {
        this.behaviour = behaviour;
    }
    
    public String toString(){
        return this.behaviour;
    }
}
