package edu.upc.fib.prop.utils;

import java.util.Scanner;

public class IOUtils {

    private static Scanner scan = new Scanner(System.in);

    public static String enterToContinue() {
        printLine("\nPress enter to continue...");
        return scan.nextLine();
    }

    public static String askForString(String text) {
        printQuestion(text);
        return scan.nextLine();
    }

    public static Integer askForInt(String text, int min, int max) {
        printQuestion(text);
        while (true) {
            String strResponse = scan.nextLine();
            if (strResponse.matches(Constants.NUMBER_REGEX)) {
                int response = Integer.parseInt(strResponse);
                if (response >= min && response <= max) {
                    return response;
                }
            } else {
                System.out.println("Invalid input, try it again.");
                printQuestion(text);
            }
        }
    }

    private static void printQuestion(String text) {
        System.out.print(text + " > ");
    }

    public static void printLine(String s){System.out.println(s);}
    public static void drawLine(int longitude){
        for(int i=0;i<longitude;++i){
            System.out.print('-');
        }
        System.out.println();
    }

}
