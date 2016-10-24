package edu.upc.fib.prop.view.menu;

import edu.upc.fib.prop.business.models.Author;
import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.business.models.Document;
import edu.upc.fib.prop.business.models.DocumentsCollection;
import edu.upc.fib.prop.utils.MenuTree;
import edu.upc.fib.prop.view.controllers.ViewController;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private ViewController viewController;
    private Scanner scan = new Scanner(System.in);

    public MainMenu(ViewController viewController) {
        this.viewController = viewController;
        MenuTree menuTree = buildMenu();
        accountManagement();
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

    private void accountManagement() {
        int optionSelected;
        printHeader("LOGIN OR SIGN UP");
        do {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("> ");
            optionSelected = scan.nextInt();
            if (optionSelected == 1) {
                System.out.print("Email > ");
                String email = scan.next();
                System.out.print("Password > ");
                String password = scan.next();
               if (viewController.userLogin(email, password)) {
                   System.out.println("Welcome " + email + "!");
                   optionSelected = 0;
               } else {
                   System.out.println("Invalid details!");
               }
            } else if (optionSelected == 2) {
                System.out.print("Email > ");
                String email = scan.next();
                System.out.print("Name > ");
                String userName = scan.next();
                System.out.print("Password > ");
                String password = scan.next();
                System.out.print("Repeat password > ");
                String password2 = scan.next();
                if (viewController.userRegister(email, userName, password, password2)) {
                    System.out.println("Welcome " + userName + ", have a great experience!");
                    optionSelected = 0;
                } else {
                    System.out.println("Invalid fields!");
                }
            }
        } while (optionSelected != 0);
    }

    private MenuTree buildMenu() {
        //Level 0
        List<String> options = new ArrayList<>();
        options.add("1. Login");
        options.add("2. Register");
        options.add("0. Exit");

        //Level 1
        options = new ArrayList<>();
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
        AuthorsCollection authorsCollection;
        DocumentsCollection documentsCollection;
        switch (action) {
            case "SearchAuthorPrefix":
                System.out.print("Type prefix > ");
                String prefix = scan.next();
                authorsCollection = viewController.getAuthorsWithPrefix(prefix);
                int i = 0;
                printHeader("AUTHORS FOUND WITH PREFIX " + prefix);
                if (!authorsCollection.getAuthors().isEmpty()) {
                    for (Author author : authorsCollection.getAuthors()) {
                        System.out.println(++i + "- " + author.getName());
                    }
                    System.out.print("Type author id > ");
                    i = scan.nextInt();
                    if (i > 0 && i <= authorsCollection.getAuthors().size()) {
                        String authorName = authorsCollection.getAuthors().get(i - 1).getName();
                        printHeader("DOCUMENTS OF: " + authorName);
                        documentsCollection = viewController.getDocumentsByAuthorId(authorName);
                        i = 0;
                        for (Document document : documentsCollection.getDocuments()) {
                            System.out.println(++i + "- " + document.getTitle());
                        }
                        System.out.print("Type document id > ");
                        i = scan.nextInt();
                        //TODO: Refactor prints and data showing to view classes
                        if (i > 0 && i <= documentsCollection.getDocuments().size()) {
                            Document document = documentsCollection.getDocuments().get(i - 1);
                            String documentTitle = document.getTitle();
                            String documentContent = document.getContent();
                            System.out.println(documentTitle);
                            for (i = 0; i < documentTitle.length(); i++)
                                System.out.print("-");
                            System.out.println(documentContent);
                        }
                    }
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
