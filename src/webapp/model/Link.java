package webapp.model;

import java.util.Objects;

public class Link {
    private final String name;
    private final String url;

    public Link(String name, String url) {
        Objects.requireNonNull(name, "Company name can not be null");
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Link{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Link link)) return false;
        return Objects.equals(getName(), link.getName()) && Objects.equals(getUrl(), link.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getUrl());
    }
}
