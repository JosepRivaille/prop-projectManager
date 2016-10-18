package edu.upc.fib.prop.business.authentication;

public interface AccountManager {

    Boolean register(String email, String name, String password, String password2);

    Boolean login(String email, String password);

    void editAccount();

    void deleteAccount();

    void logout();

}
