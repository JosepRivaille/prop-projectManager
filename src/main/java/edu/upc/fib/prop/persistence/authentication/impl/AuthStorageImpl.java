package edu.upc.fib.prop.persistence.authentication.impl;

import edu.upc.fib.prop.business.models.User;
import edu.upc.fib.prop.exceptions.AuthStorageException;
import edu.upc.fib.prop.persistence.authentication.AuthStorage;

public class AuthStorageImpl implements AuthStorage {

    @Override
    public boolean checkDetails(String email, String hashPassword) {
        return false;
    }

    @Override
    public void registerNewUser(User user) throws AuthStorageException {

    }

    @Override
    public User getUserFromEmail(String email) throws AuthStorageException {
        return null;
    }

    @Override
    public void updateExistingUser() {

    }

    @Override
    public void deleteUser(User user) {

    }
}
