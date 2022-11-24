package jeanarthur.java.WordDictionary;

public class Exception {

    public static final RuntimeException wordNotFound = new RuntimeException("| Palavra não encontrada!");
    public static final RuntimeException wordLimitReached = new RuntimeException("| Não é possível cadastrar.\n| Limite de 100 palavras\n| atingido!");
    public static final RuntimeException wordAlreadyExists = new RuntimeException("| Não é possível cadastrar!\n| Palavra já foi registrada\n| para esse idioma.");
    public static final RuntimeException invalidOperationCode = new RuntimeException("| Operação inválida!\n| Digite uma letra ou \n| número correspondente.");
    public static RuntimeException generic(String message){
        return new RuntimeException(message);
    }
}