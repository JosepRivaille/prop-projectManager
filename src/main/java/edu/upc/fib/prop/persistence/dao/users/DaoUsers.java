package edu.upc.fib.prop.persistence.dao.users;

import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.models.User;

import java.sql.SQLException;

public interface DaoUsers {

    void registerNewUser(User user) throws SQLException;

    User checkDetails(String email, String password) throws UserNotFoundException, SQLException, InvalidDetailsException;

    void updateUser(String oldEmail, User updatedUser) throws SQLException;

    void deleteUser(User user) throws SQLException;

}
