package edu.upc.fib.prop.view.controllers.impl;

import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.controllers.impl.BusinessControllerImpl;
import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.DocumentsCollection;
import edu.upc.fib.prop.view.menu.MainMenu;
import edu.upc.fib.prop.view.controllers.ViewController;

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

}
