package edu.upc.fib.prop.persistence.dao.documents;

import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;

import java.sql.Connection;

public interface DaoDocuments {

    /**
     * Gets all documents stored in persistence.
     * @return Set of all documents.
     */
    DocumentsCollection getAllDocuments(Connection c);

    /**
     * Adds a new document in persistence.
     * @param document Document to add in persistence.
     */
    void addNewDocument(Connection c, Document document) throws AlreadyExistingDocumentException;

    void deleteExistingDocument(Connection c, Document document);

    void updateExistingDocument(Connection c, Document oldDocument, Document newDocument);
}
