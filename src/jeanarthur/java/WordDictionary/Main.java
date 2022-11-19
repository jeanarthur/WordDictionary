package jeanarthur.java.WordDictionary;

import java.util.*;

public class Main {

    static Map<String, Menu> menus = new HashMap<>();
    static Map<String, Setting> settings = new HashMap<>();
    static String[] wordsPrimaryLanguage = new String[100];
    static String[] wordsSecondaryLanguage = new String[100];
    static String[][] dictionary = {wordsPrimaryLanguage, wordsSecondaryLanguage};

    public static void main(String[] args) {
        registerMenus();
        registerActionsInMenus();
        registerSettingsInMenus();
        Menu.currentMenu = menus.get("main");
        printMenu(Menu.currentMenu);
    }

    public static void registerMenus(){
        menus.put("main", new Menu("Dicionário de Palavras"));
        menus.put("settings", new Menu("Configurações"));
    }

    public static void registerActionsInMenus(){
        Menu mainMenu = menus.get("main");
        mainMenu.add(new Action("Cadastrar", register));
        mainMenu.add(new Action("Consultar", consult));
        mainMenu.add(new Action("Excluir", delete));
        mainMenu.add(new Action("Editar", edit));
        mainMenu.add(new Action("Configurar", configure));
        mainMenu.add(new Action("Sair", exit));

        Menu settingsMenu = menus.get("settings");
        settingsMenu.add(new Action("Voltar", exit));

    }

    public static void registerSettingsInMenus(){
        settings.put("programIsRunning", new Setting("Programa em execução","system", 0, "sim", "não"));
        settings.put("primaryLanguage", new Setting("Linguagem Primária","program","Português"));
        settings.put("secondaryLanguage", new Setting("Linguagem Secundária","program","Inglês"));

        Menu settingsMenu = menus.get("settings");
        settingsMenu.add(settings.get("primaryLanguage"));
        settingsMenu.add(settings.get("secondaryLanguage"));
    }

    public static void printMenu(Menu menu){
        menu.open();
    }

    static Runnable register = () -> {
        try{
            int freeIndex = getFreeSpaceIndex();
            dictionary[0][freeIndex] = requestInput(String.format("Digite a palavra (%s): ", settings.get("primaryLanguage").getCurrentState()));
            dictionary[1][freeIndex] = requestInput(String.format("Digite a palavra (%s): ", settings.get("secondaryLanguage").getCurrentState()));
        }catch (NullPointerException nullPointerException){
            throw new RuntimeException("| Não é possível cadastrar.\n| Limite de palavras atingido!");
        }
    };
    static Runnable consult = () -> {
        try {
            int index = -1;
            String search = requestInput("| Consultar: ");
            for (int i = 0; i < dictionary[0].length; i++){
                if (dictionary[0][i].equals(search)){
                    index = i;
                    break;
                }
            }

            System.out.printf("| %s: %s\n", settings.get("primaryLanguage").getCurrentState(), (dictionary[0][index]));
            System.out.printf("| %s: %s\n", settings.get("secondaryLanguage").getCurrentState(), (dictionary[1][index]));
        } catch (NullPointerException nullPointerException){
            throw new RuntimeException("| Palavra não encontrada!");
        }
    };

    static Runnable delete = () -> {
        try{
            int index = -1;
            String search = requestInput("| Excluir: ");
            for (int i = 0; i < dictionary[0].length; i++){
                if (dictionary[0][i].equals(search)){
                    index = i;
                    break;
                }
            }
            dictionary[0][index] = null;
            dictionary[1][index] = null;
        }catch (NullPointerException nullPointerException){
            throw new RuntimeException("| Palavra não encontrada!");
        }
    };

    static Runnable edit = () -> {
        try{
            int index = -1;
            String search = requestInput("| Editar: ");
            for (int i = 0; i < dictionary[0].length; i++){
                if (dictionary[0][i].equals(search)){
                    index = i;
                    break;
                }
            }
            dictionary[1][index] = requestInput(String.format("Digite a nova palavra (%s): ", settings.get("secondaryLanguage").getCurrentState()));
        }catch (NullPointerException nullPointerException){
            throw new RuntimeException("| Palavra não encontrada!");
        }
    };

    static Runnable configure = () -> {
        Menu.currentMenu = menus.get("settings");
        Menu.currentMenu.open();
    };

    static Runnable exit = () -> Menu.currentMenu.close();

    public static String requestInput(String consoleMessage){
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

    public static boolean isNumeric(String string){
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException numberFormatException){
            return false;
        }
    }

}
