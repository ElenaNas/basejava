package webapp.model;

import java.io.Serializable;
import java.util.*;

public class Resume  implements Comparable<Resume>, Serializable {
    private static final long serailUID=1L;

    private final String uuid;

    private final String fullName;


    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public String getFullName() {
        return fullName;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getFullName());
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