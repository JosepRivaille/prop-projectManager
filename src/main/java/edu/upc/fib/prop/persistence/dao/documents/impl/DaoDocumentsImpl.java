package edu.upc.fib.prop.persistence.dao.documents.impl;

import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.exceptions.DocumentContentNotFoundException;
import edu.upc.fib.prop.exceptions.DocumentNotFoundException;
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
import java.util.Set;

public class DaoDocumentsImpl implements DaoDocuments {

    public void addNewDocument(Connection c, Document document) throws AlreadyExistingDocumentException {
        String title = document.getTitle();
        String author = document.getAuthor();
        String content = document.getContent();
        String user = document.getUser();
        String cover = document.getCover();
        Map<String, Float> tf = document.getTermFrequency();
        Map<String, Map<Integer, Set<Integer>>> tp = document.getTermPositions();
        String termFrequency = StringUtils.buildJSONFromFrequencyMap(tf);
        String termPositions = StringUtils.buildJSONFromPositionsMap(tp);
        try {
            Statement statement = c.createStatement();
            String query = String.format("INSERT INTO documents VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    title, author, user, termFrequency, termPositions, content, cover);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new AlreadyExistingDocumentException();
        }
    }

    public DocumentsCollection getAllDocuments(Connection c) {
        DocumentsCollection documentsCollection = new DocumentsCollection();
        try {
            Statement statement = c.createStatement();
            String query = "SELECT * FROM documents ORDER BY title, author_name;";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String title = rs.getString("title");
                String authorName = rs.getString("author_name");
                String content = rs.getString("content");
                String user = rs.getString("user_owner");
                String termFrequency = rs.getString("term_frequency");
                String termPositions = rs.getString("term_positions");
                Document document = new Document(title, authorName, content, user);
                document.setTermFrequency(StringUtils.buildFrequencyMapFromJSON(termFrequency));
                document.setTermPositions(StringUtils.buildPositionMapFromJSON(termPositions));
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
                    StringUtils.buildJSONFromFrequencyMap(newDocument.getTermFrequency()),
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

    @Override
    public void rateDocument(Connection c, Document doc, int rating, String user) throws DocumentNotFoundException{
        try {
            Statement statement = c.createStatement();
            //String q = "INSERT OR REPLACE INTO users(user_email, title, author_name, points) VALUES ('%s','%s','%s',%d)";
            String query = String.format("INSERT OR REPLACE INTO ratings(user_email, title, author_name, points) VALUES ('%s','%s','%s', %d)", user, doc.getTitle(), doc.getAuthor(), rating);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new DocumentNotFoundException();
        }
    }

    @Override
    public void addDocumentToFavourites(Connection c, Document document, String user) throws DocumentNotFoundException{
        try {
            Statement statement = c.createStatement();
            String query = String.format("INSERT OR REPLACE INTO favourites(title,author_name,user_email) VALUES ('%s','%s','%s')", document.getTitle(), document.getAuthor(), user);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new DocumentNotFoundException();
        }
    }

    @Override
    public void deleteDocumentFromFavourites(Connection c, Document document, String user) throws DocumentNotFoundException{
        try {
            Statement statement = c.createStatement();
            String query = String.format("DELETE FROM favourites WHERE title LIKE '%s' AND author_name LIKE '%s' AND user_email LIKE '%s'", document.getTitle(), document.getAuthor(), user);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new DocumentNotFoundException();
        }
    }

    @Override
    public void deleteAllFavouritesOfDocument(Connection c, Document document) {
        Statement statement = null;
        try {
            statement = c.createStatement();
            String query = String.format("DELETE FROM favourites WHERE title LIKE '%s' AND author_name like '%s'", document.getTitle(), document.getAuthor());
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
