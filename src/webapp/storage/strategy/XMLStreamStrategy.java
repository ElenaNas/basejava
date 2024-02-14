package webapp.storage.strategy;

import webapp.model.*;
import webapp.util.XMLParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XMLStreamStrategy implements IStreamStrategy {
    private final XMLParser xmlParser;

    public XMLStreamStrategy() {
        xmlParser = new XMLParser(Resume.class, Company.class, Link.class, CompanySection.class,
                TextSection.class, ListSection.class, Company.Occupation.class);
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        xmlParser.marshall(resume, writer);
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return xmlParser.unmarshall(reader);
    }
}
