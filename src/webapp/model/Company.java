package webapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import webapp.util.LocalDateXMLAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static webapp.util.DateUtil.NOW;
import static webapp.util.DateUtil.of;


@XmlAccessorType(XmlAccessType.FIELD)
public class Company extends Section implements Serializable {
    private static final long SERIAL_VERSION_UID = 1L;

    @Expose
    private Link homePage;

    @Expose
    private List<Occupation> occupationList;

    public Company() {
    }

    public Company(String name, String url, Occupation... occupations) {
        this(new Link(name, url), Arrays.asList(occupations));
    }

    public Company(Link homePage, List<Occupation> occupationList) {
        this.homePage = homePage;
        this.occupationList = occupationList;
    }

    public List<Occupation> getOccupationList() {
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Occupation implements Serializable {
        private static final long SERIAL_VERSION_UID = 1L;

        @XmlJavaTypeAdapter(LocalDateXMLAdapter.class)
        @Expose
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate fromPeriod;
        @XmlJavaTypeAdapter(LocalDateXMLAdapter.class)
        @Expose
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate tillPeriod;
        @Expose
        private String jobTitle;
        @Expose
        private String jobDescription;

        public Occupation() {
        }

        public void setFromPeriod(LocalDate fromPeriod) {
            this.fromPeriod = fromPeriod;
        }

        public void setTillPeriod(LocalDate tillPeriod) {
            this.tillPeriod = tillPeriod;
        }

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

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public void setJobDescription(String jobDescription) {
            this.jobDescription = jobDescription;
        }

        public String getJobDescription() {
            return jobDescription;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Occupation that)) return false;
            return Objects.equals(getFromPeriod(), that.getFromPeriod()) && Objects.equals(getTillPeriod(), that.getTillPeriod()) && Objects.equals(getJobTitle(), that.getJobTitle()) && Objects.equals(getJobDescription(), that.getJobDescription());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getFromPeriod(), getTillPeriod(), getJobTitle(), getJobDescription());
        }

        @Override
        public String toString() {
            return "Occupation(" + fromPeriod + ',' + tillPeriod + ',' + jobTitle + ',' + jobDescription + ')';
        }
    }
}

