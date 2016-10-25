package edu.upc.fib.prop.business.users.impl;

import edu.upc.fib.prop.business.users.UsersManager;
import edu.upc.fib.prop.models.User;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.utils.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UsersManagerImpl implements UsersManager {

    private User currentUser;

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public User register(String email, String name, String password, String password2) throws InvalidDetailsException {
        if (!email.equals("") && !email.equals("") && !password.equals("") && !password2.equals("")) {
            if (email.matches(Constants.EMAIL_REGEX)) {
                if (password.equals(password2)) {
                    try {
                        String pwd = hashPassword(password);
                        return new User(email, name, pwd);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        throw new InvalidDetailsException();
    }

    @Override
    public void login(String email, String password) throws InvalidDetailsException {
        if (email.equals("") || password.equals("") || email.matches(Constants.EMAIL_REGEX)) {
            throw new InvalidDetailsException();
        }
    }

    @Override
    public void editAccount(String newEmail, String newName, String newPassword) throws InvalidDetailsException {
        if (newEmail.equals("") || newName.equals("") || newPassword.equals("")) {
            throw new InvalidDetailsException();
        }
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
