package jeanarthur.java.WordDictionary;

public class Setup {

    public static void main(String[] args) {
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
    }

    public static void registerMenus(){
        Menu.put("main", new Menu("Dicionário de Palavras"));
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

    }

    public static void registerSettingsInMenus(){
        Setting.put("programIsRunning", new Setting("Programa em execução", 0, "sim", "não"));
        Setting.put("primaryLanguage", new Setting("Linguagem Primária","Inglês"));
        Setting.put("secondaryLanguage", new Setting("Linguagem Secundária","Português"));
        Setting.put("activeLanguage", new Setting("Pesquisar/Listar em", Setting.get("primaryLanguage").getCurrentState(), Setting.get("secondaryLanguage").getCurrentState()));
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
