package edu.upc.fib.prop.persistence.dao.authors;

import edu.upc.fib.prop.exceptions.AuthorNotFoundException;
import edu.upc.fib.prop.models.Author;
import edu.upc.fib.prop.models.AuthorsCollection;

import java.sql.Connection;
import java.sql.SQLException;

public interface DaoAuthors {

    /**
     * Creates an author in persistence.
     * @param c DB connection.
     * @param author Author to store in the DB.
     */
    void createAuthor(Connection c, String author) throws SQLException;

    /**
     * Gets all authors stored in persistence.
     * @return Set of all authors.
     */
    AuthorsCollection getAllAuthors(Connection c);

    /**
     * Gets an author by name from persistence.
     * @param c DB connection.
     * @param author Author to search for.
     * @return Author that matches desired author.
     */
    Author getAuthorByName(Connection c, String author) throws AuthorNotFoundException;

}
