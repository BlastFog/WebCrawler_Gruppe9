package at.gr6.crawler;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Page {         //Hierachie needed; brauch eigene Url; Vaterpage sollte Kinderpages haben
    private Elements header;
    private List<Link> links;   // besser Liste von Pages

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
