package at.gr6.crawler;

import com.sun.net.httpserver.Headers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.deepl.api.*;
import org.jsoup.select.Evaluator;

public class Main {
    static Translator translator;
    static Document doc;
    static String sourceLanguage = "";
    static String targetLanguage = "";
    static String url = "";
    static int depth = 1;
    static TextResult result;
    static HashMap<String, Integer> languageStatistics = new HashMap<String, Integer>();
    static boolean translate = false;
    static Link link;

    public static void main(String[] args) throws IOException, DeepLException, InterruptedException {
        url = args[0];
        depth = Integer.parseInt(args[1]);
        targetLanguage = args[2];
        link = new Link(url, 0);

        String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";
        translator = new Translator(authKey);
        System.out.println("--------------------PAGE-START--------------------");
        readPage(link);

        getFullLanguage();
        System.out.println("source language: " + sourceLanguage);
    }

    private static void writeFile() {

    }

    private static void readPage(Link link) throws IOException, DeepLException, InterruptedException {
        doc = Jsoup.connect(link.getUrl()).get();
        int currentDepth = link.getDepthCounter();
        Elements headers = doc.select("h1,h2,h3,h4,h5,h6"); //All Headers
        Elements links = doc.select("a[href^=http]");       //All Links containing "http"
        Link link1;
        Page page1 = new Page();


        for (Element h : headers) {     //Headers
            page1.getHeader().add(h);
            translateHeader(h);
        }

        for (Element e : links) {
            String url1 = e.attr("href");
            //ystem.out.println(url1);

            link1 = new Link(url1,currentDepth+1);    //Next Page is old depth+1
            //System.out.println(link1);
            page1.getLinks().add(link1);
            depthSymbols(currentDepth+1);
            System.out.println(link1);

            if(currentDepth < depth) {
                readPage(link1);
                System.out.println("--------------------PAGE-START--------------------");
            }
        }
        System.out.println("---------------------PAGE-END---------------------");

    }

    private static void depthSymbols(int depth){
        //System.out.print("-");
        for(int i = 0; i < depth; i++){
            System.out.print("-");
        }
        System.out.print(">");
    }

    private static void translateHeader(Element e) throws DeepLException, InterruptedException {
        if (translate)
            result = translator.translateText(e.text(), null, targetLanguage);
        else
            result = new TextResult(e.text(), "de");

        for (int i = Integer.parseInt(e.tagName().charAt(1) + ""); i > 0; i--) {
            System.out.print("#");
        }
        sourceLanguage = result.getDetectedSourceLanguage();

        if (!languageStatistics.containsKey(sourceLanguage))     //Für languate Statistik
            languageStatistics.put(sourceLanguage, 1);
        else
            languageStatistics.put(sourceLanguage, languageStatistics.get(sourceLanguage) + 1);
        System.out.println(" " + result.getText());

    }

    private static String getLanguage() {
        int max = 0;
        String lang = "";
        for (String i : languageStatistics.keySet()) {
            int val = languageStatistics.get(i);
            if (val > max) {
                max = val;
                lang = i;
            }
        }
        return lang;
    }

    private static void getFullLanguage() {
        switch (getLanguage().toUpperCase()) {
            case "BG":
                sourceLanguage = "Bulgarian";
                break;
            case "CS":
                sourceLanguage = "Czech";
                break;
            case "DA":
                sourceLanguage = "Danish";
                break;
            case "DE":
                sourceLanguage = "German";
                break;
            case "EL":
                sourceLanguage = "Greek";
                break;
            case "EN-GB":
                sourceLanguage = "English (British)";
                break;
            case "EN-US":
                sourceLanguage = "English (American)";
                break;
            case "ES":
                sourceLanguage = "Spanish";
                break;
            case "ET":
                sourceLanguage = "Estonian";
                break;
            case "FI":
                sourceLanguage = "Finnish";
                break;
            case "FR":
                sourceLanguage = "French";
                break;
            case "HU":
                sourceLanguage = "Hungarian";
                break;
            case "ID":
                sourceLanguage = "Indonesian";
                break;
            case "IT":
                sourceLanguage = "Italian";
                break;
            case "JA":
                sourceLanguage = "Japanese";
                break;
            case "KO":
                sourceLanguage = "Korean";
                break;
            case "LT":
                sourceLanguage = "Lithuanian";
                break;
            case "LV":
                sourceLanguage = "Latvian";
                break;
            case "NB":
                sourceLanguage = "Norwegian (Bokmål)";
                break;
            case "NL":
                sourceLanguage = "Dutch";
                break;
            case "PL":
                sourceLanguage = "Polish";
                break;
            case "PT-BR":
                sourceLanguage = "Portuguese (Brazilian)";
                break;
            case "PT-PT":
                sourceLanguage = "Portuguese (all Portuguese varieties excluding Brazilian Portuguese)";
                break;
            case "RO":
                sourceLanguage = "Romanian";
                break;
            case "RU":
                sourceLanguage = "Russian";
                break;
            case "SK":
                sourceLanguage = "Slovak";
                break;
            case "SL":
                sourceLanguage = "Slovenian";
                break;
            case "SV":
                sourceLanguage = "Swedish";
                break;
            case "TR":
                sourceLanguage = "Turkish";
                break;
            case "UK":
                sourceLanguage = "Ukrainian";
                break;
            case "ZH":
                sourceLanguage = "Chinese (simplified)";
                break;
            default:
                sourceLanguage = "Kärntnerisch";
        }
    }
}