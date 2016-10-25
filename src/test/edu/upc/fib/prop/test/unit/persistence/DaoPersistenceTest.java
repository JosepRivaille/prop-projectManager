package edu.upc.fib.prop.test.unit.persistence;

import edu.upc.fib.prop.persistence.dao.authors.DaoAuthors;
import edu.upc.fib.prop.persistence.dao.authors.impl.DaoAuthorsImpl;
import edu.upc.fib.prop.persistence.dao.documents.DaoDocuments;
import edu.upc.fib.prop.persistence.dao.documents.impl.DaoDocumentsImpl;
import edu.upc.fib.prop.persistence.dao.users.DaoUsers;
import edu.upc.fib.prop.persistence.dao.users.impl.DaoUsersImpl;
import edu.upc.fib.prop.utils.Constants;
import edu.upc.fib.prop.utils.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoPersistenceTest {

    private static Connection c;
    private static DaoAuthors daoAuthors;
    private static DaoDocuments daoDocuments;
    private static DaoUsers daoUsers;
/*
    @BeforeClass
    public static void setUpTableAndDB() {
        try {
            Class.forName(Constants.JDBC_DRIVER);
            if (c == null) {
                c = DriverManager.getConnection(Constants.DB_TEST);
            }
            daoAuthors = new DaoAuthorsImpl(c);
            daoDocuments = new DaoDocumentsImpl(c);
            daoUsers = new DaoUsersImpl(c);

            Statement statement = c.createStatement();
            String sql = FileUtils.readFile("src/main/resources/sql/dbInitializer.sql");
            statement.executeUpdate(sql);
            statement.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }*/

    @Test
    public void dummy() {

    }

}
