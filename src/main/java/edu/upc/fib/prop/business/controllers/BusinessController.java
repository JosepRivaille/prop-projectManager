package edu.upc.fib.prop.business.controllers;

import edu.upc.fib.prop.business.models.AuthorsCollection;

public interface BusinessController {

    AuthorsCollection searchMatchingAuthors(String authorPrefix);

}
