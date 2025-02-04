package Users.Commands.Factory;

import JsonMemories.JsonAccessedData;
import JsonMemories.Orderbook;
import Users.Commands.Order;
import Users.Commands.UserCommand;

public class OrderFactory implements UserCommandFactory{
    private int orderNumber = 0;
    private Orderbook orderbook;
    @Override
    public UserCommand createUserCommand(String[] command) {
        String type = null;
        String orderType = null;
        int size = -1;
        try {
            //sistemo il tipo di ordine per avere solo la parte significativa
            //(marketorder/limitorder/stoporder)
            orderType = command[0].toLowerCase().replace("insert", "");
            //(ask/bid)
            type = command[1].toLowerCase();
            //(qtà di bitcoin)
            size = Integer.parseInt(command[2]);
            //aumento l'order ID che DEVE essere unico
            orderNumber++;
            //prezzo di acquisto
            int price = Integer.parseInt(command[3]);
            return new Order(orderType,type,size,price,orderNumber,orderbook);    
        }//potrei generare delle eccezioni specifiche per marketorder e cancelorder
        catch (Exception e) {
            //controllo se è stato un marketorder a sollevare l'eccezione
            if(orderType.toLowerCase().equals("cancelorder"))return new Order(orderType,type,-1,-1,Integer.parseInt(command[1]),orderbook);
            //il prezzo è impostato ad un intero a caso perchè tanto non viene usato e soprattutto l'intellisense almeno è contento
            if(orderType.equals("marketorder")&& size!=-1)return new Order(orderType,type,size,100,orderNumber,orderbook);
            System.out.println("out of bounds");
            return null;
        }
    }

    @Override
    public void setJsonDataStructure(JsonAccessedData data) {
        this.orderbook = (Orderbook)data;
        return;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
