package edu.upc.fib.prop.test.unit.persistence;

import edu.upc.fib.prop.Constants;
import edu.upc.fib.prop.business.models.User;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.persistence.authentication.AuthStorage;
import edu.upc.fib.prop.persistence.authentication.impl.AuthStorageImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.sql.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AuthStorageTest {

    private static Connection c;
    private static AuthStorage authStorage;

    @BeforeClass
    public static void setUpTableAndDB() {
        try {
            Class.forName(Constants.JDBC_DRIVER);
            if (c == null) {
                c = DriverManager.getConnection(Constants.DB_TEST);
            }
            authStorage = new AuthStorageImpl(c);

            Statement statement = c.createStatement();
            String sql =
                    "CREATE TABLE users(" +
                    "email      VARCHAR PRIMARY KEY," +
                    "name       VARCHAR," +
                    "password   VARCHAR);";
            statement.executeUpdate(sql);
            statement.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    @AfterClass
    public static void dropDataBase() {
        File f = new File("test.db");
        boolean deleted = f.delete();
        System.out.println("File deleted: " + deleted);
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void dropTableUsers() {
        try {
            Statement statement = c.createStatement();
            String sql = "DELETE FROM users";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* REGISTER TESTS */

    @Ignore
    @Test
    public void test_whenRegister_withCorrectUser_thenRegisterNewUser() throws UserNotFoundException, SQLException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        User user = new User(email, name, password);
        authStorage.registerNewUser(user);
        assertTrue(user.equals(authStorage.getUserFromEmail(email)));
    }

    /* USER MATCH TESTS */

    @Test(expected=UserNotFoundException.class)
    public void test_whenCheckDetails_withNonExistingUserData_thenNotFoundException() throws UserNotFoundException, SQLException {
        String email = "foo@bar.com";
        String password = "123456";
        authStorage.checkDetails(email, password);
    }

    @Test
    public void test_whenCheckDetails_withNonMatchingDetails_thenReturnFalse() throws UserNotFoundException, SQLException {
        User user = createFakeUser();
        String badPassword = "654321";
        assertFalse(authStorage.checkDetails(user.getEmail(), badPassword));
    }

    @Test
    public void test_whenCheckDetails_withMatchingDetails_thenReturnTrue() throws UserNotFoundException, SQLException {
        User user = createFakeUser();
        assertTrue(authStorage.checkDetails(user.getEmail(), user.getPassword()));
    }

    /* GET DATA TESTS */

    @Test(expected=UserNotFoundException.class)
    public void test_whenGetUserFromEmail_withNonExistingEmail_thenNotFoundException() throws UserNotFoundException, SQLException {
        String email = "foo@bar.com";
        authStorage.getUserFromEmail(email);
    }

    @Ignore
    @Test
    public void test_whenGetUserFromEmail_withExistingEmail_thenReturnUser() throws UserNotFoundException, SQLException {
        User user = createFakeUser();
        assertTrue(authStorage.getUserFromEmail(user.getEmail()).equals(user));
    }

    /* UPDATE TESTS */

    @Ignore
    @Test
    public void test_whenUpdateUser_withValidDetails_thenReturnUser() throws UserNotFoundException, SQLException {
        User user = createFakeUser();

        String newEmail = "bar@foo.com";
        String newName = "Bar";
        String newPassword = "654321";
        User newUser = new User(newEmail, newName, newPassword);

        authStorage.updateUser(user.getEmail(), newUser);
        assertTrue(newUser.equals(authStorage.getUserFromEmail(newEmail)));
    }

    /* DELETE TESTS */

    @Test(expected = UserNotFoundException.class)
    public void test_whenDeleteUser_withNoErrors_thenDeleteUserAccount() throws UserNotFoundException, SQLException {
        User user = createFakeUser();

        authStorage.deleteUser(user);
        authStorage.getUserFromEmail(user.getEmail());
    }

    // TODO: Refactor to TestUtils
    private User createFakeUser() throws SQLException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        User user = new User(email, name, password);
        authStorage.registerNewUser(user);
        return user;
    }

}
