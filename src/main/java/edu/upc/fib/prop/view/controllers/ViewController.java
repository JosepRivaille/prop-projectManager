package edu.upc.fib.prop.view.controllers;

import edu.upc.fib.prop.business.models.AuthorsCollection;

public interface ViewController {

    AuthorsCollection getAuthorsWithPrefix(String authorPrefix);

}
