package edu.upc.fib.prop.test.unit.business;

import edu.upc.fib.prop.business.users.UsersManager;
import edu.upc.fib.prop.business.users.impl.UsersManagerImpl;
import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.models.User;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.persistence.dao.users.DaoUsers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountManagerTest {

    @InjectMocks
    private UsersManager accountManager = new UsersManagerImpl();

    @Mock
    private DaoUsers daoUsers;

    @After
    public void deleteUser() {
        accountManager.setCurrentUser(null);
    }

    @Test
    public void dummy() {

    }
/*
    @Test(expected = InvalidDetailsException.class)
    public void test_whenRegister_withInvalidData_thenDoNotRegister() throws InvalidDetailsException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";
        String password2 = "";

        accountManager.register(email, name, password, password2);
    }

    @Test(expected = InvalidDetailsException.class)
    public void test_whenRegister_withNonMatchingPasswords_thenDoNotRegister() throws InvalidDetailsException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";
        String password2 = "654321";

        accountManager.register(email, name, password, password2);
    }
    @Test
    public void test_whenRegister_withCorrectDetails_thenRegister()
            throws UserNotFoundException, SQLException, InvalidDetailsException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";
        String password2 = "123456";

        doThrow(UserNotFoundException.class).when(daoUsers).checkDetails(email, password);

        accountManager.register(email, name, password, password2);
    }

    @Test(expected = InvalidDetailsException.class)
    public void test_whenLogin_withNonInvalidData_thenDoNotLogin() throws InvalidDetailsException {
        String email = "foo@bar.com";
        String password = "123456";

        accountManager.login(email, password);
    }

    @Test
    public void test_whenLogin_withCorrectDetails_thenLogin()
            throws UserNotFoundException, InvalidDetailsException, SQLException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";
        User user = new User(email, name, password);

        when(daoUsers.checkDetails(any(), any())).thenReturn(user);

        assertTrue(accountManager.login(email, password));

        User currentUser = accountManager.getCurrentUser();
        assertTrue(currentUser.equals(user));
    }

    @Test
    public void test_whenEdit_withIncorrectNewValues_thenDoNotEdit() throws UserNotFoundException, SQLException {
        User user = login();
        accountManager.setCurrentUser(user);

        String newEmail = "bar@foo.com";
        String newName = "Bar";
        String newPassword = "";

        assertFalse(accountManager.editAccount(newEmail, newName, newPassword));
    }

    @Test
    public void test_whenEdit_withCorrectNewValues_thenEdit() throws UserNotFoundException, SQLException {
        User user = login();
        accountManager.setCurrentUser(user);

        String newEmail = "bar@foo.com";
        String newName = "Bar";
        String newPassword = "654321";
        User updatedUser = new User(newEmail, newName, newPassword);

        doNothing().when(daoUsers).updateUser(newEmail, updatedUser);

        assertTrue(accountManager.editAccount(newEmail, newName, newPassword));
    }

    @Test
    public void test_whenDelete_withActiveSession_thenDelete() throws UserNotFoundException, SQLException {
        User user = login();
        accountManager.setCurrentUser(user);

        accountManager.deleteAccount();
        assertNull(accountManager.getCurrentUser());
    }


    @Test
    public void test_whenLogout_withActiveOrNonActiveSession_thenThereIsNoSession() throws UserNotFoundException, SQLException {
        User user = login();
        accountManager.setCurrentUser(user);

        accountManager.logout();
        assertNull(accountManager.getCurrentUser());
    }*/

    //TODO: Refactor to UtilsTest
    private User login() throws UserNotFoundException, SQLException, InvalidDetailsException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        User user = new User(email, name, password);
        accountManager.login(email, password);
        return user;
    }
}
