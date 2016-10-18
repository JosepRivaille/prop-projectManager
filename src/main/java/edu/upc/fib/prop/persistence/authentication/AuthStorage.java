package edu.upc.fib.prop.persistence.authentication;

import edu.upc.fib.prop.business.models.User;
import edu.upc.fib.prop.exceptions.AuthStorageException;

public interface AuthStorage {

    //Auth
    boolean checkDetails(String email, String hashPassword);

    //Create
    void registerNewUser(User user) throws AuthStorageException;

    //Read
    User getUserFromEmail(String email) throws AuthStorageException;

    //Update
    void updateExistingUser();

    //Delete
    void deleteUser(User user);

}
