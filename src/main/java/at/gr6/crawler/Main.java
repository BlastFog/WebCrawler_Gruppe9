package at.gr6.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.deepl.api.*;

public class Main {
    static Translator translator;
    static Document doc;
    static String sourceLanguage = "";
    static String targetLanguage = "";
    static String url = "";
    static int maxDepth = 1;
    static TextResult result;
    static HashMap<String, Integer> languageStatistics = new HashMap<String, Integer>();
    static boolean translate = false;
    static Link link;
    //static Page page;

    static WriteFiler filer;
    static Page p;

    public static void main(String[] args) {
        url = args[0];
        maxDepth = Integer.parseInt(args[1]);
        targetLanguage = args[2];
        String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";
        //translator = new Translator(authKey);
        p = new Page(url,1);
        readPage(p);
        setupWriter();
        write2File(p);
        try {
            filer.closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //printPages(p);

        //readPage(link);

        //System.out.println(getLimitString());

        //sourceLanguage = getFullLanguage(getLanguage().toUpperCase());
        //System.out.println("Source language: " + sourceLanguage);
        //System.out.println("Target language: " + getFullLanguage(targetLanguage.toUpperCase()));

        //writeFile();
    }

    public static String getLimitString() {
        String limit = "";
        try {
            limit = translator.getUsage().toString();
        } catch (DeepLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return limit;
    }

    private static void setupWriter() {
        try {
            filer = new WriteFiler("./report.md");
            filer.writeBeginning(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void write2File(Page p){
        try {
            filer.writeBody(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(p.getDepth()<maxDepth){
            for(Page page:p.getSubPage()){
                write2File(page);
            }
        }
    }
    private static void printPages(Page p){
        System.out.println(p.toString());
        if(p.getDepth()<maxDepth) {
            for (Page page : p.getSubPage()) {
                printPages(page);
            }
        }
    }

   /* private static void writeFile() {
        try {
            fw.write("input: <a>" + url + "</a>\n");
            fw.write("<br>depth: " + maxDepth + "\n");
            fw.write("<br>source language: " + sourceLanguage + "\n");
            fw.write("<br>target language: " + getFullLanguage(targetLanguage.toUpperCase()) + "\n");
            fw.write("<br>summary:\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*private static void writeHeader(String headers) {
        try {
            fw.write(headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

   /* private static void writeLink(String link) {
        try {
            fw.write("<br>"+link+"");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    private static String setCorrectIndentation(int depth) {
        String indents = " ";
        for (int i = 0; i < depth; i++) {
            indents += "-";
        }
        return (indents += ">");
    }
    private static void readPage(Page p){
        try {
            doc = Jsoup.connect(p.getUrl()).get();
            Elements links = doc.select("a[href]");
            Elements headers = doc.select("h1,h2,h3,h4,h5,h6");
            p.setHeader(headers);
            p.setSubPages(links);
            if(p.getDepth()<maxDepth){
                for(Page page:p.getSubPage()){
                    readPage(page);
                }
            }
        } catch (Exception e) {
            p.setBroken(true);
            e.printStackTrace();
        }
    }


    /*private static void readPage(String url,int depth){
        Page p = new Page(url,depth);
        try {
            doc = Jsoup.connect(link.getUrl()).get();
            Elements links = doc.select("a[href]");
            Elements headers = doc.select("h1,h2,h3,h4,h5,h6");
            //TODO: Add translation process here
            p.setHeader(headers);
            p.setSubPages(links);
            if(depth<maxDepth){
                readPage(page.getSubPage().toString(),depth+1);
            }
        } catch (Exception e) {
            p.setBroken(true);
            e.printStackTrace();

        }
    }*/
    //Dead Method
    /*private static void readPage(Link link) {
        //Page p = new Page(link,0);


        try {
            doc = Jsoup.connect(link.getUrl()).get();

            int currentDepth = link.getDepthCounter();
            Elements headers = doc.select("h1,h2,h3,h4,h5,h6"); //All Headers
            Elements links = doc.select("a[href]");
            Link link1;
            page = new Page();
            String headerString = "";

            for (Element h : headers) {     //Headers
                if(h.text().isEmpty())      //if not checked: it goes into the catch NOTE: Important for translation
                    continue;
                Element res = translateHeader(h);
                page.getHeader().add(res);
                for (int i = Integer.parseInt(h.tagName().charAt(1) + ""); i > 0; i--)
                    headerString += "#";
                headerString += setCorrectIndentation(currentDepth);
                headerString += (" " + res.tag() + "\n");
            }
            writeHeader(headerString);  //Write Headers


            for (Element e : links) {
                String url1 = e.attr("abs:href");
                link1 = new Link(url1, currentDepth + 1);    //Next Page is old depth+1
                page.getLinks().add(link1);
                String linkStr = "";
                linkStr += setCorrectIndentation(currentDepth);
                linkStr += (" " + url1 + "\n");
                writeLink(linkStr);   //Write Links

                if (currentDepth < maxDepth)
                    readPage(link1);
            }
        } catch (Exception e) {
            System.out.println("Broken link: " + link.getUrl());
            e.printStackTrace();
        }
    }*/

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