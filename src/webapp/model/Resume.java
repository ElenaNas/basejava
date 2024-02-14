package webapp.model;

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

    private String uuid;

    private String fullName;


    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume resume)) return false;
        return Objects.equals(getUuid(), resume.getUuid()) && Objects.equals(getFullName(),
                resume.getFullName()) && Objects.equals(contacts, resume.contacts)
                && Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getFullName(), contacts, sections);
    }

    @Override
    public int compareTo(Resume other) {
        int fullNameComparison = this.fullName.compareTo(other.fullName);
        if (fullNameComparison != 0) {
            return fullNameComparison;
        } else {
            return this.uuid.compareTo(other.uuid);
        }
    }

    @Override
    public String toString() {
        return "\nResume{" +
                "\nuuid='" + uuid + '\'' +
                ", \nfullName='" + fullName + '\'' +
                ", \ncontacts=" + contacts +
                ", \nsections=" + sections +
                '}';
    }
}