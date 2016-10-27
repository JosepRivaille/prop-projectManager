package edu.upc.fib.prop.test.unit.business;

import edu.upc.fib.prop.business.users.UsersManager;
import edu.upc.fib.prop.business.users.impl.UsersManagerImpl;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import org.junit.BeforeClass;
import org.junit.Test;

public class UsersTest {

    private static UsersManager usersManager;

    @BeforeClass
    public static void setup() {
        usersManager = new UsersManagerImpl();
    }

    /*-------------------- Register tests */

    @Test(expected = InvalidDetailsException.class)
    public void test_whenRegisterUser_withInvalidFields_thenDoNotRegister() throws InvalidDetailsException {
        String email = "admin@fib.upc.edu";
        String name = "";
        String password = "123456";
        String password2 = "123456";

        usersManager.register(email, name, password, password2);
    }

    @Test(expected = InvalidDetailsException.class)
    public void test_whenRegisterUser_withNonMatchingPasswords_thenDoNotRegister() throws InvalidDetailsException {
        String email = "admin@fib.upc.edu";
        String name = "Admin";
        String password = "123456";
        String password2 = "654321";

        usersManager.register(email, name, password, password2);
    }

    @Test
    public void test_whenRegisterUser_withValidDetails_thenRegister() throws InvalidDetailsException {
        String email = "admin@fib.upc.edu";
        String name = "Admin";
        String password = "123456";
        String password2 = "123456";

        usersManager.register(email, name, password, password2);
    }

    /*-------------------- Login tests */

    @Test(expected = InvalidDetailsException.class)
    public void test_whenLogin_withInvalidDetails_thenDoNotLogin() throws InvalidDetailsException {
        String email = "thisIsNotAnEmail";
        String password = "123456";

        usersManager.login(email, password);
    }

    @Test
    public void test_whenLogin_withValidDetails_thenLogin() throws InvalidDetailsException {
        String email = "admin@fib.upc.edu";
        String password = "123456";

        usersManager.login(email, password);
    }

    /*-------------------- Edit tests */

    @Test(expected = InvalidDetailsException.class)
    public void test_whenUpdateUser_withInvalidDetails_thenDoNotLogin() throws InvalidDetailsException {
        String newEmail = "thisIsNotAnEmail";
        String newName = "This";
        String newPassword = "123456";

        usersManager.editAccount(newEmail, newName, newPassword);
    }

    @Test
    public void test_whenUpdateUser_withValidDetails_thenLogin() throws InvalidDetailsException {
        String newEmail = "bar@foo.com";
        String newName = "This";
        String newPassword = "123456";

        usersManager.editAccount(newEmail, newName, newPassword);
    }

}
