package edu.upc.fib.prop.business.documents;

import edu.upc.fib.prop.exceptions.DocumentNotFoundException;
import edu.upc.fib.prop.models.Document;

public interface DocumentAnalyser {

    /**
     * Gets the document attribute of the DocumentAnalyser.
     * @return Current document treated.
     */
    Document getDocument();

    /**
     * Sets a document to analyse.
     * @param document Document to analyse.
     */
    void setDocument(Document document);

    /**
     * Checks if new document data is invalid in some way.
     * @return If document data is valid.
     */
    boolean hasCorrectData();

    /**
     * Calculates tf-idf of the document.
     */
    void calculateDocumentParameters() throws DocumentNotFoundException;

    Document fillEmptyUpdatedFields(Document oldDocument, Document newDocument);
}
