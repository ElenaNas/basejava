package webapp.model;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENTS("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String title;

    public String getTitle() {
        return title;
    }

    SectionType(String title) {
        this.title = title;
    }
}
