package edu.upc.fib.prop.business.models;

import java.util.ArrayList;
import java.util.List;

public class DocumentsCollection {

    private List<Document> documents;

    public DocumentsCollection() {
        this.documents = new ArrayList<>();
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void addDocument(Document document) {
        this.documents.add(document);
    }

}
