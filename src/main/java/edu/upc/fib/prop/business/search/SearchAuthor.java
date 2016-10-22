package edu.upc.fib.prop.business.search;

import edu.upc.fib.prop.business.models.Author;
import edu.upc.fib.prop.business.models.AuthorsCollection;

public class SearchAuthor {

    public AuthorsCollection filterByPrefix(AuthorsCollection authorsCollection, String authorPrefix) {
        AuthorsCollection filteredAuthors = new AuthorsCollection();
        for (Author author : authorsCollection.getAuthors()) {
            if (author.getName().toLowerCase().contains(authorPrefix.toLowerCase())) {
                filteredAuthors.addAuthor(author);
            }
        }
        return filteredAuthors;
    }
}
