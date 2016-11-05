package edu.upc.fib.prop.test.unit.business;

import edu.upc.fib.prop.business.search.SearchAuthor;
import edu.upc.fib.prop.business.search.SearchDocument;
import edu.upc.fib.prop.business.search.impl.SearchAuthorImpl;
import edu.upc.fib.prop.business.search.impl.SearchDocumentImpl;
import edu.upc.fib.prop.exceptions.AuthorNotFoundException;
import edu.upc.fib.prop.exceptions.DocumentNotFoundException;
import edu.upc.fib.prop.models.Author;
import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SearchTest {

    private SearchAuthor searchAuthor;
    private SearchDocument searchDocument;

    @Before
    public void setup() {
        this.searchAuthor = new SearchAuthorImpl();
        this.searchDocument = new SearchDocumentImpl();
    }

    /*--------------- Author */

    @Test(expected = AuthorNotFoundException.class)
    public void test_whenFilterAuthorsByPrefix_withNonMatchingResults_thenThrowNotFoundException()
            throws AuthorNotFoundException {
        AuthorsCollection authorsCollection = createAuthorsCollection();
        String prefix = "Fake";

        searchAuthor.filterByPrefix(authorsCollection, prefix);
    }

    @Test
    public void test_whenFilterAuthorsByPrefix_withAMatchingResult_thenReturnCollectionOfSize1()
            throws AuthorNotFoundException {
        AuthorsCollection authorsCollection = createAuthorsCollection();
        String prefix = "Rom";

        AuthorsCollection expected = new AuthorsCollection();
        expected.addAuthor(new Author("Roman"));

        AuthorsCollection result = searchAuthor.filterByPrefix(authorsCollection, prefix);

        assertTrue(expected.equals(result));
    }

    @Test
    public void test_whenFilterAuthorsByPrefix_withNMatchingResults_thenReturnCollectionOfSizeN()
            throws AuthorNotFoundException {
        AuthorsCollection authorsCollection = createAuthorsCollection();
        String prefix = "Jo";

        AuthorsCollection expected = new AuthorsCollection();
        expected.addAuthor(new Author("John"));
        expected.addAuthor(new Author("Joe"));
        expected.addAuthor(new Author("Josep"));

        AuthorsCollection result = searchAuthor.filterByPrefix(authorsCollection, prefix);

        assertTrue(expected.equals(result));
    }

    /*--------------- Documents */

    @Test(expected = DocumentNotFoundException.class)
    public void test_whenFilterDocumentsByAuthor_withNonMatchingResults_thenThrowNotFoundException()
            throws DocumentNotFoundException {
        DocumentsCollection documentsCollection = createDocumentsCollection();
        String authorName = "Marx";

        searchDocument.filterByAuthor(documentsCollection, authorName);
    }

    @Test
    public void test_whenFilterDocumentsByAuthor_withAMatchingResult_thenReturnCollectionOfSize1()
            throws DocumentNotFoundException {
        DocumentsCollection documentsCollection = createDocumentsCollection();
        String authorName = "Child";

        DocumentsCollection expected = new DocumentsCollection();
        expected.addDocument(new Document("Via di fuga", "Child", "viadifuga.txt", "bar@upc.edu"));

        DocumentsCollection result = searchDocument.filterByAuthor(documentsCollection, authorName);

        assertTrue(expected.equals(result));
    }

    @Test
    public void test_whenFilterDocumentsByAuthor_withNMatchingResults_thenReturnCollectionOfSizeN()
            throws DocumentNotFoundException {
        DocumentsCollection documentsCollection = createDocumentsCollection();
        String authorName = "Tolkien";

        DocumentsCollection expected = new DocumentsCollection();
        expected.addDocument(new Document("TLOTR", "Tolkien", "tlotr.txt", "foo@upc.edu"));
        expected.addDocument(new Document("Hobbit", "Tolkien", "hobbit.txt", "bar@upc.edu"));

        DocumentsCollection result = searchDocument.filterByAuthor(documentsCollection, authorName);

        assertTrue(expected.equals(result));
    }

    @Test
    public void test_whenFilterDocumentsByUser_withNonMatchingResults_thenReturnEmptyCollection()
            throws DocumentNotFoundException {
        DocumentsCollection documentsCollection = createDocumentsCollection();
        String user = "Marx";

        DocumentsCollection expected = new DocumentsCollection();

        DocumentsCollection result = searchDocument.filterByUser(documentsCollection, user);
        assertTrue(expected.equals(result));
    }

    @Test
    public void test_whenFilterDocumentsByUser_withAMatchingResult_thenReturnCollectionOfSize1()
            throws DocumentNotFoundException {
        DocumentsCollection documentsCollection = createDocumentsCollection();
        String user = "foo@upc.edu";

        DocumentsCollection expected = new DocumentsCollection();
        expected.addDocument(new Document("TLOTR", "Tolkien", "tlotr.txt", "foo@upc.edu"));

        DocumentsCollection result = searchDocument.filterByUser(documentsCollection, user);

        assertTrue(expected.equals(result));
    }

    @Test
    public void test_whenFilterDocumentsByUser_withNMatchingResults_thenReturnCollectionOfSizeN()
            throws DocumentNotFoundException {
        DocumentsCollection documentsCollection = createDocumentsCollection();
        String user = "bar@upc.edu";

        DocumentsCollection expected = new DocumentsCollection();
        expected.addDocument(new Document("Hobbit", "Tolkien", "hobbit.txt", "bar@upc.edu"));
        expected.addDocument(new Document("Via di fuga", "Child", "viadifuga.txt", "bar@upc.edu"));

        DocumentsCollection result = searchDocument.filterByUser(documentsCollection, user);

        assertTrue(expected.equals(result));
    }

    @Test(expected = DocumentNotFoundException.class)
    public void test_whenFilterDocumentsByTitleAndAuthor_withNonMatchingResult_thenThrowNotFoundException()
            throws DocumentNotFoundException {
        DocumentsCollection documentsCollection = createDocumentsCollection();
        String title = "Foo";
        String author = "Bar";

        searchDocument.filterByTitleAndAuthor(documentsCollection, title, author);
    }

    @Test
    public void test_whenFilterDocumentsByTitleAndAuthor_withMatchingResult_thenReturnDocument()
            throws DocumentNotFoundException {
        DocumentsCollection documentsCollection = createDocumentsCollection();
        String title = "TLOTR";
        String author = "Tolkien";

        Document expected = new Document("TLOTR", "Tolkien", "tlotr.txt", "foo@upc.edu");

        Document result = searchDocument.filterByTitleAndAuthor(documentsCollection, title, author);

        assertTrue(expected.equals(result));
    }

    //////////

    private AuthorsCollection createAuthorsCollection() {
        AuthorsCollection authorsCollection = new AuthorsCollection();
        authorsCollection.addAuthor(new Author("John"));
        authorsCollection.addAuthor(new Author("Joe"));
        authorsCollection.addAuthor(new Author("Jack"));
        authorsCollection.addAuthor(new Author("Roman"));
        authorsCollection.addAuthor(new Author("Greek"));
        authorsCollection.addAuthor(new Author("Josep"));
        authorsCollection.addAuthor(new Author("Guillermo"));
        authorsCollection.addAuthor(new Author("Gabriel"));
        authorsCollection.addAuthor(new Author("Aleix"));
        return authorsCollection;
    }

    private DocumentsCollection createDocumentsCollection() {
        DocumentsCollection documentsCollection = new DocumentsCollection();
        documentsCollection.addDocument(new Document("TLOTR", "Tolkien", "tlotr.txt", "foo@upc.edu"));
        documentsCollection.addDocument(new Document("Hobbit", "Tolkien", "hobbit.txt", "bar@upc.edu"));
        documentsCollection.addDocument(new Document("Via di fuga", "Child", "viadifuga.txt", "bar@upc.edu"));
        return documentsCollection;
    }

}
