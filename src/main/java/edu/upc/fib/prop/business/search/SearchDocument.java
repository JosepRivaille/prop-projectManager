package edu.upc.fib.prop.business.search;

import edu.upc.fib.prop.business.models.DocumentsCollection;

public class SearchDocument {

    public DocumentsCollection filterByAuthor(DocumentsCollection documentsCollection, String authorName) {
        DocumentsCollection filteredDocuments = new DocumentsCollection();
        documentsCollection.getDocuments().stream().filter(document ->
                document.getAuthor().toLowerCase().contains(authorName.toLowerCase()))
                .forEach(filteredDocuments::addDocument);
        return filteredDocuments;
    }

}
