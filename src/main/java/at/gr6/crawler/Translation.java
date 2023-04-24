package at.gr6.crawler;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;

import java.util.ArrayList;
import java.util.HashMap;

public class Translation {
    private Translator translator;
    private String sourceLangTag;
    private String targetLangTag;
    private String sourceLang;
    private String targetLang;
    static HashMap<String, Integer> languageStatistics;
    private boolean translate;

    public Translation(String targetLangTag, boolean translate, String authKey) throws DeepLException, InterruptedException {
        this.translator = new Translator(authKey);
        this.targetLangTag = targetLangTag;
        this.targetLang = getFullLanguage(targetLangTag);
        this.translate = translate;
        this.languageStatistics = new HashMap<String, Integer>();
    }

    public void translatePage(Page page) throws DeepLException, InterruptedException {
        if (translate) {
            TextResult result;
            ArrayList<String> headerList = page.getHeaderStringList();
            for (int i = 0; i < headerList.size(); i++) {
                result = translator.translateText(headerList.get(i), sourceLangTag, targetLangTag);
                String detectedLanguage = result.getDetectedSourceLanguage();
                headerList.set(i, result.getText());
                updateLanguageStatistics(detectedLanguage);
            }
        }
    }

    public String getSourceLang() {
        return this.sourceLang;
    }

    public String getTargetLang() {
        return this.targetLang;
    }

    private void updateLanguageStatistics(String detectedLanguage) {
        if (!languageStatistics.containsKey(detectedLanguage))
            languageStatistics.put(detectedLanguage, 1);
        else languageStatistics.put(detectedLanguage, languageStatistics.get(detectedLanguage) + 1);
    }

    public void setDetectedLanguage() throws DeepLException, InterruptedException {
        int max = 0;
        String lang = "";
        for (String i : languageStatistics.keySet()) {
            int val = languageStatistics.get(i);
            if (val >= max) {
                max = val;
                lang = i;
            }
        }
        this.sourceLangTag = lang;
        this.sourceLang = getFullLanguage(lang);
    }

    private String getFullLanguage(String langTag) throws DeepLException, InterruptedException {
        for (com.deepl.api.Language l : translator.getTargetLanguages()) {
            if (l.getCode().toLowerCase().equals(langTag.toLowerCase())) {
                return l.getName();
            }
        }
        return null;
    }
}
