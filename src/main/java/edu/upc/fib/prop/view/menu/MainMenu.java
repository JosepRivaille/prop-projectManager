package edu.upc.fib.prop.view.menu;

import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.Author;
import edu.upc.fib.prop.models.AuthorsCollection;
import edu.upc.fib.prop.models.Document;
import edu.upc.fib.prop.models.DocumentsCollection;
import edu.upc.fib.prop.utils.FileUtils;
import edu.upc.fib.prop.utils.IOUtils;
import edu.upc.fib.prop.utils.MenuTree;
import edu.upc.fib.prop.view.controllers.ViewController;
import edu.upc.fib.prop.view.document.DocumentManager;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    private ViewController viewController;

    private DocumentManager documentManager;

    public MainMenu(ViewController viewController) {
        this.viewController = viewController;
        MenuTree menuTree = buildMenu();
        if (accountManagement()) {
            DocumentsCollection myDocuments = this.viewController.getCurrentUserDocuments();
            documentManager = new DocumentManager(myDocuments);
            displayMenuOptions(menuTree);
        }
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
                // TODO: Set variable max input value
                optionSelected = IOUtils.askForInt("Select an option", 0, 10);
                if (optionSelected != 0 && optionSelected <= menuTree.getChildren().size()) {
                    MenuTree childSelected = menuTree.getChildren().get(optionSelected - 1);
                    displayMenuOptions(childSelected);
                }
            }
        } while (optionSelected != 0);
    }

    private boolean accountManagement() {
        int optionSelected;
        String email, userName, password, password2;
        printHeader("LOGIN OR SIGN UP");
        do {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            optionSelected = IOUtils.askForInt("Select an option", 0, 2);
            switch (optionSelected) {
                case 1:
                    email = IOUtils.askForString("Email");
                    password = IOUtils.askForString("Password");
                    try {
                        viewController.userLogin(email, password);
                        System.out.println("Welcome " + email + "!");
                        return true;
                    } catch (UserNotFoundException e) {
                        System.out.println("Non existing user in the system.");
                    } catch (InvalidDetailsException e){
                        System.out.println("Invalid user - password combination!");
                    }
                    break;
                case 2:
                    email = IOUtils.askForString("Email");
                    userName = IOUtils.askForString("Name");
                    password = IOUtils.askForString("Password");
                    password2 = IOUtils.askForString("Repeat password");
                    try {
                        viewController.userRegister(email, userName, password, password2);
                        System.out.println("Welcome " + email + "!");
                        return true;
                    } catch (AlreadyExistingUserException e) {
                        System.out.println("This user is already in system.");
                    } catch (InvalidDetailsException e){
                        System.out.println("Invalid input data!");
                    }
                    break;
            }
        } while (optionSelected != 0);
        return false;
    }

    private MenuTree buildMenu() {
        List<String> options;

        //Level 1
        options = new ArrayList<>();
        options.add("1. Perform search");
        options.add("2. Manage my documents");
        options.add("3. Settings");
        options.add("0. Exit");
        MenuTree root = new MenuTree("MAIN MENU", options, false);

        //Level 1.
        options = new ArrayList<>();

        options.add("1. Search documents by author");
        options.add("2. Search documents by title and author");
        options.add("3. Search documents by document and relevance");
        options.add("4. Search documents with boolean expression");
        options.add("5. Search documents with query");
        options.add("0. Back");
        root.addChild(new MenuTree("DOCUMENTS SEARCH", options, false));

        //Level 1.1
        root.getChildren().get(0).addChild(new MenuTree("SearchAuthorPrefix", null, true));
        //Level 1.2
        root.getChildren().get(0).addChild(new MenuTree("SearchDocumentTitleAndAuthor", null, true));
        //Level 1.3
        root.getChildren().get(0).addChild(new MenuTree("SearchDocumentsRelevance", null, true));
        //Level 1.4
        root.getChildren().get(0).addChild(new MenuTree("SearchDocumentsExpression", null, true));
        //Level 1.5
        root.getChildren().get(0).addChild(new MenuTree("SearchDocumentsQuery", null, true));

        //Level 2
        options = new ArrayList<>();
        options.add("1. Create new document");
        options.add("2. Read document");
        options.add("3. Edit document");
        options.add("4. Delete document");
        options.add("0. Back");
        root.addChild(new MenuTree("DOCUMENT MANAGER", options, false));

        //Level 2.1
        root.getChildren().get(1).addChild(new MenuTree("CreateDocument", null, true));
        //Level 2.2
        root.getChildren().get(1).addChild(new MenuTree("ReadDocument", null, true));
        //Level 2.3
        root.getChildren().get(1).addChild(new MenuTree("UpdateDocument", null, true));
        //Level 2.4
        root.getChildren().get(1).addChild(new MenuTree("DeleteDocument", null, true));

        //Level 3
        options = new ArrayList<>();
        options.add("1. Edit account");
        options.add("2. Delete account");
        options.add("3. Logout");
        options.add("0. Back");
        root.addChild(new MenuTree("SETTINGS", options, false));

        //Level 3.1
        root.getChildren().get(2).addChild(new MenuTree("EditAccount", null, true));
        //Level 3.2
        root.getChildren().get(2).addChild(new MenuTree("EditAccount", null, true));
        //Level 3.3
        root.getChildren().get(2).addChild(new MenuTree("EditAccount", null, true));

        return root;
    }

    private void performAction(String action) {
        AuthorsCollection authorsCollection;
        DocumentsCollection documentsCollection;
        switch (action) {
            case "SearchDocumentTitleAndAuthor":
                String documentTitle = IOUtils.askForString("Type document title");
                String authorName = IOUtils.askForString("Type author");
                try {
                    Document matchingDocument = viewController.getDocumentByTitleAndAuthor(documentTitle, authorName);
                    System.out.println(documentTitle.toUpperCase() + " - " + authorName);
                    System.out.println(FileUtils.readDocument(matchingDocument.getContent()));
                } catch (DocumentNotFoundException e) {
                    System.out.println("No documents found!");
                }

                break;
            case "SearchAuthorPrefix":
                String prefix = IOUtils.askForString("Type prefix");
                try {
                    authorsCollection = viewController.getAuthorsWithPrefix(prefix);
                    int i = 0;
                    printHeader("AUTHORS FOUND WITH PREFIX " + prefix);
                    if (!authorsCollection.getAuthors().isEmpty()) {
                        for (Author author : authorsCollection.getAuthors()) {
                            System.out.println(++i + "- " + author.getName());
                        }
                        i = IOUtils.askForInt("Choose author", 1, authorsCollection.getAuthors().size());
                        if (i > 0 && i <= authorsCollection.getAuthors().size()) {
                            authorName = authorsCollection.getAuthors().get(i - 1).getName();
                            printHeader("DOCUMENTS OF: " + authorName);
                            try {
                                documentsCollection = viewController.getDocumentsByAuthorId(authorName);
                                i = 0;
                                for (Document document : documentsCollection.getDocuments()) {
                                    System.out.println(++i + "- " + document.getTitle());
                                }
                                i = IOUtils.askForInt("Choose document", 1, documentsCollection.getDocuments().size());
                                if (i > 0 && i <= documentsCollection.getDocuments().size()) {
                                    Document document = documentsCollection.getDocuments().get(i - 1);
                                    documentTitle = document.getTitle();
                                    String documentFile = document.getContent();
                                    String documentContent = FileUtils.readDocument(documentFile);
                                    System.out.print("\n");
                                    System.out.println(documentTitle.toUpperCase());
                                    for (i = 0; i < documentTitle.length(); i++)
                                        System.out.print("-");
                                    System.out.print("\n");
                                    System.out.println(documentContent);
                                    IOUtils.enterToContinue();
                                }
                            } catch (DocumentNotFoundException e) {
                                System.out.println("This author currently has no books in the system!");
                            }
                        }
                    }
                } catch (AuthorNotFoundException e) {
                    System.out.println("This prefix did not matched any author!");
                }
                break;
            case "SearchDocumentsTitle":
                break;

            case "CreateDocument":
                Document document = documentManager.createDocument();
                try {
                    viewController.storeNewDocument(document);
                    this.documentManager.addDocumentToCollection(document);
                    System.out.println("Document created successfully.");
                } catch (AlreadyExistingDocumentException | DocumentNotFoundException e) {
                    System.out.println("Document couldn't be created!");
                }
                DocumentsCollection myDocuments = this.viewController.getCurrentUserDocuments();
                documentManager.setDocumentsCollection(myDocuments);
                break;

            case "ReadDocument":
                documentManager.readDocument();
                break;

            case "UpdateDocument":
                try {
                    Pair<Document, Document> updatedDocument = documentManager.updateDocument();
                    viewController.updateDocument(updatedDocument);
                    myDocuments = viewController.getCurrentUserDocuments();
                    documentManager.setDocumentsCollection(myDocuments);
                } catch (DocumentNotFoundException e) {
                    System.out.println("No documents found, try to create one.");
                } catch (InvalidDetailsException e) {
                    System.out.println("Updated details are invalid, try it again.");
                }
                break;

            case "DeleteDocument":
                try {
                    document = documentManager.deleteDocument();
                    viewController.deleteDocument(document);
                    myDocuments = viewController.getCurrentUserDocuments();
                    documentManager.setDocumentsCollection(myDocuments);
                } catch (DocumentNotFoundException e) {
                    System.out.println("No documents found, try to create one.");
                }
                break;
            default:
                System.out.println("Not implemented yet!");
        }
    }

    private void printHeader(String s) {
        System.out.println();
        for (int i = 0; i < s.length() + 10; ++i) {
            System.out.print("-");
        }

        System.out.println();
        for (int i = 0; i < 5; ++i) {
            System.out.print(" ");
        }
        System.out.println(s);

        for (int i = 0; i < s.length() + 10; ++i) {
            System.out.print("-");
        }
        System.out.println();
    }

}
