package com.freeCodder.JSONParser;

import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Реализация последовательности символов с экономией ресурсов: подстроки хранятся как ссылки на базовую строку
 * Выгодно использовать когда область видимости базовой строки больше или равна областям создаваемый подстрок
 */
public class SharedSourceCharSequence implements CharSequence{

    public SharedSourceCharSequence(CharSequence source){
        this.source = source;
        this.start = 0;
        this.end = source==null ? 0 : source.length();
    }

    @Override
    public SharedSourceCharSequence subSequence(int subStart, int subEnd) {

        if(subStart<0 || subEnd<subStart || subEnd > length())
            throw new IllegalArgumentException("incorrect indices");
        SharedSourceCharSequence result  = new SharedSourceCharSequence(source);
        result.start = start+subStart;
        result.end = start+subEnd;
        return result;
    }

    @Override
    public int length() {
        return end-start;
    }

    @Override
   public String toString() {
        return new StringBuilder(this).toString();
    }

    @Override
    public char charAt(int index) {
        if(index<0|| index>=length()) throw new IllegalArgumentException("incorrect index");
        return source.charAt(start+index);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        SharedSourceCharSequence tOther = (SharedSourceCharSequence) other;
        if (length() != tOther.length()) return false;
        if(source.equals(tOther.source) && start == tOther.start)
            return true;
        else {
            boolean result = true;
            for (int i = 0; i < length(); i++) {
                if (charAt(i) != source.charAt(i)) {
                    result = false;
                    break;
                }
            }
            return result;
        }
    }

    @Override
    public int hashCode() {
        return  Objects.hash(source, start, end);
    }

    private CharSequence source;
    private int start;
    private int end;

}
