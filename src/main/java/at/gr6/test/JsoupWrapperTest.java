package at.gr6.test;

import at.gr6.crawler.JsoupWrapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JsoupWrapperTest {
    private static JsoupWrapper jsoupWrapper;
    @BeforeAll
    private static void setup(){
        jsoupWrapper = new JsoupWrapper();
    }

    @Test
    void testReadWebPage() throws Exception {
        mockStatic(Jsoup.class);
        String url = "https://example.com";
        Connection mockConnection = mock(Connection.class);
        Document mockDocument = mock(Document.class);
        Elements mockHeaderElements = mock(Elements.class);
        Elements mockLinkElements = mock(Elements.class);

        when(Jsoup.connect(url)).thenReturn(mockConnection);
        when(mockConnection.get()).thenReturn(mockDocument);
        when(mockDocument.select("a[href]")).thenReturn(mockLinkElements);
        when(mockDocument.select("h1,h2,h3,h4,h5,h6")).thenReturn(mockHeaderElements);

        jsoupWrapper.readWebPage(url);

        verify(mockDocument).select("a[href]");
        verify(mockDocument).select("h1,h2,h3,h4,h5,h6");
    }

    @Test
    void getHeadersList() {
        Element mockElement = mock(Element.class);
        Elements headerElements = new Elements(mockElement);
        Elements mockLinkElements = mock(Elements.class);

        when(mockElement.tagName()).thenReturn("h1");
        when(mockElement.text()).thenReturn("Header");

        jsoupWrapper = new JsoupWrapper(headerElements,mockLinkElements);

        ArrayList<String> headerList = jsoupWrapper.getHeadersList();

        assertEquals(1,headerList.size());
        assertEquals("#Header",headerList.get(0));
    }


    @Test
    void getLinkList() {
    }
}