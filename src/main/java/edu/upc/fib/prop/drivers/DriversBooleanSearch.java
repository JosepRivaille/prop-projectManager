package edu.upc.fib.prop.drivers;

import edu.upc.fib.prop.business.search.SearchBooleanExpression;
import edu.upc.fib.prop.business.search.impl.SearchBooleanExpressionImpl;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.exceptions.InvalidQueryException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.models.DocumentsSet;
import edu.upc.fib.prop.utils.IOUtils;

public class DriversBooleanSearch{

    private static SearchBooleanExpression busqueda = new SearchBooleanExpressionImpl();

    private static void printResult(String s) {
        System.out.println("\n---- " + s + " ----\n");
    }

    public static void main(String[] args) {
        printResult("Insert some documents (-1 to stop)");
        Document doc;
        DocumentsCollection doccol = new DocumentsCollection();
        String name = IOUtils.askForString("Title");
        while (!name.equals("-1")) {
            String content = IOUtils.askForString("Content");
            doc = new Document(name, "author", content );
            try {
                doccol.addDocument(doc);
            } catch (InvalidDetailsException e) {
                System.out.println("invalid details");
            }
            System.out.println("Enter a new document:");
            name = IOUtils.askForString("Title");
        }
        do {
            String expressio = IOUtils.askForString("Insert the boolean expression");
            if (expressio.equals("-1")) break;
            try {
                DocumentsSet compleixen = busqueda.searchDocumentsByBooleanExpression(expressio, doccol);
                if(compleixen.size() == 0) printResult("No documents satisfy the expression");
                else printResult("The following documents satisfy the expression");
                for(Document d : compleixen) System.out.println(d.getTitle());
            } catch (InvalidQueryException e) {
                System.out.println("invalid query");
            }
        } while(true);
    }
}