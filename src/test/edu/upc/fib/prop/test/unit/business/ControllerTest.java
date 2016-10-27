package edu.upc.fib.prop.test.unit.business;

import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.controllers.impl.BusinessControllerImpl;
import edu.upc.fib.prop.business.users.UsersManager;
import edu.upc.fib.prop.exceptions.AlreadyExistingUserException;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.models.User;
import edu.upc.fib.prop.persistence.controllers.PersistenceController;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.doThrow;

public class ControllerTest {

    @InjectMocks
    private BusinessController businessController = new BusinessControllerImpl();

    @Mock
    private UsersManager usersManager;

    @Mock
    private PersistenceController persistenceController;

    @Ignore
    @Test(expected = InvalidDetailsException.class)
    public void test_whenRegisterUser_withInvalidDetails_thenDoNotRegister()
            throws InvalidDetailsException, AlreadyExistingUserException {
        String email = "FooBar";
        String name = "Foo";
        String password = "123456";
        String password2 = "123456";

        doThrow(new InvalidDetailsException()).when(usersManager.register(email, name, password, password2));
        businessController.registerNewUser(email, name, password, password2);
    }

    @Ignore
    @Test
    public void test_whenRegisterUser_withAlreadyExistingUser_thenDoNotRegister()
            throws InvalidDetailsException, AlreadyExistingUserException {
        String email = "foo@bar.com";
        String name = "Foo";
        String password = "123456";
        String password2 = "123456";
        User user = new User(email, name, password);

        //doThrow(new AlreadyExistingUserException()).when(persistenceController.createUser(user));
        businessController.registerNewUser(email, name, password, password2);
    }

    @Test
    public void test_whenRegisterUser_withValidDetails_thenRegister() {
    }

}
