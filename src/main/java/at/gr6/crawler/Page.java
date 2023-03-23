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
        s+=" ,Links: {";
        for (Page p:subPage) {
            s+=p.getUrl()+"\n";
        }
        s+="}";
        return s;
    }

    /**
     * Returns a String of the entire Page formatted for File I/O
     * @return
     */
    public String formatPage(){
        String str ="";
        for(Element h: header) {
            for (int i = Integer.parseInt(h.tagName().charAt(1) + ""); i > 0; i--)
                str += "#";
            str += setCorrectIndentation(this.depth);
            str += h.text() + "\n";
        }
            str+="\n";
            for (Page p:subPage) {
                str+="<br>";
                str+=setCorrectIndentation(this.depth);
                if(p.isBroken())
                    str+="broken link <a>"+p.getUrl()+"</a>\n";
                else
                    str+="link to <a>"+p.getUrl()+"</a>\n";
            }


        return str;
    }
    private static String setCorrectIndentation(int depth) {  //TODO: LAGER DEN SCHEISS AUS
        String indents = " ";
        for (int i = 0; i < depth; i++) {
            indents += "-";
        }
        return (indents += ">");
    }

    public void setSubPages(Elements links) {
        for (Element e:links) {
            //System.out.println("Links:"+e.attr("abs:href"));
            this.subPage.add(new Page(e.attr("abs:href"),this.depth+1));
        }
    }
}
