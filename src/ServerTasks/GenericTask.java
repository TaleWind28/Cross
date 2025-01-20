package ServerTasks;

import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import Communication.Message;
import Communication.Protocol;
import Users.Commands.*;
import Users.Commands.Factory.FactoryRegistry;
import Executables.ServerMain;

public class GenericTask implements Runnable {
    private Socket client;
    private long CONNECTION_TIMEOUT = 60;//tempo in secondi
    //private GameFactory factory;
    private ServerMain generatorServer;
    private ScheduledExecutorService timeoutScheduler;
    private ScheduledFuture<?> timeoutTask;
    private Protocol protocol;
    public String onlineUser = new String();
    public String helpMessage = "Comandi:\nregister<username,password> -> ti permette di registrarti per poter accedere al servizio di trading\nlogin<username,password> -> permette di accedere ad un account registrato\nupdateCredentials<username,currentPasswd,newPasswd> -> permette di aggiornare le credenziali\nlogout<username> -> permette di uscire dal servizio di trading";

    public GenericTask(Socket client_socket,Protocol protocol) throws Exception{
        super();
        this.client = client_socket;
        this.protocol = protocol;
        protocol.setSender(client_socket);
        protocol.setReceiver(client_socket);
        this.timeoutScheduler = Executors.newSingleThreadScheduledExecutor();
        //this.generatorServer = null; 
    }
    //overloading
    public GenericTask(Socket client_socket,ServerMain server,Protocol protocol) throws Exception{
        super();
        this.client = client_socket;
        this.protocol = protocol;
        this.protocol.setSender(client_socket);
        this.protocol.setReceiver(client_socket);
        this.timeoutScheduler = Executors.newSingleThreadScheduledExecutor();
        this.generatorServer = server; 
    }
    //overloading
    public GenericTask(Socket client_socket,String delimiter,Protocol protocol) throws Exception{
        super();
        this.protocol = protocol;
        this.client = client_socket;
        protocol.setSender(client_socket);
        protocol.setReceiver(client_socket); 
        this.timeoutScheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void run(){
        DisconnectTask inactivityDisconnection = new DisconnectTask(this.protocol,this.client,this.generatorServer);
        protocol.sendMessage(new Message("Per fare trading inserire un ordine di qualunque tipo"));
        try{
            while(!(Thread.currentThread().isInterrupted())){
                
                //avvio timeout inattività
                this.timeoutTask = this.timeoutScheduler.schedule(inactivityDisconnection, CONNECTION_TIMEOUT, TimeUnit.SECONDS);
                //recupero il messaggio del client
                Message clientRequest = protocol.receiveMessage();
                // //azzero il timeout
                this.timeoutTask.cancel(false);
                this.timeoutTask = null;
                //stampa il contenuto del messaggio ricevuto
                System.out.println("run:"+clientRequest.payload.toString());
                //reagisci al messaggio
                this.serverReact(clientRequest);
            }
        }
        catch(IllegalStateException e){
            System.out.println("chiudo tutto");
            return;
        }
        catch(NullPointerException e){
            this.generatorServer.onClientDisconnect(client, "Disconnessione Client");
            return;
        }
        catch(Exception e){
            System.out.println("moio");
            return;
        }
    }

    public void serverReact(Message clientRequest){
        //disconnessione volontaria -> tecnicamente è un comando
        if(clientRequest.payload.equals("FIN")){
            if(!this.onlineUser.equals("")){
                new Credentials("logout", this.onlineUser).execute(this);
            }
            protocol.sendMessage(new Message("FIN",200));
            this.generatorServer.onClientDisconnect(client, "Client Disconnesso Volontariamente");
            this.timeoutTask.cancel(false);
            return;
        }
        //stampa di debug
        System.out.println("richiesta factory: "+clientRequest.payload);
        //creo il comando richiedendo la factory
        UserCommand cmd = FactoryRegistry.getFactory(clientRequest.code).createUserCommand(clientRequest.payload.split(" "));
        //stampa di debug
        System.out.println("Comando fabbricato: "+cmd.toString());
        //creo il messaggio da inviare al client
        Message responseMessage = new Message();
        //controllo la validità del comando
        int confirmationCode = this.validate(cmd);
        //stampa di debug
        System.out.println("CC: "+confirmationCode);
        //se il codice non appartiene all'intervallo 100-199 allora non è un comando valido -> tengo degli slot liberi per eventuali altri comandi
        if (confirmationCode <99 || confirmationCode>200){
            //memorizzo il codice nel messaggio
            responseMessage.code = confirmationCode;
            //controllo su codici specifici per personalizzare la risposta
            if(confirmationCode == 401)responseMessage.payload = responseMessage.code+": Non possiedi le autorizzazioni necessarie!";
            //se non ho codici particolari allora il comando è malformato
            else responseMessage.payload = responseMessage.code+": Comando non correttamente formulato";
        }
        //se il comando è valido lo eseguo
        else responseMessage = cmd.execute(this);
        //Stampa di debug -> risposta del server
        System.out.println("Messaggio generato:\nPayload: "+responseMessage.payload+", code: "+responseMessage.code);
        //invio il messaggio al client
        protocol.sendMessage(responseMessage);
        if(responseMessage.code == 408)
        return;
    }

    //controllo solo che il comando sia benformato -> avendo il contesto si potrebbe fare direttamente nel behaviour
    private int validate(UserCommand cmd){
        if(cmd.getType().equals("credentials") && (cmd.getInfo()[1] == "none"))return 400;
        if (cmd.getType().equals("none"))return 400;
        if (cmd.getType().equals("internaloperation"))return 120;//codice delle operazioni interne
        
        if (!cmd.getInfo()[1].equals(this.onlineUser) && (cmd.getInfo()[0].toLowerCase().equals("logout")||cmd.getInfo()[0].toLowerCase().equals("updatecredentials"))){
            return 401;
        }
        if(cmd.getType().toLowerCase().contains("order")){
            //se l'utente non è loggato bisogna 
            if (this.onlineUser.equals("")){
                return 401;
            }
        }
        //codici riconoscitivi dei comandi -> 104 - 107 sono Credentials, 100 - 103 sono Order 
        return cmd.getUnicode();
    }
}


