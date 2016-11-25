package edu.upc.fib.prop.view.controllers.impl;

import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.controllers.impl.BusinessControllerImpl;
import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.*;
import edu.upc.fib.prop.view.controllers.ViewController;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ViewControllerImpl implements ViewController {

    private BusinessController businessController;

    private WebEngine webEngine;

    private Stage stage;

    public ViewControllerImpl() {
        System.out.println("Initializating view controller (CONSOLE MODE)");
        businessController = new BusinessControllerImpl();
    }

    public ViewControllerImpl(WebEngine we, Stage st) {
        System.out.println("Initializating view controller (GRAPHICAL MODE)");
        this.webEngine = we;
        this.stage = st;
        businessController = new BusinessControllerImpl();
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

    @Override
    public SortedDocumentsSet getDocumentsByRelevance(Document document, int k) throws DocumentNotFoundException {
        return this.businessController.searchDocumentsByRelevance(document, k);
    }

    @Override
    public DocumentsSet searchForAllDocuments() {
        return this.businessController.searchForAllDocuments();
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

    @Override
    public void userLogout() {
        businessController.logout();
    }

    /*-------------------- Documents */

    @Override
    public DocumentsCollection getCurrentUserDocuments() {
        return this.businessController.getCurrentUserDocuments();
    }

    @Override
    public void storeNewDocument(Document document)
            throws DocumentNotFoundException, AlreadyExistingDocumentException, InvalidDetailsException, DocumentContentNotFoundException {
        this.businessController.storeNewDocument(document);
    }

    @Override
    public void updateDocument(Pair<Document, Document> updatedDocument) throws InvalidDetailsException, AlreadyExistingDocumentException, DocumentContentNotFoundException {
        this.businessController.updateDocument(updatedDocument.getKey(), updatedDocument.getValue());
    }

    @Override
    public void deleteDocument(Document document) {
        this.businessController.deleteDocument(document);
    }

    @Override
    public Document importDocument(String path) throws ImportExportException, AlreadyExistingDocumentException, InvalidDetailsException, DocumentContentNotFoundException {
        return this.businessController.importDocument(path);
    }

    @Override
    public void exportDocument(String pathToExport, Document document, String os) throws ImportExportException, DocumentContentNotFoundException {
        this.businessController.exportDocument(pathToExport, document, os);
    }

    @Override
    public DocumentsSet getDocumentsByBooleanExpression(String booleanExpression) {
        return businessController.searchDocumentsByBooleanExpression(booleanExpression);
    }

    public void test(){
        System.out.println("TEST!");
    }
}
