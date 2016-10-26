package edu.upc.fib.prop.test.unit.persistence;

import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.models.User;
import edu.upc.fib.prop.persistence.dao.users.DaoUsers;
import edu.upc.fib.prop.persistence.dao.users.impl.DaoUsersImpl;
import edu.upc.fib.prop.utils.Constants;
import edu.upc.fib.prop.utils.FileUtils;
import edu.upc.fib.prop.utils.SecurityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class DaoUsersTest {

    private static Connection c;
    private static DaoUsers daoUsers;

    @BeforeClass
    public static void setUpTableAndDB() {
        try {
            Class.forName(Constants.JDBC_DRIVER);
            c = DriverManager.getConnection(Constants.DB_TEST);
            daoUsers = new DaoUsersImpl(c);

            Statement statement = c.createStatement();
            String sql = FileUtils.readFile("src/main/resources/sql/dbInitializer.sql");
            statement.executeUpdate(sql);
            sql = FileUtils.readFile("src/main/resources/sql/dbFiller.sql");
            statement.executeUpdate(sql);
            statement.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    @AfterClass
    public static void dropDB() {
        File f = new File("test.db");
        boolean deleted = f.delete();
        System.out.println("File deleted: " + deleted);
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*-------------------- Register tests */

    @Test(expected = SQLException.class)
    public void test_whenRegisterUser_withAlreadyExistingEmail_thenDoNotRegister() throws SQLException {
        String email = "admin@fib.upc.edu";
        String name = "Admin";
        String password = "123456";
        User user = new User(email, name, password);

        daoUsers.registerNewUser(user);
    }

    @Test
    public void test_whenRegisterUser_withValidDetails_thenRegister() throws SQLException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";
        User user = new User(email, name, password);

        daoUsers.registerNewUser(user);
    }

    /*-------------------- Login tests */

    @Test(expected = UserNotFoundException.class)
    public void test_whenCheckDetails_withUnexistingUser_thenDoNotLogin()
            throws UserNotFoundException, SQLException, InvalidDetailsException {
        String email = "bar@foo.com";
        String password = "123456";

        daoUsers.checkDetails(email, password);
    }

    @Test(expected = InvalidDetailsException.class)
    public void test_whenCheckDetails_withNonMatchingDetails_thenDoNotLogin()
            throws UserNotFoundException, InvalidDetailsException, SQLException {
        String email = "foo@bar.com";
        String password = "654321";

        daoUsers.checkDetails(email, password);
    }

    @Test
    public void test_whenCheckDetails_withValidDetails_thenLogin()
            throws UserNotFoundException, InvalidDetailsException, SQLException, NoSuchAlgorithmException {
        String email = "admin@fib.upc.edu";
        String name = "Admin";
        String password = SecurityUtils.hashData("123456");
        User expectedUser = new User(email, name, password);

        assertEquals(daoUsers.checkDetails(email, password), expectedUser);
    }

    /*-------------------- Edit users tests */

    @Test(expected = UserNotFoundException.class)
    public void test_whenUpdateUser_withNonExistingOldEmail_thenDoNotUpdateUser()
            throws UserNotFoundException, SQLException {
        String oldEmail = "fake@foo.com";
        String newEmail = "foo@bar.com";
        String newName = "Foo";
        String newPassword = "123456";
        User newUser = new User(newEmail, newName, newPassword);

        daoUsers.updateUser(oldEmail, newUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void test_whenUpdateUser_withAlreadyExistingNewEmail_thenDoNotUpdateUser()
            throws UserNotFoundException, SQLException {
        String oldEmail = "user@fib.upc.edu";
        String newEmail = "admin@fib.upc.edu";
        String newName = "Foo";
        String newPassword = "123456";
        User newUser = new User(newEmail, newName, newPassword);

        daoUsers.updateUser(oldEmail, newUser);
    }

    @Test
    public void test_whenUpdateUser_withValidDetails_thenUpdateUser() throws UserNotFoundException, SQLException {
        String oldEmail = "user@fib.upc.edu";
        String newEmail = "fake@fib.upc.edu";
        String newName = "Foo";
        String newPassword = "123456";
        User newUser = new User(newEmail, newName, newPassword);

        daoUsers.updateUser(oldEmail, newUser);
    }

    /*-------------------- Delete users tests */

    @Test
    public void test_whenDeleteUser_withNonExistingUser_thenDeleteUser()throws UserNotFoundException, SQLException {
        String email = "fake@fib.upc.edu";
        User user = new User(email, "any", "any");
        daoUsers.deleteUser(user);
    }

    @Test
    public void test_whenDeleteUser_withExistingUser_thenDeleteUser()throws UserNotFoundException, SQLException {
        String email = "admin@fib.upc.edu";
        User user = new User(email, "any", "any");
        daoUsers.deleteUser(user);
    }

}
