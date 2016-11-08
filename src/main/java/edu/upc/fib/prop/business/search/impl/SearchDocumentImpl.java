package edu.upc.fib.prop.business.search.impl;

import edu.upc.fib.prop.business.documents.DocumentTools;
import edu.upc.fib.prop.exceptions.DocumentNotFoundException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.business.search.SearchDocument;
import edu.upc.fib.prop.models.SortedDocumentsSet;
import edu.upc.fib.prop.models.WeightsVector;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class SearchDocumentImpl implements SearchDocument {

    public DocumentsCollection filterByAuthor(DocumentsCollection documentsCollection, String authorName)
            throws DocumentNotFoundException {
        DocumentsCollection filteredDocuments = new DocumentsCollection();
        documentsCollection.getDocuments().stream().filter(document ->
                document.getAuthor().toLowerCase().contains(authorName.toLowerCase()))
                .forEach(filteredDocuments::addDocument);
        if (filteredDocuments.getDocuments().isEmpty()) {
            throw new DocumentNotFoundException();
        }
        return filteredDocuments;
    }

    public DocumentsCollection filterByUser(DocumentsCollection documentsCollection, String email) {
        DocumentsCollection filteredDocuments = new DocumentsCollection();
        documentsCollection.getDocuments().stream().filter(document ->
                document.getUser().equals(email))
                .forEach(filteredDocuments::addDocument);
        return filteredDocuments;
    }

    @Override
    public Document filterByTitleAndAuthor(DocumentsCollection documentsCollection, String title, String authorName)
            throws DocumentNotFoundException {
        for (Document document : documentsCollection.getDocuments()) {
            if (document.getTitle().toLowerCase().equals(title.toLowerCase()) &&
                    document.getAuthor().toLowerCase().equals(authorName.toLowerCase())) {
                return document;
            }
        }
        throw new DocumentNotFoundException();
    }

    @Override
    public SortedDocumentsSet searchForSimilarDocuments(DocumentsCollection col, Document doc, int k) {
    //TODO REVISAR
        WeightsVector wv1 = DocumentTools.getWeights(doc, col);
        int min = Math.min(k, col.size());

        SortedDocumentsSet res = new SortedDocumentsSet(min);

        for (Document document : col.getDocuments()) {
            if(!doc.equals(document)) {
                WeightsVector wv2 = DocumentTools.getWeights(document, col);
                Double relevance = DocumentTools.getRelevanceFactor(wv1, wv2);
                res.add(document, relevance);
            }
        }
        return res;
    }
}
