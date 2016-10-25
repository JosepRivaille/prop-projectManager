package edu.upc.fib.prop.business.controllers.impl;

import edu.upc.fib.prop.business.authentication.AccountManager;
import edu.upc.fib.prop.business.authentication.impl.AccountManagerImpl;
import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.documents.impl.DocumentAnalyserImpl;
import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.models.User;
import edu.upc.fib.prop.business.search.impl.SearchAuthorImpl;
import edu.upc.fib.prop.business.search.impl.SearchDocumentImpl;
import edu.upc.fib.prop.exceptions.AlreadyExistingDocumentException;
import edu.upc.fib.prop.persistence.controllers.PersistenceController;
import edu.upc.fib.prop.persistence.controllers.impl.PersistenceControllerImpl;
import edu.upc.fib.prop.utils.Constants;
import javafx.util.Pair;

import java.util.Set;

public class BusinessControllerImpl implements BusinessController {

    private PersistenceController persistenceController;

    private SearchAuthorImpl searchAuthor;
    private SearchDocumentImpl searchDocument;

    private AuthorsCollection authorsCollection;
    private DocumentsCollection documentsCollection;

    private AccountManager accountManager;
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

        this.accountManager = new AccountManagerImpl(Constants.DB_DEVELOPMENT);
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
    public boolean checkLoginDetails(String email, String password) {
        return this.accountManager.login(email, password);
    }

    @Override
    public boolean registerNewUser(String email, String userName, String password, String password2) {
        return this.accountManager.register(email, userName, password, password2);
    }

    @Override
    public DocumentsCollection getCurrentUserDocuments() {
        //User user = this.accountManager.getCurrentUser();
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
