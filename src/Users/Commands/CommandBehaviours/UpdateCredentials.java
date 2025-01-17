package Users.Commands.CommandBehaviours;

import Communication.Message;
import JsonMemories.Userbook;
import ServerTasks.GenericTask;
import Users.User;
import Users.Commands.UserCommand;

public class UpdateCredentials implements CommandBehaviour{

    @Override
    public Message executeOrder(UserCommand cmd,GenericTask context) {
        String[] credentialsInfo = cmd.getInfo();
        Userbook userbook = (Userbook)cmd.getJsonAccessedData();

        //controllare che username e password corrispondano
        int retvalue = userbook.checkCredentials(new User(credentialsInfo[1],credentialsInfo[2]));
        if(retvalue == 400)return new Message("Password non valida",400);
        else if(retvalue == 404)return new Message("Utente non registrato",404);
        //si -> sostituire password esistente con nuova password
        userbook.getUserMap().get(credentialsInfo[1]).setPassword(credentialsInfo[3]);
        userbook.dataFlush();
        return new Message("Credenziali aggiornate", 200);
    }
    @Override
    public int getUnicode() {
        return 107;    
    }
}

