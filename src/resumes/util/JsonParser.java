package resumes.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
}
