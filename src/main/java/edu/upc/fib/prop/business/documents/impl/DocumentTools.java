package edu.upc.fib.prop.business.documents.impl;

import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;

import java.util.Map;

/**
 * Created by Guillermo on 07/11/2016.
 */
public class DocumentTools {


    public static String getRelevantTerms(Document doc){
        //TODO Hay que aplicar un filtrado para descartar las palabras irrelevantes
        return doc.getContent();
    }

    public static boolean isCorrect(Document document) {
        return !document.getTitle().equals("") &&
                !document.getAuthor().equals("") &&
                !document.getContent().equals("");
    }

    public static Document mergeDocs(Document oldDoc, Document newDoc){
        if(!newDoc.getTitle().equals("")) oldDoc.setTitle(newDoc.getTitle());
        if(!newDoc.getAuthor().equals("")) oldDoc.setAuthor(newDoc.getAuthor());
        if(!newDoc.getContent().equals("")) oldDoc.setContent(newDoc.getContent());
        return oldDoc;
    }
}
