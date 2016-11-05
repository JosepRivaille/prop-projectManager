package edu.upc.fib.prop.view.controllers;

import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import javafx.util.Pair;

import java.sql.SQLException;

public interface ViewController {

    AuthorsCollection getAuthorsWithPrefix(String authorPrefix) throws AuthorNotFoundException;

    DocumentsCollection getDocumentsByAuthorId(String authorName) throws DocumentNotFoundException;

    Document getDocumentByTitleAndAuthor(String title, String author) throws DocumentNotFoundException;

    void userLogin(String email, String password) throws UserNotFoundException, InvalidDetailsException;

    void userRegister(String email, String userName, String password, String password2)
            throws InvalidDetailsException, AlreadyExistingUserException;

    void userUpdate(String newEmail, String newName, String newPassword)
            throws InvalidDetailsException, UserNotFoundException, AlreadyExistingUserException;

    void userDelete() throws UserNotFoundException;

    DocumentsCollection getCurrentUserDocuments();

    boolean storeNewDocument(Document document);

    boolean updateDocument(Pair<String, Document> updatedDocument);

    boolean deleteDocument(Document document);

}

