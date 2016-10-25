package edu.upc.fib.prop.test.unit.persistence;

import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.models.User;
import edu.upc.fib.prop.persistence.dao.users.DaoUsers;
import edu.upc.fib.prop.persistence.dao.users.impl.DaoUsersImpl;
import edu.upc.fib.prop.utils.Constants;
import edu.upc.fib.prop.utils.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertTrue;

public class AuthenticationPersistenceTest {

    private static Connection c;
    private static DaoUsers daoUsers;

    @BeforeClass
    public static void setUpTableAndDB() {
        try {
            Class.forName(Constants.JDBC_DRIVER);
            if (c == null) {
                c = DriverManager.getConnection(Constants.DB_TEST);
            }
            daoUsers = new DaoUsersImpl(c);

            Statement statement = c.createStatement();
            String sql = FileUtils.readFile("src/main/resources/sql/dbInitializer.sql");
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

    @Test
    public void test_whenRegister_withCorrectUser_thenRegisterNewUser()
            throws UserNotFoundException, InvalidDetailsException, SQLException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        User user = new User(email, name, password);
        daoUsers.registerNewUser(user);
        assertTrue(user.equals(daoUsers.checkDetails(email, password)));
    }

    /* USER MATCH TESTS */

    @Test(expected=UserNotFoundException.class)
    public void test_whenCheckDetails_withNonExistingUserData_thenNotFoundException()
            throws UserNotFoundException, InvalidDetailsException, SQLException {
        String email = "foo@bar.com";
        String password = "123456";
        daoUsers.checkDetails(email, password);
    }

    @Test(expected = InvalidDetailsException.class)
    public void test_whenCheckDetails_withNonMatchingDetails_thenInvalidDetailsException()
            throws UserNotFoundException, InvalidDetailsException, SQLException {
        User user = createFakeUser();
        String badPassword = "654321";
        daoUsers.checkDetails(user.getEmail(), badPassword);
    }

    @Test
    public void test_whenCheckDetails_withMatchingDetails_thenReturnTrue()
            throws UserNotFoundException, InvalidDetailsException, SQLException {
        User user = createFakeUser();
        User currentUser = daoUsers.checkDetails(user.getEmail(), user.getPassword());
        assertTrue(user.equals(currentUser));
    }

    /* UPDATE TESTS */

    @Test
    public void test_whenUpdateUser_withValidDetails_thenReturnUser()
            throws UserNotFoundException, InvalidDetailsException, SQLException {
        User user = createFakeUser();

        String newEmail = "bar@foo.com";
        String newName = "Bar";
        String newPassword = "654321";
        User newUser = new User(newEmail, newName, newPassword);

        daoUsers.updateUser(user.getEmail(), newUser);
        assertTrue(newUser.equals(daoUsers.checkDetails(newEmail, newPassword)));
    }

    /* DELETE TESTS */

    @Test(expected = UserNotFoundException.class)
    public void test_whenDeleteUser_withNoErrors_thenDeleteUserAccount()
            throws UserNotFoundException, InvalidDetailsException, SQLException {
        User user = createFakeUser();

        daoUsers.deleteUser(user);
        daoUsers.checkDetails(user.getEmail(), user.getPassword());
    }

    // TODO: Refactor to TestUtils
    private User createFakeUser() throws SQLException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        User user = new User(email, name, password);
        daoUsers.registerNewUser(user);
        return user;
    }

}