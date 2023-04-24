package at.gr6.test;

import at.gr6.crawler.FileOutput;
import at.gr6.crawler.Page;
import at.gr6.crawler.Translation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileOutputTest {

    String path = "writer_test.md";

    FileOutput fileOutput;

    @Mock
    Page pageMock = mock(Page.class);

    @Mock
    Translation translationMoc = mock(Translation.class);

    FileReader reader;
    BufferedReader bufferedReader;

    @BeforeEach
    void setUp() throws IOException {
        fileOutput = new FileOutput(path);



        //pageMock = new Page("https://orf.at/",1);
    }

    @Test
    void writeBeginning() throws IOException {

        when(pageMock.getUrl()).thenReturn("https://orf.at/");
        fileOutput.writeBeginning(pageMock);
        fileOutput.closeWriter();
        String actual = readTest();
        String expected = "-----START OF FILE-----\ninput: <a>https://orf.at/</a>\n";
        assertEquals(expected,actual);
    }

    @Test
    void writeLanguage() throws IOException {
        when(translationMoc.getSourceLang()).thenReturn("German");
        when(translationMoc.getTargetLang()).thenReturn("English(British)");
        fileOutput.writeLanguage(translationMoc);
        fileOutput.closeWriter();
        String actual = readTest();
        String expected = "<br>source language: German\n<br>target language: English(British)\n<br>summary: \n";
        assertEquals(expected,actual);
    }

    @Test
    void writeBody() throws IOException {
        when(pageMock.getformattedPage()).thenReturn("### ->Sample Header \n\n ->https://orf.at/news\n");
        assertDoesNotThrow(() -> fileOutput.writeBody(pageMock));
        fileOutput.closeWriter();
        String actual = readTest();
        String expected = "### ->Sample Header \n\n ->https://orf.at/news\n";

        assertEquals(expected,actual);


    }

    @Test
    void closeFile() throws IOException {
        assertDoesNotThrow(()-> fileOutput.closeFile());
        String actual = readTest();
        String expected = "\n-----END OF FILE-----";
        assertEquals(expected,actual);


    }
    private String readTest() throws IOException {
        reader = new FileReader(path);
        bufferedReader = new BufferedReader(reader);
        int i;
        String result = "";
        while((i=bufferedReader.read())!=-1){
            result+=(char)i;
        }
        bufferedReader.close();
        return result;

    }

    @AfterEach
    void tearDown() throws IOException {
        File f = new File(path);
        assertTrue(f.delete());

    }
}