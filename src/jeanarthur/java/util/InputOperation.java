package jeanarthur.java.util;

import jeanarthur.java.word_dictionary.Exception;

import java.util.Arrays;
import java.util.Scanner;

public class InputOperation {

    public static String requestNonDuplicateInputConsidering(String[] array, String consoleMessage){
        String input = requestInput(consoleMessage);
        String words = Arrays.toString(array);
        boolean isDuplicated =
                words.contains(" " + input + ",") ||
                words.contains("[" + input + ",") ||
                words.contains(" " + input + "]");
        if (isDuplicated) { throw Exception.wordAlreadyExists; }
        return input;
    }

    public static String requestInput(String consoleMessage){
        Scanner scanner = new Scanner(System.in);
        System.out.print(consoleMessage);
        return scanner.nextLine();
    }

}