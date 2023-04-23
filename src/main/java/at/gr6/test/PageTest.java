package at.gr6.test;

import at.gr6.crawler.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageTest {

    Page page;

    String url = "https://orf.at/";

    @BeforeEach
    void setUp() {
        page = new Page(url,1);

    }
    @Test
    void testConstructor(){
        Page page1 = new Page(url,1);
        assertFalse(page1.equals(page));
    }


    @Test
    void getUrl() {
    }

    @Test
    void setHeaderStringList() {

    }
    @Test
    void getHeaderStringList() {
    }

    @Test
    void isBroken() {
    }

    @Test
    void setBroken() {
    }

    @Test
    void getSubPage() {
    }

    @Test
    void getDepth() {
    }

    @Test
    void testToString() {
    }

    @Test
    void getformattedPage() {
    }

    @Test
    void setSubPages() {
    }
}