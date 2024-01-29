package webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public record Occupation(String jobTitle, String jobDescription, LocalDate fromPeriod, LocalDate tillPeriod) {
    public Occupation {
        Objects.requireNonNull(jobTitle, "Occupation title can not be null");
        Objects.requireNonNull(jobDescription, "Occupation description can not be null");
        Objects.requireNonNull(fromPeriod, "Starting date can not be null");
        Objects.requireNonNull(tillPeriod, "End date can not be null");
    }

    @Override
    public String toString() {
        return "Occupation{" +
                "jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", fromPeriod=" + fromPeriod +
                ", tillPeriod=" + tillPeriod +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Occupation that)) return false;
        return Objects.equals(jobTitle(), that.jobTitle()) && Objects.equals(jobDescription(), that.jobDescription()) && Objects.equals(fromPeriod(), that.fromPeriod()) && Objects.equals(tillPeriod(), that.tillPeriod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobTitle(), jobDescription(), fromPeriod(), tillPeriod());
    }
}
