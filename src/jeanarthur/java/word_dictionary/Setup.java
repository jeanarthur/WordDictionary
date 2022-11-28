package jeanarthur.java.word_dictionary;

import jeanarthur.java.util.Action;
import jeanarthur.java.util.Menu;
import jeanarthur.java.util.Setting;

public class Setup {

    public static void main(String[] args) {
        registerTestWords();
        setupDictionaryMenus();
        setupDictionarySettings();
        Dictionary.start();
    }

    private static void setupDictionaryMenus(){
        // Registra o menu de instrução
        Menu instruction = Menu.put("instructions", new Menu("Instrução"));
        instruction.add("""
                Para interagir com as funções do sistema deve-se
                digitar o número ou a letra que corresponda a
                opção/operação desejada.
                """);
        instruction.addSeparator();
        instruction.add(new Action("Prosseguir", Menu.exit));

        // Registra o menu principal
        Menu main = Menu.put("main", new Menu("Dicionário de Palavras"));
        main.add(new Action("Cadastrar", Dictionary.register));
        main.add(new Action("Consultar", Dictionary.consult));
        main.add(new Action("Editar", Dictionary.edit));
        main.add(new Action("Excluir", Dictionary.delete));
        main.add(new Action("Sair", Menu.exit));

        // Registra o menu de listagem das palavras (menu dinâmico)
        Menu.put("wordList", new Menu("Lista de palavras"));

        // Registra o menu de palavra consultada (menu dinâmico)
        Menu.put("consultedWord", new Menu("Palavra consultada"));

        // Registra um menu genérico de ações (menu dinâmico)
        Menu.put("genericAction", new Menu("genericAction"));
    }

    private static void setupDictionarySettings(){
        Setting.put("programIsRunning", new Setting("Programa em execução", 0, "sim", "não"));
        Setting.put("primaryLanguage", new Setting("Linguagem Primária","Inglês"));
        Setting.put("secondaryLanguage", new Setting("Linguagem Secundária","Português"));
        Setting.put("activeLanguage", new Setting("Pesquisar/Listar em", Setting.get("primaryLanguage").getCurrentState(), Setting.get("secondaryLanguage").getCurrentState()));
    }

    public static void registerTestWords(){
        Dictionary.register("home", "casa");
        Dictionary.register("ice", "gelo");
        Dictionary.register("room", "sala");
        Dictionary.register("do", "fazer");
        Dictionary.register("clear", "limpar");
        Dictionary.register("one", "um");
        Dictionary.register("two", "dois");
        Dictionary.register("three", "três");
        Dictionary.register("four", "quatro");
        Dictionary.register("five", "5");
    }
    
}