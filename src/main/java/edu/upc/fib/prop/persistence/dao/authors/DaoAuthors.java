package edu.upc.fib.prop.persistence.dao.authors;

import edu.upc.fib.prop.models.Author;
import edu.upc.fib.prop.models.AuthorsCollection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoAuthors {

    public AuthorsCollection getAllAuthors(Connection c) {
        AuthorsCollection authorsCollection = new AuthorsCollection();
        try {
            Statement statement = c.createStatement();
            String query = "SELECT * FROM authors;";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                authorsCollection.addAuthor(new Author(rs.getString("author_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorsCollection;
    }

}
