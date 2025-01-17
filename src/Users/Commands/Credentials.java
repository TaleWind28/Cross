package Users.Commands;
import Communication.Message;
import JsonMemories.JsonAccessedData;
import JsonMemories.Userbook;
import Users.Commands.CommandBehaviours.CommandBehaviour;
import Users.Commands.CommandBehaviours.UpdateCredentials;
public class Credentials implements UserCommand{
    private String type = "credentials";
    private String accessType;
    private String username;
    private String password = new String();
    private String newPassword = new String();
    private Userbook userbook;
    private CommandBehaviour myBehaviour;

    public Credentials(String accessType,String user){
        this.username = user;
        this.accessType = accessType;
    }

    public Credentials(String accessType,String user, Userbook userbook,CommandBehaviour behaviour){
        this.username = user;
        this.accessType = accessType;
        this.userbook = userbook;
        this.myBehaviour = behaviour;
        //setPayload({user,accessType});
    }

    public Credentials(String accessType,String user, String passwd, Userbook userbook, CommandBehaviour behaviour){
        this.username = user;
        this.password = passwd;
        this.accessType = accessType;
        this.userbook = userbook;
        this.myBehaviour = behaviour;
    }

    public Credentials(String accessType,String user, String passwd, String newPasswd, Userbook userbook){
        this.accessType = "updateCredentials";
        this.username = user;
        this.password = passwd;
        this.newPassword = newPasswd;
        this.accessType = accessType;
        this.userbook = userbook;
        //pensare se passare lo userbook direttamente dal costruttore
        this.myBehaviour = new UpdateCredentials();
    }

    @Override
    public Message execute() {
        return this.myBehaviour.executeOrder(this);
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

    public String toString(){
        return "Credentials{" +
               "credType='" + this.type + '\'' +
               ", username='" + this.username+ '\'' +
               ", passwd=" + this.password +
               ", newPasswd=" + this.newPassword +
               "," +
               '}';
    }

    @Override
    public String getType() {
        return this.type;
    }

	@Override
	public JsonAccessedData getJsonAccessedData() {
        return this.userbook;
    }
}
