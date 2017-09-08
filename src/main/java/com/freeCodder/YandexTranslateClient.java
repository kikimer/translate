package com.freeCodder;

import com.freeCodder.JSONParser.*;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

public class YandexTranslateClient implements TranslateClient{
    @Override
    public String translate(String text) {
        String result = "";
        try {
            String rKey = "?key=trnsl.1.1.20170807T112200Z.e0547f602bc6a195.e9dc9d12f1f78c0bb68055dbe016edcfa2e58d38";
            String rText = "&text="+URLEncoder.encode(text,"UTF-8");
            String rLang = "&lang=en-ru";
            String urlBase = "https://translate.yandex.net/api/v1.5/tr.json/translate";

            URL url = new URL(urlBase+rKey+rText+rLang);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer sb = new StringBuffer();
            int c;
            while ((c = br.read())!=-1) sb.append((char)c);
            SharedSourceCharSequence source = new SharedSourceCharSequence(sb.toString());
            JSONNamedArray root = JSONElement.parseRootElement(source);
            JSONArray array = root.getArray("test");
            JSONString string = array.getString(0);
            result = string.getValue();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


}
