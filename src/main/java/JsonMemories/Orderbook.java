package JsonMemories;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import Users.Commands.Order;
import Utils.PriceComparator;

public class Orderbook implements JsonAccessedData{
    private String jsonFilePath;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private TreeMap<String, Order> askOrders; // Prezzi crescenti
    private  TreeMap<String, Order> bidOrders; // Prezzi decrescenti
    private  ConcurrentLinkedQueue<Order> stopOrders;//devo ancora capire cosa sono
        
    public Orderbook(String jsonFilePath){
        this.jsonFilePath = jsonFilePath;
        this.askOrders = new TreeMap<>(new PriceComparator());
        this.bidOrders = new TreeMap<>(new PriceComparator().reversed());
        this.stopOrders = new ConcurrentLinkedQueue<>(); 
    }
    
    @Override
    public int accessData(String keyword) {
        System.out.println(this.jsonFilePath);
        throw new UnsupportedOperationException("Unimplemented method 'accessData'");
    }
    
    @Override
    public void loadData() {
        System.out.println("copio");
        try (JsonReader reader = new JsonReader(new FileReader(this.jsonFilePath)))  {
            OrderClass orderData = gson.fromJson(reader,OrderClass.class);
            this.askOrders = (TreeMap<String,Order>)orderData.askMap;
            this.bidOrders = (TreeMap<String,Order>)orderData.bidMap;
            System.out.println("copio");
        }
        catch(Exception e){System.out.println("copio male");;}
        return;
    }

    public void addData(Order ord,String mapType) {
        String orderbookEntry = ord.getUser()+":"+ord.getPrice();
        System.out.println("entry:"+orderbookEntry);
        TreeMap<String,Order> ordermap = this.getRequestedMap(mapType);
        if(ordermap.containsKey(orderbookEntry))ordermap.get(orderbookEntry).addSize(ord.getSize());
        else ordermap.put(orderbookEntry, ord);;
        this.dataFlush();
        return;
    }

    TreeMap<String,Order> getRequestedMap(String request){
        //System.out.println("MMMMMMMMMMM");
        System.out.println("richiesta: "+request);
        if(request.equals("ask"))return this.askOrders;
        else return this.bidOrders;
    }

    public void dataFlush(){
        OrderClass oc = new OrderClass(this.askOrders, this.bidOrders);
        try (BufferedWriter writer = new BufferedWriter((new FileWriter(this.jsonFilePath)))) {
            writer.write(this.gson.toJson(oc));
            System.out.println("scritto");
        } catch (Exception e) {
            System.out.println("Aiuto");
        }
        return;
    }

    public Order removeData(String mapType, String orderbookEntry){
        Order ord = null;
        TreeMap<String,Order> requestedMap = getRequestedMap(mapType);
        System.out.println("entry "+orderbookEntry);
        ord = requestedMap.remove(orderbookEntry);
        dataFlush();
        return ord;
    }

    public String getBestPriceAvailable(int size,String tradeType){
        TreeMap<String,Order> requestedMap = getRequestedMap(tradeType);
        for(String key: requestedMap.keySet()){
            if(requestedMap.get(key).getSize()<size)continue;
            return key;
            //System.out.println(key);
        }
        //requestedMap.forEach((key,elem)-> System.out.println("chiave: "+key+"element: "+elem));
        return null;
    }

    public void orderSelection(){

    }
    public int mapLen() {
        return this.askOrders.size() +this.bidOrders.size();
    }

}