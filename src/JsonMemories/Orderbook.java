package JsonMemories;

public class Orderbook implements JsonAccessedData{
    private String jsonFilePath;
    public Orderbook(String jsonFilePath){
        this.jsonFilePath = jsonFilePath;
    }

    @Override
    public int accessData(String keyword) {
        System.out.println(this.jsonFilePath);
        throw new UnsupportedOperationException("Unimplemented method 'accessData'");
    }

    @Override
    public void loadData() {
        return;
        //throw new UnsupportedOperationException("Unimplemented method 'loadData'");
    }

    public void addData() {
        return;
        //throw new UnsupportedOperationException("Unimplemented method 'saveData'");
    }

}
