package webapp.model;

public enum SectionType {
    PERSONAL("Personal Info"),
    OBJECTIVE("Career Objective"),
    ACHIEVEMENTS("Achievements"),
    QUALIFICATIONS("Technical Skills"),
    EXPERIENCE("Selected Working Experience"),
    EDUCATION("Academic Background");

    private final String title;

    public String getTitle() {
        return title;
    }

    SectionType(String title) {
        this.title = title;
    }
}
