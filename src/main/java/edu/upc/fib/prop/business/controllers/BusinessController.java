package edu.upc.fib.prop.business.controllers;

import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.DocumentsCollection;

public interface BusinessController {

    /**
     * Search for matching authors with a given prefix.
     * @param authorPrefix User input to search.
     * @return Collection of matching authors (even if empty).
     */
    AuthorsCollection searchMatchingAuthors(String authorPrefix);

    /**
     * Search for documents written by an author.
     * @param authorName Author name identifier.
     * @return Collection of matching documents (even if empty).
     */
    DocumentsCollection searchDocumentsByAuthor(String authorName);

    /**
     * Check if input details match with a user.
     * @param email Account email.
     * @param password Account password.
     * @return If login is successful.
     */
    boolean checkLoginDetails(String email, String password);

    /**
     * Check if input details can be used to create a new user.
     * @param email New account email.
     * @param userName New username email.
     * @param password New password.
     * @param password2 New password repeated.
     * @return If register is successful.
     */
    boolean registerNewUser(String email, String userName, String password, String password2);
}
