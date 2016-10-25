package edu.upc.fib.prop.persistence.dao.documents;

import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;

public interface DaoDocuments {

    /**
     * Gets all documents stored in persistence.
     * @return Set of all documents.
     */
    DocumentsCollection getAllDocuments();

    /**
     * Adds a new document in persistence.
     * @param document Document to add in persistence.
     */
    void addNewDocument(Document document) throws AlreadyExistingDocumentException;

}
