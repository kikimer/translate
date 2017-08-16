package com.freeCodder.JSONParser;

/**
 * Created by yurka on 10.08.17.
 */
public class JSONInt extends JSONElement{
    public JSONInt(CharSequence source) {
        this.value = Integer.parseInt(source.toString());
    }

    public int getValue() {return value;}

    @Override
    public String toString() {
        return Integer.toString(getValue());
    }

    private int value;
}
