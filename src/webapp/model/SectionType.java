package webapp.model;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public enum SectionType {
    @Expose
    OBJECTIVE("Career Objective"),
    @Expose
    PERSONAL("Personal Info"),
    @Expose
    ACHIEVEMENTS("Achievements"),
    @Expose
    QUALIFICATIONS("Technical Skills"),
    @Expose
    EDUCATION("Academic Background"),
    @Expose
    EXPERIENCE("Selected Working Experience");

    @Expose
    private final String title;

    public String getTitle() {
        return title;
    }

    SectionType(String title) {
        this.title = title;
    }
}
