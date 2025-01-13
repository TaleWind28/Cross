package Commands;
import Communication.Message;
import org.apache.commons.lang3.ArrayUtils;

public abstract class UserCommand {
    private String type;
    private String[] payload;
    
    public UserCommand(){

    }

    public UserCommand(String[] userInput){
        this.type = userInput[0];
        this.payload = ArrayUtils.remove(userInput,0);
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public void setPayload(String[] payload) {
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public String[] getPayload() {
        return payload;
    }
    @Override
    public String toString() {
        StringBuilder payloadString = new StringBuilder();
        if (payload != null) {
            for (String item : payload) {
                payloadString.append(item).append(", ");
            }
            // Rimuove l'ultima virgola e spazio, se presenti
            if (payloadString.length() > 0) {
                payloadString.setLength(payloadString.length() - 2);
            }
        }

        return "UserCommand{" +
           "type='" + type + '\'' +
           ", payload=[" + payloadString + "]" +
           '}';
}

    public abstract Message execute();
    public abstract String[] getInfo();
}
