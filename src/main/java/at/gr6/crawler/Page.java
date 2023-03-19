package at.gr6.crawler;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private Elements header;
    private List<Link> links;

    public Page() {
        this.header = new Elements();
        this.links = new ArrayList<Link>();
    }

    public Elements getHeader() {
        return header;
    }

    public List<Link> getLinks() {
        return links;
    }
}