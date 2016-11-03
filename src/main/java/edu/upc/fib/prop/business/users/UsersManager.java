package edu.upc.fib.prop.business.users;

import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.models.User;

public interface UsersManager {

    User register(String email, String name, String password, String password2) throws InvalidDetailsException;

    String login(String email, String password) throws InvalidDetailsException;

    User getCurrentUser();

    void setCurrentUser(User currentUser);

    void editAccount(String newEmail, String newName, String newPassword) throws InvalidDetailsException;

    void logout();

}
