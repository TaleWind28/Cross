package Users.Commands.CommandBehaviours;
import Communication.Message;
import ServerTasks.GenericTask;
import Users.Commands.UserCommand;

public class LimitOrder implements CommandBehaviour{
    public Message executeOrder(UserCommand ord,GenericTask context){
        if(context.onlineUser.equals(""))return new Message("401: Per effettuare ordini bisogna creare un account o accedervi",401);
        System.out.println("limitorder: "+ord.getInfo());
        
        return new Message("limitorder",200);
    }

    @Override
    public int getUnicode() {
        return 102;    
    }
}
