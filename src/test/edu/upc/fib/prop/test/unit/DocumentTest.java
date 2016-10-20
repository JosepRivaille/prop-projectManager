package edu.upc.fib.prop.test.unit;

import edu.upc.fib.prop.business.models.Author;
import edu.upc.fib.prop.business.models.Document;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Guillermo on 18/10/2016.
 */
public class DocumentTest {

    @Test
    public void test() {
        Document d = new Document("Titulo", new Author("AutorPrueba"), "Contenido de prueba");
        assertTrue(true);
    }
}
