package edu.upc.fib.prop.persistence.dao.users;

import edu.upc.fib.prop.exceptions.AlreadyExistingUserException;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.models.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface DaoUsers {

    /**
     * Creates a user in persistence.
     * @param c DB connection.
     * @param user User to register.
     */
    void registerNewUser(Connection c, User user) throws SQLException;

    /**
     * Checks email - password combination to login an user.
     * @param c DB connection.
     * @param email Email of the account.
     * @param password Hashed password from the one that the user has given.
     * @return User that matches the details.
     */
    User checkDetails(Connection c, String email, String password)
            throws UserNotFoundException, InvalidDetailsException, SQLException;

    /**
     * Updates an user with new details.
     * @param c DB connection.
     * @param oldEmail Already existing account old email.
     * @param updatedUser User details to update.
     */
    void updateUser(Connection c, String oldEmail, User updatedUser)
            throws UserNotFoundException, AlreadyExistingUserException;

    /**
     * Deletes an user in persistence.
     * @param c DB connection
     * @param user Already existing user to delete.
     */
    void deleteUser(Connection c, User user) throws UserNotFoundException, SQLException;

}