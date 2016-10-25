package edu.upc.fib.prop.view.controllers.impl;

import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.controllers.impl.BusinessControllerImpl;
import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.Document;
import edu.upc.fib.prop.business.models.DocumentsCollection;
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
    public AuthorsCollection getAuthorsWithPrefix(String authorPrefix) {
        return this.businessController.searchMatchingAuthors(authorPrefix);
    }

    @Override
    public DocumentsCollection getDocumentsByAuthorId(String authorName) {
        return this.businessController.searchDocumentsByAuthor(authorName);
    }

    @Override
    public boolean userLogin(String email, String password) {
        return this.businessController.checkLoginDetails(email, password);
    }

    @Override
    public boolean userRegister(String email, String userName, String password, String password2) {
        return this.businessController.registerNewUser(email, userName, password, password2);
    }

    @Override
    public DocumentsCollection getCurrentUserDocuments() {
        return this.businessController.getCurrentUserDocuments();
    }

    @Override
    public boolean storeNewDocument(Document document) {
        return this.businessController.storeNewDocument(document);
    }

    @Override
    public boolean updateDocument(Pair<String, Document> updatedDocument) {
        return this.businessController.updateDocument(updatedDocument);
    }

    @Override
    public boolean deleteDocument(Document document) {
        return this.businessController.deleteDocument(document);
    }

}
