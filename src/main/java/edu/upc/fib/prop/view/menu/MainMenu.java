package edu.upc.fib.prop.view.menu;

import edu.upc.fib.prop.business.models.Author;
import edu.upc.fib.prop.business.models.AuthorsCollection;
import edu.upc.fib.prop.view.controllers.ViewController;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class MainMenu {

    private ViewController viewController;

    public MainMenu(ViewController viewController) {
        this.viewController = viewController;
        displayMenuOptions();
    }

    private void displayMenuOptions() {
        Scanner scan = new Scanner(System.in);

        // Map<DepthLevel, SelectedValue>
        Map<Integer, Integer> menuDepth = new TreeMap<>();

        do {
            printHeader("MENU PRINCIPAL");
            System.out.println("1. Realizar búsqueda");
            System.out.println("2. Gestionar documentos");
            System.out.println("3. Ajustes");
            System.out.println("0. Salir");

            System.out.print("> ");
            menuDepth.put(0, scan.nextInt());

            switch (menuDepth.get(0)) {
                case 1:
                    do {
                        printHeader("BUSQUEDA DE DOCUMENTOS");
                        System.out.println("1. Buscar documentos por autor");
                        System.out.println("2. Buscar documentos por titulo y relevancia");
                        System.out.println("3. Buscar documentos mediante expresión booleana");
                        System.out.println("4. Buscar documentos mediante query");
                        System.out.println("0. Volver");

                        System.out.print("> ");
                        menuDepth.put(1, scan.nextInt());

                        switch (menuDepth.get(1)) {
                            case 1:
                                System.out.print("Introduce prefijo > ");
                                String prefix = scan.next();
                                AuthorsCollection matchingAuthors = viewController.getAuthorsWithPrefix(prefix);
                                for (Author author : matchingAuthors.getAuthors()) {
                                    System.out.println(author.getName());
                                }
                                break;
                            case 2:
                                System.out.print("Introduce nombre autor > ");
                                break;
                            case 3:
                                System.out.print("Introduce el titulo > ");
                                String title = scan.next();
                                System.out.print("Introduce el autor > ");
                                break;
                            case 4:
                                System.out.print("Introduce la query > ");
                                System.out.println("POR IMPLEMENTAR");
                                break;
                        }

                    } while (menuDepth.get(1) != 0);
                    break;
                case 2:
                    do {
                        printHeader("GESTION DOCUMENTOS");
                        System.out.println("1. Crear nuevo documento");
                        System.out.println("2. Modificar documento");
                        System.out.println("3. Eliminar documento");
                        System.out.println("0. Volver");

                        System.out.print("> ");
                        menuDepth.put(1, scan.nextInt());
                    } while (menuDepth.get(1) != 0);
                    break;
                case 3:
                    do {
                        printHeader("AJUSTES");
                        System.out.println("1. Aqui no se que va");
                        System.out.println("0. Volver");

                        System.out.print("> ");
                        menuDepth.put(1, scan.nextInt());
                    } while (menuDepth.get(1) != 0);
                    break;
            }

        } while (menuDepth.get(0) != 0);
        printHeader("CERRANDO APLICACION");
    }

    private static void printHeader(String s) {
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
