package Users.Commands;

import Communication.Message;
import JsonMemories.JsonAccessedData;
import ServerTasks.GenericTask;
import Users.Commands.CommandBehaviours.CommandBehaviour;
import Users.Commands.CommandBehaviours.Disconnect;
import Users.Commands.CommandBehaviours.Help;

public class InternalCommand implements UserCommand{
    private String command;
    private CommandBehaviour myBehaviour;
    
    public InternalCommand(String commandtype){
        this.command = commandtype;
        setBehaviour(this.command);
    }

    public void setBehaviour(String btype){
        if (btype.toLowerCase().equals("disconnect"))this.myBehaviour = new Disconnect();
        else this.myBehaviour = new Help();
        return;
    }

    @Override
    public String getType() {
        return "internaloperation";
    }

    @Override
    public JsonAccessedData getJsonAccessedData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getJsonAccessedData'");
    }

    @Override
    public int getUnicode() {
        return myBehaviour.getUnicode();
    }
    @Override
    public Message execute(GenericTask context) {
        return this.myBehaviour.executeOrder(this, context);
    }
    @Override
    public String[] getInfo() {
        String[] info = this.command.split(" ");
        return info;
    }

}
