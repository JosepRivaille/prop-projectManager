package edu.upc.fib.prop.persistence.dao.documents;

import edu.upc.fib.prop.business.models.Author;
import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.Document;
import edu.upc.fib.prop.business.models.DocumentsCollection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoDocuments {

    public DocumentsCollection getAllDocuments(Connection c) {
        DocumentsCollection documentsCollection = new DocumentsCollection();
        try {
            Statement statement = c.createStatement();
            String query = "SELECT * FROM documents;";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                //authorsCollection.addAuthor(new Document());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documentsCollection;
    }

}
