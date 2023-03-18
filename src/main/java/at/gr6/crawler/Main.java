package at.gr6.crawler;

import com.sun.net.httpserver.Headers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

import com.deepl.api.*;
import org.jsoup.select.Evaluator;

public class Main {
    static Translator translator;
    static Document doc;
    static String sourceLanguage = "";
    static String targetLanguage = "";
    static String url = "";
    static int depth = 0;
    static TextResult result;
    static HashMap<String, Integer> languageStatistics = new HashMap<String, Integer>();

    public static void main(String[] args) throws IOException, DeepLException, InterruptedException {
        url = args[0];
        depth = Integer.parseInt(args[1]);
        targetLanguage = args[2];

        String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";
        translator = new Translator(authKey);

        readPage(url);
        getFullLanguage();
        System.out.println("source language: "+sourceLanguage);
    }

    private static void getFullLanguage(){
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

    private static void writeFile() {

    }

    private static void readPage(String url) throws IOException, DeepLException, InterruptedException {
        doc = Jsoup.connect(url).get();
        Elements el = doc.select("h1,h2,h3,h4,h5,h6");
        readHeaders(el);
    }

    private static void readHeaders(Elements el) throws DeepLException, InterruptedException {
        for (Element e : el) {
            result = translator.translateText(e.text(), null, targetLanguage);
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
}