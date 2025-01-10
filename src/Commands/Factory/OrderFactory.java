package Commands.Factory;

import Commands.Order;
import Commands.UserCommand;

public class OrderFactory implements UserCommandFactory{

    @Override
    public UserCommand createUserCommand(String[] command) {
        String type = command[0].toLowerCase();
        int size = Integer.parseInt(command[1]);
        int treshold = 0;
        //creo solo marketorder
        if (!type.contains("marketorder")){
            treshold = Integer.parseInt(command[2]);
        }

        return new Order(type,size,treshold);
    } 
}
