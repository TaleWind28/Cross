import java.net.ServerSocket;
import java.net.Socket;

import Communication.ServerProtocol;
import Communication.TCP;
import ServerTasks.*;

    
public class ServerMain extends ServerProtocol{
    public ServerMain(int port, int numThreads){
        super(port,numThreads);

    }
    public static void main(String[] args) throws Exception {
        ServerProtocol server = new ServerMain(20000,16);
        server.setProtocol(new TCP());
        server.dial();
        //System.out.println("passato pool");
        return;
    }
    
    public void dial(){
            while(true){
            try (ServerSocket server = new ServerSocket(this.PORT)) {
                Socket client_Socket = server.accept();
                //realizzare con factory per miglior versatilità
                GenericTask task = new GenericTask(client_Socket,this,this.protocol);
                addClient(client_Socket);
                this.pool.execute(task);
            } catch (Exception e) {
                System.out.println(e.getClass()+": "+e.getMessage());
                System.exit(0);
            }
        }
    }
}
