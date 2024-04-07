package resumes.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import resumes.model.Resume;
import resumes.model.Section;

import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;

public class JsonParser {
    static Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Section.class, new JsonSectionAdapter<>())
            .registerTypeAdapter(LocalDate.class, new LocalDateJSONAdapter())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

    public static <T>String write(T object, Class<Section> sectionClass) {
        return GSON.toJson(object, sectionClass);
    }

    public static <T>T read(String sectionValue, Class<T> sectionClass) {
        return GSON.fromJson(sectionValue, sectionClass);
    }

    public static String write(Resume resume) {
        return GSON.toJson(resume);
    }

    public static String write(Section section) {
        return GSON.toJson(section);
    }
}
