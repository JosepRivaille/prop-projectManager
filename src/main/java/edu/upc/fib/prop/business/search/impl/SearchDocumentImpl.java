package edu.upc.fib.prop.business.search.impl;

import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.business.search.SearchDocument;

public class SearchDocumentImpl implements SearchDocument {

    public DocumentsCollection filterByAuthor(DocumentsCollection documentsCollection, String authorName) {
        DocumentsCollection filteredDocuments = new DocumentsCollection();
        documentsCollection.getDocuments().stream().filter(document ->
                document.getAuthor().toLowerCase().contains(authorName.toLowerCase()))
                .forEach(filteredDocuments::addDocument);
        return filteredDocuments;
    }

    public DocumentsCollection filterByUser(DocumentsCollection documentsCollection, String email) {
        DocumentsCollection filteredDocuments = new DocumentsCollection();
        documentsCollection.getDocuments().stream().filter(document ->
                document.getUser().equals(email))
                .forEach(filteredDocuments::addDocument);
        return filteredDocuments;
    }
}
