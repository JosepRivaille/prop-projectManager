package edu.upc.fib.prop.persistence.authentication.impl;

import edu.upc.fib.prop.business.models.User;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.persistence.authentication.AuthStorage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthStorageImpl implements AuthStorage {

    private Connection c;

    public AuthStorageImpl(Connection c) {
        this.c = c;
    }

    @Override
    public boolean checkDetails(String email, String password) throws UserNotFoundException, SQLException {
        Statement statement = c.createStatement();
        String sql = "SELECT email, password FROM users WHERE email = '" + email + "';";
        ResultSet rs = statement.executeQuery(sql);
        if(rs.next()) {
            if (rs.getString("email").equals(email) && rs.getString("password").equals(password)) {
                return true;
            }
        } else {
            throw new UserNotFoundException();
        }
        return false;
    }

    @Override
    public void registerNewUser(User user) throws SQLException {
        Statement statement = c.createStatement();
        String sql = "INSERT INTO users VALUES ('" +
                user.getEmail() + "', '" + user.getName() + "', '" + user.getPassword() + "');";
        statement.executeUpdate(sql);
        statement.close();
    }

    @Override
    public User getUserFromEmail(String email) throws UserNotFoundException, SQLException {
        Statement statement = c.createStatement();
        String sql = "SELECT * FROM users";
        ResultSet rs = statement.executeQuery(sql);
        statement.close();
        if(rs.next()) {
            return new User(rs.getString("email"), rs.getString("name"), rs.getString("password"));
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void updateUser(String oldEmail, User updatedUser) throws SQLException {
        Statement statement = c.createStatement();
        String sql =
                "UPDATE users " +
                "SET email = '" + updatedUser.getEmail() + "', name = '" + updatedUser.getName() + "', password = '" + updatedUser.getPassword() + "' " +
                "WHERE email = '" + oldEmail + "';";
        statement.executeUpdate(sql);
        statement.close();
    }

    @Override
    public void deleteUser(User user) throws SQLException {
        Statement statement = c.createStatement();
        String sql = "DELETE FROM users WHERE email = '" + user.getEmail() + "';";
        statement.executeUpdate(sql);
        statement.close();
    }
}
