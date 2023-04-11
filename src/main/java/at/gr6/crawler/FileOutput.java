package at.gr6.crawler;

import java.io.FileWriter;
import java.io.IOException;

public class FileOutput {

    private String path;

    private FileWriter fr;


    public FileOutput(String path) throws IOException {
        this.path = path;
        fr = new FileWriter(path);

    }

    public void writeBeginning(Page p) throws IOException {
        fr.write("-----START OF FILE-----");
        fr.write("input: <a>"+p.getUrl()+"</a>");
    }
    public void writeLangage(Translation l) throws IOException {
        fr.write("<br>sourceLanguage:"+l.getSourceLang());
        fr.write("<br>target language:"+l.getTargetLang());
        fr.write("<br>summary: ");
    }
    public void writeBody(Page p) throws IOException {
        fr.write(p.formatPage());
    }

    public void closeFile() throws IOException {
        fr.write("-----END OF FILE-----");
        fr.close();
    }


}
