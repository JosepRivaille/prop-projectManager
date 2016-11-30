package edu.upc.fib.prop.view.menu;

import edu.upc.fib.prop.exceptions.*;
import edu.upc.fib.prop.models.*;
import edu.upc.fib.prop.utils.FileUtils;
import edu.upc.fib.prop.utils.IOUtils;
import edu.upc.fib.prop.utils.MenuTree;
import edu.upc.fib.prop.utils.Strings;
import edu.upc.fib.prop.view.controllers.ViewController;
import edu.upc.fib.prop.view.controllers.impl.ViewControllerImpl;
import edu.upc.fib.prop.view.document.DocumentManager;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    private ViewController viewController;

    private DocumentManager documentManager;

    public MainMenu() {
        this.viewController = new ViewControllerImpl();
        MenuTree menuTree = buildMenu();
        if (accountManagement()) {
            DocumentsCollection myDocuments = this.viewController.getCurrentUserDocuments();
            documentManager = new DocumentManager(myDocuments);
            displayMenuOptions(menuTree);
        }
        System.out.println(Strings.CLOSING_APP);
    }

    private void displayMenuOptions(MenuTree menuTree) {

        int optionSelected = 0;
        do {
            if (menuTree.isFunctionalNode()) {
                performAction(menuTree.getHeaderText());
            } else {
                printHeader(menuTree.getHeaderText());
                menuTree.getOptions().forEach(System.out::println);
                System.out.println();
                optionSelected = IOUtils.askForInt(Strings.SELECT_AN_OPTION, 0, menuTree.getChildren().size());
                System.out.println();
                if (optionSelected != 0 && optionSelected <= menuTree.getChildren().size()) {
                    MenuTree childSelected = menuTree.getChildren().get(optionSelected - 1);
                    displayMenuOptions(childSelected);
                }
            }
        } while (optionSelected != 0);
        System.out.println();
    }

    private boolean accountManagement() {
        int optionSelected;
        String email, userName, password, password2;
        printHeader(Strings.LOGIN_SIGNUP_HEADER);
        do {
            System.out.println("1. " + Strings.LOGIN);
            System.out.println("2. " + Strings.SIGNUP);
            System.out.println("0. " + Strings.EXIT);
            System.out.println();
            optionSelected = IOUtils.askForInt(Strings.SELECT_AN_OPTION, 0, 2);
            switch (optionSelected) {
                case 1:
                    email = IOUtils.askForString(Strings.EMAIL);
                    password = IOUtils.askForString(Strings.PASSWORD);
                    try {
                        viewController.userLogin(email, password);
                        System.out.println(Strings.WELCOME + " " + email + "!");
                        return true;
                    } catch (UserNotFoundException e) {
                        System.out.println(Strings.USER_NOT_FOUND);
                    } catch (InvalidDetailsException e){
                        System.out.println(Strings.INVALID_CREDENTIALS);
                    }
                    IOUtils.enterToContinue();
                    break;
                case 2:
                    email = IOUtils.askForString(Strings.EMAIL);
                    userName = IOUtils.askForString(Strings.NAME);
                    password = IOUtils.askForString(Strings.PASSWORD);
                    password2 = IOUtils.askForString(Strings.REPEAT_PASSWORD);
                    try {
                        viewController.userRegister(email, userName, password, password2);
                        System.out.println(Strings.WELCOME + " " + email + "!");
                        return true;
                    } catch (AlreadyExistingUserException e) {
                        System.out.println(Strings.USER_ALREADY_EXISTS);
                    } catch (InvalidDetailsException e){
                        System.out.println(Strings.INVALID_INPUT_DATA);
                    }
                    IOUtils.enterToContinue();
                    break;
            }
        } while (optionSelected != 0);
        return false;
    }

    private MenuTree buildMenu() {
        List<String> options;

        //Level 1
        options = new ArrayList<>();
        options.add("1. " + Strings.PERFORM_SEARCH);
        options.add("2. " + Strings.MANAGE_MY_DOCUMENTS);
        options.add("3. " + Strings.TOOLS);
        options.add("4. " + Strings.SETTINGS);
        options.add("0. " + Strings.EXIT);
        MenuTree root = new MenuTree(Strings.MAIN_MENU_HEADER, options, false);

        //Level 1.
        options = new ArrayList<>();

        options.add("1. " + Strings.SEARCH_DOCUMENTS_BY_AUTHOR);
        options.add("2. " + Strings.SEARCH_DOCUMENTS_BY_TITLE_AND_AUTHOR);
        options.add("3. " + Strings.SEARCH_DOCUMENTS_BY_RELEVANCE);
        options.add("4. " + Strings.SEARCH_DOCUMENTS_BY_BOOLEAN_EXPRESSION);
        options.add("5. " + Strings.SEARCH_DOCUMENTS_BY_QUERY);
        options.add("6. " + Strings.SEARCH_ALL_DOCUMENTS);
        options.add("0. " + Strings.GO_BACK);
        root.addChild(new MenuTree(Strings.DOCUMENTS_SEARCH_HEADER, options, false));

        //Level 1.1
        root.getChildren().get(0).addChild(new MenuTree(Strings.SEARCH_DOCUMENTS_BY_AUTHOR, null, true));
        //Level 1.2
        root.getChildren().get(0).addChild(new MenuTree(Strings.SEARCH_DOCUMENTS_BY_TITLE_AND_AUTHOR, null, true));
        //Level 1.3
        root.getChildren().get(0).addChild(new MenuTree(Strings.SEARCH_DOCUMENTS_BY_RELEVANCE, null, true));
        //Level 1.4
        root.getChildren().get(0).addChild(new MenuTree(Strings.SEARCH_DOCUMENTS_BY_BOOLEAN_EXPRESSION, null, true));
        //Level 1.5
        root.getChildren().get(0).addChild(new MenuTree(Strings.SEARCH_DOCUMENTS_BY_QUERY, null, true));
        //Level 1.6
        root.getChildren().get(0).addChild(new MenuTree(Strings.SEARCH_ALL_DOCUMENTS, null, true));

        //Level 2
        options = new ArrayList<>();
        options.add("1. " + Strings.CREATE_DOCUMENT);
        options.add("2. " + Strings.READ_DOCUMENT);
        options.add("3. " + Strings.EDIT_DOCUMENT);
        options.add("4. " + Strings.DELETE_DOCUMENT);
        options.add("0. " + Strings.GO_BACK);
        root.addChild(new MenuTree(Strings.DOCUMENTS_MANAGER_HEADER, options, false));

        //Level 2.1
        root.getChildren().get(1).addChild(new MenuTree(Strings.CREATE_DOCUMENT, null, true));
        //Level 2.2
        root.getChildren().get(1).addChild(new MenuTree(Strings.READ_DOCUMENT, null, true));
        //Level 2.3
        root.getChildren().get(1).addChild(new MenuTree(Strings.EDIT_DOCUMENT, null, true));
        //Level 2.4
        root.getChildren().get(1).addChild(new MenuTree(Strings.DELETE_DOCUMENT, null, true));

        //Level 3
        options = new ArrayList<>();
        options.add("1. " + Strings.IMPORT_DOCUMENT);
        options.add("2. " + Strings.EXPORT_DOCUMENT);
        options.add("0. " + Strings.GO_BACK);
        root.addChild(new MenuTree(Strings.DOCUMENTS_MANAGER_HEADER, options, false));

        //Level 3.1
        root.getChildren().get(2).addChild(new MenuTree(Strings.IMPORT_DOCUMENT, null, true));
        //Level 3.2
        root.getChildren().get(2).addChild(new MenuTree(Strings.EXPORT_DOCUMENT, null, true));

        //Level 4
        options = new ArrayList<>();
        options.add("1. " + Strings.EDIT_ACCOUNT);
        options.add("0. " + Strings.GO_BACK);
        root.addChild(new MenuTree(Strings.SETTINGS_HEADER, options, false));

        //Level 4.1
        root.getChildren().get(3).addChild(new MenuTree(Strings.EDIT_ACCOUNT, null, true));
        //Level 4.2
        root.getChildren().get(3).addChild(new MenuTree(Strings.DELETE_ACCOUNT, null, true));

        return root;
    }

    private void performAction(String action) {
        AuthorsCollection authorsCollection;
        DocumentsCollection documentsCollection;
        DocumentsSet documentsSet;
        String authorName, documentTitle;
        String email, userName, password;

        switch (action) {

            case Strings.SEARCH_DOCUMENTS_BY_AUTHOR:
                String prefix = IOUtils.askForString(Strings.TYPE_PREFIX);
                System.out.println();
                try {
                    authorsCollection = viewController.getAuthorsWithPrefix(prefix);
                    int i = 0;
                    printHeader(Strings.AUTHORS_FOUND_WITH_PREFIX + " '" + prefix + "'");
                    if (!authorsCollection.getAuthors().isEmpty()) {
                        for (Author author : authorsCollection.getAuthors()) {
                            System.out.printf("%-3d %-45s %n",++i, author.getName());
                        }
                        System.out.println();
                        i = IOUtils.askForInt(Strings.CHOOSE_AUTHOR, 1, authorsCollection.getAuthors().size());
                        System.out.println();
                        if (i > 0 && i <= authorsCollection.getAuthors().size()) {
                            authorName = authorsCollection.getAuthors().get(i - 1).getName();
                            printHeader(Strings.DOCUMENTS_OF+ " '" + authorName + "'");
                            try {
                                documentsCollection = viewController.getDocumentsByAuthorId(authorName);
                                i = 0;
                                for (Document document : documentsCollection.getDocuments()) {
                                    System.out.printf("%-3d %-45s %n",++i, document.getTitle());
                                }
                                System.out.println();
                                i = IOUtils.askForInt(Strings.CHOOSE_DOCUMENT, 1, documentsCollection.getDocuments().size());
                                System.out.println();
                                Document document = documentsCollection.getDocuments().get(i - 1);
                                documentTitle = document.getTitle();
                                String documentFile = document.getContent();
                                String documentContent = null;
                                try {
                                    documentContent = FileUtils.readDocument(documentFile);
                                } catch (DocumentContentNotFoundException e) {
                                    System.out.println(Strings.DOCUMENT_CONTENT_NOT_FOUND);
                                }
                                System.out.println("\n" + documentTitle.toUpperCase() + " | " + document.getAuthor());
                                int space = documentTitle.length() + document.getAuthor().length() + 3;
                                for (i = 0; i < space; i++) {
                                    System.out.print("-");
                                }
                                System.out.print("\n");
                                IOUtils.printContent(documentContent);
                            } catch (DocumentNotFoundException e) {
                                System.out.println(Strings.AUTHOR_HAS_NO_BOOKS);
                            }
                        }
                    }
                } catch (AuthorNotFoundException e) {
                    System.out.println(Strings.PREFIX_DONT_MATCH);
                }
                IOUtils.enterToContinue();
                break;

            case Strings.SEARCH_DOCUMENTS_BY_TITLE_AND_AUTHOR:
                documentTitle = IOUtils.askForString(Strings.TYPE_DOCUMENT_TITLE);
                authorName = IOUtils.askForString(Strings.TYPE_AUTHOR);
                System.out.println();
                try {
                    Document matchingDocument = viewController.getDocumentByTitleAndAuthor(documentTitle, authorName);
                    System.out.println("\n" + documentTitle.toUpperCase() + " | " + matchingDocument.getAuthor());
                    int size = documentTitle.length()+matchingDocument.getAuthor().length()+3;
                    for (int i = 0; i < size; i++) {
                        System.out.print("-");
                    }
                    System.out.println();
                    IOUtils.printContent(FileUtils.readDocument(matchingDocument.getContent()));
                } catch (DocumentNotFoundException e) {
                    System.out.println(Strings.NO_DOCUMENTS_FOUND);
                } catch (DocumentContentNotFoundException e) {
                    System.out.println(Strings.DOCUMENT_CONTENT_NOT_FOUND);
                }
                IOUtils.enterToContinue();
                break;

            case Strings.SEARCH_DOCUMENTS_BY_BOOLEAN_EXPRESSION:
                String booleanExpression = IOUtils.askForString(Strings.TYPE_THE_BOOLEAN_EXPRESSION);
                try {
                    documentsSet = viewController.getDocumentsByBooleanExpression(booleanExpression);
                    int i = 0;
                    for (Document document : documentsSet) {
                        System.out.printf("%-3d %-48s %-30s %-25s %n", i++,
                                document.getTitle(), document.getAuthor(), document.getUser());
                    }
                } catch(InvalidQueryException e) {
                    System.out.println();
                    System.out.println(Strings.INVALID_BOOLEAN_EXPRESSION);
                }

                IOUtils.enterToContinue();
                break;

            case Strings.SEARCH_DOCUMENTS_BY_QUERY:
                String query = IOUtils.askForString(Strings.TYPE_QUERY);
                int k2 = IOUtils.askForInt(Strings.TYPE_NUMBER_OF_DOCUMENTS, 1, 10000);
                try {
                    SortedDocumentsSet list = viewController.searchDocumentsByQuery(query, k2);
                    IOUtils.drawLine(100);
                    System.out.printf("    %-10s %-45s %-25s %n", "S.Factor", "Title", "Author");
                    IOUtils.drawLine(100);
                    for(int kk = 0; kk< list.getSize();++kk){
                        System.out.printf("%-3d %-10s %-45s %-25s %n",kk+1, String.format("%.2f", list.getValue(kk)),
                                list.getDocument(kk).getTitle(), list.getDocument(kk).getAuthor());
                    }
                } catch (DocumentNotFoundException e) {
                     System.out.println(Strings.NO_DOCUMENTS_FOUND);
                }
                IOUtils.enterToContinue();
                break;

            case Strings.SEARCH_DOCUMENTS_BY_RELEVANCE:

                DocumentsSet allDocuments = viewController.searchForAllDocuments();
                IOUtils.drawLine(120);
                System.out.printf("    %-48s %-30s %-25s %n", "Title", "Author", "Created by");
                IOUtils.drawLine(120);
                int i=0;
                for (Document doc : allDocuments) {
                    System.out.printf("%-3d %-48s %-30s %-25s %n", ++i, doc.getTitle(), doc.getAuthor(), doc.getUser());
                }
                System.out.println();
                int docNum = IOUtils.askForInt(Strings.SELECT_A_DOCUMENT, 1, allDocuments.size());
                int k = IOUtils.askForInt(Strings.TYPE_NUMBER_OF_DOCUMENTS, 1, 10000);
                System.out.println();
                try {
                    Document matchingDocument = null;
                    i=1;
                    for (Document doc : allDocuments) {
                        if(i==docNum) matchingDocument = doc;
                        ++i;
                    }
                    SortedDocumentsSet list = viewController.getDocumentsByRelevance(matchingDocument, k);
                    IOUtils.drawLine(100);
                    System.out.printf("    %-10s %-45s %-25s %n", "S.Factor", "Title", "Author");
                    IOUtils.drawLine(100);
                    for(int kk = 0; kk< list.getSize();++kk){
                        System.out.printf("%-3d %-10s %-45s %-25s %n",kk+1, String.format("%.2f", list.getValue(kk)),
                                list.getDocument(kk).getTitle(), list.getDocument(kk).getAuthor());
                    }
                } catch (DocumentNotFoundException e) {
                    System.out.println(Strings.NO_DOCUMENTS_FOUND);
                }
                IOUtils.enterToContinue();
                break;

            case Strings.SEARCH_ALL_DOCUMENTS:
                DocumentsSet allDocs = viewController.searchForAllDocuments();
                IOUtils.drawLine(120);
                System.out.printf("    %-48s %-30s %-29s %n", "Title", "Author", "Created by");
                IOUtils.drawLine(120);
                int ii=0;
                for (Document doc : allDocs) {
                    System.out.printf("%-3d %-48s %-30s %-29s %n", ++ii, doc.getTitle(), doc.getAuthor(), doc.getUser());
                }
                break;

            case Strings.CREATE_DOCUMENT:
                Document document = documentManager.createDocument();
                try {
                    viewController.storeNewDocument(document);
                    this.documentManager.addDocumentToCollection(document);
                    System.out.println();
                    System.out.println(Strings.DOCUMENT_CREATED_SUCCESSFULLY);
                } catch (AlreadyExistingDocumentException e) {
                    System.out.println();
                    System.out.println(Strings.DOCUMENT_NOT_CREATED_ALREADY_EXISTS);
                } catch (InvalidDetailsException e){
                    System.out.println();
                    System.out.println(Strings.DOCUMENT_NOT_CREATED_INVALID_DETAILS);
                } catch (DocumentNotFoundException e){
                    System.out.println();
                    System.out.println(Strings.DOCUMENT_NOT_CREATED);
                } catch (DocumentContentNotFoundException e) {
                    System.out.println();
                    System.out.println(Strings.CREATE_FAILED_DOCUMENT_CONTENT_NOT_FOUND);
                }
                DocumentsCollection myDocuments = this.viewController.getCurrentUserDocuments();
                documentManager.setDocumentsCollection(myDocuments);
                IOUtils.enterToContinue();
                break;

            case Strings.READ_DOCUMENT:
                documentManager.readDocument();
                IOUtils.enterToContinue();
                break;

            case Strings.EDIT_DOCUMENT:
                try {
                    Pair<Document, Document> updatedDocument = documentManager.updateDocument();
                    viewController.updateDocument(updatedDocument);
                    myDocuments = viewController.getCurrentUserDocuments();
                    documentManager.setDocumentsCollection(myDocuments);
                    System.out.println(Strings.DOCUMENT_UPDATED_SUCCESSFULY);
                } catch (DocumentNotFoundException e) {
                    System.out.println(Strings.NO_DOCUMENTS_FOUND);
                } catch (InvalidDetailsException e) {
                    System.out.println(Strings.UPDATE_FAILED_INVALID_DETAILS);
                } catch (AlreadyExistingDocumentException e) {
                    System.out.println(Strings.UPDATE_FAILED_DOCUMENT_ALREADY_EXISTS);
                } catch (DocumentContentNotFoundException e) {
                    System.out.println(Strings.UPDATE_FAILED_DOCUMENT_CONTENT_NOT_FOUND);
                }
                IOUtils.enterToContinue();
                break;

            case Strings.DELETE_DOCUMENT:
                try {
                    document = documentManager.deleteDocument();
                    viewController.deleteDocument(document);
                    myDocuments = viewController.getCurrentUserDocuments();
                    documentManager.setDocumentsCollection(myDocuments);
                    System.out.println(Strings.DOCUMENT_DELETED_SUCCESSFULLY);
                } catch (DocumentNotFoundException e) {
                    System.out.println(Strings.NO_DOCUMENTS_FOUND);
                }
                IOUtils.enterToContinue();
                break;

            case Strings.EDIT_ACCOUNT:
                email = IOUtils.askForString(Strings.EMAIL);
                userName = IOUtils.askForString(Strings.NAME);
                password = IOUtils.askForString(Strings.PASSWORD);
                System.out.println();
                try {
                    viewController.userUpdate(email, userName, password);
                    System.out.println(Strings.ACCOUNT_DETAILS_UPDATED_SUCCESSFULLY);
                    System.out.println();
                } catch (InvalidDetailsException | UserNotFoundException e) {
                    System.out.println(Strings.INVALID_DETAILS);
                } catch (AlreadyExistingUserException e) {
                    System.out.println(Strings.DETAILS_USED_BY_ANOTHER_USER);
                }
                IOUtils.enterToContinue();
                break;

//            case Strings.DELETE_ACCOUNT:
//                try {
//                    viewController.userDelete();
//                } catch (UserNotFoundException e) {
//                    System.out.println(Strings.UNABLE_DELETE_USER);
//                }
//                break;

//            case "Logout":
//                viewController.userLogout();
//                break;

            case Strings.IMPORT_DOCUMENT:
                String path = IOUtils.askForString(Strings.DOCUMENT_TO_IMPORT_PATH);

                try {

                    Document importedDoc = viewController.importDocument(path);
                    this.documentManager.addDocumentToCollection(importedDoc);

                    System.out.println();
                    System.out.println(Strings.DOCUMENT_IMPORTED_SUCCESSFULLY);
                } catch (ImportExportException e) {
                    System.out.println(Strings.ERROR_IMPORTING_THE_DOCUMENT);
                } catch (AlreadyExistingDocumentException e) {
                    System.out.println();
                    System.out.println(Strings.DOCUMENT_NOT_CREATED_ALREADY_EXISTS);
                } catch (InvalidDetailsException e){
                    System.out.println();
                    System.out.println(Strings.DOCUMENT_NOT_CREATED_INVALID_DETAILS);
                } catch (DocumentContentNotFoundException e) {
                    System.out.println();
                    System.out.println(Strings.CREATE_FAILED_DOCUMENT_CONTENT_NOT_FOUND);
                }
                break;
            case Strings.EXPORT_DOCUMENT:
                documentTitle = IOUtils.askForString(Strings.TYPE_DOCUMENT_TITLE);
                authorName = IOUtils.askForString(Strings.TYPE_AUTHOR);
                String pathToExport = IOUtils.askForString(Strings.TYPE_PATH_TO_EXPORT);
                String os;
                os = IOUtils.askForString(Strings.ARE_YOU_USING_UNIX_OR_WINDOWS);
                while(!os.toLowerCase().equals("w") && !os.toLowerCase().equals("u")) {
                    os = IOUtils.askForString(Strings.ARE_YOU_USING_UNIX_OR_WINDOWS);
                }
                System.out.println();
                try {
                    Document matchingDocument = viewController.getDocumentByTitleAndAuthor(documentTitle, authorName);
                    viewController.exportDocument(pathToExport, matchingDocument, os);
                    System.out.println();
                    System.out.println(Strings.DOCUMENT_EXPORTED_SUCCESSFULLY);
                } catch (DocumentNotFoundException e) {
                    System.out.println();
                    System.out.println(Strings.NO_DOCUMENTS_FOUND);
                } catch (ImportExportException e) {
                    System.out.println();
                    System.out.println(Strings.ERROR_EXPORTING_THE_DOCUMENT);
                } catch (DocumentContentNotFoundException e) {
                    System.out.println(Strings.DOCUMENT_CONTENT_NOT_FOUND);
                }
                IOUtils.enterToContinue();
                break;

            default:
                System.out.println(Strings.NOT_IMPLEMENTED_YET);
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





