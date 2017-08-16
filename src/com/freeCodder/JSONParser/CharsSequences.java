package com.freeCodder.JSONParser;

import java.util.Comparator;
import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CharsSequences {
    @SuppressWarnings("unchecked")
    public static <T extends CharSequence> T trim(T source){
        int length = source.length();

        int start = 0;
        for(; start<length; start++)
            if(!Character.isSpaceChar(source.charAt(start)))break;
        if(start == length) return (T) source.subSequence(0,0);

        int end = source.length()-1;
        for(; end>start; end--)
            if(!Character.isSpaceChar(source.charAt(end)))break;

        if(start == 0 && end == source.length()) return source;
        return (T)source.subSequence(start, end+1);
    }

    @SuppressWarnings("unchecked")
    public static <T extends CharSequence> T removeStartEnd(T source, char charStart, char charEnd){
        if(source.length()<2 || source.charAt(0)!=charStart || source.charAt(source.length()-1)!=charEnd) throw new RuntimeException("Format error!");
        return (T)source.subSequence(1, source.length()-1);
    }

}
