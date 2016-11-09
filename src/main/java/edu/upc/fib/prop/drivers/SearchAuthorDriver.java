package edu.upc.fib.prop.drivers;


import edu.upc.fib.prop.business.search.SearchAuthor;
import edu.upc.fib.prop.business.search.impl.SearchAuthorImpl;
import edu.upc.fib.prop.exceptions.AuthorNotFoundException;
import edu.upc.fib.prop.models.Author;
import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.utils.IOUtils;

public class SearchAuthorDriver {

    private static void printResult(String s) { System.out.println("\n---- " + s + " ----\n"); }

    private static SearchAuthor searchAuthor = new SearchAuthorImpl();

    private static AuthorsCollection fillAuthorsCollection() {
        AuthorsCollection authorsCollection = new AuthorsCollection();
        System.out.println("Enter -1 to stop inserting");
        do {
            printResult("Enter a new author");
            String author = IOUtils.askForString("Author");
            if(author.equals("-1")) { return authorsCollection; }
            authorsCollection.addAuthor(new Author(author));
        }
        while(true);
    }

    private static void testfilterByPrefix(AuthorsCollection authorsCollection) {
        String prefix = IOUtils.askForString("Type prefix");
        try {
            AuthorsCollection filteredAuthors = searchAuthor.filterByPrefix(authorsCollection, prefix);
            int i = 0;
            printResult("Authors found with prefix " + prefix);
            for (Author author : filteredAuthors.getAuthors()) {
                System.out.println(++i + "- " + author.getName());
            }
        }   catch (AuthorNotFoundException e) {
            System.out.println("No authors found");
        }
    }

    public static void main(String[] args) {
        do {
            AuthorsCollection authorsCollection = new AuthorsCollection();
            authorsCollection = fillAuthorsCollection();
            testfilterByPrefix(authorsCollection);
            IOUtils.enterToContinue();
        } while(true);
    }
}
