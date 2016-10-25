package edu.upc.fib.prop.business.documents.impl;

import edu.upc.fib.prop.business.documents.DocumentAnalyser;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;

import java.util.Map;
import java.util.TreeMap;

public class DocumentAnalyserImpl implements DocumentAnalyser {

    private DocumentsCollection documentsCollection;
    private Document document;

    public DocumentAnalyserImpl(DocumentsCollection documentsCollection) {
        this.documentsCollection = documentsCollection;
    }

    public Document getDocument() {
        return this.document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public boolean checkCorrectData() {
        return !this.document.getTitle().equals("")
                && !this.document.getAuthor().equals("")
                && !this.document.getContent().equals("");
    }

    public void calculateDocumentParameters() {
        this.document.setTermFrequency(calculateTermFrequency());
        this.documentsCollection.setInverseDocumentFrequency(calculateInverseDocumentFrequency());
    }

    private Map<String, Float> calculateTermFrequency() {
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
        return termFrequency;
    }

    private Map<String, Float> calculateInverseDocumentFrequency() {
        //Float documentsPerTerm = idf.get(word);
        //Float weight = (float) log(this.documentsCollection.getDocuments().size() / (1 + documentsPerTerm));
        return new TreeMap<String, Float>();
    }

}
