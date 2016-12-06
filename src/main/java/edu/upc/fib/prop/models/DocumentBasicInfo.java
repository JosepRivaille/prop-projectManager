package edu.upc.fib.prop.models;


import edu.upc.fib.prop.exceptions.DocumentContentNotFoundException;
import edu.upc.fib.prop.utils.FileUtils;

public class DocumentBasicInfo {

    private String title;
    private String author;
    private String user;
    private String cover;
    private Float rating;
    private String fileName;
    private String content;

    public DocumentBasicInfo(Document document) {
        this.title = document.getTitle();
        this.author = document.getAuthor();
        this.user = document.getUser();
        this.cover = document.getCover();
        this.rating = document.getRating();
        this.fileName = document.getContent();
        try {
            this.content = FileUtils.readDocument(document.getContent());
        } catch (DocumentContentNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUser() {
        return user;
    }

    public String getCover() {
        return cover;
    }

    public Float getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public String getFileName() {
        return fileName;
    }
}
