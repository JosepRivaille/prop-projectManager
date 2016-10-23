package edu.upc.fib.prop.persistence.controllers.impl;

import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.DocumentsCollection;
import edu.upc.fib.prop.persistence.controllers.PersistenceController;
import edu.upc.fib.prop.persistence.dao.authors.DaoAuthors;
import edu.upc.fib.prop.persistence.dao.documents.DaoDocuments;
import edu.upc.fib.prop.persistence.dao.users.DaoUsers;
import edu.upc.fib.prop.utils.Constants;
import edu.upc.fib.prop.utils.FileUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PersistenceControllerImpl implements PersistenceController {

    private DaoUsers daoUsers;
    private DaoAuthors daoAuthors;
    private DaoDocuments daoDocuments;

    private Connection c;

    public PersistenceControllerImpl() {
        System.out.println("Initializing persistence controller");
        initializeDB();
        daoUsers = new DaoUsers();
        daoAuthors = new DaoAuthors();
        daoDocuments = new DaoDocuments();
    }

    @Override
    public AuthorsCollection getAuthors() {
        openConnection();
        AuthorsCollection authorsCollection = daoAuthors.getAllAuthors(this.c);
        closeConnection();
        return authorsCollection;
    }

    @Override
    public DocumentsCollection getDocuments() {
        openConnection();
        DocumentsCollection documentsCollection = daoDocuments.getAllDocuments(this.c);
        closeConnection();
        return documentsCollection;
    }

    /* Private helper methods */

    private void openConnection() {
        try {
            Class.forName(Constants.JDBC_DRIVER);
            this.c = DriverManager.getConnection(Constants.DB_DEVELOPMENT);
            this.c.setAutoCommit(false);
            System.out.println("Opened database connection successfully");
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

        Statement statement;
        String sql;
        try {
            statement = c.createStatement();
            sql = FileUtils.readFile("src/main/resources/dbInitializer.sql");
            statement.executeUpdate(sql);
            statement.close();

            c.commit();
            System.out.println("DB initialized successfully");

            /*
            statement = c.createStatement();
            sql = FileUtils.readFile("src/main/resources/dbFiller.sql");
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
