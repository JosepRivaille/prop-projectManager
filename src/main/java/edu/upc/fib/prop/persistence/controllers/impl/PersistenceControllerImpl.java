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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

    //////////

    private void openConnection() {
        try {
            Class.forName(Constants.JDBC_DRIVER);
            this.c = DriverManager.getConnection(Constants.DB_DEVELOPMENT);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            this.c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeDB() {
        openConnection();

        try {
            Statement statement = c.createStatement();
            String sql = FileUtils.readFile("src/main/resources/sql/dbInitializer.sql");
            statement.executeUpdate(sql);
            statement.close();
            System.out.println(Constants.DB_DEVELOPMENT + " initialized successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
    }

    public void createContentFile(String content, String name){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("src/main/resources/documents/" + name, "UTF-8");
            writer.print(content);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void deleteContentFile(String name){
        File f = new File("src/main/resources/documents/" + name);
        f.delete();
    }

    @Override
    public void rateDocument(Document document, int rating, String user) throws DocumentNotFoundException {
            openConnection();
            daoDocuments.rateDocument(c, document, rating, user);
            closeConnection();
    }

    @Override
    public void addDocumentToFavourites(Document document, String user) throws DocumentNotFoundException {
        openConnection();
        daoDocuments.addDocumentToFavourites(c, document, user);
        closeConnection();
    }

    @Override
    public void deleteDocumentFromFavourites(Document document, String user) throws DocumentNotFoundException {
        openConnection();
        daoDocuments.deleteDocumentFromFavourites(c, document, user);
        closeConnection();
    }

    @Override
    public void deleteAllFavouritesOfDocument(Document document) {
        openConnection();
        daoDocuments.deleteAllFavouritesOfDocument(c, document);
        closeConnection();
    }


}
