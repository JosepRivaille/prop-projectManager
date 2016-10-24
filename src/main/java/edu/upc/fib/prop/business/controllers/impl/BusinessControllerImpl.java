package edu.upc.fib.prop.business.controllers.impl;

import edu.upc.fib.prop.business.authentication.AccountManager;
import edu.upc.fib.prop.business.authentication.impl.AccountManagerImpl;
import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.DocumentsCollection;
import edu.upc.fib.prop.business.search.SearchAuthor;
import edu.upc.fib.prop.business.search.SearchDocument;
import edu.upc.fib.prop.persistence.controllers.PersistenceController;
import edu.upc.fib.prop.persistence.controllers.impl.PersistenceControllerImpl;
import edu.upc.fib.prop.utils.Constants;

import java.util.Set;

public class BusinessControllerImpl implements BusinessController {

    private PersistenceController persistenceController;

    private SearchAuthor searchAuthor;
    private SearchDocument searchDocument;

    private AuthorsCollection authorsCollection;
    private DocumentsCollection documentsCollection;

    private AccountManager accountManager;

    private Set<String> excludedWordsCat;
    private Set<String> excludedWordsEng;
    private Set<String> excludedWordsEsp;

    public BusinessControllerImpl() {
        System.out.println("Initializating business controller");

        this.persistenceController = new PersistenceControllerImpl();
        this.searchAuthor = new SearchAuthor();
        this.searchDocument = new SearchDocument();

        this.accountManager = new AccountManagerImpl(Constants.DB_DEVELOPMENT);

        //TODO: Implement as lazy load
        // Load in memory all authors and documents on instantiate
        this.authorsCollection = this.persistenceController.getAuthors();
        this.documentsCollection = this.persistenceController.getDocuments();

        excludedWordsCat = this.persistenceController.getExcludedWords("cat");
        excludedWordsEng = this.persistenceController.getExcludedWords("eng");
        excludedWordsEsp = this.persistenceController.getExcludedWords("esp");
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

}
