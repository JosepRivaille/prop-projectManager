package edu.upc.fib.prop.business.models;

import java.util.Set;
import java.util.TreeSet;

public class AuthorsCollection {

    private Set<Author> authors;

    public AuthorsCollection() {
        this.authors = new TreeSet<>();
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

}
