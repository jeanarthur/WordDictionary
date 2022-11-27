package jeanarthur.java.WordDictionary;

public class Dictionary {

    public static void register(){
        int freeIndex = Main.getFreeSpaceIndex();
        try{
            if (freeIndex == -1) { throw Exception.wordLimitReached; }
            Main.dictionary[0][freeIndex] = Main.requestNonDuplicatedInputIn(Main.dictionary[0], String.format("| Digite a palavra (%s): ", Main.settings.get("primaryLanguage").getCurrentState()));
            Main.dictionary[1][freeIndex] = Main.requestNonDuplicatedInputIn(Main.dictionary[1], String.format("| Digite a palavra (%s): ", Main.settings.get("secondaryLanguage").getCurrentState()));
        } catch (RuntimeException runtimeException){
            if (freeIndex != -1) {
                Main.dictionary[0][freeIndex] = null;
                Main.dictionary[1][freeIndex] = null;
            }
            throw Exception.generic(runtimeException.getMessage());
        }
    }

    public static void register(String word1, String word2){
        int freeIndex = Main.getFreeSpaceIndex();
        try{
            if (freeIndex == -1) { throw new RuntimeException("| Não é possível cadastrar.\n| Limite de 100 palavras\n| atingido!"); }
            Main.dictionary[0][freeIndex] = word1;
            Main.dictionary[1][freeIndex] = word2;
        } catch (RuntimeException runtimeException){
            if (freeIndex != -1) {
                Main.dictionary[0][freeIndex] = null;
                Main.dictionary[1][freeIndex] = null;
            }
            throw Exception.wordNotFound;
        }
    }

    public static void consult(){
        try {
            String word = Main.requestInput("| Consultar: ");
            int index = getIndexOf(word);
            /*System.out.printf("| %s: %s\n", Main.settings.get("primaryLanguage").getCurrentState(), (Main.dictionary[0][index]));
            System.out.printf("| %s: %s\n", Main.settings.get("secondaryLanguage").getCurrentState(), (Main.dictionary[1][index]));*/
            Main.updateConsultedWordMenu(new String[]{Main.dictionary[0][index], Main.dictionary[1][index]});
            Main.menus.get("consultedWord").open();
        } catch (NullPointerException nullPointerException){
            throw Exception.generic(nullPointerException.getMessage());
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            throw Exception.wordNotFound;
        }
    }

    public static void consult(String word){
        try {
            int index = getIndexOf(word);
            /*System.out.printf("| %s: %s\n", Main.settings.get("primaryLanguage").getCurrentState(), (Main.dictionary[0][index]));
            System.out.printf("| %s: %s\n", Main.settings.get("secondaryLanguage").getCurrentState(), (Main.dictionary[1][index]));*/
            Main.updateConsultedWordMenu(new String[]{Main.dictionary[0][index], Main.dictionary[1][index]});
            Main.menus.get("consultedWord").open();
        } catch (NullPointerException nullPointerException){
            throw Exception.generic(nullPointerException.getMessage());
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            throw Exception.wordNotFound;
        }
    }

    public static void delete(){
        try{
            String word = Main.requestInput("| Excluir: ");
            int index = getIndexOf(word);
            Main.dictionary[0][index] = null;
            Main.dictionary[1][index] = null;
            Main.updateConsultedWordMenu(new String[]{Main.dictionary[0][index], Main.dictionary[1][index]});
        } catch (NullPointerException nullPointerException){
            throw Exception.generic(nullPointerException.getMessage());
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            throw Exception.wordNotFound;
        }
    }

    public static void delete(String word){
        try{
            int index = getIndexOf(word);
            Main.dictionary[0][index] = null;
            Main.dictionary[1][index] = null;
            Main.updateConsultedWordMenu(new String[]{Main.dictionary[0][index], Main.dictionary[1][index]});
        } catch (NullPointerException nullPointerException){
            throw Exception.generic(nullPointerException.getMessage());
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            throw Exception.wordNotFound;
        }
    }

    public static void edit(){
        try{
            String[] activeLanguageWords = Main.dictionary[Main.settings.get("activeLanguage").getStateValue()];
            String word = Main.requestInput("| Editar: ");
            int index = getIndexOf(word);
            activeLanguageWords[index] = Main.requestNonDuplicatedInputIn(activeLanguageWords, String.format("| Alterar '%s'(%s) para: ", activeLanguageWords[index], Main.settings.get("activeLanguage").getCurrentState()));
            Main.updateConsultedWordMenu(new String[]{Main.dictionary[0][index], Main.dictionary[1][index]});
        } catch (NullPointerException nullPointerException){
            throw Exception.generic(nullPointerException.getMessage());
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            throw Exception.wordNotFound;
        }
    }

    public static void edit(String word){
        try{
            String[] activeLanguageWords = Main.dictionary[Main.settings.get("activeLanguage").getStateValue()];
            int index = getIndexOf(word);
            activeLanguageWords[index] = Main.requestNonDuplicatedInputIn(activeLanguageWords, String.format("| Alterar '%s'(%s) para: ", activeLanguageWords[index], Main.settings.get("activeLanguage").getCurrentState()));
            Main.updateConsultedWordMenu(new String[]{Main.dictionary[0][index], Main.dictionary[1][index]});
        } catch (NullPointerException nullPointerException){
            throw Exception.generic(nullPointerException.getMessage());
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            throw Exception.wordNotFound;
        }
    }

    private static int getIndexOf(String word){
        int index = -1;
        String[] activeLanguageWords = Main.dictionary[Main.settings.get("activeLanguage").getStateValue()];
        for (int i = 0; i < activeLanguageWords.length; i++){
            if (activeLanguageWords[i] != null && activeLanguageWords[i].equals(word)){
                index = i;
                break;
            }
        }
        return index;
    }

}
