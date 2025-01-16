package Users.Commands.Factory;

import JsonMemories.Userbook;
import Users.Commands.Credentials;
import Users.Commands.UserCommand;

public class CredentialsFactory implements UserCommandFactory{
    private Userbook userbook;
    public void setUserbook(Userbook userbook) {
        this.userbook = userbook;
    }

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
                    return new Credentials(type,username,userbook);
                case "register":
                    password = command[2];
                    return new Credentials(type,username,password,userbook);
                case "login":
                    password = command[2];
                    return new Credentials(type,username,password,userbook);
                case "updateCredentials":
                    password = command[2];
                    newPassword = command[3];
                    return new Credentials(type,username,password,newPassword,userbook);
            }
            return new Credentials("none",username);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("out of bounds");
            return new Credentials("none","none");
        }
    }
    
}
