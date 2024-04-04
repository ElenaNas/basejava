package resumes.model;

public enum ContactType {
    MOBILE_PHONE_NUMBER("Номер мобильного телефона"),
    SKYPE("Skype"),
    EMAIL("Адрес электронной почты"),
    LINKEDIN_ACCOUNT("Профиль LinkedIn"),
    GITHUB_ACCOUNT("Профиль GitHub"),
    STACKOVERFLOW_ACCOUNT("Профиль stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private final String title;

    public String getTitle() {
        return title;
    }

    ContactType(String title) {
        this.title = title;
    }

}
