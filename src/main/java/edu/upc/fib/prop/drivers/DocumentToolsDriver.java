package edu.upc.fib.prop.drivers;

import edu.upc.fib.prop.business.documents.DocumentTools;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.models.WeightsVector;
import edu.upc.fib.prop.utils.IOUtils;

import javax.lang.model.element.Element;
import java.util.Map;
import java.util.TreeMap;

public class DocumentToolsDriver {
    private static void printResult(String s) {
        System.out.println("\n---- " + s + " ----\n");
    }
    private static void testIsCorrect() {
        printResult("Introduce data to test if a document is correct");
        String titol = IOUtils.askForString("Insert titol");
        String author = IOUtils.askForString("Insert author");
        String contingut = IOUtils.askForString("Insert contingut");
        Document doc = new Document(titol,author,contingut,null);
        boolean correct = DocumentTools.isCorrect(doc);
        printResult("Testing if is correct");
        System.out.println("resultat" + correct);
    }
    private static void testMergeDocs() {
        printResult("Introduce data to merge two documents");
        String titol = IOUtils.askForString("Insert titol document antic");
        String author = IOUtils.askForString("Insert author document antic");
        String contingut = IOUtils.askForString("Insert contingut document antic");
        String usuari = IOUtils.askForString("Insert usuari document antic (ha de ser el mateix pels dos)");
        Document docant = new Document(titol,author,contingut,usuari);
        titol = IOUtils.askForString("Insert titol document nou");
        author = IOUtils.askForString("Insert author document nou");
        contingut = IOUtils.askForString("Insert contingut document nou");
        usuari = IOUtils.askForString("Insert usuari document nou (ha de ser el mateix pels dos)");
        Document docnou = new Document(titol,author,contingut,usuari);
        Document doc = DocumentTools.mergeDocs(docant, docnou);
        printResult("Document updated with the following data");
        System.out.println("autor " + doc.getAuthor());
        System.out.println("titol" + doc.getTitle());
        System.out.println("content" + doc.getContent());
        System.out.println("user" + doc.getUser());
    }
    //AQUESTA FUNCIO ES CACA
    private static void testGetWeights(){
        printResult("Introduce data to test if a document is correct");
        String titol=IOUtils.askForString("Insert titol");
        String author=IOUtils.askForString("Insert author");
        String contingut=IOUtils.askForString("Insert contingut");
        Document doc=new Document(titol,author,contingut,null);
        printResult("A continuació la colecció de documents:");
        Boolean continua=Boolean.TRUE;
        DocumentsCollection col=new DocumentsCollection();
        while(continua) {
            titol = IOUtils.askForString("Inserta el titol del nou document, '-1' en cas de que no vulguis afegir mes documents a la colecció");
            if (titol.equals("-1")) continua = Boolean.FALSE;
            else {
                author = IOUtils.askForString("Insert author");
                contingut = IOUtils.askForString("Insert contingut");
                doc = new Document(titol, author, contingut, null);
                try {
                    col.addDocument(doc);
                } catch (InvalidDetailsException e) {
                    e.printStackTrace();
                }
            }
        }
        WeightsVector wei=DocumentTools.getWeights(doc,col);
        String K;
        Float V;
        TreeMap<String, Float> vec = wei.getVector();
        System.out.println("El vector de pesos creats és:");
        for (Map.Entry<String, Float> elem : vec.entrySet()) {
            System.out.println("key = " + elem.getKey() + ", value = " + elem.getValue());
        }
    }
    public static void main(String[] args) {
        do {
            printResult("Select test to perform");
            System.out.println("1- Test Correct document");
            System.out.println("2- Test Merge documents");
            System.out.println("3- Test Get weights");
            int option = IOUtils.askForInt("Select an option", 0, 3);
            switch (option) {
                case 1:
                    testIsCorrect();
                    break;
                case 2:
                    testMergeDocs();
                    break;
                case 3:
                    testGetWeights();
                    break;
                default:
                    return;
            }
            IOUtils.enterToContinue();
        } while(true);
    }
}
