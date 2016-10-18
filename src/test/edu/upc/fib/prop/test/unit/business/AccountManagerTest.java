package edu.upc.fib.prop.test.unit.business;

import edu.upc.fib.prop.business.authentication.AccountManager;
import edu.upc.fib.prop.business.authentication.impl.AccountManagerImpl;
import edu.upc.fib.prop.business.models.User;
import edu.upc.fib.prop.exceptions.AuthStorageException;
import edu.upc.fib.prop.persistence.authentication.AuthStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountManagerTest {

    @InjectMocks
    private AccountManager accountManager = new AccountManagerImpl(null);

    @Mock
    private AuthStorage authStorage;

    /* REGISTER TESTS */

    @Test
    public void test_whenRegister_withAlreadyExistingEmail_thenDoNotRegister() throws AuthStorageException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";
        String password2 = "123456";

        doThrow(new AuthStorageException("Email already in use")).when(authStorage).registerNewUser(any());
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
    public void test_whenRegister_withCorrectDetails_thenRegister() throws AuthStorageException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";
        String password2 = "123456";

        assertTrue(accountManager.register(email, name, password, password2));
    }

    /* LOGIN TESTS */

    @Test
    public void test_whenLogin_withNonMatchingDetails_thenDoNotLogin() throws AuthStorageException {
        String email = "foo@bar.com";
        String password = "123456";

        doThrow(new AuthStorageException("Invalid email or password")).when(authStorage).registerNewUser(any());
        assertFalse(accountManager.login(email, password));
    }

    @Test
    public void test_whenLogin_withCorrectDetails_thenLogin() throws AuthStorageException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        User user = new User(email, name, password);

        when(authStorage.checkDetails(any(), any())).thenReturn(true);
        when(authStorage.getUserFromEmail(any())).thenReturn(user);
        assertTrue(accountManager.login(email, password));
        assertTrue(accountManager.getCurrentUser().equals(user));
    }

    /* EDIT TESTS */

    @Test
    public void test_whenEdit_withIncorrectNewValues_thenDoNotEdit() throws AuthStorageException {
        User user = login();
        assertTrue(accountManager.getCurrentUser().equals(user));

        doThrow(new AuthStorageException("Invalid new details")).when(authStorage).updateUser(any());
        accountManager.editAccount("fake1", "fake2", "fake3");
        assertTrue(accountManager.getCurrentUser().equals(user));
    }

    @Test
    public void test_whenEdit_withCorrectNewValues_thenEdit() throws AuthStorageException {
        User user = login();
        assertTrue(accountManager.getCurrentUser().equals(user));

        when(authStorage.updateUser(user)).thenReturn(new User("fake1", "fake2", "fake3"));
        accountManager.editAccount("fake1", "fake2", "fake3");
        assertFalse(accountManager.getCurrentUser().equals(user));
    }

    /* DELETE TESTS */

    @Test
    public void test_whenDelete_withActiveSession_thenDelete() throws AuthStorageException {
        User user = login();
        assertTrue(accountManager.getCurrentUser().equals(user));

        accountManager.deleteAccount();
        assertNull(accountManager.getCurrentUser());
    }

    /* LOGOUT TESTS */

    @Test
    public void test_whenLogout_withActiveOrNonActiveSession_thenThereIsNoSession() throws AuthStorageException {
        User user = login();
        assertTrue(accountManager.getCurrentUser().equals(user));

        accountManager.logout();
        assertNull(accountManager.getCurrentUser());
    }

    // Performs login for other tests
    private User login() throws AuthStorageException {
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
