package edu.upc.fib.prop.persistence.dao.documents;

import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.exceptions.DocumentContentNotFoundException;
import edu.upc.fib.prop.exceptions.DocumentNotFoundException;
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
     * Updates documents owned by a user when changes account details
     * @param c DB connection.
     * @param oldEmail Old email to replace.
     * @param newEmail New email to set.
     */
    void updateDocumentOwner(Connection c, String oldEmail, String newEmail);

    /**
     * Deletes an existing document in the system.
     * @param c DB connection.
     * @param document Document to delete.
     */
    void deleteExistingDocument(Connection c, Document document);

    /**
     * Deletes documents owned by a user.
     * @param c DB connection.
     * @param email Email to filter documents to delete.
     */
    void deleteDocuments(Connection c, String email);

    /**
     * Rates a document
     * @param c DB connection
     * @param doc   Document to rate
     * @param rating Points given to the document
     * @param user User who rates the document
     */
    void rateDocument(Connection c, Document doc, int rating, String user) throws DocumentNotFoundException;

    /**
     * Adds a document as a favourite of a given user
     * @param c
     * @param document
     * @param user
     */
    void addDocumentToFavourites(Connection c, Document document, String user) throws DocumentNotFoundException;

    /**
     * Removes a document from an user's favourite documents list
     * @param c DB Connection
     * @param document Document to be unmarked as a favourite
     * @param user User that wants to remove the document as a favourite
     */
    void deleteDocumentFromFavourites(Connection c, Document document, String user) throws DocumentNotFoundException;

    /**
     * Removes all favourites of a given document from all users
     * @param c
     * @param document
     */
    void deleteAllFavouritesOfDocument(Connection c, Document document);
}
