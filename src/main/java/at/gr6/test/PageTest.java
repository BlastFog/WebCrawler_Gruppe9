package at.gr6.test;

import at.gr6.crawler.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PageTest {
    Page page;
    String url = "https://orf.at/";
    String header = "###sample Header ";
    int depth = 1;

    @BeforeEach
    void setUp() {
        page = new Page(url, depth);
    }

    @Test
    void testConstructor() {
        Page page1 = new Page(url, depth);
        assertTrue(page1.equals(page));
        Page page2 = new Page("wrong url", 2);
        assertFalse(page2.equals(page));
    }

    @Test
    void testHeaderStringList() {
        ArrayList<String> headerList = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            headerList.add(header + i);
        page.setHeaderStringList(headerList);

        ArrayList<String> actualHeaderStringList = page.getHeaderStringList();
        for (int i = 0; i < 10; i++)
            assertEquals(headerList.get(i), actualHeaderStringList.get(i));
    }

    @Test
    void testIsBroken() {
        assertFalse(page.isBroken());
    }

    @Test
    void testSetBroken() {
        page.setBroken(true);
        assertTrue(page.isBroken());
    }

    @Test
    void testSetSubPages() {
        ArrayList<String> linkList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            linkList.add(url + i);
        }
        page.setSubPages(linkList);
        int index = 0;
        for (Page subPage : page.getSubPage()) {
            assertEquals(linkList.get(index), subPage.getUrl());
            assertEquals(depth + 1, subPage.getDepth());
            index++;
        }
    }

    @Test
    void testGetformattedPage() {
        ArrayList<String> headerList = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            headerList.add(header + i);
        page.setHeaderStringList(headerList);

        ArrayList<String> linkList = new ArrayList<String>();
        for (int i = 0; i < 5; i++)
            linkList.add(url + i);
        page.setSubPages(linkList);
        page.getSubPage().get(4).setBroken(true);
        String expected = generateExpectedString(depth);

        assertEquals(expected, page.getformattedPage());
    }


    private String generateExpectedString(int depth) {
        String expected = "";
        for (int i = 0; i < 5; i++)
            expected += fixMarkdownFormat(header + i, depth) + "\n";
        expected += "\n";
        for (int i = 0; i < 4; i++)
            expected += "<br> " + getIndentation(depth) + "link to <a>" + url + i + "</a>" + "\n";
        expected += "<br> " + getIndentation(depth) + "broken link <a>" + url + 4 + "</a>" + "\n";
        return expected;
    }

    private String fixMarkdownFormat(String header, int depth) {
        String headerGrade = "";
        String headerString = "";
        for (int i = 0; i < header.length(); i++) {
            if (header.charAt(i) != '#') {
                headerGrade = header.substring(0, i);
                headerString = header.substring(i);
                break;
            }

        }
        String result = headerGrade + " " + getIndentation(depth) + headerString;
        return result;
    }

    private String getIndentation(int depth) {
        String result = "";
        for (int i = 0; i < depth; i++)
            result += "-";
        result += ">";
        return result;
    }


}