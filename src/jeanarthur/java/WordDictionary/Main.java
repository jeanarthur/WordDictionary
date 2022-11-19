package jeanarthur.java.WordDictionary;

import java.util.*;

public class Main {

    static Map<String, Menu> menus = new HashMap<>();
    static Map<Integer, Action> currentMenuActions = new HashMap<>();
    static Map<Character, Setting> currentMenuSettings = new HashMap<>();
    static Map<String, Setting> settings = new HashMap<>();
    static String[] wordsPrimaryLanguage = new String[100];
    static String[] wordsSecondaryLanguage = new String[100];
    static String[][] dictionary = {wordsPrimaryLanguage, wordsSecondaryLanguage};

    public static void main(String[] args) {
        registerMenus();
        registerActionsInMenus();
        registerSettingsInMenus();
        do {
            Menu mainMenu = menus.get("main");
            setCurrentMenuActions(mainMenu.getActions());
            setCurrentMenuSettings(mainMenu.getSettings());
            printMenu(mainMenu);

            String actionCode = requestInput("| Executar ação nº: ");
            if (isNumeric(actionCode)) {
                currentMenuActions.get(Integer.parseInt(actionCode)).run();
            } else {
                currentMenuSettings.get(actionCode.toCharArray()[0]).change();
            }
        } while (settings.get("programIsRunning").getCurrentState().equals("sim"));
    }

    public static void setCurrentMenuActions(List<Action> actions){
        currentMenuActions.clear();
        int i = 1;
        for (Action action : actions){
            currentMenuActions.put(i++, action);
        }
    }

    public static void setCurrentMenuSettings(List<Setting> settings){
        currentMenuSettings.clear();
        char i = 'a';
        for (Setting setting : settings){
            currentMenuSettings.put(i++, setting);
        }
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
        mainMenu.add(new Action("Sair", exit));
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
        menu.print();
    }

    static Runnable register = () -> {
        int freeIndex = getFreeSpaceIndex();
        dictionary[0][freeIndex] = requestInput(String.format("Digite a palavra (%s): ", settings.get("primaryLanguage").getCurrentState()));
        dictionary[1][freeIndex] = requestInput(String.format("Digite a palavra (%s): ", settings.get("secondaryLanguage").getCurrentState()));
    };
    static Runnable consult = () -> {
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
    };

    static Runnable delete = () -> {
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
    };

    static Runnable edit = () -> {
        int index = -1;
        String search = requestInput("| Editar: ");
        for (int i = 0; i < dictionary[0].length; i++){
            if (dictionary[0][i].equals(search)){
                index = i;
                break;
            }
        }
        dictionary[1][index] = requestInput(String.format("Digite a nova palavra (%s): ", settings.get("secondaryLanguage").getCurrentState()));
    };


    static Runnable exit = () -> settings.get("programIsRunning").change();

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
