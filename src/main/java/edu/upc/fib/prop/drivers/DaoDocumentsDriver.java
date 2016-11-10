package edu.upc.fib.prop.drivers;

import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.persistence.dao.documents.DaoDocuments;
import edu.upc.fib.prop.persistence.dao.documents.impl.DaoDocumentsImpl;
import edu.upc.fib.prop.utils.Constants;
import edu.upc.fib.prop.utils.FileUtils;
import edu.upc.fib.prop.utils.IOUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoDocumentsDriver {

    private static DaoDocuments daoDocuments = new DaoDocumentsImpl();

    private static Connection c;

    private static void printResult(String s) {
        System.out.println("\n---- " + s + " ----\n");
    }

    //TODO: Refactor to utils class
    private static void createConnection() {
        try {
            Class.forName(Constants.JDBC_DRIVER);
            c = DriverManager.getConnection(Constants.DB_DRIVERS);
            Statement statement = c.createStatement();
            String sql = FileUtils.readFile("src/main/resources/sql/dbInitializer.sql");
            statement.executeUpdate(sql);
            statement.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeConnectionAndRemoveDB() {
        File f = new File("drivers.db");
        f.delete();
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void testCreateDocument() {
        printResult("Introduce data to insert a new document");
        String title = IOUtils.askForString("Title");
        String author = IOUtils.askForString("Author");
        String owner = IOUtils.askForString("Owner");
        Document document = new Document(title, author, null, owner);
        try {
            daoDocuments.addNewDocument(c, document);
            printResult("Author created in the DB");
        } catch (AlreadyExistingDocumentException e) {
            printResult("Document already exists in the system");
        }
    }

    private static void testGetAllDocuments() {
        DocumentsCollection documentsCollection = daoDocuments.getAllDocuments(c);
        if (documentsCollection.getDocuments().isEmpty()) {
            printResult("There are no documents in the system");
        } else {
            printResult("All documents");
            for (Document document : documentsCollection.getDocuments()) {
                System.out.println(
                        "- " + document.getTitle() + " | " + document.getAuthor() + " | " + document.getUser());
            }
        }
    }

    private static void testUpdateDocument() {

    }

    private static void testUpdateDocumentWithEmail() {

    }

    private static void testDeleteDocumentWithTitleAndAuthor() {

    }

    private static void testDeleteDocumentWithUser() {

    }

    public static void main(String[] args) {
        createConnection();
        do {
            printResult("Select test to perform");
            System.out.println("1- Create document");
            System.out.println("2- Get all documents");
            System.out.println("3- Update document");
            System.out.println("4- Update document when change owner email");
            System.out.println("5- Delete documents by title-author");
            System.out.println("6- Delete documents by owner");
            System.out.println("0- Exit");
            int option = IOUtils.askForInt("Select an option", 0, 3);
            switch (option) {
                case 1:
                    testCreateDocument();
                    break;
                case 2:
                    testGetAllDocuments();
                    break;
                case 3:
                    testUpdateDocument();
                    break;
                case 4:
                    testUpdateDocumentWithEmail();
                    break;
                case 5:
                    testDeleteDocumentWithTitleAndAuthor();
                    break;
                case 6:
                    testDeleteDocumentWithUser();
                    break;
                default:
                    closeConnectionAndRemoveDB();
                    return;
            }
            IOUtils.enterToContinue();
        } while(true);
    }

}
