package webapp.model;

import java.util.Objects;
import java.util.UUID;

public class Resume  implements Comparable<Resume>{

    private final String uuid;

    private final String fullName;

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
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}