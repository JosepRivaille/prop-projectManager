package edu.upc.fib.prop.business.search;

import edu.upc.fib.prop.business.models.AuthorsCollection;

public class SearchAuthor {

    public AuthorsCollection filterByPrefix(AuthorsCollection authorsCollection, String authorPrefix) {
        AuthorsCollection filteredAuthors = new AuthorsCollection();
        authorsCollection.getAuthors().stream().filter(author ->
                author.getName().toLowerCase().contains(authorPrefix.toLowerCase()))
                .forEach(filteredAuthors::addAuthor);
        return filteredAuthors;
    }
}
