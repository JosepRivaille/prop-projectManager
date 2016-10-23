package edu.upc.fib.prop.business.controllers;

import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.DocumentsCollection;

public interface BusinessController {

    AuthorsCollection searchMatchingAuthors(String authorPrefix);

    DocumentsCollection searchDocumentsByAuthor(String authorName);

}
