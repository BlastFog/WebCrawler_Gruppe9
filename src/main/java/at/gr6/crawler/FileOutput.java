package at.gr6.crawler;

import java.io.FileWriter;
import java.io.IOException;

public class FileOutput {
    private String path;
    private FileWriter fileWriter;


    public FileOutput(String path) throws IOException {
        this.path = path;
        fileWriter = new FileWriter(path);
    }

    public void writeBeginning(Page p) throws IOException {
        fileWriter.write("-----START OF FILE-----\n");
        fileWriter.write("input: <a>" + p.getUrl() + "</a>");
        fileWriter.write("\n");
    }

    public void writeLangage(Translation l) throws IOException {
        fileWriter.write("<br>sourceLanguage: " + l.getSourceLang());
        fileWriter.write("\n");
        fileWriter.write("<br>target language: " + l.getTargetLang());
        fileWriter.write("\n");
        fileWriter.write("<br>summary: ");
        fileWriter.write("\n");
    }

    public void writeBody(Page p) throws IOException {
        fileWriter.write(p.getformattedPage());
    }

    public void closeFile() throws IOException {
        fileWriter.write("\n-----END OF FILE-----");
        fileWriter.close();
    }
}
