package Users.Commands.Factory;

import JsonMemories.JsonAccessedData;
import Users.Commands.Order;
import Users.Commands.UserCommand;

public class OrderFactory implements UserCommandFactory{
    private int orderNumber = 0;
    @Override
    public UserCommand createUserCommand(String[] command) {
        //System.out.println(command[0]+" "+command[1]+" "+command[2]);
        String orderType = command[0].toLowerCase();
        String type = command[1].toLowerCase();
        System.out.println(orderType+":"+type);
        int size = -1;
        int price = -1;
        try {
            orderNumber++;
            //qtà di bitcoin
            size = Integer.parseInt(command[2]);
            System.out.println("dopo parsing");
            //if (orderType.contains("marketorder") && command.length != 3)throw new Exception("Numero parametri errati per un marketorder");
            if(orderType.contains("marketorder")){
                //creo solo marketorder
                if(command.length!=3)throw new Exception("Numero parametri errati per un marketorder");
                return new Order(orderType, type, size, orderNumber);
            }
            System.out.println("provo");
            //gli unici altri ordini da creare sono Limit e Stop che richiedono un quarto parametro
            if (command.length > 4)throw new Exception("Numero parametri errati per stoporder e limitorder");
            //soglia per limit e stoporder
            price = Integer.parseInt(command[3]);
            //creo l'ordine
            return new Order(orderType,type,size,price,orderNumber);
        }catch (Exception e) {
            System.out.println("out of bounds");
            return new Order(orderType,type,price,-1);
        }
    }

    @Override
    public void setJsonDataStructure(JsonAccessedData data) {
        return;
    }
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
