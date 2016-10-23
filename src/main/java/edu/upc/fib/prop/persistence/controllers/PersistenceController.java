package edu.upc.fib.prop.persistence.controllers;

import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.DocumentsCollection;

public interface PersistenceController {

    AuthorsCollection getAuthors();

    DocumentsCollection getDocuments();
}
