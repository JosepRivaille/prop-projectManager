package edu.upc.fib.prop.persistence.dao.authors;

import edu.upc.fib.prop.models.AuthorsCollection;

import java.sql.Connection;

public interface DaoAuthors {

    /**
     * Gets all authors stored in persistence.
     * @param c Connection of the DB.
     * @return Set of all authors.
     */
    AuthorsCollection getAllAuthors(Connection c);

}
