package edu.upc.fib.prop.persistence.dao.documents.impl;

import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.persistence.dao.documents.DaoDocuments;
import edu.upc.fib.prop.utils.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DaoDocumentsImpl implements DaoDocuments {

    public void addNewDocument(Connection c, Document document) throws AlreadyExistingDocumentException {
        String title = document.getTitle();
        String author = document.getAuthor();
        String content = document.getContent();
        String user = document.getUser();
        Map<String, Float> tf = document.getTermFrequencyList();
        String termFrequency = StringUtils.buildJSONFromMap(tf);
        try {
            Statement statement = c.createStatement();
            String query = String.format("INSERT INTO documents VALUES('%s', '%s', '%s', '%s', '%s')",
                    title, author, user, termFrequency, content);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new AlreadyExistingDocumentException();
        }
    }

    public DocumentsCollection getAllDocuments(Connection c) {
        DocumentsCollection documentsCollection = new DocumentsCollection();
        try {
            Statement statement = c.createStatement();
            String query = "SELECT * FROM documents ORDER BY title ASC;";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String title = rs.getString("title");
                String authorName = rs.getString("author_name");
                String content = rs.getString("content");
                String user = rs.getString("user_owner");
                String termFrequency = rs.getString("term_frequency");
                Document document = new Document(title, authorName, content, user);
                document.setTermFrequency(StringUtils.buildMapFromJSON(termFrequency));
                try {
                    documentsCollection.addDocument(document);
                } catch (InvalidDetailsException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documentsCollection;
    }

    @Override
    public void updateExistingDocument(Connection c, Document oldDocument, Document newDocument) {
        String oldTitle = oldDocument.getTitle();
        String oldAuthor = oldDocument.getAuthor();
        try {
            Statement statement = c.createStatement();
            String query = String.format("UPDATE documents " +
                            "SET title='%s', author_name='%s', term_frequency='%s', content='%s'" +
                            "WHERE title='%s' AND author_name='%s';",
                    newDocument.getTitle(), newDocument.getAuthor(),
                    StringUtils.buildJSONFromMap(newDocument.getTermFrequencyList()),
                    newDocument.getContent(), oldTitle, oldAuthor);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDocumentOwner(Connection c, String oldEmail, String newEmail) {
        try {
            Statement statement = c.createStatement();
            String query = String.format("UPDATE documents " +
                            "SET user_owner='%s' WHERE user_owner='%s'", oldEmail, newEmail);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
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

    @Override
    public void deleteDocuments(Connection c, String email) {
        try {
            Statement statement = c.createStatement();
            String query = String.format("DELETE FROM documents WHERE user_owner='%s'", email);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
