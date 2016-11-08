package edu.upc.fib.prop.test.unit.persistence;

import edu.upc.fib.prop.exceptions.AuthorNotFoundException;
import edu.upc.fib.prop.models.Author;
import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.persistence.dao.authors.DaoAuthors;
import edu.upc.fib.prop.persistence.dao.authors.impl.DaoAuthorsImpl;
import edu.upc.fib.prop.utils.Constants;
import edu.upc.fib.prop.utils.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertTrue;

public class DaoAuthorsTest {

    private  Connection c;
    private DaoAuthors daoAuthors;

    @Before
    public void setUpTableAndDB() {
        try {
            Class.forName(Constants.JDBC_DRIVER);
            c = DriverManager.getConnection(Constants.DB_TEST);
            daoAuthors = new DaoAuthorsImpl();

            Statement statement = c.createStatement();
            String sql = FileUtils.readFile("src/main/resources/sql/dbInitializer.sql");
            statement.executeUpdate(sql);
            sql = FileUtils.readFile("src/main/resources/sql/dbFiller.sql");
            statement.executeUpdate(sql);
            statement.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    @After
    public void dropDB() {
        File f = new File("test.db");
        f.delete();
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*--------------- Create */

    @Test(expected = AuthorNotFoundException.class)
    public void test_whenGetAuthorByName_withNonExistingAuthor_thenThrowNotFoundException()
            throws AuthorNotFoundException {
        String fakeName = "Foo";

        daoAuthors.getAuthorByName(c, fakeName);
    }

    /*--------------- Read */

    @Test
    public void test_whenGetAllAuthors_withDefaultSQL_thenReturnExpectedCollection() {
        AuthorsCollection authorsCollection = daoAuthors.getAllAuthors(c);
        assertTrue(authorsCollection.getAuthors().size() == 9);
    }

    @Test
    public void test_whenGetAllAuthors_withExistingData_thenReturnSortedCollection() {
        AuthorsCollection authorsCollection = daoAuthors.getAllAuthors(c);

        AuthorsCollection expectedCollection = new AuthorsCollection();
        expectedCollection.addAuthor(new Author("Dwain Spratling"));
        expectedCollection.addAuthor(new Author("Earnestine Rine"));
        expectedCollection.addAuthor(new Author("Gaylord Lisowski"));
        expectedCollection.addAuthor(new Author("Mardell Tucci"));
        expectedCollection.addAuthor(new Author("Merrill Wolanski"));
        expectedCollection.addAuthor(new Author("Rodrick Weimer"));
        expectedCollection.addAuthor(new Author("Sima Hannon"));
        expectedCollection.addAuthor(new Author("Teisha Suman"));
        expectedCollection.addAuthor(new Author("Zofia Bivens"));

        assertTrue(expectedCollection.equals(authorsCollection));
    }

    @Test
    public void test_whenGetAuthorByName_withExistingAuthor_thenReturnAuthor()
            throws SQLException, AuthorNotFoundException {
        String name = "Foo";
        Author expectedAuthor = new Author(name);

        daoAuthors.createAuthor(c, name);
        Author author = daoAuthors.getAuthorByName(c, name);
        assertTrue(author.equals(expectedAuthor));
    }

}
