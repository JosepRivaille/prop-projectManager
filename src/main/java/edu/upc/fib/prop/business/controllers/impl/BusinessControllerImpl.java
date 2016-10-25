package edu.upc.fib.prop.business.controllers.impl;

import edu.upc.fib.prop.business.users.UsersManager;
import edu.upc.fib.prop.business.users.impl.UsersManagerImpl;
import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.documents.impl.DocumentAnalyserImpl;
import edu.upc.fib.prop.exceptions.AlreadyExistingUserException;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.UserNotFoundException;
import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.business.search.impl.SearchAuthorImpl;
import edu.upc.fib.prop.business.search.impl.SearchDocumentImpl;
import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.models.User;
import edu.upc.fib.prop.persistence.controllers.PersistenceController;
import edu.upc.fib.prop.persistence.controllers.impl.PersistenceControllerImpl;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.Set;

public class BusinessControllerImpl implements BusinessController {

    private PersistenceController persistenceController;

    private SearchAuthorImpl searchAuthor;
    private SearchDocumentImpl searchDocument;

    private AuthorsCollection authorsCollection;
    private DocumentsCollection documentsCollection;

    private UsersManager usersManager;
    private DocumentAnalyserImpl documentAnalyser;

    private Set<String> excludedWordsCat;
    private Set<String> excludedWordsEng;
    private Set<String> excludedWordsEsp;

    public BusinessControllerImpl() {
        System.out.println("Initializating business controller");

        this.persistenceController = new PersistenceControllerImpl();
        this.searchAuthor = new SearchAuthorImpl();
        this.searchDocument = new SearchDocumentImpl();

        // Load in memory all authors and documents on instantiate
        this.authorsCollection = this.persistenceController.getAuthors();
        this.documentsCollection = this.persistenceController.getDocuments();

        this.usersManager = new UsersManagerImpl();
        this.documentAnalyser = new DocumentAnalyserImpl(this.documentsCollection);

        this.excludedWordsCat = this.persistenceController.getExcludedWords("cat");
        this.excludedWordsEng = this.persistenceController.getExcludedWords("eng");
        this.excludedWordsEsp = this.persistenceController.getExcludedWords("esp");
    }

    @Override
    public AuthorsCollection searchMatchingAuthors(String authorPrefix) {
        return this.searchAuthor.filterByPrefix(this.authorsCollection, authorPrefix);
    }

    @Override
    public DocumentsCollection searchDocumentsByAuthor(String authorName) {
        return this.searchDocument.filterByAuthor(this.documentsCollection, authorName);
    }

    @Override
    public void checkLoginDetails(String email, String password)
            throws InvalidDetailsException, UserNotFoundException {
        usersManager.login(email, password);
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
    public DocumentsCollection getCurrentUserDocuments() {
        //User user = this.usersManager.getCurrentUser();
        //return this.searchDocument.filterByUser(this.documentsCollection, user);
        return new DocumentsCollection();
    }



    @Override
    public boolean storeNewDocument(Document document) {
        documentAnalyser.setDocument(document);
        if (documentAnalyser.checkCorrectData()) {
            documentAnalyser.calculateDocumentParameters();
            try {
                persistenceController.writeNewDocument(documentAnalyser.getDocument());
                return true;
            } catch (AlreadyExistingDocumentException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean updateDocument(Pair<String, Document> updatedDocument) {
        return false;
    }

    @Override
    public boolean deleteDocument(Document document) {
        return false;
    }

}
