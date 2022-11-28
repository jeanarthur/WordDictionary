package jeanarthur.java.WordDictionary;

import java.util.*;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        registerTestWords();
        registerMenus();
        registerActionsInMenus();
        registerSettingsInMenus();
        registerActionsInList();
        registerInstructionsMenu();
        Menu.currentMenu = Menu.get("instructions");
        Menu.currentMenu.open();
        Menu.currentMenu = Menu.get("main");
        Menu.currentMenu.open();
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
        Setting.put("programIsRunning", new Setting("Programa em execução", 0, "sim", "não"));
        Setting.put("primaryLanguage", new Setting("Linguagem Primária","Inglês"));
        Setting.put("secondaryLanguage", new Setting("Linguagem Secundária","Português"));
        Setting.put("activeLanguage", new Setting("Pesquisar/Listar em", Setting.get("primaryLanguage").getCurrentState(), Setting.get("secondaryLanguage").getCurrentState()));

        Menu settingsMenu = Menu.get("settings");
        settingsMenu.add(Setting.get("primaryLanguage"));
        settingsMenu.add(Setting.get("secondaryLanguage"));

    }

    public static void registerActionsInList(){
        Dictionary.actionsInList.put("consult", Dictionary.consultFromList);
        Dictionary.actionsInList.put("edit", Dictionary.editFromList);
        Dictionary.actionsInList.put("delete", Dictionary.deleteFromList);
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
    
}
