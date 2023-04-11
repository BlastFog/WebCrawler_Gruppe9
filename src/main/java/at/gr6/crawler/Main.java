package at.gr6.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import com.deepl.api.*;

public class Main {
    //static Translator translator;
    static Document doc;
    static String sourceLanguage = "";
    static String targetLanguage = "";
    static String url = "";
    static int maxDepth = 1;
    //static TextResult result;
    static Translation translation;

    static boolean translate = false;

    static String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";

    //static Page page;

    static FileOutput filer;
    static Page p;

    public static void main(String[] args) {
        url = args[0];
        maxDepth = Integer.parseInt(args[1]);
        targetLanguage = args[2];

        p = new Page(url, 1);
        readPage(p);
        setupWriter();
        setupTranslation();
        translatePages(p);
        write2File(p);
        try {
            filer.closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public static void setupTranslation() {
        translation = new Translation(targetLanguage, translate, authKey);
    }

    public static void translatePages(Page page) {
        try {
            translation.translatePage(page);
        } catch (DeepLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (p.getDepth() < maxDepth) {
            for (Page subPage : page.getSubPage()) {
                translatePages(subPage);
            }
        }

    }

    private static void setupWriter() {
        try {
            filer = new FileOutput("./report.md");
            filer.writeBeginning(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write2File(Page p) {
        try {
            filer.writeBody(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (p.getDepth() < maxDepth) {
            for (Page page : p.getSubPage()) {
                write2File(page);
            }
        }
    }

    private static void printPages(Page p) {
        System.out.println(p.toString());
        if (p.getDepth() < maxDepth) {
            for (Page page : p.getSubPage()) {
                printPages(page);
            }
        }
    }


    private static String setCorrectIndentation(int depth) {
        String indents = " ";
        for (int i = 0; i < depth; i++) {
            indents += "-";
        }
        return (indents += ">");
    }

    private static void readPage(Page p) {
        try {
            doc = Jsoup.connect(p.getUrl()).get();
            Elements links = doc.select("a[href]");
            Elements headers = doc.select("h1,h2,h3,h4,h5,h6");
            p.setHeader(headers);
            p.setSubPages(links);
            if (p.getDepth() < maxDepth) {
                for (Page page : p.getSubPage()) {
                    readPage(page);
                }
            }
        } catch (Exception e) {
            p.setBroken(true);
            e.printStackTrace();
        }
    }


}