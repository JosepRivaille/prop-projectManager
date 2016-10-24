package edu.upc.fib.prop.persistence.controllers;

import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.DocumentsCollection;

import java.util.Set;

public interface PersistenceController {

    AuthorsCollection getAuthors();

    DocumentsCollection getDocuments();

    Set<String> getExcludedWords(String lang);

}
