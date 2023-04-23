package at.gr6.test;

import at.gr6.crawler.Page;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class PageTest {

    Page page;

    @BeforeEach
    void setUp() {
        page = new Page("orf.at",1);

    }



}