package webapp.model;

import java.util.List;
import java.util.Objects;

public class Company extends AbstractSection {
    private final String companyName;

    protected List<Occupation> getOccupationList() {
        return occupationList;
    }

    private final List<Occupation> occupationList;

    public Company(String companyName, List<Occupation> occupationList, Link webSite) {
        Objects.requireNonNull(companyName, "Company name can not be null");
        Objects.requireNonNull(occupationList, "Occupation name can not be null");
        Objects.requireNonNull(webSite, "Website name can not be null");
        this.companyName = companyName;
        this.occupationList = occupationList;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyName='" + companyName + '\'' +
                ", occupationList=" + occupationList +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Company company)) return false;
        return Objects.equals(getCompanyName(), company.getCompanyName()) && Objects.equals(getOccupationList(), company.getOccupationList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompanyName(), getOccupationList());
    }
}
