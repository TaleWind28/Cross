package Users.Commands.CommandBehaviours;

import Communication.Message;
import JsonMemories.Userbook;
import ServerTasks.GenericTask;
import Users.Commands.UserCommand;

public class Logout implements CommandBehaviour{

    @Override
    public Message executeOrder(UserCommand cmd,GenericTask context) {
        //codice duplicato -> cambiare -> mettere nel costruttore / chiamata di funzione per inizializzare
        String[] credentialsInfo = cmd.getInfo();
        Userbook userbook = (Userbook)cmd.getJsonAccessedData();
        //controllo che esista l'utente
        if(userbook.accessData(credentialsInfo[1]) == 404)return new Message("Utente non registrato",404);
        //controllo che l'utente sia effettivamente loggato
        if(userbook.getUserMap().get(credentialsInfo[1]).getLogged() == false)return new Message("Utente non attualmente loggato",400);
        //ulteriore controllo su chi sta chiedendo il logout
        //sloggare
        context.onlineUser = "";
        userbook.getUserMap().get(credentialsInfo[1]).setLogged(false);
        userbook.dataFlush();
        return new Message("utente correttamente sloggato: "+credentialsInfo[1],200);

    }
    @Override
    public int getUnicode() {
        return 106;    
    }

}
