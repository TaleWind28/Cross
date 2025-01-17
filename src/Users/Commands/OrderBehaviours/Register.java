package Users.Commands.OrderBehaviours;

import Communication.Message;
import JsonMemories.Userbook;
import Users.User;
import Users.Commands.UserCommand;

public class Register extends OrderBehaviour{
    //private Userbook userbook;
    
    @Override
    public Message executeOrder(UserCommand cmd) {
        System.out.println("Primo Controllo");
        //controllare che username non esista già
        String[] credentialsInfo = cmd.getInfo();
        Userbook userbook = (Userbook)cmd.getJsonAccessedData();
        
        System.out.println(userbook.toString());
        
        if(userbook.accessData(credentialsInfo[1]) == 200)return new Message("Utente già presente nel database",200);//sostituire con eccezzione
        System.out.println("controllo dati utente esistente superato");
        //memorizzare username e password
        System.out.println("utente creato");
        userbook.addData(new User(credentialsInfo[1], credentialsInfo[2]));
        System.out.println("entro");
        return new Message("utente correttamente registrato col nome: "+credentialsInfo[1],201);
    }

}
