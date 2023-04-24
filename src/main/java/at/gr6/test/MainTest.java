package at.gr6.test;

import at.gr6.crawler.FileOutput;
import at.gr6.crawler.Main;
import at.gr6.crawler.Page;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;

import static at.gr6.crawler.Main.main;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class MainTest {

    @Test
    public void testMain() throws IOException {
        String[] args = {"https://example.com","2","en-GB","false"};
        main(args);
    }
}
