package edu.upc.fib.prop.test.unit.persistence;

import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.persistence.dao.documents.DaoDocuments;
import edu.upc.fib.prop.persistence.dao.documents.impl.DaoDocumentsImpl;
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
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;

public class DaoDocumentsTest {

    private Connection c;
    private DaoDocuments daoDocuments;

    @Before
    public void setUpTableAndDB() {
        try {
            Class.forName(Constants.JDBC_DRIVER);
            c = DriverManager.getConnection(Constants.DB_TEST);
            daoDocuments = new DaoDocumentsImpl();

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

    /*--------------- Add new document tests */

    @Test(expected = AlreadyExistingDocumentException.class)
    public void test_whenAddNewDocument_withAlreadyExistingDocument_thenDoNotAddNewDocument()
            throws AlreadyExistingDocumentException {
        String title = "presharing";
        String author = "Rodrick Weimer";
        String user = "foo@bar.edu";
        String content = "fake.txt";
        Map<String, Float> termFrequency = new TreeMap<>();

        Document document = new Document(title, author, user, content);
        document.setTermFrequency(termFrequency);

        daoDocuments.addNewDocument(c, document);
    }

    @Test
    public void test_whenAddNewDocument_withRealNewDocument_thenAddNewDocument()
            throws AlreadyExistingDocumentException {
        String title = "presharing";
        String author = "James Bond";
        String user = "foo@bar.edu";
        String content = "fake.txt";
        Map<String, Float> termFrequency = new TreeMap<>();

        Document document = new Document(title, author, user, content);
        document.setTermFrequency(termFrequency);

        daoDocuments.addNewDocument(c, document);
        assertTrue(daoDocuments.getAllDocuments(c).getDocuments().size() == 11);
    }

    /*--------------- Get all documents tests */

    @Test
    public void test_whenGetAllDocuments_withDefaultSQL_thenReturnExpectedCollection() {
        DocumentsCollection documentsCollection = daoDocuments.getAllDocuments(c);

        assertTrue(documentsCollection.getDocuments().size() == 10);
    }

    /*--------------- Update documents tests */

    @Test
    public void test_whenUpdateADocument_withValidDetails_thenUpdateDocument() {
        String oldTitle = "presharing";
        String oldAuthor = "Rodrick Weimer";
        String oldContent = "1";
        String user = "foo@bar.com";
        Document oldDocument = new Document(oldTitle, oldAuthor, oldContent, user);

        String newTitle = "This is a new title";
        String newAuthor = "This is a new author";
        String newContent = "This is a new content";
        Document newDocument = new Document(newTitle, newAuthor, newContent, user);

        daoDocuments.updateExistingDocument(c, oldDocument, newDocument);
        DocumentsCollection documentsCollection = daoDocuments.getAllDocuments(c);
        Document expectedUpdatedDocument = documentsCollection.getDocuments().get(0);
        assertTrue(newDocument.equals(expectedUpdatedDocument));
    }

    /*--------------- Delete documents tests */

    @Test
    public void test_whenDeleteADocument_withValidDetails_thenDeleteDocument() {
        String title = "presharing";
        String author = "Rodrick Weimer";
        String content = "1";
        Document document = new Document(title, author, content);

        daoDocuments.deleteExistingDocument(c, document);
        DocumentsCollection documentsCollection = daoDocuments.getAllDocuments(c);
        assertTrue(documentsCollection.getDocuments().size() == 9);
    }

}
