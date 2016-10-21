package edu.upc.fib.prop.view;

import java.util.Scanner;

public class Main {
    public static void main( String[] args ){
        Scanner scan = new Scanner(System.in);

        int a = -1;
        String s;

        while(a!=0){
            printHeader("MENU PRINCIPAL");
            System.out.println("1. Realizar búsqueda");
            System.out.println("2. Gestionar documentos");
            System.out.println("3. Ajustes");
            System.out.println("0. Salir");

            System.out.print("> ");
            a = scan.nextInt();

            switch(a){
                case 1:
                    // MENU BUSQUEDA AUTORES Y DOCUMENTOS
                    int b = -1;
                    while(b!=0){
                        printHeader("BUSQUEDA AUTORES Y DOCUMENTOS");
                        System.out.println("1. Buscar autores por prefijo");
                        System.out.println("2. Buscar documentos por autor");
                        System.out.println("3. Buscar documento por titulo y autor");
                        System.out.println("4. Buscar documentos mediante query");
                        System.out.println("0. Volver");

                        System.out.print("> ");
                        b = scan.nextInt();
                        switch(b) {
                            case 1:
                                System.out.print("Introduce prefijo > ");
                                s = scan.next();
                                System.out.println("POR IMPLEMENTAR");
                                break;
                            case 2:
                                System.out.print("Introduce nombre autor > ");
                                s = scan.next();
                                System.out.println("POR IMPLEMENTAR");
                                break;
                            case 3:
                                System.out.print("Introduce el titulo > ");
                                s = scan.next();
                                System.out.print("Introduce el autor > ");
                                s = scan.next();
                                System.out.println("POR IMPLEMENTAR");
                                break;
                            case 4:
                                System.out.print("Introduce la query > ");
                                s = scan.next();
                                System.out.println("POR IMPLEMENTAR");
                                break;
                        }
                    }
                    break;
                case 2:
                    // MENU GESTION DOCUMENTOS
                    int c = -1;
                    while(c!=0){
                        printHeader("GESTION DOCUMENTOS");
                        System.out.println("1. Crear nuevo documento");
                        System.out.println("2. Modificar documento");
                        System.out.println("3. Eliminar documento");
                        System.out.println("0. Volver");

                        System.out.print("> ");
                        c = scan.nextInt();
                    }
                    break;
                case 3:
                    // MENU AJUSTES
                    int d = -1;
                    while(d!=0){
                        printHeader("AJUSTES");
                        System.out.println("1. Aqui no se que va");
                        System.out.println("0. Volver");

                        System.out.print("> ");
                        d = scan.nextInt();
                    }
                    break;
            }
        }

        System.out.println("\nCerrando aplicación..");








    }

    public static void printHeader(String s){
        for(int i=0;i<s.length()+10;++i) System.out.print("-");
        System.out.print("\n");
        for(int i=0;i<5;++i) System.out.print(" ");
        System.out.print(s+"\n");
        for(int i=0;i<s.length()+10;++i) System.out.print("-");
        System.out.print("\n");

    }
}
