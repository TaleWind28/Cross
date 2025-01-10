package Commands.OrderBehaviours;

public abstract class OrderBehaviour {
    //implementare a modo passando un riferimento all'oggetto
    public int orderBook;
    
    private String behaviour;
    
    public abstract int executeOrder();
    
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
