package edu.upc.fib.prop.business.authentication.impl;

import edu.upc.fib.prop.business.authentication.AccountManager;
import edu.upc.fib.prop.business.models.User;
import edu.upc.fib.prop.exceptions.AuthStorageException;
import edu.upc.fib.prop.persistence.authentication.AuthStorage;
import edu.upc.fib.prop.persistence.authentication.impl.AuthStorageImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AccountManagerImpl implements AccountManager {

    private User user;

    public AccountManagerImpl(User user) {
        this.user = user;
    }

    @Override
    public Boolean register(String email, String name, String password, String password2) {
        if (password.equals(password2)) {
            try {
                String pwd = hashPassword(password);
                User user = new User(email, name, pwd);
                AuthStorage authStorage = new AuthStorageImpl();
                authStorage.registerNewUser(user);
                return true;
            } catch (NoSuchAlgorithmException | AuthStorageException e) {
                return false;
            }
        }
        // If passwords don't match
        return false;
    }

    @Override
    public Boolean login(String email, String password) {
        AuthStorage authStorage = new AuthStorageImpl();
        try {
            String pwd = hashPassword(password);
            boolean correctUser = authStorage.checkDetails(email, pwd);
            if (correctUser) {
                setUser(authStorage.getUserFromEmail(email));
            } return correctUser;
        } catch (NoSuchAlgorithmException | AuthStorageException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void editAccount() {
        AuthStorage authStorage = new AuthStorageImpl();
        authStorage.updateExistingUser(getUser());
    }

    @Override
    public void deleteAccount() {
        AuthStorage authStorage = new AuthStorageImpl();
        authStorage.deleteUser(getUser());
    }

    @Override
    public void logout() {
        setUser(null);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte byteData[] = md.digest();

        //Convert byte to hex format
        StringBuilder sb = new StringBuilder();
        for (byte aByteData : byteData) {
            sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
        } return sb.toString();
    }

}
