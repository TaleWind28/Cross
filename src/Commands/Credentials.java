package Commands;
import Communication.Message;
public class Credentials extends UserCommand{
    private String accessType;
    private String username;
    private String password;
    private String newPassword;

    public Credentials(String accessType,String user){
        super();
        setType("credentials");
        this.username = user;
        this.accessType = "logout";
    }

    public Credentials(String accessType,String user, String passwd){
        super();
        setType("credentials");
        this.username = user;
        this.password = passwd;
        this.accessType = accessType;
    }

    public Credentials(String accessType,String user, String passwd, String newPasswd){
        super();
        setType("credentials");
        this.accessType = "updateCredentials";
        this.username = user;
        this.password = passwd;
        this.newPassword = newPasswd;
        this.accessType = accessType;
    }

    @Override
    public void execute(Message output) {//correggere con strategy
        //per semplicità implemento con if
        switch(this.accessType.toLowerCase()){
            case "register":
                //controllare che username non esista già
                //memorizzare username e password
                output.payload = "utente correttamente registrato col nome: "+this.getUsername();
                output.code = 201;//created
                return;
            case "logout":
                //controllare che username esista
                //sloggare
                output.payload = "utente correttamente sloggato: "+this.getUsername();
                output.code = 200;

                return;
            case "login":
                //controllare che username e password corrispondano
                //si -> loggare
                output.payload = "utente loggato correttamente col nome: "+this.getUsername();
                output.code = 200;
                return;
            case "updateCredentials":
                //controllare che username e password corrispondano
                //si -> sostituire password esistente con nuova password
                output.code = 200;
                return;
        }
        
        
        return;
        // throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getNewPassword() {
        return newPassword;
    }

    public String getPassword() {
        return password;
    }
    
    @Override
    public String[] getInfo(){
        
        if (this.getType().equals("register")){
            String[] info = new String[1];
            info[0] = this.username;
            return info;
        }
        
        if (this.getType().equals("register")){
            String[] info = new String[2];
            info[0] = this.username;
            info[1] = this.password;
            return info;
        }
        
        if (this.getType().equals("register")){
            String[] info = new String[3];
            info[0] = this.username;
            info[1] = this.password;
            info[2] = this.newPassword;
            return info;
        }
        String[] info = new String[3];
            info[0] = this.username;
            info[1] = this.password;
            info[2] = this.newPassword;
        return info;
    }
}
