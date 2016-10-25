package edu.upc.fib.prop.business.search.impl;

import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.business.search.SearchAuthor;

public class SearchAuthorImpl implements SearchAuthor {

    public AuthorsCollection filterByPrefix(AuthorsCollection authorsCollection, String authorPrefix) {
        AuthorsCollection filteredAuthors = new AuthorsCollection();
        authorsCollection.getAuthors().stream().filter(author ->
                author.getName().matches("(?i)" + authorPrefix + ".*")).forEach(filteredAuthors::addAuthor);
        return filteredAuthors;
    }

}
