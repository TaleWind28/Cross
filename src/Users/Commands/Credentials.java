package Users.Commands;
import Communication.Message;
import JsonMemories.Userbook;
import Users.User;
public class Credentials extends UserCommand{
    private String accessType;
    private String username;
    private String password = new String();
    private String newPassword = new String();
    private Userbook userbook;

    public Credentials(String accessType,String user){
        super();
        setType("credentials");
        this.username = user;
        this.accessType = accessType;
    }

    public Credentials(String accessType,String user, Userbook userbook){
        super();
        setType("credentials");
        this.username = user;
        this.accessType = accessType;
        this.userbook = userbook;
        //setPayload({user,accessType});
    }

    public Credentials(String accessType,String user, String passwd, Userbook userbook){
        super();
        setType("credentials");
        this.username = user;
        this.password = passwd;
        this.accessType = accessType;
        this.userbook = userbook;
    }

    public Credentials(String accessType,String user, String passwd, String newPasswd, Userbook userbook){
        super();
        setType("credentials");
        this.accessType = "updateCredentials";
        this.username = user;
        this.password = passwd;
        this.newPassword = newPasswd;
        this.accessType = accessType;
        this.userbook = userbook;
    }

    @Override
    public Message execute() {//correggere con strategy
        //per semplicità implemento con if
        User newUser = new User(this.username,this.password);
        switch(this.accessType.toLowerCase()){
            case "register":
                System.out.println("Primo Controllo");
                //controllare che username non esista già
                System.out.println(this.userbook.toString());
                
                if(this.userbook.accessData(this.username) == 200)return new Message("Utente già presente nel database",200);//sostituire con eccezzione
                System.out.println("controllo dati utente esistente superato");
                //memorizzare username e password
                System.out.println("utente creato");
                this.userbook.addData(newUser);
                System.out.println("entro");
                return new Message("utente correttamente registrato col nome: "+this.getUsername(),201);
            case "logout":
                //controllare che username esista
                if(this.userbook.accessData(newUser.getUsername()) == 404)return new Message("Utente non registrato",404);
                if(this.userbook.getUserMap().get(newUser.getUsername()).getLogged() == false)return new Message("Utente non attualmente loggato",400);
                //ulteriore controllo su cih sta chiedendo il logout
                //sloggare
                this.userbook.getUserMap().get(getUsername()).setLogged(false);
                this.userbook.dataFlush();
                return new Message("utente correttamente sloggato: "+this.getUsername(),200);
            case "login":
                //creo un user di cui verificare le credenziali 
                User user = new User(username, password);
                //invoco checkCredentials e memorizzo il codice di ritorno
                int retcode = this.userbook.checkCredentials(user);
                //discerno i codici di ritorno
                if(retcode == 400)return new Message("Password non valida",400);
                else if(retcode == 404)return new Message("Utente non registrato",404);
                //se il codice di ritorno non è uno dei codici di errore allora username e password sono corretti
                //loggo l'utente
                user.setLogged(true);
                //aggiorno lo status sulla mappa
                this.userbook.getUserMap().get(username).setLogged(true);
                //aggiorno il jsonOriginale
                this.userbook.dataFlush();
                return new Message("utente correttamente loggato col nome: "+this.getUsername(),200);
            case "updatecredentials":
                //controllare che username e password corrispondano
                int retvalue = this.userbook.checkCredentials(newUser);
                if(retvalue == 400)return new Message("Password non valida",400);
                else if(retvalue == 404)return new Message("Utente non registrato",404);
                //si -> sostituire password esistente con nuova password
                this.userbook.getUserMap().get(newUser.getUsername()).setPassword(newPassword);
                this.userbook.dataFlush();
                return new Message("Credenziali aggiornate", 200);
        }
        return new Message();
        // throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getNewPassword() {
        return newPassword;
    }

    public String getPassword() {
        return password;
    }
    
    @Override
    public String[] getInfo(){
        String[] info = new String[4];
        info[0] = this.accessType;
        info[1] = this.username;
        info[2] = this.password;
        info[3] = this.newPassword;
        return info;
    }

    public String toString(){
        return "Credentials{" +
               "credType='" + this.getType() + '\'' +
               ", username='" + this.username+ '\'' +
               ", passwd=" + this.password +
               ", newPasswd=" + this.newPassword +
               "," +
               '}';
    }
}
