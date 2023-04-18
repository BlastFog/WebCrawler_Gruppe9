package at.gr6.crawler;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private Elements headers;       //All headers

    private ArrayList<String> headerStringList; //List of headers as String, these will be translated
    private boolean isBroken;
    private int depth;
    private String url;
    private List<Page> subPage;

    public String getUrl() {
        return url;
    }

    public Page() {
        this.headers = new Elements();
        this.headerStringList = new ArrayList<>();
        this.subPage = new ArrayList<Page>();
        this.isBroken = false;
        addHeadersToList();
    }
    public Page(String url,int depth){
        this.headers = new Elements();
        this.subPage = new ArrayList<Page>();
        this.url = url;
        this.depth = depth;
        this.isBroken = false;
        addHeadersToList();
    }

    public ArrayList<String> getHeaderStringList() {
        return headerStringList;
    }

    public void setHeaderStringList(ArrayList<String> headerStringList) {
        this.headerStringList = headerStringList;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public void setHeader(Elements header) {
        this.headers = header;
    }

    public Elements getHeader() {
        return headers;
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
        for(Element h: headers){
            s += h.text()+"\n";
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
        for(Element h: headers) {
            for (int i = Integer.parseInt(h.tagName().charAt(1) + ""); i > 0; i--)  //Detect Grade of the header
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
    private String setCorrectIndentation(int depth) {
        String indents = " ";
        for (int i = 0; i < depth; i++) {
            indents += "-";
        }
        return (indents += ">");
    }

    private void addHeadersToList(){
        for (Element header: headers) {
            headerStringList.add(header.text());
        }

    }

    public void setSubPages(Elements links) {
        for (Element e:links) {
            //System.out.println("Links:"+e.attr("abs:href"));
            this.subPage.add(new Page(e.attr("abs:href"),this.depth+1));
        }
    }
}
