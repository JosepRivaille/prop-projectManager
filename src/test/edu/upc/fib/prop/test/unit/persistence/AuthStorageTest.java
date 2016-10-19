package edu.upc.fib.prop.test.unit.persistence;

import edu.upc.fib.prop.business.models.User;
import edu.upc.fib.prop.exceptions.AuthStorageException;
import edu.upc.fib.prop.persistence.authentication.AuthStorage;
import edu.upc.fib.prop.persistence.authentication.impl.AuthStorageImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Ignore("NOT WORKING YET")
@RunWith(MockitoJUnitRunner.class)
public class AuthStorageTest {

    private Connection c;
    private AuthStorage authStorage;

    @Before
    public void setup() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            authStorage = new AuthStorageImpl(c);

            Statement statement = c.createStatement();
            String sql = "DROP TABLE IF EXISTS users";
            statement.execute(sql);
            sql =
                    "CREATE TABLE users(" +
                    "email      VARCHAR PRIMARY KEY," +
                    "name       VARCHAR," +
                    "password   VARCHAR);";
            statement.executeUpdate(sql);
            statement.close();
            c.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }

    @After
    public void hold() {
        try {
            Class.forName("org.sqlite.JDBC");
            Statement statement = c.createStatement();
            String sql = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(sql);
            statement.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /* USER MATCH TESTS */

    @Test(expected=AuthStorageException.class)
    public void test_whenCheckDetails_withUnexistingUserData_thenNotFoundException() throws AuthStorageException {
        String email = "foo@bar.com";
        String password = "123456";
        authStorage.checkDetails(email, password);
    }

    @Test
    public void test_whenCheckDetails_withNonMatchingDetails_thenReturnFalse() throws AuthStorageException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";
        String badPassword = "654321";

        User user = new User(email, name, password);
        authStorage.registerNewUser(user);

        assertFalse(authStorage.checkDetails(email, badPassword));
    }

    @Test
    public void test_whenCheckDetails_withMatchingDetails_thenReturnTrue() throws AuthStorageException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";
        String badPassword = "654321";

        User user = new User(email, name, password);
        authStorage.registerNewUser(user);

        assertTrue(authStorage.checkDetails(email, badPassword));
    }

    /* REGISTER TESTS */

    @Test(expected=AuthStorageException.class)
    public void test_whenRegister_withAlreadyExistingEmail_thenAlreadyExistingException() throws AuthStorageException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        User user = new User(email, name, password);
        authStorage.registerNewUser(user);
        assertTrue(authStorage.checkDetails(email, password));
        authStorage.registerNewUser(user);
    }

    @Test
    public void test_whenRegister_withCorrectData_thenRegisterNewUser() throws AuthStorageException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        User user = new User(email, name, password);
        authStorage.registerNewUser(user);
        assertTrue(authStorage.checkDetails(email, password));
    }

    /* GET DATA TESTS */

    @Test(expected=AuthStorageException.class)
    public void test_whenGetUserFromEmail_withNonExistingEmail_thenNotFoundException() throws AuthStorageException {
        String email = "foo@bar.com";

        authStorage.getUserFromEmail(email);
    }

    @Test
    public void test_whenGetUserFromEmail_withExistingEmail_thenReturnUser() throws AuthStorageException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        User user = new User(email, name, password);
        authStorage.registerNewUser(user);
        assertTrue(authStorage.checkDetails(email, password));

        assertTrue(authStorage.getUserFromEmail(email).equals(user));
    }

    /* UPDATE TESTS */

    @Test
    public void test_whenUpdateUser_withInvalidDetails_thenInvalidNewDetailsException() throws AuthStorageException {
        User user = createFakeUser();

        String newEmail = "";
        User updatedUser = new User(newEmail, user.getName(), user.getPassword());
        authStorage.updateUser(updatedUser);
        assertFalse(user.equals(updatedUser));
    }

    @Test
    public void test_whenUpdateUser_withValidDetails_thenReturnUser() throws AuthStorageException {
        User user = createFakeUser();

        String newEmail = "bar@foo.com";
        User updatedUser = authStorage.updateUser(new User(newEmail, user.getName(), user.getPassword()));
        assertFalse(user.equals(updatedUser));
    }

    /* DELETE TESTS */

    @Ignore("How to implement this case")
    @Test(expected=AuthStorageException.class)
    public void test_whenDeleteUser_withErrorsDeleting_thenUnableToDeleteException() {

    }

    @Test
    public void test_whenDeleteUser_withNoErrors_thenDeleteUserAccount() throws AuthStorageException {
        User user = createFakeUser();

        authStorage.deleteUser(user);
        authStorage.getUserFromEmail(user.getEmail());
    }

    private User createFakeUser() throws AuthStorageException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        User user = new User(email, name, password);
        authStorage.registerNewUser(user);
        assertTrue(authStorage.checkDetails(email, password));
        return user;
    }

}
