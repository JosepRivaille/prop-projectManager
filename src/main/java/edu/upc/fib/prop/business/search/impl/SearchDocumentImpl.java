package edu.upc.fib.prop.business.search.impl;

import edu.upc.fib.prop.business.search.SearchDocument;
import edu.upc.fib.prop.exceptions.DocumentContentNotFoundException;
import edu.upc.fib.prop.exceptions.DocumentNotFoundException;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.models.SortedDocumentsSet;
import edu.upc.fib.prop.models.WeightsVector;
import edu.upc.fib.prop.utils.FileUtils;
import sun.reflect.generics.tree.Tree;

import java.util.Map;
import java.util.TreeMap;


public class SearchDocumentImpl implements SearchDocument {

    public DocumentsCollection filterByAuthor(DocumentsCollection documentsCollection, String authorName)
            throws DocumentNotFoundException {
        DocumentsCollection filteredDocuments = new DocumentsCollection();

        documentsCollection.getDocuments().stream().filter(document ->
                document.getAuthor().toLowerCase().contains(authorName.toLowerCase())).forEach(document -> {
            try {
                filteredDocuments.addDocument(document);
            } catch (InvalidDetailsException e) {
                e.printStackTrace();
            }
        });
        if (filteredDocuments.getDocuments().isEmpty()) {
            throw new DocumentNotFoundException();
        }
        return filteredDocuments;
    }

    public DocumentsCollection filterByUser(DocumentsCollection documentsCollection, String email) {
        DocumentsCollection filteredDocuments = new DocumentsCollection();
        documentsCollection.getDocuments().stream().filter(document ->
                document.getUser().toLowerCase().equals(email.toLowerCase())).forEach(document -> {
            try {
                filteredDocuments.addDocument(document);
            } catch (InvalidDetailsException e) {
                e.printStackTrace();
            }
        });
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
        WeightsVector wv1 = col.getWeights(doc);
        int min = Math.min(k, col.size());

        SortedDocumentsSet res = new SortedDocumentsSet(min);

        for (Document document : col.getDocuments()) {
            if (!doc.equals(document)) {
                WeightsVector wv2 = col.getWeights(document);
                Double relevance = getRelevanceFactor(wv1, wv2);
                res.add(document, relevance);
            }
        }
        return res;
    }

    @Override
    public SortedDocumentsSet getRelevantDocuments(SortedDocumentsSet list, double rv) {
        SortedDocumentsSet res = new SortedDocumentsSet();
        for (int i = 0; i < list.getSize(); i++) {
            if (list.getValue(i) >= rv) res.add(list.getDocument(i), list.getValue(i));
        }
        return res;
    }

    @Override
    public SortedDocumentsSet getNonRelevantDocuments(SortedDocumentsSet list, double rv) {
        SortedDocumentsSet res = new SortedDocumentsSet();
        for (int i = 0; i < list.getSize(); i++) {
            if (list.getValue(i) < rv) res.add(list.getDocument(i), list.getValue(i));
        }
        return res;
    }

    @Override
    public Document getRocchioQuery(Document query, SortedDocumentsSet list, double rv, float b, float c)
            throws DocumentContentNotFoundException, DocumentNotFoundException {
            SortedDocumentsSet rDocs = getRelevantDocuments(list, rv);
            SortedDocumentsSet nrDocs = getNonRelevantDocuments(list, rv);
            Document rDoc = rDocs.getDocument(0);
            Document nrDoc = nrDocs.getDocument(0);
            if (rDoc != null && nrDoc != null) {
                for (int i = 0; i < rDocs.getSize(); i++) {
                    for (Map.Entry<String, Float> entry : rDocs.getDocument(i).getTermFrequency().entrySet()) {
                        if (!rDoc.getTermFrequency().containsKey(entry.getKey())) {
                            rDoc.getTermFrequency().put(entry.getKey(), 1f);
                        } else {
                            rDoc.getTermFrequency().replace(entry.getKey(), rDoc.getTermFrequency().get(entry.getKey()) + 1f);
                        }
                    }
                }
                for (Map.Entry<String, Float> entry : rDoc.getTermFrequency().entrySet()) {
                    rDoc.getTermFrequency().replace(entry.getKey(), rDoc.getTermFrequency().get(entry.getKey()) *
                            (1f / (float) rDoc.getTermFrequency().size()) * b);
                }
                for (int i = 0; i < nrDocs.getSize(); i++) {
                    for (Map.Entry<String, Float> entry : nrDocs.getDocument(i).getTermFrequency().entrySet()) {
                        if (nrDoc.getTermFrequency().containsKey(entry.getKey())) {
                            if (!nrDoc.getTermFrequency().containsKey(entry.getKey())) {
                                nrDoc.getTermFrequency().put(entry.getKey(), 1f);
                            } else {
                                nrDoc.getTermFrequency().replace(entry.getKey(), nrDoc.getTermFrequency().get(entry.getKey()) + 1f);
                            }
                        }
                    }
                }
                for (Map.Entry<String, Float> entry : nrDoc.getTermFrequency().entrySet()) {
                    nrDoc.getTermFrequency().replace(entry.getKey(), nrDoc.getTermFrequency().get(entry.getKey()) *
                            (1f / (float) nrDoc.getTermFrequency().size()) * c);
                }
                for (Map.Entry<String, Float> entry : rDoc.getTermFrequency().entrySet()) {
                    if (!query.getTermFrequency().containsKey(entry.getKey())) {
                        query.getTermFrequency().put(entry.getKey(), entry.getValue());
                    } else {
                        query.getTermFrequency().replace(entry.getKey(), query.getTermFrequency().get(entry.getKey()) + entry.getValue());
                    }
                }
                for (Map.Entry<String, Float> entry : nrDoc.getTermFrequency().entrySet()) {
                    if (query.getTermFrequency().containsKey(entry.getKey())) {
                        if (query.getTermFrequency().get(entry.getKey()) - entry.getValue() < 0f) {
                            query.getTermFrequency().remove(entry.getKey());
                        } else {
                            query.getTermFrequency().replace(entry.getKey(), query.getTermFrequency().get(entry.getKey()) - entry.getValue());
                        }
                    }
                }
            return query;
        }
        throw new DocumentNotFoundException();
    }

    private double getRelevanceFactor(WeightsVector wv1, WeightsVector wv2){
        Double sumProd = 0.0;
        Double sumSquaresWv1 = 0.0;
        Double sumSquaresWv2 = 0.0;
        for(String term : wv1){
            sumSquaresWv1 += wv1.getWeight(term)*wv1.getWeight(term);
            if(wv2.contains(term)) {
                sumProd += wv1.getWeight(term) * wv2.getWeight(term);
            }
        }
        for(String term : wv2){
            sumSquaresWv2 += wv2.getWeight(term)*wv2.getWeight(term);
        }
        return sumProd/( (sumSquaresWv1 + sumSquaresWv2) - sumProd);
    }
}
