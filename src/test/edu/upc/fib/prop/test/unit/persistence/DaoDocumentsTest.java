package edu.upc.fib.prop.test.unit.persistence;

import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.persistence.dao.documents.DaoDocuments;
import edu.upc.fib.prop.persistence.dao.documents.impl.DaoDocumentsImpl;
import edu.upc.fib.prop.utils.Constants;
import edu.upc.fib.prop.utils.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;

public class DaoDocumentsTest {

    private static Connection c;
    private static DaoDocuments daoDocuments;

    @BeforeClass
    public static void setUpTableAndDB() {
        try {
            Class.forName(Constants.JDBC_DRIVER);
            c = DriverManager.getConnection(Constants.DB_TEST);
            daoDocuments = new DaoDocumentsImpl(c);

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

    @AfterClass
    public static void dropDB() {
        File f = new File("test.db");
        boolean deleted = f.delete();
        System.out.println("File deleted: " + deleted);
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*--------------- Get all documents tests */

    @Test
    public void test_whenGetAllDocuments_withDefaultSQL_thenReturnExpectedCollection() {
        DocumentsCollection documentsCollection = daoDocuments.getAllDocuments();

        assertTrue(documentsCollection.getDocuments().size() == 10);
    }

    /*--------------- Add new document tests */

    @Test(expected = AlreadyExistingDocumentException.class)
    public void test_whenAddNewDocument_withAlreadyExistingDocument_thenDoNotAddNewDocument()
            throws AlreadyExistingDocumentException {
        String title = "presharing";
        String author = "Rodrick Weimer";
        String user = "admin";
        Map<String, Float> termFrequency = new TreeMap<>();
        String content = "1";
        Document document = new Document(title, author, user, content);
        document.setTermFrequency(termFrequency);

        daoDocuments.addNewDocument(document);
    }

    @Test
    public void test_whenAddNewDocument_withRealNewDocument_thenAddNewDocument()
            throws AlreadyExistingDocumentException {
        String title = "presharing";
        String author = "James Bond";
        String user = "admin";
        Map<String, Float> termFrequency = new TreeMap<>();
        String content = "1";
        Document document = new Document(title, author, user, content);
        document.setTermFrequency(termFrequency);

        daoDocuments.addNewDocument(document);
        assertTrue(daoDocuments.getAllDocuments().getDocuments().size() == 11);
    }

}
