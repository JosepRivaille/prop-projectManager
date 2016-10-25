package edu.upc.fib.prop.persistence.dao.documents;

import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;

import java.sql.Connection;

public interface DaoDocuments {

    /**
     * Gets all documents stored in persistence.
     * @param c Connection of the DB.
     * @return Set of all documents.
     */
    DocumentsCollection getAllDocuments(Connection c);

    /**
     * Adds a new document in persistence.
     * @param c Connection of the DB.
     * @param document Document to add in persistence.
     */
    void addNewDocument(Connection c, Document document);

}
