package edu.upc.fib.prop.models;

import edu.upc.fib.prop.exceptions.DocumentContentNotFoundException;
import edu.upc.fib.prop.utils.Constants;
import edu.upc.fib.prop.utils.FileUtils;

import javax.print.Doc;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;

public class Document {
    private String title;
    private String author;
    private String user;
    private String content;
    private Map<String, Float> termFrequency;

    public Document(String title, String author, String content, String user) {
        termFrequency = new TreeMap<>();
        this.title = title;
        this.author = author;
        this.user = user;
        this.content = content;
        termFrequency = new TreeMap<>();
        test();
    }

    public Document() {
        termFrequency = new TreeMap<>();
        test();
    }

    public Document(String title, String author, String content) {
        this.title = title;
        this.author = author;
        termFrequency = new TreeMap<>();
        this.content = content;
        test();
    }
    public void test(){
        termFrequency.put("test", 23f);
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

    public void setUser(String user) {
        this.user = user;
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

    public void updateFreqs() throws DocumentContentNotFoundException {
        Float max = 1f;
        termFrequency = new TreeMap<>();
        for (String word : FileUtils.readDocument(content).split(Constants.WORD_SEPARATION_REGEX)) {
            word = word.toLowerCase();
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
    }

    public Document clone(){
        Document clonedDoc = new Document(this.title, this.author, this.content, this.user);
        clonedDoc.setTermFrequency(this.termFrequency);
        return clonedDoc;
    }

    public boolean isCorrect() {
        return !this.title.equals("") &&
                !this.author.equals("") &&
                !this.content.equals("");
    }

    public Document merge(Document newDoc){
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
        } catch (DocumentContentNotFoundException e){return false;}
    }
}
