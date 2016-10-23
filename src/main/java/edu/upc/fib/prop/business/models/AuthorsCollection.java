package edu.upc.fib.prop.business.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AuthorsCollection {

    private List<Author> authors;

    public AuthorsCollection() {
        this.authors = new ArrayList<>();
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

}
