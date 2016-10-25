package edu.upc.fib.prop.persistence.dao.documents.impl;

import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.persistence.dao.documents.DaoDocuments;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoDocumentsImpl implements DaoDocuments {

    private Connection c;

    public DaoDocumentsImpl(Connection c) {
        this.c = c;
    }

    public DocumentsCollection getAllDocuments() {
        DocumentsCollection documentsCollection = new DocumentsCollection();
        try {
            Statement statement = c.createStatement();
            String query = "SELECT * FROM documents;";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String title = rs.getString("title");
                String authorName = rs.getString("author_name");
                String content = rs.getString("content");
                documentsCollection.addDocument(new Document(title, authorName, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documentsCollection;
    }

    public void addNewDocument(Document document) throws AlreadyExistingDocumentException {
        String email = document.getTitle();
        String author = document.getAuthor();
        String content = document.getContent();
        try {
            Statement statement = c.createStatement();
            String query = "INSERT INTO documents VALUES()";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new AlreadyExistingDocumentException();
        }
    }

}
