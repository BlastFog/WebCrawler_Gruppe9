package at.gr6.crawler;

import java.io.IOException;

import com.deepl.api.*;

public class Main {
    static String targetLanguage = "";
    static String url = "";
    static int maxDepth;
    static Translation translation;
    static boolean translate = false;
    static String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";
    static FileOutput filer;
    static Page page;


    public static void main(String[] args) {
        url = args[0];
        maxDepth = Integer.parseInt(args[1]);
        targetLanguage = args[2];
        if (args[3].equals("true"))
            translate = true;
        page = new Page(url, 1);
        readPage(page);
        setupWriter();
        setupTranslation();
        translatePages(page);
        writeLangHeader();
        writeToFile(page);
        try {
            filer.closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeLangHeader() {
        try {
            translation.setDetectedLanguage();
            filer.writeLanguage(translation);
        } catch (IOException | DeepLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setupTranslation() {
        try {
            translation = new Translation(targetLanguage, translate, authKey);
        } catch (DeepLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void translatePages(Page page) {
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

    private static void writeToFile(Page page) {
        try {
            filer.writeBody(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (page.getDepth() < maxDepth) {
            for (Page subPage : page.getSubPage()) {
                writeToFile(subPage);
            }
        }
    }

    private static void readPage(Page page) {
        try {
            JsoupWrapper jsoupWrapper = new JsoupWrapper();
            jsoupWrapper.readWebPage(page.getUrl());
            page.setHeaderStringList(jsoupWrapper.getHeadersList());
            page.setSubPages(jsoupWrapper.getLinkList());
            if (page.getDepth() < maxDepth) {
                for (Page subPage : page.getSubPage()) {
                    readPage(subPage);
                }
            }
        } catch (Exception e) {
            page.setBroken(true);
        }
    }
}