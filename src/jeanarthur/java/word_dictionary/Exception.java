package jeanarthur.java.word_dictionary;

public class Exception {

    public static final RuntimeException wordNotFound = generic("| Palavra não encontrada nesse idioma!");
    public static final RuntimeException wordLimitReached = generic("| Não é possível cadastrar.\n| Limite de 100 palavras\n| atingido!");
    public static final RuntimeException wordAlreadyExists = generic("| Não é possível cadastrar!\n| Palavra já foi registrada\n| para esse idioma.");
    public static final RuntimeException invalidOperationCode = generic("| Operação inválida!\n| Digite uma letra ou \n| número correspondente.");
    public static RuntimeException generic(String message){
        return new RuntimeException("\u001B[31m" + message + "\u001B[0m");
    }
}