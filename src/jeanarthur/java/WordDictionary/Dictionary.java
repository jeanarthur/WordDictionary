package jeanarthur.java.WordDictionary;

import java.util.function.Consumer;

import static jeanarthur.java.util.ArrayOperation.getFreeSpaceIndex;
import static jeanarthur.java.util.ArrayOperation.getNotNullValues;
import static jeanarthur.java.util.InputOperation.requestInput;
import static jeanarthur.java.util.InputOperation.requestNonDuplicateInputConsidering;

public class Dictionary {

    private static final String[] wordsPrimaryLanguage = new String[100];
    private static final String[] wordsSecondaryLanguage = new String[100];
    private static final String[][] dictionary = {wordsPrimaryLanguage, wordsSecondaryLanguage};
    static String[] registeredWords;
    static int wordListIndex = 0;

    public static String[][] get(){
        return dictionary;
    }
    
    public static void register(){
        int freeIndex = getFreeSpaceIndex(dictionary[0]);
        try{
            if (freeIndex == -1) { throw Exception.wordLimitReached; }
            dictionary[0][freeIndex] = requestNonDuplicateInputConsidering(dictionary[0], String.format("| Digite a palavra (%s): ", Main.settings.get("primaryLanguage").getCurrentState()));
            dictionary[1][freeIndex] = requestNonDuplicateInputConsidering(dictionary[1], String.format("| Digite a palavra (%s): ", Main.settings.get("secondaryLanguage").getCurrentState()));
        } catch (RuntimeException runtimeException){
            if (freeIndex != -1) {
                dictionary[0][freeIndex] = null;
                dictionary[1][freeIndex] = null;
            }
            throw Exception.generic(runtimeException.getMessage());
        }
    }

    public static void register(String word1, String word2){
        int freeIndex = getFreeSpaceIndex(dictionary[0]);
        try{
            if (freeIndex == -1) { throw new RuntimeException("| Não é possível cadastrar.\n| Limite de 100 palavras\n| atingido!"); }
            dictionary[0][freeIndex] = word1;
            dictionary[1][freeIndex] = word2;
        } catch (RuntimeException runtimeException){
            if (freeIndex != -1) {
                dictionary[0][freeIndex] = null;
                dictionary[1][freeIndex] = null;
            }
            throw Exception.wordNotFound;
        }
    }

    public static void consult(){
        try {
            String word = requestInput("| Consultar: ");
            int index = getIndexOf(word);
            Main.updateConsultedWordMenu(new String[]{dictionary[0][index], dictionary[1][index]});
        } catch (NullPointerException nullPointerException){
            throw Exception.generic(nullPointerException.getMessage());
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            throw Exception.wordNotFound;
        }
    }

    public static void consult(String word){
        try {
            int index = getIndexOf(word);
            Main.updateConsultedWordMenu(new String[]{dictionary[0][index], dictionary[1][index]});
        } catch (NullPointerException nullPointerException){
            throw Exception.generic(nullPointerException.getMessage());
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            throw Exception.wordNotFound;
        }
    }

    public static void delete(){
        try{
            String word = requestInput("| Excluir: ");
            int index = getIndexOf(word);
            dictionary[0][index] = null;
            dictionary[1][index] = null;
            Main.updateConsultedWordMenu(new String[]{dictionary[0][index], dictionary[1][index]});
        } catch (NullPointerException nullPointerException){
            throw Exception.generic(nullPointerException.getMessage());
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            throw Exception.wordNotFound;
        }
    }

    public static void delete(String word){
        try{
            int index = getIndexOf(word);
            dictionary[0][index] = null;
            dictionary[1][index] = null;
            Main.updateConsultedWordMenu(new String[]{dictionary[0][index], dictionary[1][index]});
        } catch (NullPointerException nullPointerException){
            throw Exception.generic(nullPointerException.getMessage());
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            throw Exception.wordNotFound;
        }
    }

    public static void edit(){
        try{
            String[] activeLanguageWords = dictionary[Main.settings.get("activeLanguage").getStateValue()];
            String word = requestInput("| Editar: ");
            int index = getIndexOf(word);
            activeLanguageWords[index] = requestNonDuplicateInputConsidering(activeLanguageWords, String.format("| Alterar '%s'(%s) para: ", activeLanguageWords[index], Main.settings.get("activeLanguage").getCurrentState()));
            Main.updateConsultedWordMenu(new String[]{dictionary[0][index], dictionary[1][index]});
        } catch (NullPointerException nullPointerException){
            throw Exception.generic(nullPointerException.getMessage());
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            throw Exception.wordNotFound;
        }
    }

    public static void edit(String word){
        try{
            String[] activeLanguageWords = dictionary[Main.settings.get("activeLanguage").getStateValue()];
            int index = getIndexOf(word);
            activeLanguageWords[index] = requestNonDuplicateInputConsidering(activeLanguageWords, String.format("| Alterar '%s'(%s) para: ", activeLanguageWords[index], Main.settings.get("activeLanguage").getCurrentState()));
            Main.updateConsultedWordMenu(new String[]{dictionary[0][index], dictionary[1][index]});
        } catch (NullPointerException nullPointerException){
            throw Exception.generic(nullPointerException.getMessage());
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            throw Exception.wordNotFound;
        }
    }

    private static int getIndexOf(String word){
        int index = -1;
        String[] activeLanguageWords = dictionary[Main.settings.get("activeLanguage").getStateValue()];
        for (int i = 0; i < activeLanguageWords.length; i++){
            if (activeLanguageWords[i] != null && activeLanguageWords[i].equals(word)){
                index = i;
                break;
            }
        }
        return index;
    }

    static void updateList(Consumer<String> actionInList){
        registeredWords = getNotNullValues(dictionary[Main.settings.get("activeLanguage").getStateValue()]);
        Menu wordList = Menu.get("wordList");
        wordList.clear();
        int wordCount = 0;
        for (int i = wordListIndex; i < registeredWords.length; i++){
            wordList.add(new Action(registeredWords[i], actionInList, registeredWords[i]), String.valueOf(i + 1));
            if (++wordCount == 5){ break; }
        }
        wordList.addSeparator();
        if (wordListIndex > 0){ wordList.add(new Action("Anterior", previousInList), "a"); }
        if (wordListIndex + 5 < registeredWords.length){ wordList.add(new Action("Próximo", nextInList), "p"); }
        wordList.add(new Action("Voltar", Menu.exit), "v");
    }
    
    static Runnable register = Dictionary::register;

    static Runnable search = () -> {
        consult();
        Menu.get("consultedWord").open();
    };

    static Runnable consult = () -> {
        Main.currentActionInList = "consult";
        Main.updateGenericActionMenu("Consultar", search);
    };
    
    static Runnable edit = () -> {
        Main.currentActionInList = "edit";
        Main.updateGenericActionMenu("Editar", Dictionary::edit);
    };
    static Runnable delete = () -> {
        Main.currentActionInList = "delete";
        Main.updateGenericActionMenu("Excluir", Dictionary::delete);
    };

    static Consumer<String> consultFromList = (String word) -> {
        Dictionary.consult(word);
        updateList(Main.actionsInList.get(Main.currentActionInList));
        Menu.get("consultedWord").open();
    };

    static Consumer<String> editFromList = (String word) -> {
        Dictionary.edit(word);
        updateList(Main.actionsInList.get(Main.currentActionInList));
    };

    static Consumer<String> deleteFromList = (String word) -> {
        Dictionary.delete(word);
        updateList(Main.actionsInList.get(Main.currentActionInList));
    };

    static Consumer<String> deleteFromConsult = (String word) -> {
        Dictionary.delete(word);
        updateList(Main.actionsInList.get(Main.currentActionInList));
        Menu.currentMenu.close();
    };
    
    static Runnable list = () -> {
        wordListIndex = 0;
        updateList(Main.actionsInList.get(Main.currentActionInList));
        Menu.get("wordList").open();
    };
    
    private static final Runnable nextInList = () -> {
        wordListIndex += (wordListIndex + 5 < registeredWords.length) ? 5 : registeredWords.length - wordListIndex;
        updateList(Main.actionsInList.get(Main.currentActionInList));
    };
    
    private static final Runnable previousInList = () -> {
        wordListIndex -= 5;
        updateList(Main.actionsInList.get(Main.currentActionInList));
    };
}
