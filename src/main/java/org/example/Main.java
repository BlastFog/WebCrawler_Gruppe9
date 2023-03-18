package org.example;
import com.sun.net.httpserver.Headers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import com.deepl.api.*;

public class Main {
    static Translator translator;
    public static void main(String[] args) throws IOException, DeepLException, InterruptedException {
        String url = args[0];
        int depth = Integer.parseInt(args[1]);
        String targetLanguage = args[2];
        String sourceLanguage;
        //System.out.println(language);

        String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";  // Replace with your key
        translator = new Translator(authKey);
        //TextResult result = translator.translateText("Hello, world!", null, language);
        //System.out.println(result.getText());

        Document doc = Jsoup.connect(url).get();

        Elements el = doc.select("h1");
        //sourceLanguage =
        TextResult result;

        for(Element e : el){
            result = translator.translateText(e.toString(), null, targetLanguage);
            //System.out.printf("%s\n",e);
            System.out.println(result.getText());
        }
    }
}