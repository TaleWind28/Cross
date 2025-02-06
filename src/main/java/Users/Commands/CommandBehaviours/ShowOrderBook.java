package Users.Commands.CommandBehaviours;

import Communication.Message;
import ServerTasks.GenericTask;
import Users.Commands.UserCommand;

public class ShowOrderBook implements CommandBehaviour{

    @Override
    public Message executeOrder(UserCommand cmd, GenericTask context) {
        
        return new Message("[200] Orderbook:",200);    
    }

    @Override
    public int getUnicode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUnicode'");
    }
    
}
