package edu.upc.fib.prop.view.controllers;

import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.DocumentsCollection;

public interface ViewController {

    AuthorsCollection getAuthorsWithPrefix(String authorPrefix);

    DocumentsCollection getDocumentsByAuthorId(String authorName);

}
