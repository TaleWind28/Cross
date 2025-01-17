package Users.Commands.CommandBehaviours;

import Communication.Message;
import JsonMemories.Userbook;
import Users.User;
import Users.Commands.UserCommand;

public class Login implements CommandBehaviour {

    @Override
    public Message executeOrder(UserCommand cmd) {
        String[] credentialsInfo = cmd.getInfo();
        Userbook userbook = (Userbook)cmd.getJsonAccessedData();
        User user = new User(credentialsInfo[1], credentialsInfo[2]);
        //invoco checkCredentials e memorizzo il codice di ritorno
        int retcode = userbook.checkCredentials(user);
        //discerno i codici di ritorno
        if(retcode == 400)return new Message("Password non valida",400);
        else if(retcode == 404)return new Message("Utente non registrato",404);
        //se il codice di ritorno non è uno dei codici di errore allora username e password sono corretti
        //loggo l'utente
        user.setLogged(true);
        //aggiorno lo status sulla mappa
        userbook.getUserMap().get(credentialsInfo[1]).setLogged(true);
        //aggiorno il jsonOriginale
        userbook.dataFlush();
        return new Message("utente correttamente loggato col nome: "+credentialsInfo[1],200);

    }

}
