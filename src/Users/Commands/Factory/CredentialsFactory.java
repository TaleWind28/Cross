package Users.Commands.Factory;

import JsonMemories.JsonAccessedData;
import JsonMemories.Userbook;
import Users.Commands.Credentials;
import Users.Commands.UserCommand;

public class CredentialsFactory implements UserCommandFactory{
    private Userbook userbook;
    public void setJsonDataStructure(JsonAccessedData userbook) {
        this.userbook = (Userbook)userbook;
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
                    System.out.println("Comando Register:\nusername: "+username+", password: "+password);
                    Credentials cred = new Credentials(type,username,password,userbook);
                    return cred;
                case "login":
                    password = command[2];
                    //System.out.println("passwd: "+password);
                    return new Credentials(type,username,password,userbook);
                case "updatecredentials":
                    password = command[2];
                    newPassword = command[3];
                    System.out.println("password:"+password+",newpassword: "+newPassword);
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
