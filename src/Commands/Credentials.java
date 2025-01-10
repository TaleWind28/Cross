package Commands;

public class Credentials extends UserCommand{
    private String username;
    private String password;
    private String newPassword;

    public Credentials(String user){
        this.username = user;
    }

    public Credentials(String user, String passwd){
        this.username = user;
        this.password = passwd;
    }

    public Credentials(String user, String passwd, String newPasswd){
        this.username = user;
        this.password = passwd;
        this.newPassword = newPasswd;
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }


}
