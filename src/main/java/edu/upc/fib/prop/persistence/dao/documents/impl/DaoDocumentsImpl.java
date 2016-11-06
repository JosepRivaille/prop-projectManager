package edu.upc.fib.prop.persistence.dao.documents.impl;

import edu.upc.fib.prop.exceptions.DocumentNotFoundException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.persistence.dao.documents.DaoDocuments;
import edu.upc.fib.prop.utils.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DaoDocumentsImpl implements DaoDocuments {

    private Connection c;

    public DocumentsCollection getAllDocuments(Connection c) {
        DocumentsCollection documentsCollection = new DocumentsCollection();
        try {
            Statement statement = c.createStatement();
            String query = "SELECT * FROM documents;";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String title = rs.getString("title");
                String authorName = rs.getString("author_name");
                String content = rs.getString("content");
                String user = rs.getString("user_owner");
                documentsCollection.addDocument(new Document(title, authorName, content, user));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documentsCollection;
    }

    public void addNewDocument(Connection c, Document document) throws AlreadyExistingDocumentException {
        String title = document.getTitle();
        String author = document.getAuthor();
        String content = document.getContent();
        String user = document.getUser();
        Map<String, Float> tf = document.getTermFrequencyList();
        String termFrequency = StringUtils.buildJSON(tf);
        try {
            Statement statement = c.createStatement();
            String query = String.format("INSERT INTO documents VALUES('%s', '%s', '%s', '%s', '%s')",
                    title, author, user, termFrequency, content);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new AlreadyExistingDocumentException();
        }
    }

    @Override
    public void deleteExistingDocument(Connection c, Document document) {
        String title = document.getTitle();
        String author = document.getAuthor();
        try {
            Statement statement = c.createStatement();
            String query = String.format("DELETE FROM documents WHERE title='%s' AND author_name='%s';",
                    title, author);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
