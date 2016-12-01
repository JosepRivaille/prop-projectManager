package edu.upc.fib.prop.business.controllers;

import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.*;

public interface BusinessController {

    /*--------------- Users */

    /**
     * Check if input details match with a user.
     *
     * @param email    Account email.
     * @param password Account password.
     */
    void checkLoginDetails(String email, String password) throws InvalidDetailsException, UserNotFoundException;

    /**
     * Check if input details can be used to create a new user.
     *
     * @param email     New account email.
     * @param userName  New username.
     * @param password  New password.
     * @param password2 New password repeated.
     */
    void registerNewUser(String email, String userName, String password, String password2)
            throws InvalidDetailsException, AlreadyExistingUserException;

    /**
     * Check if input details can be used to update an existing user.
     *
     * @param newEmail    Account new email.
     * @param newName     Account new name.
     * @param newPassword Account new password.
     */
    void updateUser(String newEmail, String newName, String newPassword)
            throws InvalidDetailsException, UserNotFoundException, AlreadyExistingUserException;

    /**
     * Deletes current user from the system.
     */
    void deleteUser() throws UserNotFoundException;

    /**
     * Deletes active user session.
     */
    void logout();

    /*--------------- Authors */

    /**
     * Search for matching authors with a given prefix.
     *
     * @param authorPrefix User input to search.
     * @return Collection of matching authors (even if empty).
     */
    AuthorsCollection searchMatchingAuthors(String authorPrefix) throws AuthorNotFoundException;

    /*--------------- Documents */

    /**
     * Search for documents written by an author.
     *
     * @param authorName Author name identifier.
     * @return Collection of matching documents (even if empty).
     */
    DocumentsCollection searchDocumentsByAuthor(String authorName) throws DocumentNotFoundException;

    /**
     * Search for documents given a title and an author.
     *
     * @param title      Document title identifier.
     * @param authorName Document author identifier.
     * @return Document matching title and author.
     */
    Document searchDocumentsByTitleAndAuthor(String title, String authorName) throws DocumentNotFoundException;

    /**
     * Returns all the documents stored in the system
     *
     * @return All documents in the system.
     */
    DocumentsSet searchForAllDocuments();

    /**
     * Sorts a k size document set by relevance given a document.
     *
     * @param document reference document
     * @param k        number of documents to find
     * @return sorted set of similar documents
     */
    SortedDocumentsSet searchDocumentsByRelevance(Document document, int k)
            throws DocumentNotFoundException;

    /**
     * Gets current session logged user documents.
     *
     * @return Documents owned by current user.
     */
    DocumentsCollection getCurrentUserDocuments();

    /**
     * Stores in persistence a new document.
     *
     * @param document Document to store.
     */
    void storeNewDocument(Document document)
            throws AlreadyExistingDocumentException, InvalidDetailsException, DocumentContentNotFoundException;

    /**
     * Updates a document in persistence.
     *
     * @param oldDoc Old document
     * @param newDoc New document
     */
    void updateDocument(Document oldDoc, Document newDoc)
            throws InvalidDetailsException, AlreadyExistingDocumentException, DocumentContentNotFoundException;

    /**
     * Deletes a document in persistence.
     *
     * @param document Document to delete.
     */
    void deleteDocument(Document document);

    /**
     * Imports a document.
     *
     * @param path Path of the document to import.
     */
    Document importDocument(String path) throws ImportExportException, AlreadyExistingDocumentException,
            InvalidDetailsException, DocumentContentNotFoundException;

    /**
     * Exports an existing document.
     *
     * @param pathToExport Path where the document will be exported.
     * @param document     Document to export.
     * @param os           OS used by the user.
     */
    void exportDocument(String pathToExport, Document document, String os)
            throws ImportExportException, DocumentContentNotFoundException;

    /**
     * Search for matching documents by a boolean expression.
     *
     * @param booleanExpression Expression to search by.
     * @return A set of matching documents.
     */
    DocumentsSet searchDocumentsByBooleanExpression(String booleanExpression) throws InvalidQueryException;

    /**
     * Search for k similar documents to a given key
     *
     * @param str query to search
     * @param k   number of documents to search
     * @return sorted set of similar documents
     * @throws DocumentNotFoundException
     */
    SortedDocumentsSet searchDocumentsByQuery(String str, int k)
            throws DocumentNotFoundException;


    /**
     * Rates a document.
     *
     * @param document
     * @param rating
     */
    void rateDocument(Document document, int rating) throws DocumentNotFoundException;

    /**
     * Add document to favourites.
     *
     * @param document
     */
    void addDocumentToFavourites(Document document) throws DocumentNotFoundException;

    /**
     * Removes a document from an user's favourites documents list
     * @param document
     */
    void deleteDocumentFromFavourites(Document document) throws DocumentNotFoundException;
}