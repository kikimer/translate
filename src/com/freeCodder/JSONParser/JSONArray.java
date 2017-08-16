package com.freeCodder.JSONParser;


import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


public class JSONArray extends JSONElement implements Iterable<JSONElement>{
    @Override
    public Iterator<JSONElement> iterator() {
        return items.iterator();
    }

    JSONArray(CharSequence source) {
        source = CharsSequences.removeStartEnd(source,'[', ']');

        List<CharSequence> elements = separate(source, ',');
        items = new ArrayList<>(elements.size());
        for(CharSequence element : elements){
            items.add(parseElement(element));
        }
    }

    public JSONElement get(int index){
        if(index<0 || index >= length())
            throw new RuntimeException("incorrect indices");
        return items.get(index);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(int index, Class<T> tClass){
        JSONElement result = get(index);
        if(result.getClass() != tClass) throw new RuntimeException("incorrect type");
        return (T)result;
    }

    public JSONNamedArray getNamedArray(int index){
        return get(index, JSONNamedArray.class);
    }

    public JSONArray getArray(int index){
        return get(index, JSONArray.class);
    }

    public JSONString getString(int index){
        return get(index, JSONString.class);
    }

    public JSONInt getInt(int index){
        return get(index, JSONInt.class);
    }

    public int length() {
        return items.size();
    }

    private List<JSONElement> items;

}
