package Users.Commands.CommandBehaviours;
import Communication.Message;
import ServerTasks.GenericTask;
import Users.Commands.UserCommand;

public class MarketOrder implements CommandBehaviour {
    
    @Override
    public Message executeOrder(UserCommand ord,GenericTask context){
        if(context.onlineUser.equals(""))return new Message("401: Per effettuare ordini bisogna creare un account o accedervi",401);
        System.out.println("marketorder: "+ord.getInfo());
        //output.payload = "ordine inserito correttamente";
        return new Message("MarketOrder Correttamente Inserito",200);
    }

    @Override
    public int getUnicode() {
        return 100;    
    }

}
