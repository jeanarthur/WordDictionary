package jeanarthur.java.util;

import java.util.Arrays;

public class ArrayOperation {

    public static int getFreeSpaceIndex(String[] array){
        int freeIndex = -1;
        for (int i = 0; i < array.length; i++){
            if (array[i] == null){
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