package edu.upc.fib.prop.persistence.dao.users.impl;

import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.models.User;
import edu.upc.fib.prop.persistence.dao.users.DaoUsers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoUsersImpl implements DaoUsers {

    private Connection c;

    public DaoUsersImpl(Connection c) {
        this.c = c;
    }

    @Override
    public void registerNewUser(User user) throws SQLException {
        Statement statement = c.createStatement();
        String sql = "INSERT INTO users VALUES ('" +
                user.getEmail() + "', '" + user.getName() + "', '" + user.getPassword() + "', 0);";
        statement.executeUpdate(sql);
        statement.close();
    }

    @Override
    public User checkDetails(String email, String password) throws UserNotFoundException, SQLException, InvalidDetailsException {
        Statement statement = c.createStatement();
        String sql = "SELECT * FROM users WHERE email = '" + email + "';";
        ResultSet rs = statement.executeQuery(sql);
        if(rs.next()) {
            String authEmail = rs.getString("email");
            String authName = rs.getString("user_name");
            String authPassword = rs.getString("password");
            if (authEmail.equals(email) && authPassword.equals(password)) {
                return new User(authEmail, authName, authPassword);
            } else {
                throw new InvalidDetailsException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void updateUser(String oldEmail, User updatedUser) throws SQLException {
        Statement statement = c.createStatement();
        String sql =
                "UPDATE users " +
                        "SET email = '" + updatedUser.getEmail() + "', user_name = '" + updatedUser.getName() + "', password = '" + updatedUser.getPassword() + "' " +
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