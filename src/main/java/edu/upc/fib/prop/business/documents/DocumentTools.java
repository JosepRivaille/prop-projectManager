package edu.upc.fib.prop.business.documents;

import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.models.WeightsVector;

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
        //TODO La idea es encapsular la estrucura de datos que almacena los pesos, habria que modificar esto y recorrerlo sin acceder directamente al map.
        Double sum = 0.0;
        TreeMap<String, Float> vec1 = wv1.getVector();
        TreeMap<String, Float> vec2 = wv2.getVector();

        for (Map.Entry<String, Float> elem : vec1.entrySet()) {
            if(vec2.containsKey(elem.getKey())) sum += elem.getValue() * vec2.get(elem.getKey());
        }
        /*Iterator it1 = wv1.iterator();
        Iterator it2 = wv2.iterator();
        while(it1.hasNext()){
            if(wv2.contains(wv1.iterator().next()));
        }*/
        return sum;
    }
}
