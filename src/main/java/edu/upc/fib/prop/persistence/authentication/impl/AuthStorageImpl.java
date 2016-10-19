package edu.upc.fib.prop.persistence.authentication.impl;

import edu.upc.fib.prop.business.models.User;
import edu.upc.fib.prop.exceptions.AuthStorageException;
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
    public boolean checkDetails(String email, String password) throws AuthStorageException {
        try {
            Statement statement = c.createStatement();
            String sql = "SELECT email, password FROM users WHERE email = '" + email + "';";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()) {
                if (rs.getString("email").equals(email) && rs.getString("password").equals(password)) {
                    return true;
                }
            } else {
                throw new AuthStorageException("User doesn't exist");
            }
        } catch (SQLException e) {
            throw new AuthStorageException("DB error");
        }
        return false;
    }

    @Override
    public void registerNewUser(User user) throws AuthStorageException {
        try {
            Statement statement = c.createStatement();
            String sql = "SELECT * FROM users WHERE email = '" + user.getEmail() + "';";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()) {
                throw new AuthStorageException("Email already registered");
            } else {
                sql =
                    "INSERT INTO users VALUES ('" +
                    user.getEmail() + "', '" + user.getName() + "', '" + user.getPassword() + "');";
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new AuthStorageException("DB ERROR -> " + e.getMessage());
        }
    }

    @Override
    public User getUserFromEmail(String email) throws AuthStorageException {
        try {
            Statement statement = c.createStatement();
            String sql = "SELECT * FROM users WHERE email = '" + email + "';";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()) {
                return new User(rs.getString("email"), rs.getString("name"), rs.getString("password"));
            } else {
                throw new AuthStorageException("User doesn't exist");
            }
        } catch (SQLException e) {
            throw new AuthStorageException("DB error");
        }
    }

    @Override
    public User updateUser(User user) throws AuthStorageException {
        return null;
    }

    @Override
    public void deleteUser(User user) throws AuthStorageException {

    }
}
