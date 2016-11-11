package edu.upc.fib.prop.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import edu.upc.fib.prop.exceptions.DocumentContentNotFoundException;
import edu.upc.fib.prop.exceptions.ImportExportException;
import edu.upc.fib.prop.models.Document;

import java.io.*;
import java.util.Iterator;

/**
 * Created by Guillermo on 10/11/2016.
 */
public class ImportExport {

    public static Document importDocument(String path) throws ImportExportException{
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
    }

    public static void exportDocument(String pathToExport, Document document) throws ImportExportException, DocumentContentNotFoundException {
        String fileName = document.getTitle().replace(" ", "") + ".json";
        Document d = document.clone();
        d.setTermFrequency(null);
        d.setUser(null);
        d.setContent(FileUtils.readDocument(d.getContent()));
        final Gson myGson = new GsonBuilder().setPrettyPrinting().create();
        final String prettyGson  = myGson.toJson(d);


        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(pathToExport + '\\' + fileName);
            Writer outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");

            outputStreamWriter.write(prettyGson);

            outputStreamWriter.close();
        } catch (Exception e) {
            throw new ImportExportException();
        }
    }
}
