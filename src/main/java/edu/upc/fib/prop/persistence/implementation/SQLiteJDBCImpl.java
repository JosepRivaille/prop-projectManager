package edu.upc.fib.prop.persistence.implementation;

import edu.upc.fib.prop.persistence.SQLiteJDBC;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteJDBCImpl implements SQLiteJDBC {

    private Connection connection = null;

    public SQLiteJDBCImpl(String connectionName) {
        setConnection(connectionName);
    }

    public Connection getConnection() {
        return this.connection;
    }

    private void setConnection(String connectionName) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(connectionName);
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

}
