package jeanarthur.java.WordDictionary;

import java.util.*;

public class Main {

    static List<Action> actions = new ArrayList<>();
    static Map<String, Menu> menus = new HashMap<>();
    static Map<Integer, Action> currentMenuActions = new HashMap<>();
    static Map<String, Setting> settings = new HashMap<>();
    static String[] wordsPrimaryLanguage = new String[100];
    static String[] wordsSecondaryLanguage = new String[100];
    static String[][] dictionary = {wordsPrimaryLanguage, wordsSecondaryLanguage};

    public static void main(String[] args) {
        registerMenus();
        registerActionsInMenus();
        registerSettings();
        do {
            setCurrentMenuActions(menus.get("main").getActions());
            printMenu(menus.get("main"));
            int actionCode = requestIntInput("| Executar ação nº: ");
            currentMenuActions.get(actionCode).run();
        } while (settings.get("programIsRunning").getCurrentState().equals("sim"));
    }

    public static void setCurrentMenuActions(List<Action> actions){
        currentMenuActions.clear();
        int i = 1;
        for (Action action : actions){
            currentMenuActions.put(i++, action);
        }
    }

    public static void registerMenus(){
        menus.put("main", new Menu("Dicionário de Palavras"));
        menus.put("settings", new Menu("Configurações"));
    }

    public static void registerActionsInMenus(){
        Menu mainMenu = menus.get("main");
        mainMenu.add(new Action("Cadastrar", "mainMenu", Register));
        mainMenu.add(new Action("Consultar", "mainMenu", Consult));
        mainMenu.add(new Action("Excluir", "mainMenu", Delete));
        mainMenu.add(new Action("Editar", "mainMenu", Edit));
        mainMenu.add(new Action("Sair", "mainMenu", Exit));
    }

    private static List<Action> getActionsByGroup(String group){
        List<Action> groupActions = new ArrayList<>();
        for (Action action : actions){
            if (action.getGroup().equals(group)){ groupActions.add(action); }
        }
        return groupActions;
    }

    public static void registerSettings(){
        settings.put("programIsRunning", new Setting("Programa em execução","system", 0, "sim", "não"));
        settings.put("primaryLanguage", new Setting("Linguagem Primária","program","Português"));
        settings.put("secondaryLanguage", new Setting("Linguagem Secundária","program","Inglês"));
    }

    public static void printMenu(Menu menu){
        menu.print();
        /*System.out.println("+========================+");
        System.out.println("| Dicionário de Palavras |");
        System.out.println("|------------------------|");
        System.out.println("| 1. Cadastrar           |");
        System.out.println("| 2. Consultar           |");
        System.out.println("| 3. Excluir             |");
        System.out.println("| 4. Editar              |");
        System.out.println("| 5. Sair                |");
        System.out.println("+========================+");*/
    }

    static Runnable Register = () -> {
        int freeIndex = getFreeSpaceIndex();
        dictionary[0][freeIndex] = requestStringInput(String.format("Digite a palavra (%s): ", settings.get("primaryLanguage").getCurrentState()));
        dictionary[1][freeIndex] = requestStringInput(String.format("Digite a palavra (%s): ", settings.get("secondaryLanguage").getCurrentState()));
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
        System.out.printf("| %s: %s\n", settings.get("primaryLanguage").getCurrentState(), (dictionary[0][index]));
        System.out.printf("| %s: %s\n", settings.get("secondaryLanguage").getCurrentState(), (dictionary[1][index]));
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
        dictionary[1][index] = requestStringInput(String.format("Digite a nova palavra (%s): ", settings.get("secondaryLanguage").getCurrentState()));
    };

    static Runnable Exit = () -> settings.get("programIsRunning").change.run();

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
