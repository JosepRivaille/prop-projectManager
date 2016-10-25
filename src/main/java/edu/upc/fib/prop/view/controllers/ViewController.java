package edu.upc.fib.prop.view.controllers;

import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import javafx.util.Pair;

public interface ViewController {

    AuthorsCollection getAuthorsWithPrefix(String authorPrefix);

    DocumentsCollection getDocumentsByAuthorId(String authorName);

    boolean userLogin(String email, String password);

    boolean userRegister(String email, String userName, String password, String password2);

    DocumentsCollection getCurrentUserDocuments();

    boolean storeNewDocument(Document document);

    boolean updateDocument(Pair<String, Document> updatedDocument);

    boolean deleteDocument(Document document);
}

