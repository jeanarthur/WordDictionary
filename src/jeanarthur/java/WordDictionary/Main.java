package jeanarthur.java.WordDictionary;

import java.util.*;
import java.util.function.Consumer;

public class Main {

    static Map<String, Menu> menus = new HashMap<>();
    static Map<String, Setting> settings = new HashMap<>();
    static String[] wordsPrimaryLanguage = new String[100];
    static String[] wordsSecondaryLanguage = new String[100];
    static String[][] dictionary = {wordsPrimaryLanguage, wordsSecondaryLanguage};
    static Map<String, Consumer<String>> actionsInList = new HashMap<>();
    static String currentActionInList;
    static String[] registeredWords;
    static int wordListIndex = 0;

    public static void main(String[] args) {
        registerTestWords();
        registerMenus();
        registerActionsInMenus();
        registerSettingsInMenus();
        registerActionsInList();
        registerInstructionsMenu();
        Menu.currentMenu = menus.get("instructions");
        printMenu(Menu.currentMenu);
        Menu.currentMenu = menus.get("main");
        printMenu(Menu.currentMenu);
    }

    public static void registerTestWords(){
        Dictionary.register("home", "casa");
        Dictionary.register("ice", "gelo");
        Dictionary.register("room", "sala");
        Dictionary.register("do", "fazer");
        Dictionary.register("clear", "limpar");
    }

    public static void registerMenus(){
        menus.put("main", new Menu("Dicionário de Palavras"));
        menus.put("settings", new Menu("Configurações"));
        menus.put("wordList", new Menu("Lista de palavras"));
        menus.put("consultedWord", new Menu("Palavra consultada"));
        menus.put("genericAction", new Menu("genericAction"));
        menus.put("instructions", new Menu("Instrução"));
    }

    public static void registerActionsInMenus(){
        Menu mainMenu = menus.get("main");
        mainMenu.add(new Action("Cadastrar", register));
        mainMenu.add(new Action("Consultar", consult));
        mainMenu.add(new Action("Editar", edit));
        mainMenu.add(new Action("Excluir", delete));
        //mainMenu.add(new Action("Configurar", configure));
        mainMenu.add(new Action("Sair", exit));

        Menu settingsMenu = menus.get("settings");
        settingsMenu.add(new Action("Voltar", exit));

    }

    public static void registerSettingsInMenus(){
        settings.put("programIsRunning", new Setting("Programa em execução", 0, "sim", "não"));
        settings.put("primaryLanguage", new Setting("Linguagem Primária","Inglês"));
        settings.put("secondaryLanguage", new Setting("Linguagem Secundária","Português"));
        settings.put("activeLanguage", new Setting("Pesquisar/Listar em", settings.get("primaryLanguage").getCurrentState(), settings.get("secondaryLanguage").getCurrentState()));

        Menu settingsMenu = menus.get("settings");
        settingsMenu.add(settings.get("primaryLanguage"));
        settingsMenu.add(settings.get("secondaryLanguage"));

    }

    public static void registerActionsInList(){
        actionsInList.put("consult", consultFromList);
        actionsInList.put("edit", editFromList);
        actionsInList.put("delete", deleteFromList);
    }

    public static void registerInstructionsMenu(){
        Menu instructions = menus.get("instructions");
        instructions.add("""
                Para interagir com as funções do sistema deve-se
                digitar o número ou a letra que corresponda a
                opção/operação desejada.
                """);
        instructions.addSeparator();
        instructions.add(new Action("Prosseguir", exit));
    }

    public static void printMenu(Menu menu){
        menu.open();
    }

    static Runnable register = Dictionary::register;
    static Runnable consult = () -> {
        currentActionInList = "consult";
        updateGenericActionMenu("Consultar", Dictionary::consult);
        menus.get("consultedWord").open();
    };
    static Runnable edit = () -> {
        currentActionInList = "edit";
        updateGenericActionMenu("Editar", Dictionary::edit);
    };
    static Runnable delete = () -> {
        currentActionInList = "delete";
        updateGenericActionMenu("Excluir", Dictionary::delete);
    };

    static Consumer<String> consultFromList = (String word) -> {
        Dictionary.consult(word);
        updateList(actionsInList.get(currentActionInList));
    };

    static Consumer<String> editFromList = (String word) -> {
        Dictionary.edit(word);
        updateList(actionsInList.get(currentActionInList));
    };

    static Consumer<String> deleteFromList = (String word) -> {
        Dictionary.delete(word);
        updateList(actionsInList.get(currentActionInList));
        Menu.currentMenu.close();
    };

    static Runnable list = () -> {
        wordListIndex = 0;
        updateList(actionsInList.get(currentActionInList));
        menus.get("wordList").open();
    };


    static void updateConsultedWordMenu(String[] words){
        Menu consulted = menus.get("consultedWord");
        consulted.clear();
        consulted.add(String.format("%s: %s\n", Main.settings.get("primaryLanguage").getCurrentState(), words[0]));
        consulted.add(String.format("%s: %s\n", Main.settings.get("secondaryLanguage").getCurrentState(), words[1]));
        consulted.addSeparator();
        consulted.add(new Action("Editar", editFromList, words[settings.get("activeLanguage").getStateValue()]), "a");
        consulted.add(new Action("Excluir", deleteFromList, words[settings.get("activeLanguage").getStateValue()]), "b");
        consulted.add(new Action("Voltar", exit), "v");
    }

    static void updateGenericActionMenu(String title, Runnable action){
        menus.put("genericAction", new Menu(title));
        settings.put("activeLanguage", new Setting("Pesquisar/Listar em", settings.get("primaryLanguage").getCurrentState(), settings.get("secondaryLanguage").getCurrentState()));
        Menu generic = menus.get("genericAction");
        generic.add(new Action("Pesquisar", action));
        generic.add(new Action("Listar", list));
        generic.addSeparator();
        generic.add(settings.get("activeLanguage"));
        generic.addSeparator();
        generic.add(new Action("Voltar", exit), "v");
        generic.open();
    }

    static void updateList(Consumer<String> actionInList){
        registeredWords = getNotNullValues(dictionary[settings.get("activeLanguage").getStateValue()]);
        Menu wordList = menus.get("wordList");
        wordList.clear();
        int wordCount = 0;
        for (int i = wordListIndex; i < registeredWords.length; i++){
            wordList.add(new Action(registeredWords[i], actionInList, registeredWords[i]), String.valueOf(i + 1));
            if (++wordCount == 5){ break; }
        }
        wordList.addSeparator();
        if (wordListIndex > 0){ wordList.add(new Action("Anterior", previousInList), "a"); }
        if (wordListIndex + 5 < registeredWords.length){ wordList.add(new Action("Próximo", nextInList), "p"); }
        wordList.add(new Action("Voltar", exit), "v");
    }

    static Runnable nextInList = () -> {
        wordListIndex += (wordListIndex + 5 < registeredWords.length) ? 5 : registeredWords.length - wordListIndex;
        updateList(actionsInList.get(currentActionInList));
    };
    static Runnable previousInList = () -> {
        wordListIndex -= 5;
        updateList(actionsInList.get(currentActionInList));
    };
    static Runnable exit = () -> Menu.currentMenu.close();

    public static String requestNonDuplicatedInputIn(String[] stringArray, String consoleMessage){
        String input = requestInput(consoleMessage);
        boolean isDuplicated = Arrays.toString(stringArray).contains(" " + input) ||
                               Arrays.toString(stringArray).contains("[" + input);
        if (isDuplicated) { throw Exception.wordAlreadyExists; }
        return input;
    }

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

    public static String[] getNotNullValues(String[] array){
        String[] notNullValues = new String[array.length];
        int i = 0;

        for (String string : array){
            if (string != null){
                notNullValues[i++] = string;
            }
        }

        return Arrays.copyOfRange(notNullValues, 0, i);
    }

}
