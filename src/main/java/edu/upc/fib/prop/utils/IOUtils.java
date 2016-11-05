package edu.upc.fib.prop.utils;

import java.util.Scanner;

public class IOUtils {

    private static Scanner scan = new Scanner(System.in);

    public static String askForString(String text) {
        printQuestion(text);
        return scan.next();
    }

    public static Integer askForInt(String text, int min, int max) {
        printQuestion(text);
        int response = -1;
        while (response == -1) {
            response = scan.nextInt();
            if (response < min || response > max) {
                System.out.println("Invalid input, try it again.");
                printQuestion(text);
                response = -1;
            }
        }
        return response;
    }

    private static void printQuestion(String text) {
        System.out.print(text + " > ");
    }

}
