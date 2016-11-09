package edu.upc.fib.prop.business.controllers.impl;

import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.documents.DocumentTools;
import edu.upc.fib.prop.business.search.impl.SearchAuthorImpl;
import edu.upc.fib.prop.business.search.impl.SearchDocumentImpl;
import edu.upc.fib.prop.business.users.UsersManager;
import edu.upc.fib.prop.business.users.impl.UsersManagerImpl;
import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.*;
import edu.upc.fib.prop.persistence.controllers.PersistenceController;
import edu.upc.fib.prop.persistence.controllers.impl.PersistenceControllerImpl;

import java.sql.SQLException;

public class BusinessControllerImpl implements BusinessController {

    private PersistenceController persistenceController;

    private SearchAuthorImpl searchAuthor;
    private SearchDocumentImpl searchDocument;

    private AuthorsCollection authorsCollection;
    private DocumentsCollection documentsCollection;

    private UsersManager usersManager;

    public BusinessControllerImpl() {
        System.out.println("Initializing business controller");

        this.persistenceController = new PersistenceControllerImpl();
        this.searchAuthor = new SearchAuthorImpl();
        this.searchDocument = new SearchDocumentImpl();

        // Load in memory all authors and documents on instantiate
        this.authorsCollection = this.persistenceController.getAuthors();
        this.documentsCollection = this.persistenceController.getDocuments();

        this.usersManager = new UsersManagerImpl();
    }

    /*--------------- Users */

    @Override
    public DocumentsCollection searchDocumentsByAuthor(String authorName) throws DocumentNotFoundException {
        return this.searchDocument.filterByAuthor(this.documentsCollection, authorName);
    }

    @Override
    public Document searchDocumentsByTitleAndAuthor(String title, String authorName)
            throws DocumentNotFoundException {
        return this.searchDocument.filterByTitleAndAuthor(this.documentsCollection, title, authorName);
    }

    @Override
    public DocumentsSet searchForAllDocuments() {
        return this.documentsCollection.getAllDocuments();
    }

    @Override
    public SortedDocumentsSet searchDocumentsByRelevance(Document document, int k)
            throws DocumentNotFoundException {
        return this.searchDocument.searchForSimilarDocuments(this.documentsCollection, document, k);
    }


    @Override
    public void checkLoginDetails(String email, String password)
            throws InvalidDetailsException, UserNotFoundException {
        password = usersManager.login(email, password);
        User user = persistenceController.loginUser(email, password);
        usersManager.setCurrentUser(user);
    }

    @Override
    public void registerNewUser(String email, String userName, String password, String password2)
            throws InvalidDetailsException, AlreadyExistingUserException {
        User user = usersManager.register(email, userName, password, password2);
        persistenceController.createUser(user);
        usersManager.setCurrentUser(user);
    }

    @Override
    public void updateUser(String newEmail, String newName, String newPassword)
            throws InvalidDetailsException, UserNotFoundException, AlreadyExistingUserException {
        User user = usersManager.register(newEmail, newName, newPassword, newPassword);
        persistenceController.updateUser(usersManager.getCurrentUser(), user);
        usersManager.setCurrentUser(user);
    }

    @Override
    public void deleteUser() throws UserNotFoundException {
        User user = usersManager.getCurrentUser();
        persistenceController.deleteUser(user);
    }

    @Override
    public void logout() {
        usersManager.logout();
    }

    /*--------------- Authors */

    @Override
    public AuthorsCollection searchMatchingAuthors(String authorPrefix) throws AuthorNotFoundException {
        return this.searchAuthor.filterByPrefix(this.authorsCollection, authorPrefix);
    }

    @Override
    public DocumentsCollection searchDocumentsByAuthor(String authorName) throws DocumentNotFoundException {
        return this.searchDocument.filterByAuthor(this.documentsCollection, authorName);
    }

    @Override
    public Document searchDocumentsByTitleAndAuthor(String title, String authorName)
            throws DocumentNotFoundException {
        return this.searchDocument.filterByTitleAndAuthor(this.documentsCollection, title, authorName);
    }

    @Override
    public SortedDocumentsSet searchDocumentsByRelevance(Document document, int k)
            throws DocumentNotFoundException {
        return this.searchDocument.searchForSimilarDocuments(this.documentsCollection, document, k);
    }

    @Override
    public DocumentsCollection getCurrentUserDocuments() {
        String user = this.usersManager.getCurrentUser().getEmail();
        return this.searchDocument.filterByUser(this.documentsCollection, user);
    }

    @Override
    public void storeNewDocument(Document doc) throws AlreadyExistingDocumentException, InvalidDetailsException  {
        doc.setUser(usersManager.getCurrentUser().getEmail());
        if (DocumentTools.isCorrect(doc)) {
            try {
                documentsCollection.addDocument(doc);
                persistenceController.writeNewDocument(doc);
                reloadDBData();
            } catch (SQLException e) {
                throw new AlreadyExistingDocumentException();
            }
        }
    }

    @Override
    public void updateDocument(Document oldDoc, Document newDoc) throws InvalidDetailsException, AlreadyExistingDocumentException {
                Document updatedDoc = documentsCollection.updateDocument(oldDoc, newDoc);
                persistenceController.updateDocument(oldDoc, updatedDoc);
                reloadDBData();
    }

    @Override
    public void deleteDocument(Document document) {
        documentsCollection.deleteDocument(document);
        persistenceController.deleteDocument(document);
        reloadDBData();
    }



    //////////

    // TODO: Improve performance
    private void reloadDBData() {
        authorsCollection = persistenceController.getAuthors();
        documentsCollection = persistenceController.getDocuments();
    }

}
