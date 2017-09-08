package com.freeCodder.JSONParser;

import java.util.Map;
import java.util.TreeMap;


public class JSONString extends JSONElement{
    public JSONString(CharSequence source) {
        source = CharsSequences.removeStartEnd(source,'"', '"');

        boolean isEscapeSymbol = false;
        StringBuffer sb = new StringBuffer(source.length());
        for(int i=0; i<source.length(); i++){
            char c = source.charAt(i);
            if(isEscapeSymbol) {
                c = parseEscSymbol(c);
                isEscapeSymbol = false;
            }else if(c == '\\') {
                isEscapeSymbol = true;
                continue;
            }
            sb.append(c);
        }
        this.value = sb.toString();
    }

    public String getValue() {return value;}

    @Override
    public String toString() {
        return getValue() ;
    }

    private String value;

    /**
     * key: escape-symbol
     * value: is valid value
     */
    private static char parseEscSymbol(char escSymbol){
        Character result = escapeSymbols.get(escSymbol);
        if(result == null) return escSymbol;
        return result;
    }
    private static Map<Character, Character> escapeSymbols;
    static {
        escapeSymbols = new TreeMap<>();
        escapeSymbols.put('t','\t');
        escapeSymbols.put('n','\n');
        escapeSymbols.put('r','\r');
        escapeSymbols.put('f','\f');
        escapeSymbols.put('b','\b');
    }
}
