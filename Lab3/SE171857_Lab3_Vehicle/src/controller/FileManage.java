package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManage {

    public void saveToFile(List objList, String fileName) {
        try (FileWriter fw = new FileWriter(fileName)) {

            for (Object obj : objList) {
                fw.write(obj + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(FileManage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> loadFromFile(String fileName) {
        List<String> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    result.add(line);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FileManage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

}
