package Executables;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

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
        this.registeredUsers = new Userbook("src\\main\\java\\JsonFiles\\Users.json");
        this.orderbook = new Orderbook("src\\main\\java\\JsonFiles\\OrderBook.json");
    }

    public static void main(String[] args) throws Exception {
        ServerMain server = new ServerMain(20000,16);
        // Aggiungi uno shutdown hook alla JVM
        Runtime.getRuntime().addShutdownHook(
            new Thread(
                () -> {
                    System.out.println("Ctrl+C rilevato -> chiusura server in corso...");
                    //Arresta il thread pool
                    server.pool.shutdown();
                    try {
                        //Attende la terminazione dei thread attivi
                        if (!server.pool.awaitTermination(10, TimeUnit.MILLISECONDS)) {
                            System.out.println("[Server] Interruzione forzata dei thread attivi...");
                            server.pool.shutdownNow();
                        }
                    } catch (InterruptedException e) {
                        //Forza l'arresto in caso di interruzione
                        server.pool.shutdownNow();
                    }
                    System.out.println("[Server] Threadpool terminato");
                    server.registeredUsers.getUserMap().forEach((username, user)-> user.setLogged(false));
                    server.registeredUsers.dataFlush();
                    System.out.println("[Server] Utenti correttamente sloggati");
                }
            )
        );
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
    public Userbook getRegisteredUsers() {
        return registeredUsers;
    }

    public Orderbook getOrderbook() {
        return orderbook;
    }

    public void initialConfig(){
        this.registeredUsers.loadData();
        // this.orderbook.addData(new Order("marketorder", "ask",5,0),"ask");
        // this.orderbook.addData(new Order("marketorder", "ask",5,2),"ask");
        // this.orderbook.addData(new Order("marketorder", "bid",5,1),"bid");
        this.orderbook.loadData();
        int progressiveOrderNumber = this.orderbook.mapLen()-1;
        System.out.println("Numero Ordine: "+progressiveOrderNumber);
        FactoryRegistry.updateFactoryData(0, registeredUsers);
        FactoryRegistry.updateFactoryData(1, orderbook);
        // UserCommandFactory fact = FactoryRegistry.getFactory(1);
        // if(fact instanceof OrderFactory)((OrderFactory)fact).setOrderNumber(progressiveOrderNumber);
        // FactoryRegistry.getFactory(1).createUserCommand()
        return;
    }
}
