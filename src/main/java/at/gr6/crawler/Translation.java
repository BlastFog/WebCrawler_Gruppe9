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

    public Translation(String targetLangTag, boolean translate, String authKey) {
        this.targetLangTag = targetLangTag;
        this.targetLang = getFullLanguage(targetLangTag);
        this.translate = translate;
        languageStatistics = new HashMap<String, Integer>();
        translator = new Translator(authKey);
    }

    public void translatePage(Page page) throws DeepLException, InterruptedException {  //translates only the String list of the Page
        if (translate) {
            TextResult result;
            ArrayList<String> headerList = page.getHeaderStringList();
            for (int i = 0;i<headerList.size();i++){
                result = translator.translateText(headerList.get(i), sourceLangTag, targetLangTag);
                String detectedLanguage = result.getDetectedSourceLanguage();
                headerList.set(i, result.getText());

                if (!languageStatistics.containsKey(detectedLanguage))     //For language statistics
                    languageStatistics.put(detectedLanguage, 1);
                else
                    languageStatistics.put(detectedLanguage, languageStatistics.get(detectedLanguage) + 1);
            }
            sourceLangTag = getdetectedLanguage();
        }

    }

    public String getSourceLang() {
        return getFullLanguage(sourceLangTag);
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }

    private String getdetectedLanguage() {
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
    private void setdetectedLanguage() {
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

    public void enableTranslation(boolean translate) {
        this.translate = translate;
    }
    public String getLimitString() {
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

    private String getFullLanguage(String lang) {
        String language = "";
        switch (lang.toUpperCase()) {
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
