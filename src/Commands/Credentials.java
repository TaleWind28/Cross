package Commands;
import Communication.Message;
public class Credentials extends UserCommand{
    private String username;
    private String password;
    private String newPassword;

    public Credentials(String user){
        super();
        setType("credentials");
        this.username = user;
    }

    public Credentials(String user, String passwd){
        super();
        setType("credentials");
        this.username = user;
        this.password = passwd;
    }

    public Credentials(String user, String passwd, String newPasswd){
        super();
        setType("credentials");
        this.username = user;
        this.password = passwd;
        this.newPassword = newPasswd;
    }

    @Override
    public void execute(Message output) {//correggere con strategy
        output.payload = "utente loggato correttamente col nome"+this.getUsername();
        output.code = 200;
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
