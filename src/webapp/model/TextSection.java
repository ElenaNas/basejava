package webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection{
    private final String text;

    public TextSection(String text) {
        Objects.requireNonNull(text, "Text field can not be null");
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof TextSection that)) return false;
        return Objects.equals(getText(), that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText());
    }
}
