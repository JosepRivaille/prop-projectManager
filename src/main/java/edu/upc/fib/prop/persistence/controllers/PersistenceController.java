package edu.upc.fib.prop.persistence.controllers;

import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.models.User;

import java.sql.SQLException;

public interface PersistenceController {

    /**
     * Gets all the authors from the data base.
     * @return Collection of all authors from the data base.
     */
    AuthorsCollection getAuthors();
    /**
     * Gets all the documents from the data base.
     * @return Collection of all documents from the data base.
     */
    DocumentsCollection getDocuments();

    /**
     * Modifies an existing document for a given one.
     * @param document Given document.
     */
    void writeNewDocument(Document document) throws AlreadyExistingDocumentException, SQLException;

    /**
     * Creates a given user in the data base.
     * @param user Given user.
     */
    void createUser(User user) throws AlreadyExistingUserException;

    /**
     * Checks the validity of a user in the data base.
     * @param email Given email.
     * @param password Given password.
     * @return The user with the given email and password.
     */
    User loginUser(String email, String password) throws UserNotFoundException, InvalidDetailsException;

    /**
     * Overwrites a user in the data base for another one.
     * @param currentUser User that is doing the modification.
     * @param newUser The modification of the user.
     */
    void updateUser(User currentUser, User newUser) throws UserNotFoundException, AlreadyExistingUserException;

    /**
     * Deletes a given user from the data base.
     * @param user Given user.
     */
    void deleteUser(User user) throws UserNotFoundException;

    /**
     * Deletes a given document from the data base.
     * @param document Given document.
     */
    void deleteDocument(Document document);

    /**
     * Overwrites a document in the data base for another one.
     * @param oldDocument Modified document.
     * @param newDocument Modifying document.
     */
    void updateDocument(Document oldDocument, Document newDocument);

    /**
     * Updates a document ratings
     * @param document document to rate
     * @param rating points given to the document
     * @param user User who rates
     */
    void rateDocument(Document document, int rating, String user) throws DocumentNotFoundException;

    /**
     *  Adds a document as a favourite of a given user
     * @param document document to add as a favourite
     * @param user User that added the document as a favourite
     */
    void addDocumentToFavourites(Document document, String user) throws DocumentNotFoundException;

    /**
     * Removes a document from an user's favourite documents list
     * @param document Document that will be removed from favourites list
     * @param user User that deleted the document as a favourite
     */
    void deleteDocumentFromFavourites(Document document, String user) throws DocumentNotFoundException;

    /**
     * Removes all favourites from all users of a given document
     * @param document The favourites related to this document will be deleted
     */
    void deleteAllFavouritesOfDocument(Document document);

    /**
     * Removes all ratings from all users of a given document
     * @param document The ratings related to this document will be deleted
     */
    void deleteAllRatingsOfDocument(Document document);

    /**
     * It returns, if exists, the document with a given title and author.
     * @param title
     * @param author
     * @return
     */
    Document getDocument(String title, String author);
}
