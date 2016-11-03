package edu.upc.fib.prop.persistence.dao.users.impl;

import edu.upc.fib.prop.exceptions.AlreadyExistingUserException;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.models.User;
import edu.upc.fib.prop.persistence.dao.users.DaoUsers;
import edu.upc.fib.prop.utils.Constants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoUsersImpl implements DaoUsers {

    @Override
    public void registerNewUser(Connection c, User user) throws SQLException {
        Statement statement = c.createStatement();
        String sql = "INSERT INTO users VALUES ('" +
                user.getEmail() + "', '" + user.getName() + "', '" + user.getPassword() + "', 0);";
        int inserted = statement.executeUpdate(sql);
        if (inserted != 1)
            throw new SQLException();
        statement.close();
    }

    @Override
    public User checkDetails(Connection c, String email, String password) throws UserNotFoundException, InvalidDetailsException, SQLException {
        Statement statement = c.createStatement();
        String sql = "SELECT * FROM users WHERE email = '" + email + "';";
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()) {
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
    public void updateUser(Connection c, String oldEmail, User updatedUser)
            throws UserNotFoundException, AlreadyExistingUserException {
        Statement statement = null;
        try {
            statement = c.createStatement();
            String sql =
                    "UPDATE users SET email = '" + updatedUser.getEmail() + "', user_name = '" + updatedUser.getName() +
                            "', password = '" + updatedUser.getPassword() + "' WHERE email = '" + oldEmail + "';";
            int updated = statement.executeUpdate(sql);
            if (updated != 1) {
                throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            throw new AlreadyExistingUserException();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteUser(Connection c, User user) throws UserNotFoundException, SQLException {
        Statement statement = c.createStatement();
        String sql = "DELETE FROM users WHERE email = '" + user.getEmail() + "';";
        int deleted = statement.executeUpdate(sql);
        if (deleted != 1)
            throw new UserNotFoundException();
        statement.close();
    }
}
