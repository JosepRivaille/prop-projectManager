package edu.upc.fib.prop.business.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DocumentsCollection {

    private List<Document> documents;
    private Map<String, Float> inverseDocumentFrequency;

    public DocumentsCollection() {
        this.documents = new ArrayList<>();
        this.inverseDocumentFrequency = new TreeMap<>();
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void addDocument(Document document) {
        this.documents.add(document);
    }

    public Float getTermFrequencyInverseDocumentFrequency(Document document, String word) {
        return document.getTermFrequency(word) * inverseDocumentFrequency.get(word);
    }

}
