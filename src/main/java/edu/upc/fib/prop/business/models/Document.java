package edu.upc.fib.prop.business.models;

public class Document {

    private Long id;
    private String title;
    private DocumentType documentType;
    private Float rate;

    public Document(Long id, String title, DocumentType documentType, Float rate) {
        this.id = id;
        this.title = title;
        this.documentType = documentType;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
