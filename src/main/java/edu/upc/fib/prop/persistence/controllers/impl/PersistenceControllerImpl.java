package edu.upc.fib.prop.persistence.controllers.impl;

import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.persistence.controllers.PersistenceController;
import edu.upc.fib.prop.persistence.dao.authors.DaoAuthors;
import edu.upc.fib.prop.persistence.dao.documents.DaoDocuments;
import edu.upc.fib.prop.persistence.dao.users.DaoUsers;
import edu.upc.fib.prop.utils.Constants;

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
            sql =   "CREATE TABLE IF NOT EXISTS users (" +
                    "email VARCHAR PRIMARY KEY, " +
                    "username VARCHAR NOT NULL, " +
                    "password VARCHAR NOT NULL, " +
                    "admin BOOLEAN NOT NULL);";
            statement.executeUpdate(sql);
            statement.close();

            statement = c.createStatement();
            sql =   "CREATE TABLE IF NOT EXISTS authors (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom VARCHAR NOT NULL);";
            statement.executeUpdate(sql);
            statement.close();

            statement = c.createStatement();
            sql =   "CREATE TABLE IF NOT EXISTS documents (" +
                    "title VARCHAR PRIMARY KEY, " +
                    "author VARCHAR NOT NULL, " +
                    "content VARCHAR NOT NULL);";
            statement.executeUpdate(sql);
            statement.close();

            c.commit();
            System.out.println("DB initialized successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
    }

}
