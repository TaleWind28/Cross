package ServerTasks;

import java.net.Socket;

import Users.Communication.Message;
import Users.Communication.Protocol;
import Users.Communication.ServerProtocol;

public class DisconnectTask implements Runnable{
    private Protocol protocol;
    private Socket socket;
    private ServerProtocol server;
    public DisconnectTask(Protocol proto, Socket socket, ServerProtocol server){
        this.protocol = proto;
        this.socket = socket;
        this.server = server;
    }

    public void run(){
        try {    
            Message clientMessage = new Message("Timeout di inattività. Disconnessione.",408);
            this.protocol.sendMessage(clientMessage);
            this.server.onClientDisconnect(socket,"Client disconnesso per inattività");
        }catch(Exception e){
            //System.out.println(e.getMessage());
        }
    };
}
