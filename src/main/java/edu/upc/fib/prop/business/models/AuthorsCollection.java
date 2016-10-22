package edu.upc.fib.prop.business.models;

import java.util.HashSet;
import java.util.Set;

public class AuthorsCollection {

    private Set<Author> authors;

    public AuthorsCollection() {
        this.authors = new HashSet<>();
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

}
