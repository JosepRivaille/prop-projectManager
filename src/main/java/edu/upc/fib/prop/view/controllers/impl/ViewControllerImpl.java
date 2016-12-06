package edu.upc.fib.prop.view.controllers.impl;

import com.google.gson.Gson;
import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.controllers.impl.BusinessControllerImpl;
import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.*;
import edu.upc.fib.prop.view.controllers.ViewController;
import javafx.util.Pair;

public class ViewControllerImpl implements ViewController {

    private BusinessController businessController;

    public ViewControllerImpl() {
        System.out.println("Initializating view controller (CONSOLE MODE)");
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

    @Override
    public SortedDocumentsSet searchDocumentsByQuery(String str, int k) throws DocumentNotFoundException {
        return this.businessController.searchDocumentsByQuery(str, k);
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
            throws DocumentNotFoundException, AlreadyExistingDocumentException, InvalidDetailsException {
        this.businessController.storeNewDocument(document);
    }

    @Override
    public void updateDocument(Pair<Document, Document> updatedDocument)
            throws InvalidDetailsException, AlreadyExistingDocumentException, DocumentNotFoundException {
        this.businessController.updateDocument(updatedDocument.getKey().getTitle(), updatedDocument.getKey().getAuthor(), updatedDocument.getValue());
    }

    @Override
    public void deleteDocument(Document document) {
        try {
            this.businessController.deleteDocument(document.getTitle(), document.getAuthor());
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Document importDocument(String path)
            throws ImportExportException, AlreadyExistingDocumentException, InvalidDetailsException {
        return this.businessController.importDocument(path);
    }

    @Override
    public void exportDocument(String pathToExport, Document document, String os) throws ImportExportException {
        this.businessController.exportDocument(pathToExport, document, os);
    }

    @Override
    public DocumentsSet getDocumentsByBooleanExpression(String booleanExpression) throws InvalidQueryException {
        return businessController.searchDocumentsByBooleanExpression(booleanExpression);
    }

    public void test(String text){
        System.out.println("Your text is: " + text);
    }

    public String testAuthorsJSON() throws AuthorNotFoundException {
        AuthorsCollection authorsCollection = businessController.searchMatchingAuthors("");
        return new Gson().toJson(authorsCollection);
    }

    @Override
    public void rateDocument(Document document, int rating) throws DocumentNotFoundException {
        this.businessController.rateDocument(document, rating);
    }

    @Override
    public void addDocumentToFavourites(Document document) throws DocumentNotFoundException {
        this.businessController.addDocumentToFavourites(document);
    }

    @Override
    public void deleteDocumentFromFavourites(Document document) throws DocumentNotFoundException {
        this.businessController.deleteDocumentFromFavourites(document);
    }

    @Override
    public Document getRocchioQuery(String query, SortedDocumentsSet list, double rv, float b, float c)
            throws DocumentNotFoundException{
        return businessController.getRocchioQuery(query,list,rv,b,c);
    }


}
