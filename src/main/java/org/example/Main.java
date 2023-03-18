package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
//import com.deepl.DeepLClient;
//import com.deepl.util.Language;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://de.wikipedia.org/";
        Document doc = Jsoup.connect(url).get();
        String text = doc.select("header").text();
        System.out.println(text);
    }
}