package webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Company extends AbstractSection {
    private final Link homePage;

    private List<Occupation> occupationList = new ArrayList<>();

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
}
