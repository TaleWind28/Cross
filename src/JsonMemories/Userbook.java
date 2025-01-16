package JsonMemories;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import Users.User;

public class Userbook implements JsonAccessedData{
    protected ConcurrentHashMap<String,User> userMap = new ConcurrentHashMap<>();
    private String jsonFilePath;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Userbook(String jsonFilePath){
        this.jsonFilePath = jsonFilePath;
    }

    //cerco sulla mappa
    @Override
    public int accessData(String name) {
        if (this.userMap.containsKey(name))return 200;
        System.out.println(this.userMap.get(name).toString());
        return 404;
    }

    //carico la mappa in una struttura dati
    @Override
    public void loadData() {
        //if(new File("src\JsonFiles\Users.json").isFile())System.out.println("è un file");
        try (JsonReader reader = new JsonReader(new FileReader(this.jsonFilePath)))  {
            reader.beginObject();
            while(reader.hasNext()){
                reader.nextName();
                User us = this.gson.fromJson(reader,User.class);
                System.out.println(us.getPassword());
                this.userMap.put(us.getUsername(), us);
            }
        }
        catch(EOFException e){
            System.out.println("File utenti vuoto");
            return;
        }
        catch (Exception e) {
            System.out.println(e.getClass());
            System.exit(0);
        }
    }

    //aggiungo dati alla struttura dati ed al file Json
    public void addData(User user){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(this.jsonFilePath));) {
            //inserisco nella mappa
            this.userMap.put(user.getUsername(), user);
            //System.out.println("insermento nel gson");
            String jsonString = this.gson.toJson(this.userMap);
            writer.write(jsonString);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getClass()+ "," + e.getCause());
            System.exit(0);
        }
        

        return;
    }

    public ConcurrentHashMap<String, User> getUserMap() {
        return userMap;
    }

}
