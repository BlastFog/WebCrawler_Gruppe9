package at.gr6.crawler;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private Elements header;
    private boolean isBroken;
    private int depth;
    private String url;
    private List<Page> subPage;

    public String getUrl() {
        return url;
    }

    public Page() {
        this.header = new Elements();
        this.subPage = new ArrayList<Page>();
        this.isBroken = false;
    }
    public Page(String url,int depth){
        this.header = new Elements();
        this.subPage = new ArrayList<Page>();
        this.url = url;
        this.depth = depth;
        this.isBroken = false;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public void setHeader(Elements header) {
        this.header = header;
    }

    public Elements getHeader() {
        return header;
    }

    public List<Page> getSubPage() {
        return subPage;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        String s = "Page{ url= "+this.url+", depth= "+depth+", broken="+isBroken+" ,Headers:{ \n ";
        for(Element h: header){
            s+=h.text()+"\n";
        }
        s+="}";
        return s;
    }

    public void setSubPages(Elements links) {
        for (Element e:links) {
            System.out.println("Links:"+e.attr("abs:href"));
            Page p = new Page(e.attr("abs:href"),this.depth+1);
        }
    }
}
