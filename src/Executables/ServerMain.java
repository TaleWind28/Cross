package Executables;
import java.net.ServerSocket;
import java.net.Socket;
import JsonMemories.Userbook;
import ServerTasks.*;
import Users.Communication.ServerProtocol;
import Users.Communication.TCP;

    
public class ServerMain extends ServerProtocol{
    //private Map<String,User> registeredUsers = new ConcurrentHashMap<>();
    private Userbook registeredUsers;
    //l'orderbook deve essere una classe a sè con tante mappe per ordini
    //private Map orderbook = new TreeMap<String,Integer>();
    public ServerMain(int port, int numThreads){
        super(port,numThreads);
        this.registeredUsers = new Userbook("src\\JsonFiles\\Users.json");
    }

    public static void main(String[] args) throws Exception {
        ServerMain server = new ServerMain(20000,16);
        server.initialConfig();
        server.setProtocol(new TCP());
        server.dial();
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
    
    public void initialConfig(){
        this.registeredUsers.loadData();
        //this.registeredUsers.addData(new User("Alessia","Cesare98"));
        return;
    }
}
