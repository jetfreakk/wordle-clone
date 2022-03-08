package Wordle;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileProcessor {
    public List<String> read(String fileName) {

        List<String> wordList = new ArrayList<>();

        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = reader.readLine()) != null) {
                wordList.add(strLine);
            }
            in.close();

        } catch (Exception e) {
            System.err.println("Error");
        }
        return wordList;
    }
}
