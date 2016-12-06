package edu.upc.fib.prop.view.controllers.impl;

import com.google.gson.Gson;
import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.controllers.impl.BusinessControllerImpl;
import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.*;
import edu.upc.fib.prop.utils.StringUtils;
import edu.upc.fib.prop.view.controllers.ViewGraphicController;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ViewGraphicControllerImpl implements ViewGraphicController {

    private BusinessController businessController;

    public ViewGraphicControllerImpl() {
        System.out.println("Initializating view controller (GRAPHICAL MODE)");
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
        businessController.checkLoginDetails(email, password);
    }

    @Override
    public void userRegister(String email, String userName, String password, String password2)
            throws InvalidDetailsException, AlreadyExistingUserException {
        businessController.registerNewUser(email, userName, password, password2);
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
    public void storeNewDocument(String documentJSON) throws DocumentNotFoundException, AlreadyExistingDocumentException,
            InvalidDetailsException, DocumentContentNotFoundException {
        Document document = StringUtils.parseJSONToDocument(documentJSON);
        this.businessController.storeNewDocument(document);
    }

    @Override
    public void updateDocument(Pair<Document, Document> updatedDocument) throws InvalidDetailsException, AlreadyExistingDocumentException, DocumentContentNotFoundException {

    }

    @Override
    public void deleteDocument(String title, String authorName) {
        try {
            this.businessController.deleteDocument(title, authorName);
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void userLogout() {

    }

    @Override
    public String searchForAllDocuments() {
        DocumentsSet documents = this.businessController.searchForAllDocuments();
        List<DocumentBasicInfo> documentsBasicInfo = new ArrayList<>();
        for (Document document : documents) {
            documentsBasicInfo.add(new DocumentBasicInfo(document));
        }
        return new Gson().toJson(documentsBasicInfo);
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

    public void test(){
        System.out.println("ADJHADISDIUGAISUGD");
    }
}
