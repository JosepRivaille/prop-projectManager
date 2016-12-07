package edu.upc.fib.prop.view.controllers.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.controllers.impl.BusinessControllerImpl;
import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.*;
import edu.upc.fib.prop.utils.StringUtils;
import edu.upc.fib.prop.view.controllers.ViewGraphicController;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewGraphicControllerImpl implements ViewGraphicController {

    private BusinessController businessController;

    public ViewGraphicControllerImpl() {
        System.out.println("Initializing view controller (GRAPHICAL MODE)");
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
        List<DocumentBasicInfo> documentsBasicInfo = new ArrayList<>();
        for (Document document : documentsCollection.getAllDocuments()) {
            documentsBasicInfo.add(new DocumentBasicInfo(document));
        }
        return new Gson().toJson(documentsBasicInfo);
    }

    @Override
    public String getDocumentByTitleAndAuthor(String title, String author) throws DocumentNotFoundException {
        Document document = this.businessController.searchDocumentsByTitleAndAuthor(title, author);
        DocumentBasicInfo documentBasicInfo = new DocumentBasicInfo(document);
        return new Gson().toJson(documentBasicInfo);
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
        DocumentsCollection documents = this.businessController.getCurrentUserDocuments();
        List<DocumentBasicInfo> documentsBasicInfo = documents.getDocuments().stream().map(DocumentBasicInfo::new)
                .collect(Collectors.toList());
        return new Gson().toJson(documentsBasicInfo);
    }

    @Override
    public void storeNewDocument(String documentJSON)
            throws AlreadyExistingDocumentException, InvalidDetailsException {
        Document document = StringUtils.parseJSONToDocument(documentJSON);
        this.businessController.storeNewDocument(document);
    }

    @Override
    public void updateDocument(String oldDocumentJSON, String editedDocumentJSON)
            throws InvalidDetailsException, AlreadyExistingDocumentException, DocumentNotFoundException {
        Document oldDocument = StringUtils.parseJSONToDocument(oldDocumentJSON);
            Document editedDocument = StringUtils.parseJSONToDocument(editedDocumentJSON);
            businessController.updateDocument(oldDocument.getTitle(), oldDocument.getAuthor(), editedDocument);
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
    public String importDocument(String path)
            throws ImportExportException, AlreadyExistingDocumentException, InvalidDetailsException {
        return null;
    }

    @Override
    public void exportDocument(String pathToExport, Document document, String os) throws ImportExportException {

    }

    @Override
    public Float rateDocument(String title, String author, int rating) throws DocumentNotFoundException {
        return this.businessController.rateDocument(title, author, rating);
    }

    public boolean isDocumentFavourite(String title, String author){
        return this.businessController.isDocumentFavourite(title, author);
    }

    public void addFavourite(String title, String author) throws DocumentNotFoundException {
        this.businessController.addDocumentToFavourites(title, author);
    }

    public void removeFavourite(String title, String author) throws DocumentNotFoundException {
        this.businessController.deleteDocumentFromFavourites(title, author);
    }
    public void test(){
        System.out.println("ADJHADISDIUGAISUGD");
    }
}
