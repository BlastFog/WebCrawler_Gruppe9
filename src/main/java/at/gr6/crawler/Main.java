package at.gr6.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import com.deepl.api.*;

public class Main {
    static Document doc;
    static String targetLanguage = "";
    static String url = "";
    static int maxDepth;
    static Translation translation;
    static boolean translate = false;
    static String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";
    static FileOutput filer;
    static Page page;

    static JsoupWrapper jsoupWrapper;

    public static void main(String[] args) {
        url = args[0];
        maxDepth = Integer.parseInt(args[1]);
        targetLanguage = args[2];
        page = new Page(url, 1);
        jsoupWrapper = new JsoupWrapper();
        readPage(page);
        setupWriter();
        setupTranslation();
        translatePages(page);
        writeLangHeader(translation);
        write2File(page);
        try {
            filer.closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeLangHeader(Translation translation) {
        translation.setDetectedLanguage();
        try {
            filer.writeLangage(translation);
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
        if (Main.page.getDepth() < maxDepth) {
            for (Page subPage : page.getSubPage()) {
                translatePages(subPage);
            }
        }

    }

    private static void setupWriter() {
        try {
            filer = new FileOutput("./report.md");
            filer.writeBeginning(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write2File(Page page) {
        try {
            filer.writeBody(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (page.getDepth() < maxDepth) {
            for (Page subPage : page.getSubPage()) {
                write2File(subPage);
            }
        }
    }

    private static void readPage(Page p) {
        try {
            jsoupWrapper.readWebPage(p.getUrl());
            p.setHeaderStringList(jsoupWrapper.getHeadersList());
            p.setSubPages(jsoupWrapper.getLinkList());
            if (p.getDepth() < maxDepth) {
                for (Page page : p.getSubPage()) {
                    readPage(page);
                }
            }
        } catch (Exception e) {
            p.setBroken(true);
        }
    }
}