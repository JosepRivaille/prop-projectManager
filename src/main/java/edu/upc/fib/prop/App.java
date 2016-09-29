package edu.upc.fib.prop;

import edu.upc.fib.prop.persistence.implementation.SQLiteJDBCImpl;

import java.sql.SQLException;
import java.sql.Statement;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        SQLiteJDBCImpl database = new SQLiteJDBCImpl("jdbc:sqlite:development.db");
        try {
            Statement stmt = null;
            String sql =
                    "CREATE TABLE USER " +
                    "(EMAIL     CHAR(50)    PRIMARY KEY     NOT NULL," +
                    " NAME      CHAR(50)    NOT NULL, " +
                    " PASSWORD  CHAR(50)    NOT NULL)";
            stmt = database.getConnection().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            database.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
