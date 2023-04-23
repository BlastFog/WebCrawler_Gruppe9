package at.gr6.crawler;

import org.mockito.InjectMocks;

import java.io.FileWriter;
import java.io.IOException;

public class FileOutput {
    private String path;
    private final FileWriter fileWriter;






    public FileOutput(String path) throws IOException {
        this.path = path;
        fileWriter = new FileWriter(path);
    }

    public void writeBeginning(Page p) throws IOException {
        fileWriter.write("-----START OF FILE-----\n");
        fileWriter.write("input: <a>" + p.getUrl() + "</a>");
        fileWriter.write("\n");
    }

    public void writeLanguage(Translation l) throws IOException {
        fileWriter.write("<br>source language: " + l.getSourceLang());
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
    public void  closeWriter() throws IOException {
        fileWriter.close();
    }
}
