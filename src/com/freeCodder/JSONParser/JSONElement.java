package com.freeCodder.JSONParser;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yurka on 10.08.17.
 */
public abstract class JSONElement {

    /**
     * Определяет тип объекта и запускает его конструктор
     * @param source
     * @return
     */

    public static JSONNamedArray parseRootElement(CharSequence source){
        source = CharsSequences.trim(source);
        char c = source.charAt(0);
        if(c == '{')return new JSONNamedArray(source);
        throw new RuntimeException("can't parse first symbol: '"+c+"'");

    }

    public static JSONElement parseElement(CharSequence source){
        source = CharsSequences.trim(source);
        char c = source.charAt(0);
        if(c == '{')return new JSONNamedArray(source);
        if(c == '[')return new JSONArray(source);
        if(c == '"')return new JSONString(source);
        if(Character.isDigit(c))return new JSONInt(source);
        throw new RuntimeException("can't parse first symbol: '"+c+"'");

    }
    /**
     * Разбить на часть по разделителю с учетом вложенных {} [] и ""
     * @param source
     * @param separator
     * @return
     */
    protected static List<CharSequence> separate(CharSequence source, char separator){
        if(source.charAt(source.length()-1)!=separator)
            source = new AppendableCharSequence(source, String.valueOf(separator));

        List<CharSequence> result = new LinkedList<>();
        int start = 0;                  //начало элемента. i будет его концом
        int level = 0;                  //уровень вложенности скобок
        boolean isText = false;         //признак текста в кавычках
        boolean isEscapeSymbol = false; //признак спец-символа, для корректного обхода экранированных кавычек(\")
        for(int i = 0; i < source.length(); i++){
            char c = source.charAt(i);

            //Спецсимвол
            if(isEscapeSymbol) {
                isEscapeSymbol=false;
                if(c == '"') continue;
            }else if (c == '\\') {
                isEscapeSymbol = true;
                continue;
            }

            //Текст в двойных кавычках
            if(c == '"') isText = !isText;
            if(isText) continue;

            //уровень вложенности
            switch (c){
                case '{':
                case '[': level++; break;
                case '}':
                case ']': level--; break;
            }
            if(level>0)continue;
            if(level<0)throw new RuntimeException("source have error in brackets!");

            //separating
            if(c == separator){
                result.add(source.subSequence(start, i));
                start = i+1;
            }

        }
        return  result;



    }




}
