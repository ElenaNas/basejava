package webapp.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Company extends AbstractSection {
    private final Link homePage;

    private final List<Occupation> occupationList;

    protected List<Occupation> getOccupationList() {
        return occupationList;
    }

    public Link getHomePage() {
        return homePage;
    }

    public Company(String name, String url, Occupation... occupations) {
        this(new Link(name, url), Arrays.asList(occupations));
    }

    public Company(Link homePage, List<Occupation> occupationList) {
        this.homePage = homePage;
        this.occupationList = occupationList;
    }

    @Override
    public String toString() {
        return "Company{" +
                "homePage=" + homePage +
                ", occupationList=" + occupationList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company company)) return false;
        return Objects.equals(homePage, company.homePage) && Objects.equals(getOccupationList(), company.getOccupationList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, getOccupationList());
    }

    public static class Occupation{
        private final String jobTitle;
        private final String jobDescription;
        private final LocalDate fromPeriod;
        private final LocalDate tillPeriod;

        public Occupation(String jobTitle, String jobDescription, LocalDate fromPeriod, LocalDate tillPeriod) {
            Objects.requireNonNull(jobTitle, "Occupation title can not be null");
            Objects.requireNonNull(jobDescription, "Occupation description can not be null");
            Objects.requireNonNull(fromPeriod, "Starting date can not be null");
            Objects.requireNonNull(tillPeriod, "End date can not be null");
            this.jobTitle = jobTitle;
            this.jobDescription = jobDescription;
            this.fromPeriod = fromPeriod;
            this.tillPeriod = tillPeriod;
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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Occupation that)) return false;
            return Objects.equals(jobTitle, that.jobTitle) && Objects.equals(jobDescription, that.jobDescription) && Objects.equals(fromPeriod, that.fromPeriod) && Objects.equals(tillPeriod, that.tillPeriod);
        }

        @Override
        public int hashCode() {
            return Objects.hash(jobTitle, jobDescription, fromPeriod, tillPeriod);
        }
    }
}
