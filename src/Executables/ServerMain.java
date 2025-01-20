package Executables;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import Communication.ServerProtocol;
import Communication.TCP;
import JsonMemories.Orderbook;
import JsonMemories.Userbook;
import ServerTasks.*;
import Users.Commands.Factory.FactoryRegistry;

public class ServerMain extends ServerProtocol{
    private Userbook registeredUsers;
    private Orderbook orderbook;
    public ServerMain(int port, int numThreads){
        super(port,numThreads);
        this.registeredUsers = new Userbook("src\\JsonFiles\\Users.json");
        this.orderbook = new Orderbook("src\\JsonFiles\\Users.json");
    }

    public static void main(String[] args) throws Exception {
        ServerMain server = new ServerMain(20000,16);
        server.initialConfig();
        server.dial();
        return;
    }
    
    public void dial(){
            
            try (ServerSocket server = new ServerSocket()) {
                String bindAddress = "0.0.0.0"; // Ascolta su tutte le interfacce di rete
                server.bind(new InetSocketAddress(bindAddress,this.PORT));
                while(true){
                    Socket client_Socket = server.accept();
                    //realizzare con factory per miglior versatilità -> inutile in quanto ho solo una task
                    GenericTask task = new GenericTask(client_Socket,this,new TCP());
                    addClient(client_Socket);
                    this.pool.execute(task);
                }
            } catch (Exception e) {
                System.out.println(e.getClass()+": "+e.getMessage());
                System.exit(0);
            }
    }
    
    public void initialConfig(){
        this.registeredUsers.loadData();
        this.orderbook.loadData();
        FactoryRegistry.updateFactoryData(0, registeredUsers);
        FactoryRegistry.updateFactoryData(1, orderbook);
        //this.registeredUsers.addData(new User("Alessia","Cesare98"));
        return;
    }
}
