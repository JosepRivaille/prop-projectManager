package edu.upc.fib.prop.persistence.authentication;

import edu.upc.fib.prop.business.models.User;
import edu.upc.fib.prop.exceptions.UserNotFoundException;

import java.sql.SQLException;

public interface AuthStorage {

    //Auth
    boolean checkDetails(String email, String hashPassword) throws UserNotFoundException, SQLException;

    //Create
    void registerNewUser(User user) throws SQLException;

    //Read
    User getUserFromEmail(String email) throws UserNotFoundException, SQLException;

    //Update
    void updateUser(String oldEmail, User user) throws SQLException;

    //Delete
    void deleteUser(User user) throws SQLException;

}
