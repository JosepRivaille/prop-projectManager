package edu.upc.fib.prop.persistence.dao.documents;

import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;

import java.sql.Connection;

public interface DaoDocuments {

    /**
     * Adds a new document in persistence.
     * @param c DB connection.
     * @param document Document to add in persistence.
     */
    void addNewDocument(Connection c, Document document) throws AlreadyExistingDocumentException;

    /**
     * Gets all documents stored in persistence.
     * @param c DB connection.
     * @return Set of all documents.
     */
    DocumentsCollection getAllDocuments(Connection c);

    /**
     * Updates an existing document with new Data.
     * @param c DB connection.
     * @param oldDocument Old document to be replaced.
     * @param newDocument New document to store.
     */
    void updateExistingDocument(Connection c, Document oldDocument, Document newDocument);

    /**
     * Deletes an existing document in the system.
     * @param c DB connection.
     * @param document Document to delete.
     */
    void deleteExistingDocument(Connection c, Document document);
}
