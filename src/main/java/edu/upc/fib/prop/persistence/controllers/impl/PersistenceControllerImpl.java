package edu.upc.fib.prop.persistence.controllers.impl;

import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.models.User;
import edu.upc.fib.prop.persistence.controllers.PersistenceController;
import edu.upc.fib.prop.persistence.dao.authors.DaoAuthors;
import edu.upc.fib.prop.persistence.dao.authors.impl.DaoAuthorsImpl;
import edu.upc.fib.prop.persistence.dao.documents.DaoDocuments;
import edu.upc.fib.prop.persistence.dao.documents.impl.DaoDocumentsImpl;
import edu.upc.fib.prop.persistence.dao.users.DaoUsers;
import edu.upc.fib.prop.persistence.dao.users.impl.DaoUsersImpl;
import edu.upc.fib.prop.utils.Constants;
import edu.upc.fib.prop.utils.FileUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PersistenceControllerImpl implements PersistenceController {

    private DaoAuthors daoAuthors;
    private DaoDocuments daoDocuments;
    private DaoUsers daoUsers;

    private Connection c;

    public PersistenceControllerImpl() {
        System.out.println("Initializing persistence controller");
        initializeDB();
        daoAuthors = new DaoAuthorsImpl();
        daoDocuments = new DaoDocumentsImpl();
        daoUsers = new DaoUsersImpl();
    }

    @Override
    public AuthorsCollection getAuthors() {
        openConnection();
        AuthorsCollection authorsCollection = daoAuthors.getAllAuthors(c);
        closeConnection();
        return authorsCollection;
    }

    @Override
    public DocumentsCollection getDocuments() {
        openConnection();
        DocumentsCollection documentsCollection = daoDocuments.getAllDocuments(c);
        closeConnection();
        return documentsCollection;
    }

    @Override
    public void writeNewDocument(Document document) throws AlreadyExistingDocumentException, SQLException {
        openConnection();
        daoDocuments.addNewDocument(c, document);
        try {
            daoAuthors.getAuthorByName(c, document.getAuthor());
        } catch (AuthorNotFoundException e) {
            daoAuthors.createAuthor(c, document.getAuthor());
        }
        closeConnection();
    }

    @Override
    public void createUser(User user) throws AlreadyExistingUserException {
        try {
            openConnection();
            daoUsers.registerNewUser(c, user);
            closeConnection();
        } catch (SQLException e) {
            throw new AlreadyExistingUserException();
        }
    }

    @Override
    public User loginUser(String email, String password) throws UserNotFoundException, InvalidDetailsException {
        try {
            openConnection();
            User user = daoUsers.checkDetails(c, email, password);
            closeConnection();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateUser(User currentUser, User newUser) throws UserNotFoundException, AlreadyExistingUserException {
        openConnection();
        daoUsers.updateUser(c, currentUser.getEmail(), newUser);
        if (!currentUser.getEmail().equals(newUser.getEmail())) {
            daoDocuments.updateDocumentOwner(c, currentUser.getEmail(), newUser.getEmail());
        }
        closeConnection();
    }

    @Override
    public void deleteUser(User user) throws UserNotFoundException {
        try {
            openConnection();
            daoUsers.deleteUser(c, user);
            daoDocuments.deleteDocuments(c, user.getEmail());
            closeConnection();
        } catch (UserNotFoundException | SQLException e) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteDocument(Document document) {
        openConnection();
        daoDocuments.deleteExistingDocument(c, document);
        daoAuthors.deleteIfNoDocuments(c, document.getAuthor());
        closeConnection();
    }

    @Override
    public void updateDocument(Document oldDocument, Document newDocument) {
        openConnection();
        daoDocuments.updateExistingDocument(c, oldDocument, newDocument);
        daoAuthors.deleteIfNoDocuments(c, oldDocument.getAuthor());
        if(!daoAuthors.existsAuthor(c, newDocument.getAuthor())) try {
            daoAuthors.createAuthor(c, newDocument.getAuthor());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    /* Private helper methods */

    private void openConnection() {
        try {
            Class.forName(Constants.JDBC_DRIVER);
            this.c = DriverManager.getConnection(Constants.DB_DEVELOPMENT);
            //System.out.println("Opened database connection...");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            this.c.close();
            //System.out.println("Closed database connection...");
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

            System.out.println(Constants.DB_DEVELOPMENT + " initialized successfully");
//
//            statement = c.createStatement();
//            sql = FileUtils.readFile("src/main/resources/sql/mockData.sql");
//            statement.executeUpdate(sql);
//            statement.close();
//
//            System.out.println("DB filled successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
    }

}
