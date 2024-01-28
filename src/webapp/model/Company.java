package webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Company extends AbstractSection{
    private final String companyName;
    private final String jobTitle;
    private final String jobDescription;
    private final Link webSite;
    private final LocalDate fromPeriod;
    private final LocalDate tillPeriod;

    public Company(String companyName, String jobTitle, String jobDescription, String url,
                   LocalDate fromPeriod, LocalDate tillPeriod) {
        Objects.requireNonNull(fromPeriod, "Starting date can not be null");
        Objects.requireNonNull(tillPeriod, "End date can not be null");
        Objects.requireNonNull(jobTitle, "Job title can not be null");
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.webSite = new Link(companyName, url);
        this.fromPeriod = fromPeriod;
        this.tillPeriod = tillPeriod;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public Link getWebSite() {
        return webSite;
    }

    public LocalDate getFromPeriod() {
        return fromPeriod;
    }

    public LocalDate getTillPeriod() {
        return tillPeriod;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyName='" + companyName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", webSite=" + webSite +
                ", fromPeriod=" + fromPeriod +
                ", tillPeriod=" + tillPeriod +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Company company)) return false;
        return Objects.equals(getCompanyName(), company.getCompanyName()) && Objects.equals(getJobTitle(), company.getJobTitle()) && Objects.equals(getWebSite(), company.getWebSite()) && Objects.equals(getFromPeriod(), company.getFromPeriod()) && Objects.equals(getTillPeriod(), company.getTillPeriod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompanyName(), getJobTitle(), getWebSite(), getFromPeriod(), getTillPeriod());
    }
}
