package jeanarthur.java.WordDictionary;

import java.util.*;
import java.util.function.Consumer;

public class Main {

    
    static Map<String, Setting> settings = new HashMap<>();
    static Map<String, Consumer<String>> actionsInList = new HashMap<>();
    static String currentActionInList;

    public static void main(String[] args) {
        registerTestWords();
        registerMenus();
        registerActionsInMenus();
        registerSettingsInMenus();
        registerActionsInList();
        registerInstructionsMenu();
        Menu.currentMenu = Menu.get("instructions");
        printMenu(Menu.currentMenu);
        Menu.currentMenu = Menu.get("main");
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
        Menu.put("main", new Menu("Dicionário de Palavras"));
        Menu.put("settings", new Menu("Configurações"));
        Menu.put("wordList", new Menu("Lista de palavras"));
        Menu.put("consultedWord", new Menu("Palavra consultada"));
        Menu.put("genericAction", new Menu("genericAction"));
        Menu.put("instructions", new Menu("Instrução"));
    }

    public static void registerActionsInMenus(){
        Menu mainMenu = Menu.get("main");
        mainMenu.add(new Action("Cadastrar", Dictionary.register));
        mainMenu.add(new Action("Consultar", Dictionary.consult));
        mainMenu.add(new Action("Editar", Dictionary.edit));
        mainMenu.add(new Action("Excluir", Dictionary.delete));
        mainMenu.add(new Action("Sair", Menu.exit));

        Menu settingsMenu = Menu.get("settings");
        settingsMenu.add(new Action("Voltar", Menu.exit));

    }

    public static void registerSettingsInMenus(){
        settings.put("programIsRunning", new Setting("Programa em execução", 0, "sim", "não"));
        settings.put("primaryLanguage", new Setting("Linguagem Primária","Inglês"));
        settings.put("secondaryLanguage", new Setting("Linguagem Secundária","Português"));
        settings.put("activeLanguage", new Setting("Pesquisar/Listar em", settings.get("primaryLanguage").getCurrentState(), settings.get("secondaryLanguage").getCurrentState()));

        Menu settingsMenu = Menu.get("settings");
        settingsMenu.add(settings.get("primaryLanguage"));
        settingsMenu.add(settings.get("secondaryLanguage"));

    }

    public static void registerActionsInList(){
        actionsInList.put("consult", Dictionary.consultFromList);
        actionsInList.put("edit", Dictionary.editFromList);
        actionsInList.put("delete", Dictionary.deleteFromList);
    }

    public static void registerInstructionsMenu(){
        Menu instructions = Menu.get("instructions");
        instructions.add("""
                Para interagir com as funções do sistema deve-se
                digitar o número ou a letra que corresponda a
                opção/operação desejada.
                """);
        instructions.addSeparator();
        instructions.add(new Action("Prosseguir", Menu.exit));
    }

    public static void printMenu(Menu menu){
        menu.open();
    }
    
    static void updateConsultedWordMenu(String[] words){
        Menu consulted = Menu.get("consultedWord");
        consulted.clear();
        consulted.add(String.format("%s: %s\n", Main.settings.get("primaryLanguage").getCurrentState(), words[0]));
        consulted.add(String.format("%s: %s\n", Main.settings.get("secondaryLanguage").getCurrentState(), words[1]));
        consulted.addSeparator();
        consulted.add(new Action("Editar", Dictionary.editFromList, words[settings.get("activeLanguage").getStateValue()]), "a");
        consulted.add(new Action("Excluir", Dictionary.deleteFromConsult, words[settings.get("activeLanguage").getStateValue()]), "b");
        consulted.add(new Action("Voltar", Menu.exit), "v");
    }

    static void updateGenericActionMenu(String title, Runnable action){
        Menu.put("genericAction", new Menu(title));
        settings.put("activeLanguage", new Setting("Pesquisar/Listar em", settings.get("primaryLanguage").getCurrentState(), settings.get("secondaryLanguage").getCurrentState()));
        Menu generic = Menu.get("genericAction");
        generic.add(new Action("Pesquisar", action));
        generic.add(new Action("Listar", Dictionary.list));
        generic.addSeparator();
        generic.add(settings.get("activeLanguage"));
        generic.addSeparator();
        generic.add(new Action("Voltar", Menu.exit), "v");
        generic.open();
    }
    
}
