package edu.upc.fib.prop.business.search;

import edu.upc.fib.prop.exceptions.DocumentNotFoundException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.models.User;

public interface SearchDocument {

    /**
     * Filter documents by an existing author.
     * @param documentsCollection List of all documents.
     * @param authorName Author identifier name.
     * @return Set of matching documents.
     */
    DocumentsCollection filterByAuthor(DocumentsCollection documentsCollection, String authorName);

    /**
     * Filter documents by the user owner.
     * @param documentsCollection Set of all documents.
     * @param email User to check owned documents.
     * @return Set of matching documents.
     */
    DocumentsCollection filterByUser(DocumentsCollection documentsCollection, String email);

    Document filterByTitleAndAuthor(DocumentsCollection documentsCollection, String title, String authorName)
            throws DocumentNotFoundException;
}
