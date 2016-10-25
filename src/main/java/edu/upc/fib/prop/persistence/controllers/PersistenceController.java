package edu.upc.fib.prop.persistence.controllers;

import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.Document;
import edu.upc.fib.prop.business.models.DocumentsCollection;
import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;

import java.util.Set;

public interface PersistenceController {

    AuthorsCollection getAuthors();

    DocumentsCollection getDocuments();

    Set<String> getExcludedWords(String lang);

    void writeNewDocument(Document document) throws AlreadyExistingDocumentException;
}
