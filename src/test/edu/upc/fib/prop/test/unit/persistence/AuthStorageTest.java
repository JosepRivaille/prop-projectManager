package edu.upc.fib.prop.test.unit.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthStorageTest {

    /* USER MATCH TESTS */

    @Test
    public void test_whenCheckDetails_withUnexistingUserData_thenNotFoundException() {

    }

    @Test
    public void test_whenCheckDetails_withNonMatchingDetails_thenReturnFalse() {

    }

    @Test
    public void test_whenCheckDetails_withMatchingDetails_thenReturnTrue() {

    }

    /* REGISTER TESTS */

    @Test
    public void test_whenRegister_withAlreadyExistingEmail_thenAlreadyExistingException() {

    }

    @Test
    public void test_whenRegister_withCorrectData_thenRegisterNewUser() {

    }

    /* GET DATA TESTS */

    @Test
    public void test_whenGetUserFromEmail_withNonExistingEmail_thenNotFoundException() {

    }

    @Test
    public void test_whenGetUserFromEmail_withExistingEmail_thenReturnUser() {

    }

    /* UPDATE TESTS */

    @Test
    public void test_whenUpdateUser_withInvalidDetails_thenInvalidNewDetailsException() {

    }

    @Test
    public void test_whenUpdateUser_withValidDetails_thenReturnUser() {

    }

    /* DELETE TESTS */

    @Test
    public void test_whenDeleteUser_withErrorsDeleting_thenUnableToDeleteException() {

    }

    @Test
    public void test_whenDeleteUser_withNoErrors_thenDeleteUserAccount() {

    }

}
