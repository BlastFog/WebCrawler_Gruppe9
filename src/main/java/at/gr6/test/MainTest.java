package at.gr6.test;

import at.gr6.crawler.Main;
import at.gr6.crawler.Page;
import org.junit.Test;

import static at.gr6.crawler.Main.main;
import static org.mockito.Mockito.*;


public class MainTest {

    @Test
    public void testMain(){
        String[] args = {"https://example.com","1","en-GB"};
        main(args);
    }
}
