package webapp.model;

import java.io.Serializable;
import java.util.Objects;

public record Link(String name, String url) implements Serializable {
    private static final long SERIAL_VERSION_UID =1L;

    public Link {
        Objects.requireNonNull(name, "Company name can not be null");
    }

    @Override
    public String toString() {
        return "\nLink{" +
                "\nname='" + name + '\'' +
                ", \nurl='" + url + '\'' +
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
