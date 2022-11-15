package jeanarthur.java.WordDictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static Map<Integer, Runnable> actions = new HashMap<>();
    static Map<String, Boolean> settings = new HashMap<>();
    static String[] wordsLang1 = new String[100];
    static String[] wordsLang2 = new String[100];
    static String[][] dictionary = {wordsLang1, wordsLang2};

    static String lang1 = "Inglês";
    static String lang2 = "Português";

    public static void main(String[] args) {
        registerActions();
        registerSettings();
        do {
            printMenu();
            int actionCode = requestIntInput("| Executar ação nº: ");
            actions.get(actionCode).run();
        } while (settings.get("programIsRunning"));
    }

    public static void registerActions(){
        actions.put(1, Register);
        actions.put(2, Consult);
        actions.put(3, Delete);
        actions.put(4, Edit);
        actions.put(5, Exit);
    }

    public static void registerSettings(){
        settings.put("programIsRunning", true);
    }

    public static void printMenu(){
        System.out.println("+========================+");
        System.out.println("| Dicionário de Palavras |");
        System.out.println("|------------------------|");
        System.out.println("| 1. Cadastrar           |");
        System.out.println("| 2. Consultar           |");
        System.out.println("| 3. Excluir             |");
        System.out.println("| 4. Editar              |");
        System.out.println("| 5. Sair                |");
        System.out.println("+========================+");
    }

    static Runnable Register = () -> {
        int freeIndex = getFreeSpaceIndex();
        dictionary[0][freeIndex] = requestStringInput(String.format("Digite a palavra (%s): ", lang1));
        dictionary[1][freeIndex] = requestStringInput(String.format("Digite a palavra (%s): ", lang2));
    };
    static Runnable Consult = () -> {
        int index = -1;
        String search = requestStringInput("| Consultar: ");
        for (int i = 0; i < dictionary[0].length; i++){
            if (dictionary[0][i].equals(search)){
                index = i;
                break;
            }
        }
        System.out.printf("| %s: %s\n", lang1, (dictionary[0][index]));
        System.out.printf("| %s: %s\n", lang2, (dictionary[1][index]));
    };

    static Runnable Delete = () -> {
        int index = -1;
        String search = requestStringInput("| Excluir: ");
        for (int i = 0; i < dictionary[0].length; i++){
            if (dictionary[0][i].equals(search)){
                index = i;
                break;
            }
        }
        dictionary[0][index] = null;
        dictionary[1][index] = null;
    };

    static Runnable Edit = () -> {
        int index = -1;
        String search = requestStringInput("| Editar: ");
        for (int i = 0; i < dictionary[0].length; i++){
            if (dictionary[0][i].equals(search)){
                index = i;
                break;
            }
        }
        dictionary[1][index] = requestStringInput(String.format("Digite a nova palavra (%s): ", lang2));
    };

    static Runnable Exit = () -> settings.put("programIsRunning", false);

    public static int requestIntInput(String consoleMessage){
        Scanner scanner = new Scanner(System.in);
        System.out.print(consoleMessage);
        return scanner.nextInt();
    }

    public static String requestStringInput(String consoleMessage){
        Scanner scanner = new Scanner(System.in);
        System.out.print(consoleMessage);
        return scanner.nextLine();
    }

    public static int getFreeSpaceIndex(){
        int freeIndex = -1;
        for (int i = 0; i < dictionary[0].length; i++){
            if (dictionary[0][i] == null){
                freeIndex = i;
                break;
            }
        }
        return freeIndex;
    }

}
