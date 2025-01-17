package Users.Commands.OrderBehaviours;
import Communication.Message;
import JsonMemories.JsonAccessedData;
import Users.Commands.UserCommand;

public abstract class OrderBehaviour {
    //implementare a modo passando un riferimento all'oggetto
    private JsonAccessedData jsonData;
    
    private String behaviour;
    
    public abstract Message executeOrder(UserCommand cmd);
    
    public void setJsonData(JsonAccessedData data){
        this.jsonData = data;
    }

    public JsonAccessedData getJsonData(){
        return this.jsonData;
    }

    public String getBehaviour() {
        return this.behaviour;
    }

    public void setBehaviour(String behaviour) {
        this.behaviour = behaviour;
    }
    
    // public String toString(){
    //     return this.behaviour;
    // }
}
