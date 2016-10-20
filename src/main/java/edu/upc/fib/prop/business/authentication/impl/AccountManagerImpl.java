package edu.upc.fib.prop.business.authentication.impl;

import edu.upc.fib.prop.business.authentication.AccountManager;
import edu.upc.fib.prop.business.models.User;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.persistence.authentication.AuthStorage;
import edu.upc.fib.prop.persistence.authentication.impl.AuthStorageImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountManagerImpl implements AccountManager {

    private User currentUser;
    private AuthStorage authStorage;

    public AccountManagerImpl(User currentUser, String dbName) {
        this.currentUser = currentUser;
        try {
            if (dbName != null) {
                Connection c = DriverManager.getConnection(dbName);
                this.authStorage = new AuthStorageImpl(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public boolean register(String email, String name, String password, String password2) {
        try {
            authStorage.checkDetails(email, password);
        } catch (UserNotFoundException e) {
            if (password.equals(password2)) {
                try {
                    String pwd = hashPassword(password);
                    User user = new User(email, name, pwd);
                    authStorage.registerNewUser(user);
                    return true;
                } catch (SQLException | NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException | InvalidDetailsException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean login(String email, String password) {
        try {
            String pwd = hashPassword(password);
            setCurrentUser(authStorage.checkDetails(email, pwd));
            return true;
        } catch (UserNotFoundException | InvalidDetailsException | NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editAccount(String newEmail, String newName, String newPassword) {
        if (newEmail.equals("") || newName.equals("") || newPassword.equals("")) {
            return false;
        }
        User updatedUser = new User(newEmail, newName, newPassword);
        try {
            authStorage.updateUser(getCurrentUser().getEmail(), updatedUser);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAccount() {
        try {
            authStorage.deleteUser(getCurrentUser());
            setCurrentUser(null);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public void logout() {
        setCurrentUser(null);
    }

    //Hash password with SHA-256
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
