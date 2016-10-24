package edu.upc.fib.prop.business.search;

import edu.upc.fib.prop.business.models.AuthorsCollection;

public class SearchAuthor {

    /**
     *
     * @param authorsCollection
     * @param authorPrefix
     * @return
     */
    public AuthorsCollection filterByPrefix(AuthorsCollection authorsCollection, String authorPrefix) {
        AuthorsCollection filteredAuthors = new AuthorsCollection();
        authorsCollection.getAuthors().stream().filter(author ->
                author.getName().matches("(?i)" + authorPrefix + ".*")).forEach(filteredAuthors::addAuthor);
        return filteredAuthors;
    }

}
