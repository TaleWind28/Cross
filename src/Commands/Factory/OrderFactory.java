package Commands.Factory;

import Commands.Order;
import Commands.UserCommand;

public class OrderFactory implements UserCommandFactory{

    @Override
    public UserCommand createUserCommand(String[] command) {
        String type = command[0].toLowerCase();
        try {
            int size = Integer.parseInt(command[1]);
            int treshold = 0;
        //creo solo marketorder
            if (!type.contains("marketorder")){
                treshold = Integer.parseInt(command[2]);
            }
            // System.out.println(type + size + treshold);
            return new Order(type,size,treshold);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("out of bounds");
            return new Order("none",-5);
        }
    } 
}
