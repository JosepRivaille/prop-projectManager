package edu.upc.fib.prop.business.search.impl;

import edu.upc.fib.prop.exceptions.DocumentNotFoundException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.business.search.SearchDocument;

public class SearchDocumentImpl implements SearchDocument {

    public DocumentsCollection filterByAuthor(DocumentsCollection documentsCollection, String authorName)
            throws DocumentNotFoundException {
        DocumentsCollection filteredDocuments = new DocumentsCollection();
        documentsCollection.getDocuments().stream().filter(document ->
                document.getAuthor().toLowerCase().contains(authorName.toLowerCase()))
                .forEach(filteredDocuments::addDocument);
        if (filteredDocuments.getDocuments().isEmpty()) {
            throw new DocumentNotFoundException();
        }
        return filteredDocuments;
    }

    public DocumentsCollection filterByUser(DocumentsCollection documentsCollection, String email) {
        DocumentsCollection filteredDocuments = new DocumentsCollection();
        documentsCollection.getDocuments().stream().filter(document ->
                document.getUser().equals(email))
                .forEach(filteredDocuments::addDocument);
        return filteredDocuments;
    }

    @Override
    public Document filterByTitleAndAuthor(DocumentsCollection documentsCollection, String title, String authorName)
            throws DocumentNotFoundException {
        for (Document document : documentsCollection.getDocuments()) {
            if (document.getTitle().toLowerCase().equals(title.toLowerCase()) &&
                    document.getAuthor().toLowerCase().equals(authorName.toLowerCase())) {
                return document;
            }
        }
        throw new DocumentNotFoundException();
    }
}
