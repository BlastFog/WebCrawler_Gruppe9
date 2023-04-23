package at.gr6.crawler;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Page {
    //private Elements headers;
    private ArrayList<String> headerStringList; //List of headers as String, these will be translated
    private boolean isBroken;
    private int depth;
    private String url;
    private List<Page> subPage;

    public String getUrl() {
        return this.url;
    }

    public void setHeaderStringList(ArrayList<String> headerStringList) {
        this.headerStringList = headerStringList;
    }

    public Page(String url, int depth) {
        //this.headers = new Elements();
        this.subPage = new ArrayList<Page>();
        this.url = url;
        this.depth = depth;
        this.isBroken = false;
        this.headerStringList = new ArrayList<>();
    }

    public ArrayList<String> getHeaderStringList() {
        return this.headerStringList;
    }

    public boolean isBroken() {
        return this.isBroken;
    }

    public void setBroken(boolean broken) {
        this.isBroken = broken;
    }
/*
    public void setHeader(Elements header) {
        this.headers = header;
    }
*/
    public List<Page> getSubPage() {
        return this.subPage;
    }

    public int getDepth() {
        return this.depth;
    }

    @Override
    public String toString() {
        String s = "Page{ url= " + this.url + ", depth= " + depth + ", broken=" + isBroken + " ,Headers:{ \n ";
        for (String header : headerStringList)
            s += header + "\n";
        s += " ,Links: {";
        for (Page p : subPage)
            s += p.getUrl() + "\n";
        s += "}";
        return s;
    }

    /**
     * Returns a String of the entire Page formatted for File I/O
     *
     * @return
     */
  /*
    public String formatPage() {
        String str = "";
        int index = 0;
        for (String header : headerStringList) {
            for (int i = Integer.parseInt(h.tagName().charAt(1) + ""); i > 0; i--)  //Detect Grade of the header
                str += "#";
            str += setCorrectIndentation();
            str += headerStringList.get(index) + "\n";
            index++;
        }
        str += "\n";
        for (Page p : subPage) {
            str += "<br>";
            str += setCorrectIndentation();
            if (p.isBroken())
                str += "broken link <a>" + p.getUrl() + "</a>\n";
            else
                str += "link to <a>" + p.getUrl() + "</a>\n";
        }
        return str;
    }

    private String setCorrectIndentation() {
        String indents = " ";
        for (int i = 0; i < depth; i++)
            indents += "-";
        return (indents += ">");
    }*/
/*
    public void addHeadersToList() {
        for (Element header : headers)
            headerStringList.add(header.text());
    }
*/
    public void setSubPages(ArrayList<String> linkList) {
        for (String link : linkList)
            this.subPage.add(new Page(link, this.depth + 1));
    }
}
