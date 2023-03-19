package at.gr6.crawler;

public class Link {
    private String url;
    private int depthCounter;
    boolean broken; //Broken link

    public Link(String url, int depthCounter) {
        this.url = url;
        this.depthCounter = depthCounter;
    }

    public String getUrl() {
        return url;
    }

    public int getDepthCounter() {
        return depthCounter;
    }

    public void setDepthCounter(int depthCounter) {
        this.depthCounter = depthCounter;
    }

    @Override
    public String toString() {
        return "Link{Url: "+this.getUrl()+", Depth: "+this.getDepthCounter()+"}";
    }
}