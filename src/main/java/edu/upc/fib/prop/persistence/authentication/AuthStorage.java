package edu.upc.fib.prop.persistence.authentication;

import edu.upc.fib.prop.models.User;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.UserNotFoundException;

import java.sql.SQLException;

public interface AuthStorage {

    //Read
    User checkDetails(String email, String hashPassword) throws UserNotFoundException, SQLException, InvalidDetailsException;

    //Create
    void registerNewUser(User user) throws SQLException;

    //Update
    void updateUser(String oldEmail, User user) throws SQLException;

    //Delete
    void deleteUser(User user) throws SQLException;

}
