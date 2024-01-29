package webapp.model;

import java.util.Objects;

public record Link(String name, String url) {
    public Link {
        Objects.requireNonNull(name, "Company name can not be null");
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
        return Objects.equals(name(), link.name()) && Objects.equals(url(), link.url());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name(), url());
    }
}
