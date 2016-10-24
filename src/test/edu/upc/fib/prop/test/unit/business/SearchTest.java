package edu.upc.fib.prop.test.unit.business;

import edu.upc.fib.prop.business.models.Author;
import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.Document;
import edu.upc.fib.prop.business.models.DocumentsCollection;
import edu.upc.fib.prop.business.search.SearchAuthor;
import edu.upc.fib.prop.business.search.SearchDocument;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SearchTest {

    private static SearchAuthor searchAuthor = new SearchAuthor();
    private static SearchDocument searchDocument = new SearchDocument();

    @Test
    public void test_whenSearchForAuthorWithPrefix_withMatchingPrefix_thenReturnCollectionWithMatchingAuthors() {
        AuthorsCollection fakeAuthorCollection = buildAuthors();
        String fakePrefix = "xyz";
        String fakePrefix1 = "jo";
        String fakePrefix2 = "j";

        assertTrue(searchAuthor.filterByPrefix(fakeAuthorCollection, fakePrefix).getAuthors().isEmpty());
        assertTrue(searchAuthor.filterByPrefix(fakeAuthorCollection, fakePrefix1).getAuthors().size() == 1);
        assertTrue(searchAuthor.filterByPrefix(fakeAuthorCollection, fakePrefix2).getAuthors().size() == 2);
    }

    @Test
    public void test_whenSearchForDocumentWithAuthor_withExistingAuthor_thenReturnCollectionWithMatchingDocuments() {
        DocumentsCollection fakeDocumentsCollection = buildDocuments();
        String fakeName = "John";
        String fakeName1 = "Jane";
        String fakeName2 = "Glen";

        assertTrue(searchDocument.filterByAuthor(fakeDocumentsCollection, fakeName).getDocuments().size() == 2);
        assertTrue(searchDocument.filterByAuthor(fakeDocumentsCollection, fakeName1).getDocuments().size() == 1);
        assertTrue(searchDocument.filterByAuthor(fakeDocumentsCollection, fakeName2).getDocuments().size() == 0);
    }

    // Private helper methods

    private AuthorsCollection buildAuthors() {
        AuthorsCollection fakeAuthorCollection = new AuthorsCollection();
        fakeAuthorCollection.addAuthor(new Author("John"));
        fakeAuthorCollection.addAuthor(new Author("Jane"));
        fakeAuthorCollection.addAuthor(new Author("Glen"));
        return fakeAuthorCollection;
    }

    private DocumentsCollection buildDocuments() {
        DocumentsCollection fakeDocumentsCollection = new DocumentsCollection();
        fakeDocumentsCollection.addDocument(new Document("PROP", "John", "Blah blah blah"));
        fakeDocumentsCollection.addDocument(new Document("IES", "John", "Blah blah blah"));
        fakeDocumentsCollection.addDocument(new Document("EDA", "Jane", "Blah blah blah"));
        return fakeDocumentsCollection;
    }

}
