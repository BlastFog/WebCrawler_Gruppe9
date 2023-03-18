package at.gr6.crawler;
import com.sun.net.httpserver.Headers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import com.deepl.api.*;
import org.jsoup.select.Evaluator;

public class Main {
    static Translator translator;
    public static void main(String[] args) throws IOException, DeepLException, InterruptedException {
        String url = args[0];
        int depth = Integer.parseInt(args[1]);
        String targetLanguage = args[2];
        String sourceLanguage ="";
        //System.out.println(language);

        String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";  // Replace with your key
        translator = new Translator(authKey);
        //TextResult result = translator.translateText("Hello, world!", null, language);
        //System.out.println(result.getText());

        Document doc = Jsoup.connect(url).get();


        Evaluator ev = new Evaluator.Attribute("h");
        Elements el = doc.select("h1");
        TextResult result;

        for(Element e : el){
            result = translator.translateText(e.toString(), null, targetLanguage);
            sourceLanguage = result.getDetectedSourceLanguage();
            System.out.println(result.getText());
        }
        System.out.println(sourceLanguage);
    }
}