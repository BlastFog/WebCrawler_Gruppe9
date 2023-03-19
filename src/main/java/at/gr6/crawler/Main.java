package at.gr6.crawler;

import com.sun.net.httpserver.Headers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.deepl.api.*;

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
    static Page page;
    static FileWriter fw;

    public static void main(String[] args) throws IOException, DeepLException, InterruptedException {
        setup();
        url = /*"http://www.broken-404.com"*/args[0];
        depth = Integer.parseInt(args[1]);
        targetLanguage = args[2];
        link = new Link(url, 0);

        String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";
        translator = new Translator(authKey);
        System.out.println("--------------------PAGE-START--------------------");

        readPage(link);

        sourceLanguage = getFullLanguage(getLanguage().toUpperCase());
        System.out.println("Source language: " + sourceLanguage);
        System.out.println("Target language: " + getFullLanguage(targetLanguage.toUpperCase()));

        writeFile();
    }

    private static void setup() {
        try {
            fw = new FileWriter("./report.md");
        } catch (Exception e) {
        }
    }


    private static void writeFile() {
        try {
            fw.write("input: <a>" + url + "</a>\n");
            fw.write("<br>depth: " + depth + "\n");
            fw.write("<br>source language: " + sourceLanguage + "\n");
            fw.write("<br>target language: " + getFullLanguage(targetLanguage.toUpperCase()) + "\n");
            fw.write("<br>summary:\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeHeader(String headers) {
        //System.out.println(headers);
        try {
            fw.write(headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeLink(String link) {
        try {
            fw.write(link);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String setCorrectIndentation(int depth) {
        String indents = " ";
        for (int i = 0; i < depth; i++) {
            indents += "-";
        }
        return (indents += ">");
    }

    private static void readPage(Link link) {
        try {
            doc = Jsoup.connect(link.getUrl()).get();

            int currentDepth = link.getDepthCounter();
            Elements headers = doc.select("h1,h2,h3,h4,h5,h6"); //All Headers
            Elements links = doc.select("a[href^=http]");       //All Links with prefix "http"
            Link link1;
            page = new Page();
            String headerString = "";

            for (Element h : headers) {     //Headers
                Element res = translateHeader(h);
                //Element res = new Element(result.getText());
                page.getHeader().add(res);
                //System.out.println(res.tag());
                for (int i = Integer.parseInt(h.tagName().charAt(1) + ""); i > 0; i--)
                    headerString += "#";
                headerString += setCorrectIndentation(currentDepth);
                headerString += (" " + res.tag() + "\n");
            }
            writeHeader(headerString);  //Write Headers


            for (Element e : links) {
                String url1 = e.attr("href");
                link1 = new Link(url1, currentDepth + 1);    //Next Page is old depth+1
                page.getLinks().add(link1);
                //depthSymbols(currentDepth + 1);
                String linkStr = "";
                linkStr += setCorrectIndentation(currentDepth);
                linkStr += (" " + url1 + "\n");
                writeLink(linkStr);   //Write Links

                //System.out.println("link to: " + link1);

                if (currentDepth < depth) {
                    readPage(link1);
                    System.out.println("--------------------PAGE-START--------------------");
                }
            }
            System.out.println("---------------------PAGE-END---------------------");
        } catch (Exception e) {
            System.out.println("Broken link: " + link.getUrl());
        }
    }

    private static void depthSymbols(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("-");
        }
        System.out.print(">");
    }

    private static Element translateHeader(Element e) throws DeepLException, InterruptedException {
        TextResult result;
        if (translate)
            result = translator.translateText(e.text(), null, targetLanguage);
        else
            result = new TextResult(e.text(), "");

        sourceLanguage = result.getDetectedSourceLanguage();

        if (!languageStatistics.containsKey(sourceLanguage))     //For language statistics
            languageStatistics.put(sourceLanguage, 1);
        else
            languageStatistics.put(sourceLanguage, languageStatistics.get(sourceLanguage) + 1);
        //System.out.println(" " + result.getText()); --------------------------------------------------------------

        return new Element(result.getText());
    }

    private static String getLanguage() {
        int max = 0;
        String lang = "";
        for (String i : languageStatistics.keySet()) {
            int val = languageStatistics.get(i);
            if (val >= max) {
                max = val;
                lang = i;
            }
        }
        return lang;
    }

    private static String getFullLanguage(String lang) {
        String language = "";
        switch (lang) {
            case "BG":
                language = "Bulgarian";
                break;
            case "CS":
                language = "Czech";
                break;
            case "DA":
                language = "Danish";
                break;
            case "DE":
                language = "German";
                break;
            case "EL":
                language = "Greek";
                break;
            case "EN-GB":
                language = "English (British)";
                break;
            case "EN":
                language = "English";
                break;
            case "EN-US":
                language = "English (American)";
                break;
            case "ES":
                language = "Spanish";
                break;
            case "ET":
                language = "Estonian";
                break;
            case "FI":
                language = "Finnish";
                break;
            case "FR":
                language = "French";
                break;
            case "HU":
                language = "Hungarian";
                break;
            case "ID":
                language = "Indonesian";
                break;
            case "IT":
                language = "Italian";
                break;
            case "JA":
                language = "Japanese";
                break;
            case "KO":
                language = "Korean";
                break;
            case "LT":
                language = "Lithuanian";
                break;
            case "LV":
                language = "Latvian";
                break;
            case "NB":
                language = "Norwegian (Bokmål)";
                break;
            case "NL":
                language = "Dutch";
                break;
            case "PL":
                language = "Polish";
                break;
            case "PT-BR":
                language = "Portuguese (Brazilian)";
                break;
            case "PT-PT":
                language = "Portuguese (all Portuguese varieties excluding Brazilian Portuguese)";
                break;
            case "RO":
                language = "Romanian";
                break;
            case "RU":
                language = "Russian";
                break;
            case "SK":
                language = "Slovak";
                break;
            case "SL":
                language = "Slovenian";
                break;
            case "SV":
                language = "Swedish";
                break;
            case "TR":
                language = "Turkish";
                break;
            case "UK":
                language = "Ukrainian";
                break;
            case "ZH":
                language = "Chinese (simplified)";
                break;
            default:
                language = "Kärntnerisch";
        }
        return language;
    }
}