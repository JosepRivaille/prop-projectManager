package edu.upc.fib.prop.utils;

import edu.upc.fib.prop.exceptions.DocumentNotFoundException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class FileUtils {

    public static String readFile(String filePath) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Set<String> getExcludedWords(String lang) {
        Set<String> excludedWords = new TreeSet<>();
        FileUtils.readFile("src/main/resources/words/" + lang + ".txt");
        return excludedWords;
    }

    public static String readDocument(String documentTitle) throws DocumentNotFoundException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/resources/documents/" + documentTitle));
            StringBuilder sb = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
            return sb.toString();

        } catch (IOException e) {
            throw new DocumentNotFoundException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createDocument(String fileName, String content) {
        File f = new File("test.db");
        boolean deleted = f.delete();
        System.out.println("File deleted: " + deleted);
    }
}
