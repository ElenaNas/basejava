package webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    private final List<Company> companies;

    public CompanySection(Company... companies) {
        this(Arrays.asList(companies));
    }

    public CompanySection(List<Company> companies) {
        Objects.requireNonNull(companies, "Company can not be null");
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public String toString() {
        return "\nCompanySection{" +
                "\ncompanies=" + companies +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof CompanySection that)) return false;
        return Objects.equals(getCompanies(), that.getCompanies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompanies());
    }
}
