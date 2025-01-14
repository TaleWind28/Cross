package Users.Commands.Factory;

import Users.Commands.Order;
import Users.Commands.UserCommand;

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
            if (orderType.contains("marketorder") && command.length != 3)throw new Exception("Numero parametri errati per un marketorder");
            if ((orderType.contains("stoporder") || orderType.contains("limitorder"))&& command.length != 4)throw new Exception("Numero parametri errati per stoporder e limitorder");
            //creo solo marketorder
            if (orderType.contains("marketorder") && command.length == 3){return new Order(orderType,type, size);}
            if (command.length > 4) throw new Exception("comando malformato");
            treshold = Integer.parseInt(command[3]);
            // System.out.println(type + size + treshold);
            return new Order(orderType,type,size,treshold);
            //return ord;
        }catch (Exception e) {
            System.out.println("out of bounds");
            return new Order("none","none",-5);
        }
    } 
}
