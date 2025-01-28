package Executables;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;

import Communication.ClientProtocol;
import Communication.Message;
import Communication.TCP;


public class ClientMain extends ClientProtocol{
    public volatile boolean canSend;
    public Socket sock = null;
    public String helpMessage = "Comandi:\nregister<username,password> -> ti permette di registrarti per poter accedere al servizio di trading\nlogin<username,password> -> permette di accedere ad un account registrato\nupdateCredentials<username,currentPasswd,newPasswd> -> permette di aggiornare le credenziali\nlogout<username> -> permette di uscire dal servizio di trading";
    public CountDownLatch latch = new CountDownLatch(1);
        private boolean sigintTermination = false;
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
                    System.out.println("mimmo");
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
                            //this.sock.close();
                            System.out.println(serverAnswer.payload);
                            this.sock.close();
                            if (!this.sigintTermination)System.exit(0);
                            this.latch.countDown();
                            System.out.println("mino");
                            return;
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
            catch(Exception e){
                System.out.println("Stiamo riscontrando dei problemi sul server, procederemo a chiudere la connessione, ci scusiamo per il disagio");
                System.exit(0);
            }
            
        }
    
        public void sendBehaviour(){ 
            while(true){
                if(this.canSend){
                    Message serverCommand = new Message(this.userInput.nextLine());
                    String[] cmd = serverCommand.payload.split(" "); 
                    //controllo il tipo di comando richiesto
                    if(cmd[0].toLowerCase().equals("register") || cmd[0].toLowerCase().equals("login") || cmd[0].toLowerCase().equals("logout") || cmd[0].toLowerCase().equals("updatecredentials") || cmd[0].toLowerCase().equals("exit")){
                        //0 -> credenziali
                        serverCommand.code = 0;
                    }else if(cmd[0].toLowerCase().contains("order")) serverCommand.code = 1;//1 -> order
                    else serverCommand.code = 2; //2 -> internalCommand
                    //invio la richiesta al server
                    this.protocol.sendMessage(serverCommand);
                    //impedisco al client di mandare altri messaggi finchè non arriva la risposta del server
                    this.canSend = false;
                }
            }
        }
        //dialogo col server sfruttando il multithreading
        public void multiDial(){
            //apro il socket
            // Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //     System.out.println("Il programma sta per essere interrotto. Esecuzione delle operazioni di pulizia...");
            //     // Puoi inserire qui qualsiasi operazione di pulizia, come la chiusura di file, risorse, ecc.
            //     try {
            //         Thread.sleep(2000);  // Simula un'attività di pulizia (es. salvataggio stato, chiusura connessioni)
            //         System.out.println("Pulizia completata.");
            //         try {
            //             this.sock.close();
            //         } catch (IOException e) {;}
            //         System.out.println("addios");
            //     } catch (InterruptedException e) {
            //         System.out.println("Errore durante la pulizia.");
            //     }
            // }));
            try {
                //apro il socket lato client
                this.sock = new Socket(this.ip,this.port);
                //stampa di debug
                System.out.println("socket"+this.sock.toString());
                //imposto il protocollo di comunicazione
                this.setProtocol(new TCP());
                //imposto receiver, sender e receiverThread
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
            }
            //input errato
            catch(IOException e){
                System.out.println("C'è stato un'errore sulla lettura dal socket");
            }
            catch(NoSuchElementException e){
                System.out.println("Ctr+c Rilevato -> chiusura in corso...");
                this.canSend = true;
                this.protocol.sendMessage(new Message("exit",0));
                this.canSend = false;
                this.sigintTermination = true;
        }
        //eccezione generica
        catch(Exception e ){
            System.out.println("eccezione: "+e.getClass()+" : "+e.getStackTrace()+" : "+e.getCause());
        }
        finally{
            //se il socket non è stato aperto termino direttamente perchè non ho niente da chiudere
            if (this.sock == null)System.exit(0);
            try {
                //attendo la terminazione del receiver
                this.receiverThread.join();   
                //chiudo il socket
                this.sock.close();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
            
    }
}
