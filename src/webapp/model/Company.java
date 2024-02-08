package webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static webapp.util.DateUtil.NOW;
import static webapp.util.DateUtil.of;


public class Company extends Section implements Serializable {
    private static final long serailUID=1L;

    private final Link homePage;

    private List<Occupation> occupationList = new ArrayList<>();

    public Company(String name, String url, Occupation... occupations) {
        this(new Link(name, url), Arrays.asList(occupations));
    }

    public Company(Link homePage, List<Occupation> occupationList) {
        this.homePage = homePage;
        this.occupationList = occupationList;
    }

    protected List<Occupation> getOccupationList() {
        return occupationList;
    }

    public Link getHomePage() {
        return homePage;
    }

    @Override
    public String toString() {
        return "\nCompany{" +
                "\nhomePage=" + homePage +
                ", \noccupationList=" + occupationList +
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

    public static class Occupation implements Serializable{
        private static final long serailUID=1L;

        private final LocalDate fromPeriod;
        private final LocalDate tillPeriod;
        private final String jobTitle;
        private final String jobDescription;

        public Occupation(int startYear, Month fromPeriod, String jobTitle, String jobDescription) {
            this(of(startYear, fromPeriod), NOW, jobTitle, jobDescription);
        }

        public Occupation(int startYear, Month startMonth, int endYear, Month endMonth, String jobTitle, String jobDescription) {
            this(of(startYear, startMonth), of(endYear, endMonth), jobTitle, jobDescription);
        }

        public Occupation(LocalDate fromPeriod, LocalDate tillPeriod, String jobTitle, String jobDescription) {
            Objects.requireNonNull(fromPeriod, "Starting date can not be null");
            Objects.requireNonNull(tillPeriod, "End date can not be null");
            Objects.requireNonNull(jobTitle, "Occupation title can not be null");
            Objects.requireNonNull(jobDescription, "Occupation description can not be null");
            this.fromPeriod = fromPeriod;
            this.tillPeriod = tillPeriod;
            this.jobTitle = jobTitle;
            this.jobDescription = jobDescription;
        }

        public LocalDate getFromPeriod() {
            return fromPeriod;
        }

        public LocalDate getTillPeriod() {
            return tillPeriod;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public String getJobDescription() {
            return jobDescription;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Occupation position = (Occupation) o;
            return Objects.equals(fromPeriod, position.fromPeriod) &&
                    Objects.equals(tillPeriod, position.tillPeriod) &&
                    Objects.equals(jobTitle, position.jobTitle) &&
                    Objects.equals(jobDescription, position.jobDescription);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fromPeriod, tillPeriod, jobTitle, jobDescription);
        }

        @Override
        public String toString() {
            return "Occupation(" + fromPeriod + ',' + tillPeriod + ',' + jobTitle + ',' + jobDescription + ')';
        }
    }
}

