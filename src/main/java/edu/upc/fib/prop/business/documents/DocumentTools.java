package edu.upc.fib.prop.business.documents;

import edu.upc.fib.prop.exceptions.DocumentContentNotFoundException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.models.WeightsVector;
import edu.upc.fib.prop.utils.FileUtils;

import java.util.Map;
import java.util.TreeMap;

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
        Document mergedDoc = oldDoc.clone();
        if(!newDoc.getTitle().equals("")) mergedDoc.setTitle(newDoc.getTitle());
        if(!newDoc.getAuthor().equals("")) mergedDoc.setAuthor(newDoc.getAuthor());
        if(!newDoc.getContent().equals("")) mergedDoc.setContent(newDoc.getContent());
        return mergedDoc;
    }

    public static WeightsVector getWeights(Document doc, DocumentsCollection col){
        WeightsVector vec = new WeightsVector();

        for (Map.Entry<String, Float> tf : doc.getTermFrequencyList().entrySet()) {
            vec.put(tf.getKey(), tf.getValue()*col.getIdf(tf.getKey()));
        }

        return vec;
    }

    public static double getRelevanceFactor(WeightsVector wv1, WeightsVector wv2){
        Double sum = 0.0;
        for(String term : wv1){
            if(wv2.contains(term)) sum += wv1.getWeight(term) * wv2.getWeight(term);
        }
        return sum;
    }

    public static boolean isContentPathCorrect(Document doc) {
        try {
            FileUtils.readDocument(doc.getContent());
            return true;
        } catch (DocumentContentNotFoundException e){return false;}
    }
}
