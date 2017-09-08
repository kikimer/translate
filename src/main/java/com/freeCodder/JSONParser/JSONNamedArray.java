package com.freeCodder.JSONParser;

import java.util.*;

public class JSONNamedArray extends JSONElement implements Iterable<Map.Entry<CharSequence,JSONElement>>{


    JSONNamedArray(CharSequence source){

        source = CharsSequences.removeStartEnd(source,'{', '}');

        List<CharSequence> parseElements = separate(source, ',');
        items = new TreeMap<>(Comparator.comparing(CharSequence::length));
        for(CharSequence element : parseElements){
            List<CharSequence> keyValue = separate(element, ':');
            if(keyValue.size()!=2) throw new RuntimeException("format error: \"key\":value");
            CharSequence key = CharsSequences.removeStartEnd(keyValue.get(0), '"', '"');
            items.put(key, parseElement(keyValue.get(1)));
        }

    }

    @Override
    public Iterator<Map.Entry<CharSequence,JSONElement>> iterator() {
        return items.entrySet().iterator();
    }

    public JSONElement get(CharSequence key){
        JSONElement result = items.get(key);
        if(result == null) throw new RuntimeException("no found key: "+key);
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(CharSequence key, Class<T> tClass){
        JSONElement result = get(key);
        if(result.getClass() != tClass) throw new RuntimeException("incorrect type");
        return (T)result;
    }

    public JSONNamedArray getNamedArray(CharSequence key){
        return get(key, JSONNamedArray.class);
    }

    public JSONArray getArray(CharSequence key){
        return get(key, JSONArray.class);
    }

    public JSONString getString(CharSequence key){
        return get(key, JSONString.class);
    }

    public JSONInt getInt(CharSequence key){
        return get(key, JSONInt.class);
    }

    public int length() {
        return items.size();
    }

    private Map<CharSequence, JSONElement> items;
}
