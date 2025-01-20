package Users.Commands.CommandBehaviours;
import Communication.Message;
import ServerTasks.GenericTask;
import Users.Commands.UserCommand;

public class LimitOrder implements CommandBehaviour{
    public Message executeOrder(UserCommand ord,GenericTask context){
        System.out.println("limitorder: "+ord.getInfo());
        return new Message("limitorder",200);
    }

    @Override
    public int getUnicode() {
        return 102;    
    }
}
