package at.gr6.test;

import at.gr6.crawler.Page;
import at.gr6.crawler.Translation;
import com.deepl.api.DeepLException;
import com.deepl.api.Translator;
import kotlin.collections.ArraysKt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TranslationTest {
    static Translation translation;
    @BeforeEach
    private void setup() throws DeepLException, InterruptedException{
        translation = new Translation("en-GB",true,"56a1abfc-d443-0e69-8963-101833b4014e:fx");
    }

    @Test
    void testTranslatePage() throws DeepLException, InterruptedException {
        String translated1 = "Welcome to this test page";
        String translated2 = "This is a test";

        Page testPage = new Page("https://example.com",1);
        ArrayList<String> headerList = new ArrayList<>();
        headerList.add("Willkommen auf dieser Test Seite");
        headerList.add("Das ist ein test");
        testPage.setHeaderStringList(headerList);

        translation.translatePage(testPage);

        assertEquals(translated1,testPage.getHeaderStringList().get(0));
        assertEquals(translated2,testPage.getHeaderStringList().get(1));
    }

    @Test
    void testGetSourceLang() throws DeepLException, InterruptedException {
        testTranslatePage();
        translation.setDetectedLanguage();
        assertEquals(translation.getSourceLang(),"German");
    }

    @Test
    void testGetTargetLang() {
        assertEquals(translation.getTargetLang(),"English (British)");
    }


}