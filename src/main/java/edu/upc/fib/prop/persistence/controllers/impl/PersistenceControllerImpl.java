package edu.upc.fib.prop.persistence.controllers.impl;

import edu.upc.fib.prop.exceptions.AlreadyExistingUserException;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.models.User;
import edu.upc.fib.prop.persistence.controllers.PersistenceController;
import edu.upc.fib.prop.persistence.dao.authors.DaoAuthors;
import edu.upc.fib.prop.persistence.dao.authors.impl.DaoAuthorsImpl;
import edu.upc.fib.prop.persistence.dao.documents.impl.DaoDocumentsImpl;
import edu.upc.fib.prop.persistence.dao.users.DaoUsers;
import edu.upc.fib.prop.persistence.dao.users.impl.DaoUsersImpl;
import edu.upc.fib.prop.utils.Constants;
import edu.upc.fib.prop.utils.FileUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class PersistenceControllerImpl implements PersistenceController {

    private DaoAuthors daoAuthors;
    private DaoDocumentsImpl daoDocuments;
    private DaoUsers daoUsers;

    private Connection c;

    public PersistenceControllerImpl() {
        System.out.println("Initializing persistence controller");
        initializeDB();
        daoAuthors = new DaoAuthorsImpl(c);
        daoDocuments = new DaoDocumentsImpl(c);
        daoUsers = new DaoUsersImpl(c);
    }

    @Override
    public AuthorsCollection getAuthors() {
        openConnection();
        AuthorsCollection authorsCollection = daoAuthors.getAllAuthors();
        closeConnection();
        return authorsCollection;
    }

    @Override
    public DocumentsCollection getDocuments() {
        openConnection();
        DocumentsCollection documentsCollection = daoDocuments.getAllDocuments();
        closeConnection();
        return documentsCollection;
    }

    @Override
    public Set<String> getExcludedWords(String lang) {
        return FileUtils.getExcludedWords(lang);
    }

    @Override
    public void writeNewDocument(Document document) throws AlreadyExistingDocumentException {
        daoDocuments.addNewDocument(document);
    }

    @Override
    public void createUser(User user) throws AlreadyExistingUserException {
        try {
            daoUsers.registerNewUser(user);
        } catch (SQLException e) {
            throw new AlreadyExistingUserException();
        }
    }

    @Override
    public User loginUser(String email, String password) throws UserNotFoundException, InvalidDetailsException {
        try {
            return daoUsers.checkDetails(email, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* Private helper methods */

    private void openConnection() {
        try {
            Class.forName(Constants.JDBC_DRIVER);
            this.c = DriverManager.getConnection(Constants.DB_DEVELOPMENT);
            this.c.setAutoCommit(false);
            System.out.println("Opened database connection...");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            this.c.close();
            System.out.println("Closed database connection...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeDB() {
        openConnection();

        Statement statement;
        String sql;
        try {
            statement = c.createStatement();
            sql = FileUtils.readFile("src/main/resources/sql/dbInitializer.sql");
            statement.executeUpdate(sql);
            statement.close();

            c.commit();
            System.out.println("DB initialized successfully");

            /*
            statement = c.createStatement();
            sql = FileUtils.readFile("src/main/resources/sql/dbFiller.sql");
            statement.executeUpdate(sql);
            statement.close();

            c.commit();
            System.out.println("DB filled successfully");
            */
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
    }

}
