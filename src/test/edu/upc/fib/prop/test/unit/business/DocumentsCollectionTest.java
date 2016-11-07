package edu.upc.fib.prop.test.unit.business;

import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import org.junit.Test;

public class DocumentsCollectionTest {

    @Test
    public void whenAddWord_IfExists_ThenIncrement(){
        DocumentsCollection col = new DocumentsCollection();

        Document d1 = new Document("title", "author", "content");
        col.addDocument(d1);

    }
}
