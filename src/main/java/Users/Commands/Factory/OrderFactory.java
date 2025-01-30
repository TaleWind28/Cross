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
        try {
            orderNumber++;
            //qtà di bitcoin
            int size = Integer.parseInt(command[2]);
            System.out.println("dopo parsing");
            //if (orderType.contains("marketorder") && command.length != 3)throw new Exception("Numero parametri errati per un marketorder");
            if(orderType.contains("marketorder")){
                //creo solo marketorder
                if(command.length!=3)throw new Exception("Numero parametri errati per un marketorder");
                return new Order(orderType, type, size, orderNumber);
            }
            //gli unici altri ordini da creare sono Limit e Stop che richiedono un quarto parametro
            if (command.length != 4)throw new Exception("Numero parametri errati per stoporder e limitorder");
            //soglia per limit e stoporder
            int treshold = Integer.parseInt(command[3]);
            //creo l'ordine
            return new Order(orderType,type,size,treshold,orderNumber);
        }catch (Exception e) {
            System.out.println("out of bounds");
            return new Order("none","none",-5,-1);
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
