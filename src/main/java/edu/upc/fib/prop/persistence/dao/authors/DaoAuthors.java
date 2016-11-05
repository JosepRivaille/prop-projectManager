package edu.upc.fib.prop.persistence.dao.authors;

import edu.upc.fib.prop.exceptions.AuthorNotFoundException;
import edu.upc.fib.prop.models.Author;
import edu.upc.fib.prop.models.AuthorsCollection;

import java.sql.Connection;
import java.sql.SQLException;

public interface DaoAuthors {

    /**
     * Gets all authors stored in persistence.
     * @return Set of all authors.
     */
    AuthorsCollection getAllAuthors(Connection c);

    Author getAuthorByName(Connection c, String author) throws AuthorNotFoundException;

    void createAuthor(Connection c, String author) throws SQLException;
}
