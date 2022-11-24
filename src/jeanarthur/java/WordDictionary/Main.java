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
        Menu.currentMenu = menus.get("main");
        printMenu(Menu.currentMenu);
    }

    public static void registerTestWords(){
        Dictionary.register("casa", "home");
        Dictionary.register("gelo", "ice");
        Dictionary.register("sala", "room");
        Dictionary.register("fazer", "do");
        Dictionary.register("limpar", "clear");
    }

    public static void registerMenus(){
        menus.put("main", new Menu("Dicionário de Palavras"));
        menus.put("settings", new Menu("Configurações"));
        menus.put("wordList", new Menu("Lista de palavras"));
        menus.put("genericAction", new Menu("genericAction"));
    }

    public static void registerActionsInMenus(){
        Menu mainMenu = menus.get("main");
        mainMenu.add(new Action("Cadastrar", register));
        mainMenu.add(new Action("Consultar", consult));
        mainMenu.add(new Action("Editar", edit));
        mainMenu.add(new Action("Excluir", delete));
        mainMenu.add(new Action("Configurar", configure));
        mainMenu.add(new Action("Listar", list));
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

    public static void registerActionsInList(){
        actionsInList.put("consult", consultFromList);
        actionsInList.put("edit", editFromList);
        actionsInList.put("delete", deleteFromList);
    }

    public static void printMenu(Menu menu){
        menu.open();
    }

    static Runnable register = Dictionary::register;
    static Runnable consult = () -> {
        currentActionInList = "consult";
        updateGenericActionMenu("Consultar", Dictionary::consult);
    };
    static Runnable edit = () -> {
        currentActionInList = "edit";
        updateGenericActionMenu("Editar", Dictionary::edit);
    };
    static Runnable delete = () -> {
        currentActionInList = "delete";
        updateGenericActionMenu("Deletar", Dictionary::delete);
    };

    static Runnable configure = () -> {
        Menu.currentMenu = menus.get("settings");
        Menu.currentMenu.open();
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
    };

    static Runnable list = () -> {
        wordListIndex = 0;
        updateList(actionsInList.get(currentActionInList));
        menus.get("wordList").open();
    };

    static void updateGenericActionMenu(String title, Runnable action){
        menus.put("genericAction", new Menu(title));
        Menu generic = menus.get("genericAction");
        generic.add(new Action("Pesquisar", action));
        generic.add(new Action("Listar", list));
        generic.addSeparator();
        generic.add(new Action("Voltar", exit), "v");
        generic.open();
    }

    static void updateList(Consumer<String> actionInList){
        registeredWords = getNotNullValues(dictionary[0]);
        Menu wordList = menus.get("wordList");
        wordList.clear();
        int wordCount = 0;
        for (int i = wordListIndex; i < registeredWords.length; i++){
            wordList.add(new Action(registeredWords[i], actionInList, registeredWords[i]), String.valueOf(i + 1));
            if (++wordCount == 5){ break; }
        }
        wordList.addSeparator();
        if (wordListIndex > 0){ wordList.add(new Action("[ Anterior ]", previousInList), "a"); }
        if (wordListIndex + 5 < registeredWords.length){ wordList.add(new Action("[ Próximo ]", nextInList), "p"); }
        wordList.add(new Action("[ Voltar ]", exit), "v");
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
        if (isDuplicated) { throw new RuntimeException("| Não é possível cadastrar!\n| Palavra já foi registrada\n| para esse idioma."); }
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

    public static boolean isNumeric(String string){
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException numberFormatException){
            return false;
        }
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
