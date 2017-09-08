package com.freeCodder.JSONParser;

import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Создание последовательности символов из двух последовательностей символов
 */
public class AppendableCharSequence implements CharSequence{

    public AppendableCharSequence(CharSequence fSource){
        this(fSource, null);
    }

    public AppendableCharSequence(CharSequence fSource, CharSequence sSource){
        this.fSource = fSource;
        this.sSource = sSource;
    }

    @Override
    public AppendableCharSequence subSequence(int start, int end) {

        if(start<0 || end<start || end > length())
            throw new IllegalArgumentException("incorrect indices");

        CharSequence first, second;

        int startSecond = max(start-fSource.length(),0);
        int endSecond = max(end-fSource.length(),0);
        if(startSecond == endSecond)
            second = null;
        else
            second = sSource.subSequence(startSecond, endSecond);

        int startFirst = min(start, fSource.length());
        int endFirst = min(end, fSource.length());
        if(startFirst == endFirst) {
            first = second;
            second = null;
        }else
            first = fSource.subSequence(startFirst, endFirst);

        return new AppendableCharSequence(first, second);
    }

    @Override
    public int length() {

        int fLength = fSource==null?0:fSource.length();
        int sLength = sSource==null?0:sSource.length();
        return fLength+sLength;
    }

    @Override
   public String toString() {
        return new StringBuilder(this).toString();
    }

    @Override
    public char charAt(int index) {
        if(index<0 || index>=length()) throw new IllegalArgumentException("incorrect index");
        if(index<fSource.length()) return fSource.charAt(index);
        else return sSource.charAt(index-fSource.length());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        AppendableCharSequence tOther = (AppendableCharSequence) other;
        return Objects.equals(fSource, tOther.fSource) && Objects.equals(sSource, tOther.sSource);
    }

    @Override
    public int hashCode() {
        return  Objects.hash(fSource, sSource);
    }

    private CharSequence fSource;
    private CharSequence sSource;
}
