package edu.upc.fib.prop.models;

import edu.upc.fib.prop.exceptions.DocumentContentNotFoundException;
import edu.upc.fib.prop.utils.Constants;
import edu.upc.fib.prop.utils.FileUtils;

import java.util.Map;
import java.util.TreeMap;

public class Document {
    private String title;
    private String author;
    private String user;
    private String content;
    private Map<String, Float> termFrequency;
    private Map<String, Integer> termPositions;

    public Document(String title, String author, String content, String user) {
        termFrequency = new TreeMap<>();
        this.title = title;
        this.author = author;
        this.user = user;
        this.content = content;
        termFrequency = new TreeMap<>();
    }

    public Document(String title, String author, String content) {
        this.title = title;
        this.author = author;
        termFrequency = new TreeMap<>();
        this.content = content;
    }

    /* Getters and Setters */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Float> getTermFrequency() {
        return termFrequency;
    }

    public void setTermFrequency(Map<String, Float> termFrequency) {
        this.termFrequency = termFrequency;
    }

    public Map<String, Integer> getTermPositions() {
        return termPositions;
    }

    public void setTermPositions(Map<String, Integer> termPositions) {
        this.termPositions = termPositions;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    /* Utils */

    public void updateFrequencies() throws DocumentContentNotFoundException {
        Float max = 1f;
        termFrequency = new TreeMap<>();
        for (String word : FileUtils.readDocument(content).split(Constants.WORD_SEPARATION_REGEX)) {
            word = word.toLowerCase();
            if (!termFrequency.containsKey(word)) {
                termFrequency.put(word, 1f);
            } else {
                Float aux = termFrequency.get(word) + 1;
                termFrequency.put(word, aux);
                if (aux > max) {
                    max = aux;
                }
            }
        }
        for (Map.Entry<String, Float> tf : termFrequency.entrySet()) {
            Float newValue = 0.5f + (0.5f * tf.getValue() / max);
            tf.setValue(newValue);
        }
    }

    public Document clone(){
        Document clonedDoc = new Document(this.title, this.author, this.content, this.user);
        clonedDoc.setTermFrequency(this.termFrequency);
        return clonedDoc;
    }

    public boolean isCorrect() {
        return !this.title.equals("")
                && !this.author.equals("")
                && !this.content.equals("");
    }

    Document merge(Document newDoc) {
        Document mergedDoc = this.clone();
        if(!newDoc.getTitle().equals("")) mergedDoc.setTitle(newDoc.getTitle());
        if(!newDoc.getAuthor().equals("")) mergedDoc.setAuthor(newDoc.getAuthor());
        if(!newDoc.getContent().equals("")) mergedDoc.setContent(newDoc.getContent());
        return mergedDoc;
    }

    public boolean isContentPathCorrect() {
        try {
            FileUtils.readDocument(this.getContent());
            return true;
        } catch (DocumentContentNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return title != null ? title.equals(document.title) : document.title == null && (author != null ?
                author.equals(document.author) : document.author == null && (user != null ? user.equals(document.user) :
                document.user == null && (content != null ? content.equals(document.content) : document.content == null
                        && (termFrequency != null ? termFrequency.equals(document.termFrequency) :
                        document.termFrequency == null))));
    }

}
