package edu.upc.fib.prop.view.menu;

import edu.upc.fib.prop.business.models.Author;
import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.Document;
import edu.upc.fib.prop.business.models.DocumentsCollection;
import edu.upc.fib.prop.utils.MenuTree;
import edu.upc.fib.prop.view.controllers.ViewController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private ViewController viewController;
    private Scanner scan = new Scanner(System.in);

    public MainMenu(ViewController viewController) {
        this.viewController = viewController;
        MenuTree menuTree = buildMenu();
        displayMenuOptions(menuTree);
        printHeader("CLOSING APP");
    }

    private void displayMenuOptions(MenuTree menuTree) {

        int optionSelected = 0;
        do {
            if (menuTree.isFunctionalNode()) {
                performAction(menuTree.getHeaderText());
            } else {
                printHeader(menuTree.getHeaderText());
                menuTree.getOptions().forEach(System.out::println);
                System.out.print("> ");
                optionSelected = scan.nextInt();
                if (optionSelected != 0 && optionSelected <= menuTree.getChildren().size()) {
                    MenuTree childSelected = menuTree.getChildren().get(optionSelected - 1);
                    displayMenuOptions(childSelected);
                }
            }
        } while (optionSelected != 0);
    }

    private MenuTree buildMenu() {
        //Level 1
        List<String> options = new ArrayList<>();
        options.add("1. Perform search");
        options.add("2. Manage documents");
        options.add("3. Settings");
        options.add("0. Exit");
        MenuTree root = new MenuTree("MAIN MENU", options, false);

        //Level 1.1
        options = new ArrayList<>();
        options.add("1. Search documents by author");
        options.add("2. Search documents by title and relevance");
        options.add("3. Search documents with boolean expression");
        options.add("4. Search documents with query");
        options.add("0. Back");
        root.addChild(new MenuTree("DOCUMENTS SEARCH", options, false));

        //Level 1.1.1
        root.getChildren().get(0).addChild(new MenuTree("SearchAuthorPrefix", null, true));
        //Level 1.1.2
        root.getChildren().get(0).addChild(new MenuTree("SearchDocumentsTitle", null, true));
        //Level 1.1.3
        root.getChildren().get(0).addChild(new MenuTree("SearchDocumentsExpression", null, true));
        //Level 1.1.4
        root.getChildren().get(0).addChild(new MenuTree("SearchDocumentsQuery", null, true));

        //Level 1.2
        options = new ArrayList<>();
        options.add("1. Create new document");
        options.add("2. Read document");
        options.add("3. Edit document");
        options.add("4. Delete document");
        options.add("0. Back");
        root.addChild(new MenuTree("DOCUMENT MANAGER", options, false));

        //Level 1.2.1
        root.getChildren().get(1).addChild(new MenuTree("CreateDocument", null, true));
        //Level 1.2.2
        root.getChildren().get(1).addChild(new MenuTree("ReadDocument", null, true));
        //Level 1.2.3
        root.getChildren().get(1).addChild(new MenuTree("UpdateDocument", null, true));
        //Level 1.2.4
        root.getChildren().get(1).addChild(new MenuTree("DeleteDocument", null, true));

        //Level 1.3
        options = new ArrayList<>();
        options.add("1. ¿?");
        options.add("0. Back");
        root.addChild(new MenuTree("SETTINGS", options, false));

        //Level 1.3.1
        root.getChildren().get(2).addChild(new MenuTree("RandomNow", null, true));

        return root;
    }

    private void performAction(String action) {
        switch (action) {
            case "SearchAuthorPrefix":
                System.out.print("Type prefix > ");
                String prefix = scan.next();
                AuthorsCollection matchingAuthors = viewController.getAuthorsWithPrefix(prefix);
                int i = 0;
                printHeader("AUTHORS FOUND WITH: " + prefix);
                for (Author author : matchingAuthors.getAuthors()) {
                    System.out.println(++i + "- " + author.getName());
                }
                System.out.print("> ");
                i = scan.nextInt();
                String authorName = matchingAuthors.getAuthors().get(i - 1).getName();
                printHeader("DOCUMENTS OF: " + authorName);
                DocumentsCollection documentsCollection = viewController.getDocumentsByAuthorId(authorName);
                for (Document document : documentsCollection.getDocuments()) {
                    System.out.println("- " + document.getTitle());
                }
                break;
            case "SearchDocumentsTitle":

                break;
        }
    }

    private void printHeader(String s) {
        System.out.println();
        for (int i = 0; i < s.length() + 10; ++i)
            System.out.print("-");

        System.out.println();
        for (int i = 0; i < 5; ++i)
            System.out.print(" ");
        System.out.println(s);

        for (int i = 0; i < s.length() + 10; ++i)
            System.out.print("-");
        System.out.println();
    }

}
