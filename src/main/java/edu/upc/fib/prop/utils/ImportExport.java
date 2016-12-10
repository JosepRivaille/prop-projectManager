package edu.upc.fib.prop.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import edu.upc.fib.prop.exceptions.ImportExportException;
import edu.upc.fib.prop.models.Document;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

/**
 * Created by Guillermo on 10/11/2016.
 */
public class ImportExport {

    public static Document importDocument(Stage st) throws ImportExportException{
        try {
            Gson gson = new Gson();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(st);
            JsonReader reader = new JsonReader(new FileReader(file));
            Document doc = gson.fromJson(reader, Document.class);
            doc.setCover(Document.DEFAULT_COVER);
            if (doc.isCorrect()) return doc;
            else throw new ImportExportException();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    public static Document importDocument(Stage st) throws ImportExportException{
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new ImportExportException();
        }
        Document doc = gson.fromJson(reader, Document.class);


        String fileName = doc.getTitle().replace(" ", "") + ".txt";

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("src/main/resources/documents/" + fileName);
            Writer outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");

            outputStreamWriter.write(doc.getContent());

            outputStreamWriter.close();
        } catch (Exception e) {
            throw new ImportExportException();
        }

        doc.setContent(fileName);

        return doc;
        return null;
    }
    */

    public static void exportDocument(String pathToExport, Document document, String os) throws ImportExportException {
        /*String fileName = document.getTitle().replace(" ", "") + ".json";
        Document d = document.clone();
        d.setTermFrequency(null);
        d.setUser(null);
        d.setContent(FileUtils.readDocument(d.getContent()));
        final Gson myGson = new GsonBuilder().setPrettyPrinting().create();
        final String prettyGson  = myGson.toJson(d);


        OutputStream outputStream;
        try {
            if(os.toLowerCase().equals("w")) outputStream = new FileOutputStream(pathToExport + '\\' + fileName);
            else outputStream = new FileOutputStream(pathToExport + '/' + fileName);
            Writer outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");

            outputStreamWriter.write(prettyGson);

            outputStreamWriter.close();
        } catch (Exception e) {
            throw new ImportExportException();
        }*/
    }
}
