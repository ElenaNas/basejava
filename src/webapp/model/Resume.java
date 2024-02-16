package webapp.model;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume  implements Comparable<Resume>, Serializable {
    private static final long SERIAL_VERSION_UID =1L;

   @Expose
    private String uuid;

    @Expose
    private String fullName;

    @Expose
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    @Expose
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "UUID can not be null");
        Objects.requireNonNull(fullName, "Name can not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getContact(ContactType contactSection) {
        return contacts.get(contactSection);
    }

    public void setContact(ContactType contactSection, String contact) {
        contacts.put(contactSection, contact);
    }

    public Section getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public void setSection(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
    }

    public void addContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public void addSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume resume)) return false;
        return Objects.equals(getUuid(), resume.getUuid()) && Objects.equals(getFullName(), resume.getFullName()) && Objects.equals(getContacts(), resume.getContacts()) && Objects.equals(getSections(), resume.getSections());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getFullName(), getContacts(), getSections());
    }

    @Override
    public int compareTo(Resume o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contacts=" + contacts +
                ", sections=" + sections +
                '}';
    }
}