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

        String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";
        translator = new Translator(authKey);

        Document doc = Jsoup.connect(url).get();

        Elements el = doc.select("h1,h2,h3,h4,h5,h6");
        TextResult result;

        for(Element e : el){
            result = translator.translateText(e.text(), null, targetLanguage);
            sourceLanguage = result.getDetectedSourceLanguage();
            System.out.println(result.getText());
        }
        System.out.println(sourceLanguage);
    }
}