package Users.Commands;
import Users.Communication.Message;
public class Credentials extends UserCommand{
    private String accessType;
    private String username;
    private String password = new String();
    private String newPassword = new String();

    public Credentials(String accessType,String user){
        super();
        setType("credentials");
        this.username = user;
        this.accessType = accessType;
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
    public Message execute() {//correggere con strategy
        //per semplicità implemento con if
        switch(this.accessType.toLowerCase()){
            case "register":
                //controllare che username non esista già
                //memorizzare username e password
                return new Message("utente correttamente registrato col nome: "+this.getUsername(),201);
            case "logout":
                //controllare che username esista
                //sloggare
                return new Message("utente correttamente sloggato: "+this.getUsername(),200);
            case "login":
                //controllare che username e password corrispondano
                //si -> loggare
                return new Message("utente correttamente loggato col nome: "+this.getUsername(),200);
            case "updateCredentials":
                //controllare che username e password corrispondano
                //si -> sostituire password esistente con nuova password
                return new Message("Credenziali aggiornate", 200);
        }
        
        
        return new Message();
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
        String[] info = new String[4];
        info[0] = this.accessType;
        info[1] = this.username;
        info[2] = this.password;
        info[3] = this.newPassword;
        return info;
    }
}
