package edu.upc.fib.prop.view.document;

import edu.upc.fib.prop.exceptions.DocumentNotFoundException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.utils.FileUtils;
import javafx.util.Pair;

import java.util.Scanner;

public class DocumentManager {

    private DocumentsCollection documentsCollection;

    private Scanner scan = new Scanner(System.in);

    public DocumentManager(DocumentsCollection documentsCollection) {
        this.documentsCollection = documentsCollection;
    }

    public void setDocumentsCollection(DocumentsCollection documentsCollection) {
        this.documentsCollection = documentsCollection;
    }

    public Document createDocument() {
        System.out.print("Title > ");
        String title = scan.next();
        System.out.print("Author > ");
        String author = scan.next();
        System.out.print("Content > ");
        String content = scan.next();
        return new Document(title, author, content, null);
    }

    public void readDocument() {
        showList();
        int documentSelected = scan.nextInt() - 1;
        Document document = documentsCollection.getDocuments().get(documentSelected);
        System.out.println(document.getTitle().toUpperCase() + " | " + document.getAuthor().toUpperCase());
        String documentFileName = document.getContent();
        try {
            String documentContent = FileUtils.readDocument(documentFileName);
            System.out.println(documentContent);
        } catch (DocumentNotFoundException e) {
            System.out.println("Document not found in docs folder.");
        }
    }

    public Pair<String, Document> updateDocument() {
        showList();
        int documentSelected = scan.nextInt() - 1;
        Document oldDocument = documentsCollection.getDocuments().get(documentSelected);
        String newTitle = scan.next();
        String newAuthor = scan.next();
        String newContent = scan.next();
        Document newDocument = new Document(newTitle, newAuthor, newContent, oldDocument.getUser());
        return new Pair<>(oldDocument.getTitle(), newDocument);
    }

    public Document deleteDocument() {
        int documentSelected = scan.nextInt() - 1;
        return documentsCollection.getDocuments().get(documentSelected);
    }

    private void showList() {
        int i = 0;
        for (Document document : this.documentsCollection.getDocuments()) {
            System.out.println(++i + "- " + document.getTitle() + " | " + document.getAuthor());
        }
    }

}
