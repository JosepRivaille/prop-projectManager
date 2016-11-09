package edu.upc.fib.prop.test.unit.business;

import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import org.junit.Test;

public class DocumentsCollectionTest {

    @Test
    public void whenAddWord_IfExists_ThenIncrement() throws InvalidDetailsException{
        DocumentsCollection col = new DocumentsCollection();

        Document d1 = new Document("title", "author", "content");
        col.addDocument(d1);

    }
}
