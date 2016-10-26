package edu.upc.fib.prop.models;

import java.util.Map;
import java.util.TreeMap;

public class Document {
    private String title;
    private String author;
    private String user;
    private String content;
    private Map<String, Float> termFrequency;

    public Document(String title, String author, String content, String user) {
        this.title = title;
        this.author = author;
        this.user = user;
        this.content = content;

        termFrequency = new TreeMap<>();
    }

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

    public Map<String, Float> getTermFrequencyList() {
        return termFrequency;
    }

    public Float getTermFrequency(String word) {
        return termFrequency.get(word);
    }

    public void setTermFrequency(Map<String, Float> termFrequency) {
        this.termFrequency = termFrequency;
    }

    public String getUser() {
        return user;
    }
}
