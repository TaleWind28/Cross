import java.io.FileReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import ServerTasks.*;
import Users.User;
import Users.Communication.ServerProtocol;
import Users.Communication.TCP;

    
public class ServerMain extends ServerProtocol{
    //private ConcurrentHashMap orderBook;
    private ConcurrentHashMap<String,User> registeredUsers;
    public ServerMain(int port, int numThreads){
        super(port,numThreads);
        this.registeredUsers = new ConcurrentHashMap<>();
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
        try (JsonReader reader = new JsonReader(new FileReader("JsonFile/Users.json")))  {
            reader.beginArray();
            Gson gson = new Gson();
            while(reader.hasNext()){
                User us = gson.fromJson(reader,User.class);
                this.registeredUsers.put(us.getUsername(), us);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return;
    }
}
