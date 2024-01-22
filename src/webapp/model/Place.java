package webapp.model;

import java.util.Objects;

public class Place {
    private final String placeName;
    private final String title;
    private final String description;

    public Place(String placeName, String title, String description) {
        this.placeName = placeName;
        this.title = title;
        this.description = description;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Place{" +
                "placeName='" + placeName + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Place place)) return false;
        return Objects.equals(getPlaceName(), place.getPlaceName()) && Objects.equals(getTitle(), place.getTitle()) && Objects.equals(getDescription(), place.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlaceName(), getTitle(), getDescription());
    }
}
