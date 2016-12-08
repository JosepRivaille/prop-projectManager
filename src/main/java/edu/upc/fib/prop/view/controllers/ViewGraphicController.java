package edu.upc.fib.prop.view.controllers;

import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.*;
import javafx.util.Pair;

public interface ViewGraphicController {

    /**
     * Gets all authors given a prefix.
     *
     * @param authorPrefix Given prefix to filter authors.
     * @return Collection of matching authors.
     */
    String getAuthorsWithPrefix(String authorPrefix) throws AuthorNotFoundException;

    /**
     * Gets all documents of an author.
     *
     * @param authorName Given author name to filter.
     * @return Collection of matching documents.
     */
    String getDocumentsByAuthorId(String authorName) throws DocumentNotFoundException;

    /**
     * Get a document that matches with a title and its author.
     *
     * @param title  Given title.
     * @param author Given author.
     * @return Matching document
     */
    String getDocumentByTitleAndAuthor(String title, String author) throws DocumentNotFoundException;

    /**
     * Gets documents matching a boolean expression
     *
     * @param booleanExpression Expression following a format to search.
     * @return Documents matching a boolean expression.
     */
    String getDocumentsByBooleanExpression(String booleanExpression) throws InvalidQueryException;

    String getDocumentsByQuery(String query) throws InvalidQueryException;

    /**
     * Gets the k most similar document to a given document
     *
     * @param document Document to compare with.
     * @param k        Number of similar documents to look for.
     * @return K more similar documents.
     */
    String getDocumentsByRelevance(Document document, int k) throws DocumentNotFoundException;

    /**
     * Tries to log a user in the system.
     *
     * @param email    Email to try the login..
     * @param password Password to try the login.
     */
    void userLogin(String email, String password) throws UserNotFoundException, InvalidDetailsException;

    /**
     * Tries to register a new user in the system.
     *
     * @param email     Email to try the register.
     * @param userName  Username to try the register.
     * @param password  Password to try the register.
     * @param password2 Repeat password to try the register.
     */
    void userRegister(String email, String userName, String password, String password2) throws InvalidDetailsException, AlreadyExistingUserException;

    /**
     * Tries to update an user with new details.
     *
     * @param newEmail    New email to set to the user.
     * @param newName     New username to set to the user.
     * @param newPassword New password to set to the user.
     */
    void userUpdate(String newEmail, String newName, String newPassword)
            throws InvalidDetailsException, UserNotFoundException, AlreadyExistingUserException;

    /**
     * Tries to delete an user from the system.
     */
    void userDelete() throws UserNotFoundException;

    /**
     * Gets documents from the current user.
     */
    String getCurrentUserDocuments();

    String getCurrentUserFavourites();

    /**
     * Stores a new document in the system.
     *
     * @param documentJSON New document to store.
     */
    void storeNewDocument(String documentJSON) throws AlreadyExistingDocumentException, InvalidDetailsException;


    void updateDocument(String oldDocumentJSON, String editedDocumentJSON)
            throws InvalidDetailsException, AlreadyExistingDocumentException, DocumentNotFoundException;

    /**
     * Document to delete from the system.
     *
     * @param title  Document to delete title.
     * @param author Document to delete author.
     */
    void deleteDocument(String title, String author);

    /**
     * Removes user session.
     */
    void userLogout();

    /**
     * Returns all the documents in the system.
     *
     * @return DocumentSet with all the documents.
     */
    String searchForAllDocuments();

    /**
     * Imports a document to the system.
     *
     * @param path The path where the document will be imported.
     * @return The document imported.
     */
    String importDocument(String path)
            throws ImportExportException, AlreadyExistingDocumentException, InvalidDetailsException;

    /**
     * Exports an existing document.
     *
     * @param pathToExport The path where the doccument will be exported.
     * @param document     The document to export.
     */
    void exportDocument(String pathToExport, Document document, String os) throws ImportExportException;

    Float rateDocument(String title, String author, int rating) throws DocumentNotFoundException;
}

