package edu.upc.fib.prop.business.authentication;

import edu.upc.fib.prop.business.models.User;

public interface AccountManager {

    Boolean register(String email, String name, String password, String password2);

    Boolean login(String email, String password);

    User getUser();

    void setUser(User user);

    void editAccount();

    void deleteAccount();

    void logout();

}
