package webapp.model;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class MapSection {

    private final Map<Date, Place> experience;

    public MapSection(Map<Date, Place> experience) {
        this.experience =experience;
    }

    public Map<Date, Place> getExperience() {
        return experience;
    }

    @Override
    public String toString() {
        return "MapSection{" +
                "experience=" + experience +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof MapSection that)) return false;
        return Objects.equals(getExperience(), that.getExperience());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExperience());
    }
}
