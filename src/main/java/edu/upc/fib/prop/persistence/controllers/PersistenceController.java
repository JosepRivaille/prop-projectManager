package edu.upc.fib.prop.persistence.controllers;

import edu.upc.fib.prop.exceptions.AlreadyExistingUserException;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.models.User;

import java.sql.SQLException;
import java.util.Set;

public interface PersistenceController {

    AuthorsCollection getAuthors();

    DocumentsCollection getDocuments();

    Set<String> getExcludedWords(String lang);



    void writeNewDocument(Document document) throws AlreadyExistingDocumentException, SQLException;

    void createUser(User user) throws AlreadyExistingUserException;

    User loginUser(String email, String password) throws UserNotFoundException, InvalidDetailsException;

    void updateUser(User currentUser, User newUser) throws UserNotFoundException, AlreadyExistingUserException;

    void deleteUser(User user) throws UserNotFoundException;

    void deleteDocument(Document document);
}
