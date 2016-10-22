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

public class PersistenceControllerImpl implements PersistenceController {

    private DaoUsers daoUsers;
    private DaoAuthors daoAuthors;
    private DaoDocuments daoDocuments;

    private Connection c;

    public PersistenceControllerImpl() {
        System.out.println("Initializing persistence controller");
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

}
