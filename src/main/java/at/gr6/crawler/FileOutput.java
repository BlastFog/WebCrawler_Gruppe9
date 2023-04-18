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
        fr.write("-----START OF FILE-----\n");
        fr.write("input: <a>" + p.getUrl() + "</a>");
        fr.write("\n");
    }

    public void writeLangage(Translation l) throws IOException {
        fr.write("<br>sourceLanguage: " + l.getSourceLang());
        fr.write("\n");
        fr.write("<br>target language: " + l.getTargetLang());
        fr.write("\n");
        fr.write("<br>summary: ");
        fr.write("\n");
    }

    public void writeBody(Page p) throws IOException {
        fr.write(p.formatPage());
    }

    public void closeFile() throws IOException {
        fr.write("\n-----END OF FILE-----");
        fr.close();
    }
}
