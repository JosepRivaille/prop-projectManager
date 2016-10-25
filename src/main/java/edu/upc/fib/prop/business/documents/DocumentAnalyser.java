package edu.upc.fib.prop.business.documents;

import edu.upc.fib.prop.business.models.Document;

import java.util.Map;
import java.util.TreeMap;

import static java.lang.Math.log;

public class DocumentAnalyser {

    public static void calculateTermFrequency(Document document) {
        String content = document.getContent();
        Float max = 1f;
        Map<String, Float> termFrequency = new TreeMap<>();
        for (String word : content.split("\\s+")) {
            if (!termFrequency.containsKey(word)) {
                termFrequency.put(word, 1f);
            } else {
                Float aux = termFrequency.get(word) + 1;
                termFrequency.put(word, aux);
                if (aux > max)
                    max = aux;
            }
        }
        for (Map.Entry<String, Float> tf : termFrequency.entrySet()) {
            Float newValue = 0.5f + (0.5f * tf.getValue() / max);
            tf.setValue(newValue);
        }
        document.setTermFrequency(termFrequency);
    }

    public static void calculateInverseDocumentFrequency(Map<String, Float> idf, String word, int totalDocuments) {
        Float documentsPerTerm = idf.get(word);
        Float weight = (float) log(totalDocuments / (1 + documentsPerTerm));
    }

}
