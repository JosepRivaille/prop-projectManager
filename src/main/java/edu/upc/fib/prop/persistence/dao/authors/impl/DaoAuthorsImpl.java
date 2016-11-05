package edu.upc.fib.prop.persistence.dao.authors.impl;

import edu.upc.fib.prop.exceptions.AuthorNotFoundException;
import edu.upc.fib.prop.models.Author;
import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.persistence.dao.authors.DaoAuthors;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoAuthorsImpl implements DaoAuthors {

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

    @Override
    public Author getAuthorByName(Connection c, String author) throws AuthorNotFoundException {
        try {
            Statement statement = c.createStatement();
            String query = String.format("SELECT * FROM authors WHERE author_name='%s';", author);
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return new Author(rs.getString("author_name"));
            } else {
                throw new AuthorNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createAuthor(Connection c, String author) throws SQLException {
        Statement statement = c.createStatement();
        String query = String.format("INSERT INTO authors VALUES('%s');", author);
        statement.executeUpdate(query);
    }

}
