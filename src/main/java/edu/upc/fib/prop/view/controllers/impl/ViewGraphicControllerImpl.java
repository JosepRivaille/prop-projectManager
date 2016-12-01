package edu.upc.fib.prop.view.controllers.impl;

import com.google.gson.Gson;
import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.controllers.impl.BusinessControllerImpl;
import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.*;
import edu.upc.fib.prop.view.controllers.ViewGraphicController;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ViewGraphicControllerImpl implements ViewGraphicController {

    private BusinessController businessController;
    private WebEngine webEngine;
    private Stage stage;

    public ViewGraphicControllerImpl(WebEngine we, Stage st) {
        System.out.println("Initializating view controller (GRAPHICAL MODE)");
        this.webEngine = we;
        this.stage = st;
        businessController = new BusinessControllerImpl();
    }

    @Override
    public String getAuthorsWithPrefix(String authorPrefix) throws AuthorNotFoundException {
        AuthorsCollection authorsCollection = this.businessController.searchMatchingAuthors(authorPrefix);
        return new Gson().toJson(authorsCollection);
    }

    @Override
    public String getDocumentsByAuthorId(String authorName) throws DocumentNotFoundException {
        DocumentsCollection documentsCollection = this.businessController.searchDocumentsByAuthor(authorName);
        return new Gson().toJson(documentsCollection);
    }

    @Override
    public String getDocumentByTitleAndAuthor(String title, String author) throws DocumentNotFoundException {
        Document document = this.businessController.searchDocumentsByTitleAndAuthor(title, author);
        return new Gson().toJson(document);
    }

    @Override
    public String getDocumentsByBooleanExpression(String booleanExpression) throws InvalidQueryException {
        return null;
    }

    @Override
    public String getDocumentsByRelevance(Document document, int k) throws DocumentNotFoundException {
        SortedDocumentsSet sortedDocumentsSet = this.businessController.searchDocumentsByRelevance(document, k);
        return new Gson().toJson(sortedDocumentsSet);
    }

    @Override
    public void userLogin(String email, String password) throws UserNotFoundException, InvalidDetailsException {

    }

    @Override
    public void userRegister(String email, String userName, String password, String password2) throws InvalidDetailsException, AlreadyExistingUserException {

    }

    @Override
    public void userUpdate(String newEmail, String newName, String newPassword) throws InvalidDetailsException, UserNotFoundException, AlreadyExistingUserException {

    }

    @Override
    public void userDelete() throws UserNotFoundException {

    }

    @Override
    public String getCurrentUserDocuments() {
        return null;
    }

    @Override
    public void storeNewDocument(Document document) throws DocumentNotFoundException, AlreadyExistingDocumentException, InvalidDetailsException, DocumentContentNotFoundException {

    }

    @Override
    public void updateDocument(Pair<Document, Document> updatedDocument) throws InvalidDetailsException, AlreadyExistingDocumentException, DocumentContentNotFoundException {

    }

    @Override
    public void deleteDocument(Document document) {

    }

    @Override
    public void userLogout() {

    }

    @Override
    public String searchForAllDocuments() {
        DocumentsSet documents = this.businessController.searchForAllDocuments();
        //TODO: Return only basic info
        return new Gson().toJson(documents);
    }

    @Override
    public String importDocument(String path) throws ImportExportException, AlreadyExistingDocumentException, InvalidDetailsException, DocumentContentNotFoundException {
        return null;
    }

    @Override
    public void exportDocument(String pathToExport, Document document, String os) throws ImportExportException, DocumentContentNotFoundException {

    }

    @Override
    public void rateDocument(Document document, int rating) throws DocumentNotFoundException {
        this.businessController.rateDocument(document, rating);
    }

    @Override
    public void addDocumentToFavourites(Document document) throws DocumentNotFoundException {
        this.businessController.addDocumentToFavourites(document);
    }
}
