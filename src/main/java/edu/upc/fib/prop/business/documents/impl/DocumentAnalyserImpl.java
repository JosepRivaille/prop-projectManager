package edu.upc.fib.prop.business.documents.impl;

import edu.upc.fib.prop.business.documents.DocumentAnalyser;
import edu.upc.fib.prop.exceptions.DocumentNotFoundException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.utils.Constants;
import edu.upc.fib.prop.utils.FileUtils;

import java.util.Map;
import java.util.TreeMap;

public class DocumentAnalyserImpl implements DocumentAnalyser {

    private DocumentsCollection documentsCollection;
    private Document document;

    public DocumentAnalyserImpl(DocumentsCollection documentsCollection) {
        this.documentsCollection = documentsCollection;
    }

    public Document getDocument() {
        return this.document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public boolean hasCorrectData() {
        return !this.document.getTitle().equals("") &&
                !this.document.getAuthor().equals("") &&
                !this.document.getContent().equals("");
    }

    public void calculateDocumentParameters() throws DocumentNotFoundException {
        this.document.setTermFrequency(calculateTermFrequency());
        this.documentsCollection.setInverseDocumentFrequency(calculateInverseDocumentFrequency());
    }

    @Override
    public Document fillEmptyUpdatedFields(Document oldDocument, Document newDocument) {
        if (newDocument.getTitle().equals("")) {
            newDocument.setTitle(oldDocument.getTitle());
        }
        if (newDocument.getAuthor().equals("")) {
            newDocument.setAuthor(oldDocument.getAuthor());
        }
        if (newDocument.getContent().equals("")) {
            newDocument.setContent(oldDocument.getAuthor());
        }
        return newDocument;
    }

    private Map<String, Float> calculateTermFrequency() throws DocumentNotFoundException {
        String contentFile = document.getContent();
        String content = FileUtils.readDocument(contentFile);
        Float max = 1f;
        Map<String, Float> termFrequency = new TreeMap<>();
        for (String word : content.split(Constants.WORD_SEPARATION_REGEX)) {
            if (!termFrequency.containsKey(word)) {
                termFrequency.put(word, 1f);
            } else {
                Float aux = termFrequency.get(word) + 1;
                termFrequency.put(word, aux);
                if (aux > max)
                    max = aux;
            }
        }
        for (Map.Entry<String, Float> tf : termFrequency.entrySet()) {
            Float newValue = 0.5f + (0.5f * tf.getValue() / max);
            tf.setValue(newValue);
        }
        return termFrequency;
    }

    private Map<String, Float> calculateInverseDocumentFrequency() {
        //Float documentsPerTerm = idf.get(word);
        //Float weight = (float) log(this.documentsCollection.getDocuments().size() / (1 + documentsPerTerm));
        return new TreeMap<>();
    }

}
