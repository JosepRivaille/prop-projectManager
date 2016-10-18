package edu.upc.fib.prop.test.unit.business;

import edu.upc.fib.prop.business.authentication.AccountManager;
import edu.upc.fib.prop.business.authentication.impl.AccountManagerImpl;
import edu.upc.fib.prop.business.models.User;
import edu.upc.fib.prop.exceptions.AuthStorageException;
import edu.upc.fib.prop.persistence.authentication.AuthStorage;
import org.junit.Ignore;
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

    @Ignore("Doesn't work properly")
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

    @Ignore("Doesn't work properly")
    @Test
    public void test_whenLogin_withNonMatchingDetails_thenDoNotLogin() throws AuthStorageException {
        String email = "foo@bar.com";
        String password = "123456";

        doThrow(new AuthStorageException("Invalid email or password")).when(authStorage).registerNewUser(any());
        assertFalse(accountManager.login(email, password));
    }

    @Ignore("Doesn't work properly")
    @Test
    public void test_whenLogin_withCorrectDetails_thenLogin() {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        User user = new User(email, name, password);

        when(authStorage.checkDetails(email, password)).thenReturn(true);
        assertTrue(accountManager.login(email, password));
        assertTrue(accountManager.getUser().equals(user));
    }

    /* EDIT TESTS */

    @Test
    public void test_whenEdit_withIncorrectNewValues_thenDoNotEdit() {

    }

    @Test
    public void test_whenEdit_withCorrectNewValues_thenEdit() {

    }

    /* DELETE TESTS */

    @Ignore("Doesn't work properly")
    @Test
    public void test_whenDelete_withActiveSession_thenDelete() throws AuthStorageException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";

        User user = new User(email, name, password);

        when(authStorage.checkDetails(email, password)).thenReturn(true);
        accountManager.login(email, password);
        assertTrue(accountManager.getUser().equals(user));

        when(authStorage.deleteUser(user)).thenReturn(true);
        assertNull(accountManager.getUser());
        doThrow(new AuthStorageException("Invalid email or password")).when(authStorage).getUserFromEmail(email);
    }

    /* LOGOUT TESTS */

    @Ignore("Doesn't work properly")
    @Test
    public void test_whenLogout_withActiveOrNonActiveSession_thenThereIsNoSession() {
        String email = "foo@bar.com";
        String password = "123456";

        when(authStorage.checkDetails(any(), any())).thenReturn(true);
        accountManager.login(email, password);

        accountManager.logout();
        assertNull(accountManager.getUser());
    }


}
