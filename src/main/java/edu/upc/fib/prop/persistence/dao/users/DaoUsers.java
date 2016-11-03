package edu.upc.fib.prop.persistence.dao.users;

import edu.upc.fib.prop.exceptions.AlreadyExistingUserException;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.models.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface DaoUsers {

    void registerNewUser(Connection c, User user) throws SQLException;

    User checkDetails(Connection c, String email, String password)
            throws UserNotFoundException, InvalidDetailsException, SQLException;

    void updateUser(Connection c, String oldEmail, User updatedUser)
            throws UserNotFoundException, AlreadyExistingUserException;

    void deleteUser(Connection c, User user) throws UserNotFoundException, SQLException;

}
