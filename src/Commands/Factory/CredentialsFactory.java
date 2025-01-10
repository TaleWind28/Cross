package Commands.Factory;

import Commands.Credentials;
import Commands.UserCommand;

public class CredentialsFactory implements UserCommandFactory{

    @Override
    public UserCommand createUserCommand(String[] command) throws ArrayIndexOutOfBoundsException{
        String type = command[0].toLowerCase();
        String username = command[1];   
        String password = null;
        String newPassword = null;
        try {
            switch (type) {
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
        } catch (ArrayIndexOutOfBoundsException e) {
            System.exit(0);
        }
        return new Credentials(username);
    }
    
}
