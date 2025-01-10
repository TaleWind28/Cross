package Commands.Factory;

import Commands.Credentials;
import Commands.UserCommand;

public class CredentialsFactory implements UserCommandFactory{

    @Override
    public UserCommand createUserCommand(String[] command) throws ArrayIndexOutOfBoundsException{
        String type = command[0].toLowerCase();
        System.out.println(type);
        try {
            String username = command[1];   
            String password = null;
            String newPassword = null;
            switch (type) {
                case "logout":
                    return new Credentials(username);
                case "register":
                    password = command[2];
                    return new Credentials(username,password);
                case "login":
                    password = command[2];
                    return new Credentials(username,password);
                case "updateCredentials":
                    password = command[2];
                    newPassword = command[3];
                    return new Credentials(username,password,newPassword);
            }
            return new Credentials(username);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("out of bounds");
            return new Credentials("none");
        }
    }
    
}
