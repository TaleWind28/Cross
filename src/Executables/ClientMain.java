package Executables;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import Users.Communication.ClientProtocol;
import Users.Communication.Message;
import Users.Communication.TCP;


public class ClientMain extends ClientProtocol{
    public volatile boolean canSend;
    public Socket sock = null;
    public String helpMessage = "Comandi:\nregister<username,password> -> ti permette di registrarti per poter accedere al servizio di trading\nlogin<username,password> -> permette di accedere ad un account registrato\nupdateCredentials<username,currentPasswd,newPasswd> -> permette di aggiornare le credenziali\nlogout<username> -> permette di uscire dal servizio di trading";
    public ClientMain(String IP, int PORT){
        super(IP,PORT);
        this.canSend = false;
        
    }
    
    public static void main(String args[]) throws Exception{
        ClientMain client = new ClientMain("127.0.0.1", 20000);
        client.multiDial();
    }

    public void receiveBehaviour(){
        try{
            while(true){
                Message serverAnswer = this.protocol.receiveMessage();
                //controllo risposta server
                switch (serverAnswer.code) {
                    case 200:
                        if (serverAnswer.payload.equals("FIN")){
                            System.out.println("Chiusura Connessione");
                            this.sock.close();
                            System.exit(0);
                        }
                        System.out.println(serverAnswer.payload);
                        this.canSend = true;
                        continue;
                    case 408:
                        this.sock.close();
                        System.out.println("moio");
                        System.exit(0);
                        continue;
                    case 401:
                        System.out.println(serverAnswer.code+": Autenticazione Richiesta!");
                        this.canSend = true;
                        continue;
                    case 400:
                        System.out.println("comando errato digitare aiuto per una lista dettagliata");
                        this.canSend = true;
                        continue;
                    default:
                        System.out.println(serverAnswer.payload);
                        this.canSend = true;
                        continue;
                }
                
            }
                

        }catch(IOException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        catch(NullPointerException e){
            System.out.println("Stiamo riscontrando dei problemi sul server, procederemo a chiudere la connessione, ci scusiamo per il disagio");
            System.exit(0);
        }
        
    }

    public void sendBehaviour(){ 
        while(true){
            if(this.canSend){
                Message serverCommand = new Message(this.userInput.nextLine());
                // String[] cmd = serverCommand.split(" "); 
                
                if(serverCommand.payload.equals("aiuto")){
                    System.out.println(helpMessage);
                    continue;
                }
                this.protocol.sendMessage(serverCommand);
                this.canSend = false;
            }
        }
    }
    //dialogo col server sfruttando il multithreading
    public void multiDial(){
        //apro il socket
        try {
            this.sock = new Socket(this.ip,this.port);
            this.setProtocol(new TCP());
            //imposto receiver, sender e senderThread
            this.protocol.setReceiver(sock);
            this.protocol.setSender(sock);
            this.setReceiverThread();
            //attivo il thread di ricezione
            this.receiverThread.start();
            //faccio eseguire il comportamento di invio dal main thread
            this.sendBehaviour();
        }
        //disconnessione accidentale
        catch (SocketException e) {
            //System.out.println(e.getClass());
            System.out.println("Il server al momento non è disponibile :( , ci scusiamo per il disagio ");
            System.exit(0);
        }
        //input errato
        catch(IOException e){
            System.out.println("C'è stato un'errore sulla lettura dal socket");
            System.exit(0);;

        }
        //eccezione generica
        catch(Exception e ){
            System.out.println("eccezione: "+e.getClass()+" : "+e.getStackTrace());
        }
        
    }
}
