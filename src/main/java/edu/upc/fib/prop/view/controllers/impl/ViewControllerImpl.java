package edu.upc.fib.prop.view.controllers.impl;

import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.controllers.impl.BusinessControllerImpl;
import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.view.menu.MainMenu;
import edu.upc.fib.prop.view.controllers.ViewController;
import javafx.util.Pair;

public class ViewControllerImpl implements ViewController {

    private BusinessController businessController;

    public ViewControllerImpl() {
        System.out.println("Initializating view controller");
        businessController = new BusinessControllerImpl();
        new MainMenu(this);
    }

    @Override
    public AuthorsCollection getAuthorsWithPrefix(String authorPrefix) throws AuthorNotFoundException {
        return this.businessController.searchMatchingAuthors(authorPrefix);
    }

    @Override
    public DocumentsCollection getDocumentsByAuthorId(String authorName) throws DocumentNotFoundException {
        return this.businessController.searchDocumentsByAuthor(authorName);
    }

    @Override
    public Document getDocumentByTitleAndAuthor(String title, String author) throws DocumentNotFoundException {
        return this.businessController.searchDocumentsByTitleAndAuthor(title, author);
    }

    /*-------------------- Users */

    @Override
    public void userLogin(String email, String password) throws UserNotFoundException, InvalidDetailsException {
        businessController.checkLoginDetails(email, password);
    }

    @Override
    public void userRegister(String email, String userName, String password, String password2)
            throws InvalidDetailsException, AlreadyExistingUserException {
        businessController.registerNewUser(email, userName, password, password2);
    }

    @Override
    public void userUpdate(String newEmail, String newName, String newPassword)
            throws InvalidDetailsException, UserNotFoundException, AlreadyExistingUserException {
        businessController.updateUser(newEmail, newName, newPassword);
    }

    @Override
    public void userDelete() throws UserNotFoundException {
        businessController.deleteUser();
    }

    /*-------------------- Documents */

    @Override
    public DocumentsCollection getCurrentUserDocuments() {
        return this.businessController.getCurrentUserDocuments();
    }

    @Override
    public void storeNewDocument(Document document)
            throws DocumentNotFoundException, AlreadyExistingDocumentException {
        this.businessController.storeNewDocument(document);
    }

    @Override
    public void updateDocument(Pair<Document, Document> updatedDocument) throws InvalidDetailsException {
        this.businessController.updateDocument(updatedDocument);
    }

    @Override
    public void deleteDocument(Document document) {
        this.businessController.deleteDocument(document);
    }

}
