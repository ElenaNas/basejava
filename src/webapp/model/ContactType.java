package webapp.model;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public enum ContactType {
    @Expose
    MOBILE_PHONE_NUMBER("Mobile Phone Number"),

    @Expose
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("skype:" + value, value);
        }
    },

    @Expose
    EMAIL("Email") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("mailto:" + value, value);
        }
    },

    @Expose
    LINKEDIN_ACCOUNT("LinkedIn") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },

    @Expose
    GITHUB_ACCOUNT("GitHub") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },

    @Expose
    STACKOVERFLOW_ACCOUNT("Stackoverflow") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },

    @Expose
    HOME_PAGE("Home page") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    };

    @Expose
    private final String title;

    public String getTitle() {
        return title;
    }

    ContactType(String title) {
        this.title = title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return value == null ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }

    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}
