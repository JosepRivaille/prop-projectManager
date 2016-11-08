package edu.upc.fib.prop.business.documents.impl;

import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.models.WeightsVector;

import java.util.Map;
import java.util.TreeMap;

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

    public static WeightsVector getWeights(Document doc, DocumentsCollection col){
        WeightsVector vec = new WeightsVector();

        for (Map.Entry<String, Float> tf : doc.getTermFrequencyList().entrySet()) {
            vec.put(tf.getKey(), tf.getValue()*col.getIdf(tf.getKey()));
        }

        return vec;
    }

    public static double getRelevanceFactor(WeightsVector wv1, WeightsVector wv2){
        //TODO REVISAR
        Double sum = 0.0;
        TreeMap<String, Float> vec1 = wv1.getVector();
        TreeMap<String, Float> vec2 = wv2.getVector();

        for (Map.Entry<String, Float> elem : vec1.entrySet()) {
            if(vec2.containsKey(elem.getKey())) sum += elem.getValue() * vec2.get(elem.getKey());
        }
        return sum;
    }
}
