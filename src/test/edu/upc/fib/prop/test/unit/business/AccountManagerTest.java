package edu.upc.fib.prop.test.unit.business;

import edu.upc.fib.prop.business.authentication.AccountManager;
import edu.upc.fib.prop.business.authentication.impl.AccountManagerImpl;
import edu.upc.fib.prop.business.models.User;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.persistence.authentication.AuthStorage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountManagerTest {

    @InjectMocks
    private AccountManager accountManager = new AccountManagerImpl(null, null);

    @Mock
    private AuthStorage authStorage;

    @After
    public void deleteUser() {
        accountManager.setCurrentUser(null);
    }

    @Test
    public void test_whenRegister_withAlreadyExistingEmail_thenDoNotRegister() {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";
        String password2 = "123456";

        assertFalse(accountManager.register(email, name, password, password2));
    }

    @Test
    public void test_whenRegister_withNonMatchingPasswords_thenDoNotRegister() {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";
        String password2 = "654321";

        assertFalse(accountManager.register(email, name, password, password2));
    }
    @Test
    public void test_whenRegister_withCorrectDetails_thenRegister() throws UserNotFoundException, SQLException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";
        String password2 = "123456";

        doThrow(UserNotFoundException.class).when(authStorage).getUserFromEmail(email);

        assertTrue(accountManager.register(email, name, password, password2));
    }

    @Test
    public void test_whenLogin_withNonExistingEmail_thenDoNotLogin() throws UserNotFoundException, SQLException {
        String email = "foo@bar.com";
        String password = "123456";
        doThrow(UserNotFoundException.class).when(authStorage).registerNewUser(any());

        assertFalse(accountManager.login(email, password));
    }

    @Test
    public void test_whenLogin_withNonMatchingDetails_thenDoNotLogin() throws UserNotFoundException, SQLException {
        String email = "foo@bar.com";
        String password = "123456";

        when(authStorage.checkDetails(any(), any())).thenReturn(false);

        assertFalse(accountManager.login(email, password));
    }

    @Test
    public void test_whenLogin_withCorrectDetails_thenLogin() throws UserNotFoundException, SQLException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        when(authStorage.checkDetails(any(), any())).thenReturn(true);
        when(authStorage.getUserFromEmail(email)).thenReturn(new User(email, name, "any"));

        assertTrue(accountManager.login(email, password));
        assertTrue(accountManager.getCurrentUser().getEmail().equals(email));
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

        doNothing().when(authStorage).updateUser(newEmail, updatedUser);

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
    }

    //TODO: Refactor to UtilsTest
    private User login() throws UserNotFoundException, SQLException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        User user = new User(email, name, password);
        when(authStorage.checkDetails(any(), any())).thenReturn(true);
        when(authStorage.getUserFromEmail(any())).thenReturn(user);
        accountManager.login(email, password);
        return user;
    }

}
