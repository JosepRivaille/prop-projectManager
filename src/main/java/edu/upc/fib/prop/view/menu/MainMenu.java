package edu.upc.fib.prop.view.menu;

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
        String data;

        do {
            printHeader("MENU PRINCIPAL");
            System.out.println("1. Realizar búsqueda");
            System.out.println("2. Gestionar documentos");
            System.out.println("3. Ajustes");
            System.out.println("0. Salir");

            System.out.println("> ");
            menuDepth.put(0, scan.nextInt());

            switch (menuDepth.get(0)) {
                case 1:
                    do {
                        printHeader("BUSQUEDA AUTORES Y DOCUMENTOS");
                        System.out.println("1. Buscar autores por prefijo");
                        System.out.println("2. Buscar documentos por autor");
                        System.out.println("3. Buscar documento por titulo y autor");
                        System.out.println("4. Buscar documentos mediante query");
                        System.out.println("0. Volver");

                        System.out.println("> ");
                        menuDepth.put(1, scan.nextInt());

                        switch (menuDepth.get(1)) {
                            case 1:
                                System.out.print("Introduce prefijo > ");
                                data = scan.next();
                                //TODO: Just as an example
                                viewController.searchAuthorByPrefix(data);
                                break;
                            case 2:
                                System.out.print("Introduce nombre autor > ");
                                data = scan.next();
                                System.out.println("POR IMPLEMENTAR");
                                break;
                            case 3:
                                System.out.print("Introduce el titulo > ");
                                data = scan.next();
                                System.out.print("Introduce el autor > ");
                                data = scan.next();
                                System.out.println("POR IMPLEMENTAR");
                                break;
                            case 4:
                                System.out.print("Introduce la query > ");
                                data = scan.next();
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

                        System.out.println("> ");
                        menuDepth.put(1, scan.nextInt());
                    } while (menuDepth.get(1) != 0);
                    break;
                case 3:
                    do {
                        printHeader("AJUSTES");
                        System.out.println("1. Aqui no se que va");
                        System.out.println("0. Volver");

                        System.out.println("> ");
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
