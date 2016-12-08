package edu.upc.fib.prop.models;


public class DocumentBasicInfo {

    private String title;
    private String author;
    private String user;
    private String cover;
    private Float rating;
    private String content;

    public DocumentBasicInfo(Document document) {
        this.title = document.getTitle();
        this.author = document.getAuthor();
        this.user = document.getUser();
        this.cover = document.getCover();
        this.rating = document.getRating();
        this.content = document.getContent();
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
}
