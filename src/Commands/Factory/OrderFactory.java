package Commands.Factory;

import Commands.Order;
import Commands.UserCommand;

public class OrderFactory implements UserCommandFactory{

    @Override
    public UserCommand createUserCommand(String[] command) {
        //System.out.println(command[0]+" "+command[1]+" "+command[2]);
        String orderType = command[0].toLowerCase();
        String type = command[1].toLowerCase();
        System.out.println(orderType+":"+type);
        try {
            int size = Integer.parseInt(command[2]);
            System.out.println("dopo parsing");
            int treshold = 0;
            //creo solo marketorder
            if (orderType.contains("marketorder")){return new Order(orderType,type, size);}
            treshold = Integer.parseInt(command[3]);
            // System.out.println(type + size + treshold);
            return new Order(orderType,type,size,treshold);
            //return ord;
        }catch(NumberFormatException e){
            System.out.println("campo vuoto");
            return new Order("none", "none",-5);
        }
         catch (Exception e) {
            // TODO: handle exception
            System.out.println("out of bounds");
            return new Order("none","none",-5);
        }
    } 
}
