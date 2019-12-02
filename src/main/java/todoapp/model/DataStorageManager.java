package main.java.todoapp.model;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataStorageManager {

    public AppManager importData(String fileName) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            var appManager = (AppManager) in.readObject();
            in.close();
            return appManager;
        } catch (Exception e) {
            return new AppManager();
        }
    }

    public void exportData(String fileName, AppManager appManager) throws IOException {

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));

        out.writeObject(appManager);

        out.close();
    }

}
