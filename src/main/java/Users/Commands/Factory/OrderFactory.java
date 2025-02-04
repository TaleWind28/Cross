package Users.Commands.Factory;

import java.lang.invoke.SwitchPoint;

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
            //qtà di bitcoin
            size = Integer.parseInt(command[2]);
            //prezzo di acquisto
            int price = Integer.parseInt(command[3]);
            return new Order(orderType,type,size,price,orderNumber,orderbook);    
        }catch (Exception e) {
            //controllo solo la size perchè è l'ultimo parametro prima dell'eccezzioen che mi aspetto per i marketorder
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
