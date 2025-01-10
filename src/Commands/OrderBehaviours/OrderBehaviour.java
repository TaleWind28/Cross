package Commands.OrderBehaviours;

public abstract class OrderBehaviour {
    //implementare a modo passando un riferimento all'oggetto
    public int orderBook;
    public abstract int executeOrder();
}
