package edu.upc.fib.prop.business.search;

import edu.upc.fib.prop.models.DocumentsSet;


public interface BooleanExp {

    /**
     * Search for matching authors given a prefix
     * @param expresio boolean expresion to search by.
     * @return Set of matching documents.
     */
    DocumentsSet SearchDocumentsByBooleanExp(String expresio);
}
