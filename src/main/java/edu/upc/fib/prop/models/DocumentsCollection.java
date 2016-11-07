package edu.upc.fib.prop.models;

import edu.upc.fib.prop.business.documents.impl.DocumentTools;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import org.mockito.internal.util.collections.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DocumentsCollection {

    private List<Document> documents;
    private Map<String, Integer> wordsFrequencies;

    public DocumentsCollection() {
        this.documents = new ArrayList<>();
        this.wordsFrequencies = new TreeMap<>();
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void addDocument(Document document) {
        this.documents.add(document);

        for(Map.Entry<String,Float> entry : document.getTermFrequencyList().entrySet()) {
            addWord(entry.getKey());
        }
    }

    public void deleteDocument(Document document) {
        this.documents.remove(document);
        for(Map.Entry<String,Float> entry : document.getTermFrequencyList().entrySet()) {
            removeWord(entry.getKey());
        }
    }

    public void updateDocument(Document oldDoc, Document newDoc) throws InvalidDetailsException {
        newDoc = DocumentTools.mergeDocs(oldDoc, newDoc);
        this.documents.remove(oldDoc);
        for(Map.Entry<String,Float> entry : oldDoc.getTermFrequencyList().entrySet()) {
            removeWord(entry.getKey());
        }
        this.documents.add(newDoc);
        for(Map.Entry<String,Float> entry : newDoc.getTermFrequencyList().entrySet()) {
            addWord(entry.getKey());
        }
    }

    public Float getWordWeight(Document doc, String word){
        return doc.getTermFrequency(word) * getIdf(word);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentsCollection that = (DocumentsCollection) o;
        return documents != null ? documents.equals(that.documents) : that.documents == null;

    }

    public void addWord(String word){
        if(wordsFrequencies.containsKey(word)) wordsFrequencies.put(word, wordsFrequencies.get(word)+1);
        else wordsFrequencies.put(word, 1);
    }

    public void removeWord(String word){
        if(wordsFrequencies.containsKey(word)) wordsFrequencies.put(word, wordsFrequencies.get(word)-1);
       if(wordsFrequencies.get(word)<=0) wordsFrequencies.remove(word);
    }

    public Float getIdf(String word){
        return (float)Math.log(documents.size()/wordsFrequencies.get(word));
    }
}
