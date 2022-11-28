package jeanarthur.java.util;

import jeanarthur.java.WordDictionary.Exception;

import java.util.Arrays;
import java.util.Scanner;

public class InputOperation {

    public static String requestNonDuplicateInputConsidering(String[] array, String consoleMessage){
        String input = requestInput(consoleMessage);
        boolean isDuplicated = Arrays.toString(array).contains(" " + input) ||
                Arrays.toString(array).contains("[" + input);
        if (isDuplicated) { throw Exception.wordAlreadyExists; }
        return input;
    }

    public static String requestInput(String consoleMessage){
        Scanner scanner = new Scanner(System.in);
        System.out.print(consoleMessage);
        return scanner.nextLine();
    }

}
