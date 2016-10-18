package edu.upc.fib.prop.test.unit.business;

import edu.upc.fib.prop.business.authentication.AccountManager;
import edu.upc.fib.prop.business.authentication.impl.AccountManagerImpl;
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

@RunWith(MockitoJUnitRunner.class)
public class AccountManagerTest {

    @InjectMocks
    private AccountManager accountManager = new AccountManagerImpl(null);

    @Mock
    private AuthStorage authStorage;

    @Ignore("Test not completed")
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

}
