package Users.Commands;
import Communication.Message;
import JsonMemories.JsonAccessedData;
import JsonMemories.Userbook;
import ServerTasks.GenericTask;
import Users.Commands.CommandBehaviours.CommandBehaviour;

public class Credentials implements UserCommand{
    private String type = "credentials";
    private String accessType;
    private String username;
    private String password = new String();
    private String newPassword = new String();
    private Userbook userbook;
    private CommandBehaviour myBehaviour;
    private int unicode;

    public Credentials(String accessType,String user){
        this.username = user;
        this.accessType = accessType;
    }

    public Credentials(String accessType,String user, Userbook userbook,CommandBehaviour behaviour){
        this.username = user;
        this.accessType = accessType;
        this.userbook = userbook;
        this.myBehaviour = behaviour;
        this.unicode = behaviour.getUnicode();
        //setPayload({user,accessType});
    }

    public Credentials(String accessType,String user, String passwd, Userbook userbook, CommandBehaviour behaviour){
        this.username = user;
        this.password = passwd;
        this.accessType = accessType;
        this.userbook = userbook;
        this.myBehaviour = behaviour;
        this.unicode = behaviour.getUnicode();
    }

    public Credentials(String accessType,String user, String passwd, String newPasswd, Userbook userbook, CommandBehaviour behaviour){
        this.accessType = accessType;
        this.username = user;
        this.password = passwd;
        this.newPassword = newPasswd;
        this.accessType = accessType;
        this.userbook = userbook;
        this.myBehaviour = behaviour;
        this.unicode = behaviour.getUnicode();
    }

    @Override
    public Message execute(GenericTask context) {
        return this.myBehaviour.executeOrder(this,context);
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

    public int getUnicode() {
        return this.unicode;
    }
}
