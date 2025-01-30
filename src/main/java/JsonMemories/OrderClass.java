package JsonMemories;

import java.util.Map;

import Users.Commands.Order;

public class OrderClass {
    Map<Integer,Order> askMap;
    Map<Integer,Order>bidMap;
    public OrderClass(Map<Integer,Order> askMap,Map<Integer,Order>bidMap){
        this.askMap = askMap;
        this.bidMap = bidMap;
    }
}
