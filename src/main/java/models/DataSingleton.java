package models;

public class DataSingleton {

    private static final DataSingleton instance =new DataSingleton();
    private int id;
    private DataSingleton(){}

    public static  DataSingleton getInstance(){
        return instance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
