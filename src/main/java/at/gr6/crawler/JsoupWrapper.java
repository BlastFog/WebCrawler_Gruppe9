package at.gr6.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class JsoupWrapper {

    private Elements headers;

    private Elements links;

    public JsoupWrapper(){
    }

    public void readWebPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        this.links = doc.select("a[href]");
        this.headers = doc.select("h1,h2,h3,h4,h5,h6");
    }
    public ArrayList<String> getHeadersList(){
        ArrayList<String> headerList = new ArrayList<String>();
        for(Element header: headers){
            headerList.add(detectHeaderGrade(header)+header.text());
        }
        return headerList;
    }
    public ArrayList<String> getLinkList(){
        ArrayList<String> headerList = new ArrayList<String>();
        for(Element link: links){
            headerList.add(link.text());
        }
        return headerList;
    }
    private String detectHeaderGrade(Element header){
        String str = "";
        for (int i = Integer.parseInt(header.tagName().charAt(1) + ""); i > 0; i--){
        str += "#";
        }
        return str;
    }

}