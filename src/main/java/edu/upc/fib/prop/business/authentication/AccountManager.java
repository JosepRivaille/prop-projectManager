package edu.upc.fib.prop.business.authentication;

import edu.upc.fib.prop.business.models.User;

public interface AccountManager {

    boolean register(String email, String name, String password, String password2);

    Boolean login(String email, String password);

    User getCurrentUser();

    void setCurrentUser(User currentUser);

    boolean editAccount(String newEmail, String newName, String newPassword);

    boolean deleteAccount();

    void logout();

}
