package edu.upc.fib.prop.view.document;

import edu.upc.fib.prop.exceptions.DocumentNotFoundException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.utils.FileUtils;
import edu.upc.fib.prop.utils.IOUtils;
import javafx.util.Pair;

public class DocumentManager {

    private DocumentsCollection documentsCollection;

    public DocumentManager(DocumentsCollection documentsCollection) {
        this.documentsCollection = documentsCollection;
    }

    public void setDocumentsCollection(DocumentsCollection documentsCollection) {
        this.documentsCollection = documentsCollection;
    }

    public void addDocumentToCollection(Document document) {
        this.documentsCollection.addDocument(document);
    }

    public Document createDocument() {
        String title = IOUtils.askForString("Title");
        String author = IOUtils.askForString("Author");
        String content = IOUtils.askForString("Content file");
        return new Document(title, author, content);
    }

    public void readDocument() {
        int size = showList();
        if (size > 0) {
            int documentSelected = IOUtils.askForInt("Document", 1, size) - 1;
            Document document = this.documentsCollection.getDocuments().get(documentSelected);
            System.out.println(document.getTitle().toUpperCase() + " | " + document.getAuthor().toUpperCase());
            String documentFileName = document.getContent();
            try {
                String documentContent = FileUtils.readDocument(documentFileName);
                System.out.println(documentContent);
            } catch (DocumentNotFoundException e) {
                System.out.println("Document content not found in /resources/documents folder.");
            }
        } else {
            System.out.println("No documents found!");
        }
    }

    public Pair<Document, Document> updateDocument() throws DocumentNotFoundException {
        int numDocuments = showList();
        if (numDocuments == 0) {
            throw new DocumentNotFoundException();
        } else {
            int documentSelected = IOUtils.askForInt("Choose a document", 1, numDocuments);
            Document oldDocument = this.documentsCollection.getDocuments().get(documentSelected - 1);
            String newTitle = IOUtils.askForString("New title");
            String newAuthor = IOUtils.askForString("New author");
            String newContent = IOUtils.askForString("New content");
            Document newDocument = new Document(newTitle, newAuthor, newContent, oldDocument.getUser());
            return new Pair<>(oldDocument, newDocument);
        }
    }

    public Document deleteDocument() throws DocumentNotFoundException {
        int numDocuments = showList();
        if (numDocuments == 0) {
            throw new DocumentNotFoundException();
        } else {
            int documentSelected = IOUtils.askForInt("Choose a document", 1, numDocuments);
            return this.documentsCollection.getDocuments().get(documentSelected - 1);
        }
    }

    private int showList() {
        int i = 0;
        for (Document document : this.documentsCollection.getDocuments()) {
            System.out.println(++i + "- " + document.getTitle() + " | " + document.getAuthor());
        }
        return this.documentsCollection.getDocuments().size();
    }

}
