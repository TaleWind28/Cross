package ServerTasks;

import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import Communication.Message;
import Communication.Protocol;
import Communication.ServerProtocol;

import Commands.*;
import Commands.Factory.FactoryRegistry;

public class GenericTask implements Runnable {
    private Socket client;
    private long CONNECTION_TIMEOUT = 60;//tempo in secondi
    //private GameFactory factory;
    private ServerProtocol disconnectBehaviour;
    private ScheduledExecutorService timeoutScheduler;
    private ScheduledFuture<?> timeoutTask;
    private Protocol protocol;
    private String onlineUser = new String();

    public GenericTask(Socket client_socket,Protocol protocol) throws Exception{
        super();
        this.client = client_socket;
        this.protocol = protocol;
        protocol.setSender(client_socket);
        protocol.setReceiver(client_socket);
        this.timeoutScheduler = Executors.newSingleThreadScheduledExecutor();
        //this.disconnectBehaviour = null; 
    }
    //overloading
    public GenericTask(Socket client_socket,ServerProtocol disconnection,Protocol protocol) throws Exception{
        super();
        this.client = client_socket;
        this.protocol = protocol;
        protocol.setSender(client_socket);
        protocol.setReceiver(client_socket);
        this.timeoutScheduler = Executors.newSingleThreadScheduledExecutor();
        this.disconnectBehaviour = disconnection; 
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
        DisconnectTask inactivityDisconnection = new DisconnectTask(this.protocol,this.client,this.disconnectBehaviour);
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
            this.disconnectBehaviour.onClientDisconnect(client, "Disconnessione Client");
            return;
        }
        catch(Exception e){
            System.out.println("moio");
            return;
        }
    }

    public void serverReact(Message clientRequest){
        
        String factoryrequest = null;
        //disconnessione volontaria
        if(clientRequest.payload.equals("FIN")){
            protocol.sendMessage(new Message("FIN",200));
            this.disconnectBehaviour.onClientDisconnect(client, "Client Disconnesso Volontariamente");
            this.timeoutTask.cancel(false);
            return;
        }
        if(clientRequest.payload.toLowerCase().contains("logout") || clientRequest.payload.toLowerCase().contains("login") || clientRequest.payload.toLowerCase().contains("updatecredentials") || clientRequest.payload.toLowerCase().contains("register")){
            factoryrequest = "credentials";
        }else
        if(clientRequest.payload.toLowerCase().contains("marketorder") || clientRequest.payload.toLowerCase().contains("stoporder") || clientRequest.payload.toLowerCase().contains("limitorder")){
            factoryrequest = "order";
        }else{
            protocol.sendMessage(new Message("Comando non corretto",400));
            return;
        }
        System.out.println("richiesta factory: "+factoryrequest);
        UserCommand cmd = FactoryRegistry.getFactory(factoryrequest).createUserCommand(clientRequest.payload.split(" "));
        //cmd.toString();
        System.out.println("Comando fabbricato: "+cmd.toString());
        Message responseMessage = new Message();
        
        System.out.println("Messaggio creato");
        //System.out.println(this.onlineUser);
        if (this.validateCommand(cmd)){
            //System.out.println("entro");
            cmd.execute(responseMessage);
            //Fare Strategy per credenziali 
            if (factoryrequest.equals("credentials") && (responseMessage.code == 201)){
                this.onlineUser = cmd.getInfo()[0];
                System.out.println(this.onlineUser);
            }
        }else{
            if(this.onlineUser.equals(""))responseMessage.code = 401;
            else responseMessage.code = 400;
        }   
        
        //risposta del server
        protocol.sendMessage(responseMessage);
    }

    private boolean validateCommand(UserCommand cmd){
        if(cmd.getType().equals("credentials") && (cmd.getInfo()[0] == "none")){
            return false;
        }
        if (cmd.getType().equals("none")){
            return false;
        }
        
        if(cmd.getType().toLowerCase().contains("order")){
            //se utente non autenticato -> return false
            if (this.onlineUser.equals("")){
                return false;
            }
        }
        return true;
    }
}


