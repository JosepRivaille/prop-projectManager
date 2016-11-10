package edu.upc.fib.prop.drivers;

import edu.upc.fib.prop.business.documents.DocumentTools;
import edu.upc.fib.prop.exceptions.InvalidDetailsException;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.models.WeightsVector;
import edu.upc.fib.prop.utils.IOUtils;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class DocumentToolsDriver {
    private static void printResult(String s) {
        System.out.println("\n---- " + s + " ----\n");
    }
    private static void testIsCorrect() {
        printResult("Introduce data to test if a document is correct");
        String titol = IOUtils.askForString("Insert tito l");
        String author = IOUtils.askForString("Insert author ");
        String contingut = IOUtils.askForString("Insert contingut ");
        Document doc = new Document(titol,author,contingut,null);
        boolean correct = DocumentTools.isCorrect(doc);
        printResult("Testing if is correct");
        System.out.println("resultat: " + correct);
    }
    private static void testMergeDocs() {
        printResult("Introduce data to merge two documents");
        String titol = IOUtils.askForString("Insert titol document antic");
        String author = IOUtils.askForString("Insert author document antic");
        String contingut = IOUtils.askForString("Insert contingut document antic");
        Document docant = new Document(titol,author,contingut,null);
        titol = IOUtils.askForString("Insert titol document nou");
        author = IOUtils.askForString("Insert author document nou");
        contingut = IOUtils.askForString("Insert contingut document nou");
        Document docnou = new Document(titol,author,contingut,null);
        Document doc = DocumentTools.mergeDocs(docant, docnou);
        printResult("Document updated with the following data");
        System.out.println("autor " + doc.getAuthor());
        System.out.println("titol " + doc.getTitle());
        System.out.println("content" + doc.getContent());
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
            titol = IOUtils.askForString("Inserta el titol del nou document, '-1' si ja has acabat");
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
        System.out.println("El vector de pesos creats és: ");
        for (Map.Entry<String, Float> elem : vec.entrySet()) {
            System.out.println("key = " + elem.getKey() + ", value = " + elem.getValue());
        }
    }
    private static void testGetRelevanceFactor() {
        WeightsVector we1 = new WeightsVector();
        WeightsVector we2 = new WeightsVector();
        Boolean continua = Boolean.TRUE;
        printResult("Inserta els valors desitjats pel primer WeightVector:");
        while(continua) {
            String key = IOUtils.askForString("Inserta una nova paraula (-1 si has acabat)");
            if (key.equals("-1")) continua = Boolean.FALSE;
            else {
                Scanner scan = new Scanner(System.in);
                System.out.print("Inserta el seu pes (coma per indicar decimals) > ");
                Float value = scan.nextFloat();
                we1.put(key,value);
            }
        }
        printResult("Inserta els valors desitjats pel segón WeightVector:");
        continua = Boolean.TRUE;
        while(continua) {
            String key = IOUtils.askForString("Inserta una nova paraula (-1 si has acabat)");
            if (key.equals("-1")) continua = Boolean.FALSE;
            else {
                Scanner scan = new Scanner(System.in);
                System.out.print("Inserta el seu pes (coma per indicar decimals) > ");
                Float value = scan.nextFloat();
                we2.put(key,value);
            }
        }
        double res = DocumentTools.getRelevanceFactor(we1,we2);
        System.out.println("El factor de relevancia és: " + res);


    }
    public static void main(String[] args) {
        do {
            printResult("Select test to perform");
            System.out.println("1- Test Correct document");
            System.out.println("2- Test Merge documents");
            System.out.println("3- Test Get weights");
            System.out.println("4- Test Get relevance factor");
            int option = IOUtils.askForInt("Select an option", 0, 4);
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
                case 4:
                    testGetRelevanceFactor();
                    break;
                default:
                    return;
            }
            IOUtils.enterToContinue();
        } while(true);
    }
}
