package webapp.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import webapp.model.Resume;
import webapp.model.Section;
import webapp.model.TextSection;

import static webapp.TestData.RESUME_1;

class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume2 = JsonParser.read(json, Resume.class);
        Assertions.assertEquals(RESUME_1, resume2);

    }

    @Test
    void write() {
        Section section1 = new TextSection("Text Section 1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assertions.assertEquals(section1, section2);
    }
}