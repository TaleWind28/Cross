package JsonMemories;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import Users.User;
import Users.Commands.Order;

public class Orderbook implements JsonAccessedData{
    private String jsonFilePath;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final TreeMap<Integer, Order> askOrders; // Prezzi crescenti
    private final TreeMap<Integer, Order> bidOrders; // Prezzi decrescenti
    private final ConcurrentLinkedQueue<Order> stopOrders;
    public Orderbook(String jsonFilePath){
        this.jsonFilePath = jsonFilePath;
        this.askOrders = new TreeMap<>();
        this.bidOrders = new TreeMap<>();
        this.stopOrders = new ConcurrentLinkedQueue<>(); 
    }

    @Override
    public int accessData(String keyword) {
        System.out.println(this.jsonFilePath);
        throw new UnsupportedOperationException("Unimplemented method 'accessData'");
    }

    @Override
    public void loadData() {
        try (JsonReader reader = new JsonReader(new FileReader(this.jsonFilePath)))  {
            //inizio la lettura
            reader.beginObject();
            reader.beginArray();
            System.out.println("oggetto iniziato");
            //ciclo su tutti gli elementi del Json
            while(reader.hasNext()){
                //consumo la key della hasmap
                reader.nextName();
                //creo l'utente da inserire
                User us = this.gson.fromJson(reader,User.class);
                ////stampa di debug
                //System.out.println(us.getPassword());
                //carico l'utente nella mappa
                //this.userMap.put(us.getUsername(), us);
            }
        }
        catch(Exception e){

        }
        return;
    }

    public void addData(Order ord,String mapType) {
        if (mapType.equals("ask"))this.askOrders.put(ord.getorderID(),ord);
        else this.bidOrders.put(ord.getorderID(),ord);
        this.dataFlush();
        return;
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

}